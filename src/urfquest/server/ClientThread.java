package urfquest.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import urfquest.shared.message.Message;

public class ClientThread implements Runnable {
	
	private Socket socket;
	private Server server;
	private int id;

	private ObjectOutputStream out;
	private ObjectInputStream in;
	
	private Thread t;
	private boolean stopped;

	public ClientThread(Server serv, Socket s, int id) {
		this.socket = s;
		this.server = serv;
		this.id = id;
		
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

	@Override
	public void run() {
		while (!stopped) {
			try {
				Message m = (Message)in.readObject();
				m.clientID = id;
				this.server.intakeMessage(m);
			} catch (IOException e) {
				System.out.println("client " + id + " disconnected");
				stopped = true;
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				System.out.println("Message class not found");
				e.printStackTrace();
			}
		}
		
		try {
			socket.close();
			System.out.println("connection closed");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void stop() throws IOException {
		stopped = true;
	}
	
	public void send(Message m) {
		try {
			out.writeObject(m);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public int getID() {
		return this.id;
	}
}
