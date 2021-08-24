package urfquest.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import urfquest.Main;
import urfquest.server.map.Map;
import urfquest.server.map.MapChunk;
import urfquest.server.state.State;
import urfquest.shared.message.Message;
import urfquest.shared.message.MessageType;

public class Server {
    
	private State game;
	
	private Random random;
	
	private int port;
	private ServerSocket serverSocket = null;
	private HashMap<Integer, ClientThread> clients = new HashMap<>();
	private List<Message> incomingMessages = Collections.synchronizedList(new ArrayList<Message>());

	public Server(int seed, int port) {
		this.random = new Random(seed);
		this.port = port;
		
		this.setGame(new State());
        this.getGame().setGameRunning(true);
		
		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		// launch the master server listening thread
		new MasterThread(this);
	}
	
	public void intakeMessage(Message m) {
		incomingMessages.add(m);
	}
	
	// TODO: make this loop's thread sleep until a message is recieved
	public void processMessages() {
		while (true) {
			if (incomingMessages.size() > 0) {
				Message m = incomingMessages.remove(0);
				
				switch (m.type) {
				case PLAYER_MOVE:
					Main.logger.verbose(m.clientID + " - " + m.toString());
					// TODO: this handling is temporary. process before reflecting back to the client
					game.getPlayer(m.clientID).move(m.pos[0], m.pos[1]);
					break;
				case CHUNK_LOAD:
					Main.logger.debug(m.clientID + " - " + m.toString());
					MapChunk c = game.getPlayer(m.clientID).getMap().getChunk(m.xyChunk[0], m.xyChunk[1]);
					if (c == null) {
						c = game.getPlayer(m.clientID).getMap().createChunk(m.xyChunk[0], m.xyChunk[1]);
					}
					m.payload = c.getAllTileTypes();
					m.payload2 = c.getAllTileSubtypes();
					this.sendMessageToSingleClient(m, m.clientID);
					break;
				default:
					Main.logger.debug(m.clientID + " - " + m.toString());
					break;
				}
			}
		}
	}
	
	public void sendMessageToSingleClient(Message m, int id) {
		ClientThread c = clients.get(id);
		c.send(m);
	}
	
	public void sendMessageToAllClients(Message m) {
		
	}
	
	public State getGame() {
		return game;
	}

	public void setGame(State game) {
		this.game = game;
	}

	private class MasterThread implements Runnable {
		
		private Thread t;
		private Server s;
		
		public MasterThread(Server server) {
			this.s = server;
			this.t = new Thread(this, "Master server thread");
			this.t.start();
		}

		@Override
		public void run() {
			// while the game is running, continue listening on the given socket
			Main.logger.info("server listening on port: " + serverSocket.getLocalPort());
			while (true) {
				Socket socket = null;
			    try {
					socket = serverSocket.accept();
					int clientID = random.nextInt();
					Main.logger.info("new client has connected with id " + clientID);
					ClientThread t = new ClientThread(s, socket, clientID);
					clients.put(clientID, t);
					game.createPlayer(clientID);
					
					// send initial chunks of map
					Map surfaceMap = game.getSurfaceMap();
					for (int x = -1; x < 1; x++) {
						for (int y = -1; y < 1; y++) {
							Message m = new Message();
							m.type = MessageType.CHUNK_LOAD;
							
							MapChunk chunk = surfaceMap.getChunk(x, y);
							m.payload = chunk.getAllTileTypes();
							m.payload2 = chunk.getAllTileSubtypes();
							m.xyChunk[0] = x;
							m.xyChunk[1] = y;
							t.send(m);
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
					System.exit(2);
				}
			}
		}
	}
	
	public void shutdown() {
		// close server
		try {
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// close all sockets
		for (ClientThread t : clients.values()) {
			try {
				t.stop();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
