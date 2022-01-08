package urfquest.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

import urfquest.IDGenerator;
import urfquest.Main;
import urfquest.client.Client;
import urfquest.shared.message.Message;

public class ClientThread implements Runnable {
	
	private Server server;
	
	private Client client; // only used for locally attached clients

	private Socket socket;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	
	private Thread t;
	private boolean stopped;
	
	public int id;

	public ClientThread(Server serv, Socket s) {
		this.server = serv;
		this.id = IDGenerator.newID();
		Main.server.getLogger().info("new client has connected with id " + this.id);

		this.client = null;
		this.socket = s;
		try {
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.stopped = false;
		this.t = new Thread(this, "ClientThread");
		this.t.start();
	}
	
	public ClientThread(Server serv, Client c) {
		this.server = serv;
		this.id = IDGenerator.newID();
		Main.server.getLogger().info("new local client added with id " + this.id);
		
		this.client = c;
		this.socket = null;
		
		this.stopped = false;
	}

	@Override
	public void run() {
		while (!stopped) {
			try {
				Message m = (Message)in.readObject();
				m.clientID = id;
				this.server.intakeMessage(m);
			} catch (IOException e) {
				Main.server.getLogger().info("Client " + id + " disconnected");
				stopped = true;
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				Main.server.getLogger().warning("Message class not found");
				e.printStackTrace();
			}
		}
		
		try {
			socket.close();
			Main.server.getLogger().info("connection closed");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void stop() throws IOException {
		stopped = true;
	}
	
	public void send(Message m) {
		try {
			if (socket == null) {
				client.processMessage(m);
			} else {
				out.writeObject(m);
			}
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
