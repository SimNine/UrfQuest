package server;

import java.io.IOException;
import java.net.Socket;

public class ClientThread {
	
	private Socket socket;
	private Server server;

	public ClientThread(Server serv, Socket s) {
		this.socket = s;
		this.server = server;
	}
	
	public void startThread() {
		
	}
	
	public void stopThread() throws IOException {
		socket.close();
	}
	
}
