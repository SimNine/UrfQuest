package xyz.urffer.urfquest.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

import xyz.urffer.urfquest.client.Client;
import xyz.urffer.urfquest.server.commands.CommandPermissions;
import xyz.urffer.urfquest.shared.protocol.Message;
import xyz.urffer.urfquest.shared.protocol.Packet;
import xyz.urffer.urfquest.shared.protocol.messages.MessageDisconnect;

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
				Packet p = (Packet)in.readObject();
				p.setClientID(this.id);
				this.server.intakePacket(p);
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
		MessageDisconnect m = new MessageDisconnect();
		m.reason = reason;
		m.disconnectedClientID = this.id;
		
		Packet p = new Packet(this.id, m);
		server.intakePacket(p);
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
		Packet p = new Packet(server.getServerID(), m);
		
		if (socket == null) {
			client.processPacket(p);
		} else {
			try {
				out.writeObject(p);
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
