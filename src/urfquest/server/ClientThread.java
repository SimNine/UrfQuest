package urfquest.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

import urfquest.client.Client;
import urfquest.server.commands.CommandPermissions;
import urfquest.shared.message.Message;
import urfquest.shared.message.MessageType;

public class ClientThread {
	
	private Server server;
	
	private Client client; // only used for locally attached clients

	private Socket socket;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	
	private int commandPermissions;
	
	public int id;
	
	private ClientThread(Server serv) {
		this.server = serv;
		this.id = IDGenerator.newID();
		this.setCommandPermissions(CommandPermissions.NORMAL);
	}

	public ClientThread(Server serv, Socket s) {
		this(serv);
		this.server.getLogger().info("new remote client has connected with id " + this.id);
		
		this.client = null;
		this.socket = s;
		try {
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public ClientThread(Server serv, Client c) {
		this(serv);
		this.server.getLogger().info("new local client added with id " + this.id);
		
		this.client = c;
		this.socket = null;
	}

	public void mainLoop() {
		while (true) {
			try {
				Message m = (Message)in.readObject();
				m.clientID = id;
				this.server.intakeMessage(m);
			} catch (SocketException e) {
				this.server.getLogger().info("Client " + id + " connection reset");
				this.beginDisconnectProcess("Client window closed");
				break;
				// e.printStackTrace();
			} catch (IOException e) {
				this.server.getLogger().warning("Client " + id + " disconnected with error");
				this.beginDisconnectProcess("A connection error has occurred");
				e.printStackTrace();
				break;
			} catch (ClassNotFoundException e) {
				this.server.getLogger().warning("Message class not found");
				e.printStackTrace();
			}
		}
	}
	
	private void beginDisconnectProcess(String reason) {
		Message m = new Message();
		m.type = MessageType.DISCONNECT_CLIENT;
		m.clientID = id;
		m.payload = reason;
		server.intakeMessage(m);
	}
	
	public void stop() {
		try {
			if (socket != null) {
				socket.close();
			} else {
				client = null;
			}
			this.server.getLogger().info("Client " + this.id + " connection closed");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void send(Message m) {
		if (socket == null) {
			client.processMessage(m);
		} else {
			try {
				out.writeObject(m);
			} catch (IOException e) {
				this.beginDisconnectProcess("A connection error has occurred");
				e.printStackTrace();
			}
		}
	}

	public int getCommandPermissions() {
		return commandPermissions;
	}

	public void setCommandPermissions(int commandPermissions) {
		this.commandPermissions = commandPermissions;
	}
}
