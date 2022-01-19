package urfquest.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import urfquest.Logger;
import urfquest.Logger.LogLevel;
import urfquest.Main;
import urfquest.client.Client;
import urfquest.server.entities.mobs.Player;
import urfquest.server.map.Map;
import urfquest.server.map.MapChunk;
import urfquest.server.state.State;
import urfquest.shared.ChatMessage;
import urfquest.shared.message.Constants;
import urfquest.shared.message.EntityType;
import urfquest.shared.message.Message;
import urfquest.shared.message.MessageType;

public class Server implements Runnable {
    
	private State game;
	
	private ServerSocket serverSocket = null;
	private List<Message> incomingMessages = Collections.synchronizedList(new ArrayList<Message>());
	private HashMap<Integer, ClientThread> clients = new HashMap<>();
	private UserMap userMap = new UserMap();
	
	private ArrayDeque<ChatMessage> chatMessages = new ArrayDeque<ChatMessage>();
	
	private Logger logger;
	
	public Server(int seed) {
		this.setGame(new State());
        this.getGame().setGameRunning(true);
        
        this.logger = new Logger(LogLevel.LOG_DEBUG, "SERVER");
        
        this.serverSocket = null;
	}

	public Server(int seed, int port) {
		this.setGame(new State());
        this.getGame().setGameRunning(true);
        
        this.logger = new Logger(LogLevel.LOG_DEBUG, "SERVER");
		
		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public Logger getLogger() {
		return this.logger;
	}
	
	public void intakeMessage(Message m) {
		incomingMessages.add(m);
	}

	// TODO: make this loop's thread sleep until a message is recieved
	@Override
	public void run() {
		// launch the master server listening thread
		new ServerListenerThread(this);
		
		while (true) {
			if (incomingMessages.size() > 0) {
				Message m = incomingMessages.remove(0);
				processMessage(m);
			}
		}
	}
	
	public void processMessage(Message m) {
		ClientThread c = clients.get(m.clientID);
		
		switch (m.type) {
			case PLAYER_REQUEST: {
				Main.server.getLogger().info(m.clientID + " - " + m.toString());
				// - Creates a player with the requested name
				// - Sends the newly created player to all clients
				// TODO: check if the requesting client already has an assigned player
				String playerName = m.entityName;
				Player p = game.createPlayer(playerName, c);
				userMap.addEntry(c.id, p.id, playerName);
				
				m = new Message();
				m.type = MessageType.ENTITY_INIT;
				m.entityName = playerName;
				m.entityType = EntityType.PLAYER;
				m.pos = p.getPos();
				m.clientID = c.id;
				m.entityID = p.id;
				this.sendMessageToAllClients(m);
				break;
			}
			case PLAYER_MOVE: {
				Main.server.getLogger().verbose(m.clientID + " - " + m.toString());
				// - Recieves a request from a client to move their player
				// - Tests if the move is allowed; if so, does the move
				Player movedPlayer = game.getPlayer(userMap.getPlayerIdFromClientId(m.clientID));
				movedPlayer.attemptIncrementPos(m.pos[0], m.pos[1]);
				break;
			}
			case MAP_REQUEST: {
				Main.server.getLogger().info(m.clientID + " - " + m.toString());
				// - Recieves a request from a client to load a new map
				// - Sends metadata of the requested map to the client - TODO
				// - Sends chunks nearby the player to the client
				// - Sends all entities currently on the map
				// TODO: narrow the number of entities that are sent
				
				Map map = game.getAllMaps().get(m.mapID);
				
//					m = new Message();
//					m.type = MessageType.MAP_METADATA;
				
				for (int x = -Constants.localMapRadius/2; x < Constants.localMapRadius/2; x++) {
					for (int y = -Constants.localMapRadius/2; y < Constants.localMapRadius/2; y++) {
						m = new Message();
						m.type = MessageType.CHUNK_LOAD;
						
						MapChunk chunk = map.getChunk(x, y);
						m.payload = chunk.getAllTileTypes();
						m.payload2 = chunk.getAllTileSubtypes();
						m.xyChunk[0] = x;
						m.xyChunk[1] = y;
						c.send(m);
					}
				}
				
				for (Player player : map.getPlayers().values()) {
					m = new Message();
					m.type = MessageType.ENTITY_INIT;
					m.entityType = EntityType.PLAYER;
					m.entityID = player.id;
					m.entityName = player.getName();
					m.pos = player.getPos();
					c.send(m);
				}
				break;
			}
			case CHUNK_LOAD: {
				Main.server.getLogger().debug(m.clientID + " - " + m.toString());
				// - Recieves a request from a client to load a chunk
				// - Sends the chunk data back to the client
				MapChunk chunk = game.getPlayer(userMap.getPlayerIdFromClientId(m.clientID)).getMap().getChunk(m.xyChunk[0], m.xyChunk[1]);
				if (chunk == null) {
					chunk = game.getPlayer(userMap.getPlayerIdFromClientId(m.clientID)).getMap().createChunk(m.xyChunk[0], m.xyChunk[1]);
				}
				m.payload = chunk.getAllTileTypes();
				m.payload2 = chunk.getAllTileSubtypes();
				c.send(m);
				break;
			}
			case DEBUG_PLAYER_INFO: {
				Main.server.getLogger().debug(m.clientID + " - " + m.toString());
				int playerID = userMap.getPlayerIdFromClientId(m.clientID);
				Player p = game.getPlayer(playerID);
				String playerPos = p.getCenter()[0] + "," + p.getCenter()[1];
				
				m = new Message();
				m.type = MessageType.DEBUG_PLAYER_INFO;
				m.payload = playerPos;
				c.send(m);
				break;
			}
			case CHAT_MESSAGE: {
				Main.server.getLogger().info(m.clientID + " - " + m.toString());
				int playerID = userMap.getPlayerIdFromClientId(m.clientID);
				Player p = game.getPlayer(playerID);
				
				ChatMessage chatMessage = (ChatMessage)m.payload;
				chatMessage.source = p.getName();
				
				chatMessages.addFirst(chatMessage);
				
				if (chatMessage.message.charAt(0) == '/') {
					CommandProcessor.processCommand(this, chatMessage.message, m.clientID);
				} else {
					m.entityName = p.getName();
					this.sendMessageToAllClients(m);
				}
				break;
			}
			default: {
				Main.server.getLogger().debug(m.clientID + " - " + m.toString());
				break;
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
		return this.game;
	}

	public void setGame(State game) {
		this.game = game;
	}
	
	public UserMap getUserMap() {
		return this.userMap;
	}
	
	public ArrayDeque<ChatMessage> getAllChatMessages() {
		return chatMessages;
	}

	private class ServerListenerThread implements Runnable {
		
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
			Main.server.getLogger().info("server listening on port: " + serverSocket.getLocalPort());
			while (true) {
				Socket socket = null;
			    try {
					socket = serverSocket.accept();
					ClientThread t = new ClientThread(s, socket);
					clients.put(t.id, t);
					
					// send a connection confirmation message
					Message m = new Message();
					m.type = MessageType.CONNECTION_CONFIRMED;
					m.clientID = t.id;
					m.mapID = game.getSurfaceMap().id;
					t.send(m);
				} catch (IOException e) {
					e.printStackTrace();
					System.exit(2);
				}
			}
		}
	}
	
	public void attachLocalClient(Client c) {
		ClientThread t = new ClientThread(this, c);
		clients.put(t.id, t);
		
		// send connection confirmation message
		Message m = new Message();
		m.type = MessageType.CONNECTION_CONFIRMED;
		m.clientID = t.id;
		m.mapID = game.getSurfaceMap().id;
		t.send(m);
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
