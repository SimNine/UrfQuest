package urfquest.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import urfquest.IDGenerator;
import urfquest.Main;
import urfquest.server.entities.mobs.Player;
import urfquest.server.map.Map;
import urfquest.server.map.MapChunk;
import urfquest.server.state.State;
import urfquest.shared.message.Constants;
import urfquest.shared.message.EntityType;
import urfquest.shared.message.Message;
import urfquest.shared.message.MessageType;

public class Server {
    
	private State game;
	
	private int port;
	private ServerSocket serverSocket = null;
	private HashMap<Integer, ClientThread> clients = new HashMap<>();
	private List<Message> incomingMessages = Collections.synchronizedList(new ArrayList<Message>());

	public Server(int seed, int port) {
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
				case PLAYER_REQUEST:
					Main.logger.info(m.clientID + " - " + m.toString());
					// - Creates a player with the requested name
					// - Sends the newly created player to all clients
					// TODO: check if the requesting client already has an assigned player
					String playerName = m.entityName;
					int clientID = m.clientID;
					Player p = game.createPlayer(m.clientID, playerName);
					m = new Message();
					m.type = MessageType.ENTITY_INIT;
					m.entityName = playerName;
					m.entityType = EntityType.PLAYER;
					m.pos = p.getPos();
					m.clientID = clientID;
					m.entityID = clientID;
					this.sendMessageToAllClients(m);
					break;
				case PLAYER_MOVE:
					// - Recieves a request from a client to move their player
					// - Tests if the move is allowed; if so, does the move
					Main.logger.verbose(m.clientID + " - " + m.toString());
					game.getPlayer(m.clientID).attemptMove(m.pos[0], m.pos[1]);
					break;
				case CHUNK_LOAD:
					// - Recieves a request from a client to load a chunk
					// - Sends the chunk data back to the client
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
		for (Entry<?, ClientThread> e : clients.entrySet()) {
			e.getValue().send(m);
		}
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
					int clientID = IDGenerator.newID();
					Main.logger.info("new client has connected with id " + clientID);
					ClientThread t = new ClientThread(s, socket, clientID);
					clients.put(clientID, t);
					
					// send a connection confirmation message
					Message m = new Message();
					m.type = MessageType.CONNECTION_CONFIRMED;
					m.clientID = t.getID();
					t.send(m);
					
					// send initial chunks of map
					Map surfaceMap = game.getSurfaceMap();
					for (int x = -Constants.localMapRadius/2; x < Constants.localMapRadius/2; x++) {
						for (int y = -Constants.localMapRadius/2; y < Constants.localMapRadius/2; y++) {
							m = new Message();
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
