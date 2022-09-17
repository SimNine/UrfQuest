package xyz.urffer.urfquest.server;

import java.io.IOException;
import java.net.Socket;

import xyz.urffer.urfquest.shared.protocol.messages.MessageConnectionConfirmed;

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
				Thread thread = new Thread(new Runnable() {
					@Override
					public void run() {
						t.mainLoop();
					}
				});
				thread.setName("ClientThread-" + t.id);
				thread.start();
				this.s.addClient(t.id, t);
				
				// send a connection confirmation message
				MessageConnectionConfirmed m = new MessageConnectionConfirmed();
				m.startingMapID = this.s.getState().getSurfaceMap().id;
				m.clientID = t.id;
				this.s.sendMessageToSingleClient(m, t.id);
//				t.send(m);
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(2);
			}
		}
	}
}
