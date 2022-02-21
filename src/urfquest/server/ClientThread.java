package urfquest.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

import urfquest.client.Client;
import urfquest.server.commands.CommandPermissions;
import urfquest.shared.message.Message;

public class ClientThread {
	
	private Server server;
	
	private Client client; // only used for locally attached clients

	private Socket socket;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	
	private boolean stopped;
	
	private int commandPermissions;
	
	public int id;
	
	private ClientThread(Server serv) {
		this.server = serv;
		this.id = IDGenerator.newID();
		this.setCommandPermissions(CommandPermissions.NORMAL);
		this.stopped = false;
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
		while (!stopped) {
			try {
				Message m = (Message)in.readObject();
				m.clientID = id;
				this.server.intakeMessage(m);
			} catch (SocketException e) {
				this.server.getLogger().info("Client " + id + " connection reset");
				stopped = true;
				// e.printStackTrace();
			} catch (IOException e) {
				this.server.getLogger().warning("Client " + id + " disconnected with error");
				stopped = true;
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				this.server.getLogger().warning("Message class not found");
				e.printStackTrace();
			}
		}
		
		try {
			socket.close();
			this.server.getLogger().info("Client " + this.id + " connection closed");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void stop() throws IOException {
		stopped = true;
	}
	
	public void send(Message m) {
		if (socket == null) {
			client.processMessage(m);
		} else {
			try {
				out.writeObject(m);
			} catch (SocketException e) {
				// TODO: look into the appropriate way to handle disconnection
				try {
					this.stop();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			} catch (IOException e) {
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
