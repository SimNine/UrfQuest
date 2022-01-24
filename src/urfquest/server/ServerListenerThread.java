package urfquest.server;

import java.io.IOException;
import java.net.Socket;

import urfquest.shared.message.Message;
import urfquest.shared.message.MessageType;

class ServerListenerThread implements Runnable {
	
	private Thread t;
	private Server s;
	
	public ServerListenerThread(Server server) {
		this.s = server;
		this.t = new Thread(this, "ServerListenerThread");
		this.t.start();
	}

	@Override
	public void run() {
		// while the game is running, continue listening on the given socket
		this.s.getLogger().info("server listening on port: " + s.getServerSocket().getLocalPort());
		while (true) {
			Socket socket = null;
		    try {
				socket = this.s.getServerSocket().accept();
				ClientThread t = new ClientThread(s, socket);
				this.s.addClient(t.id, t);
				
				// send a connection confirmation message
				Message m = new Message();
				m.type = MessageType.CONNECTION_CONFIRMED;
				m.clientID = t.id;
				m.mapID = this.s.getState().getSurfaceMap().id;
				t.send(m);
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(2);
			}
		}
	}
}
