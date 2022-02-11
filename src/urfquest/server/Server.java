package urfquest.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Random;

import urfquest.IDGenerator;
import urfquest.Logger;
import urfquest.Logger.LogLevel;
import urfquest.client.Client;
import urfquest.server.entities.mobs.Player;
import urfquest.server.map.Map;
import urfquest.server.map.MapChunk;
import urfquest.server.state.State;
import urfquest.shared.ChatMessage;
import urfquest.shared.Constants;
import urfquest.shared.message.EntityType;
import urfquest.shared.message.Message;
import urfquest.shared.message.MessageType;

public class Server {
	private long seed;
	private Random random;
	private int id;
    
	private State state;
	
	private ServerSocket serverSocket = null;
	private MessageQueue incomingMessages = new MessageQueue();
	private HashMap<Integer, ClientThread> clients = new HashMap<>();
	private UserMap userMap = new UserMap();
	
	private ArrayDeque<ChatMessage> chatMessages = new ArrayDeque<ChatMessage>();
	
	private Logger logger;
	
	private Thread commandParserThread;
	
	public Server(long seed) {
		this.seed = seed;
		this.random = new Random(seed);
		this.id = IDGenerator.newID();
		
		this.setState(new State(this));
        this.getState().setGameRunning(true);
        
        this.logger = new Logger(LogLevel.LOG_DEBUG, "SERVER");
        
        this.serverSocket = null;
	}

	public Server(long seed, int port) {
		this(seed);
		
		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		commandParserThread = new Thread(new Runnable() {
			public void run() {
				BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
				while (true) {
					try {
						String line = reader.readLine();
						
						Message m = new Message();
						m.clientID = id;
						m.type = MessageType.CHAT_MESSAGE;
						m.payload = new ChatMessage("SERVER", line);
						incomingMessages.add(m);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});
		commandParserThread.start();
	}
	
	public int getServerID() {
		return this.id;
	}
	
	public Logger getLogger() {
		return this.logger;
	}
	
	public void intakeMessage(Message m) {
		incomingMessages.add(m);
	}

	public void mainLoop() {
		this.logger.all("Main loop started");
		while (true) {
			Message m = incomingMessages.poll();
			this.processMessage(m);
		}
	}
	
	public void initListenerThread() {
		new ServerListenerThread(this);
	}
	
	public void processMessage(Message m) {
		ClientThread c = clients.get(m.clientID);
		
		switch (m.type) {
			case PLAYER_REQUEST: {
				this.getLogger().info(m.clientID + " - " + m.toString());
				// - Creates a player with the requested name
				// - Sends the newly created player to all clients
				// TODO: check if the requesting client already has an assigned player
				String playerName = m.entityName;
				Player newPlayer = new Player(this, this.state, 
											  this.state.getSurfaceMap(), 
											  this.state.getSurfaceMap().getHomeCoords()[0], 
											  this.state.getSurfaceMap().getHomeCoords()[1], 
											  playerName, c);
				this.state.addPlayer(newPlayer);
				this.state.getSurfaceMap().addPlayer(newPlayer);
				userMap.addEntry(c.id, newPlayer.id, playerName);
				break;
			}
			case PLAYER_MOVE: {
				this.getLogger().verbose(m.clientID + " - " + m.toString());
				// - Recieves a request from a client to move their player
				// - Tests if the move is allowed; if so, does the move
				Player movedPlayer = state.getPlayer(userMap.getPlayerIdFromClientId(m.clientID));
				movedPlayer.attemptIncrementPos(m.pos[0], m.pos[1]);
				break;
			}
			case MAP_REQUEST: {
				this.getLogger().info(m.clientID + " - " + m.toString());
				// - Recieves a request from a client to load a new map
				// - Sends metadata of the requested map to the client - TODO
				// - Sends chunks nearby the player to the client
				// - Sends all entities currently on the map
				// TODO: narrow the number of entities that are sent
				
				Map map = state.getAllMaps().get(m.mapID);
				
//					m = new Message();
//					m.type = MessageType.MAP_METADATA;
				
				for (int x = -Constants.CLIENT_CACHED_MAP_DIAMETER/2; x < Constants.CLIENT_CACHED_MAP_DIAMETER/2; x++) {
					for (int y = -Constants.CLIENT_CACHED_MAP_DIAMETER/2; y < Constants.CLIENT_CACHED_MAP_DIAMETER/2; y++) {
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

				// TODO: OtherPlayerMovementTest's second test case occasionally fails here.
				// When this happens, the loop below does not iterate, even though there are players
				// on the map. This causes the existing player to not be found on the new client.
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
				this.getLogger().debug(m.clientID + " - " + m.toString());
				// - Recieves a request from a client to load a chunk
				// - Sends the chunk data back to the client
				MapChunk chunk = state.getPlayer(userMap.getPlayerIdFromClientId(m.clientID)).getMap().getChunk(m.xyChunk[0], m.xyChunk[1]);
				if (chunk == null) {
					chunk = state.getPlayer(userMap.getPlayerIdFromClientId(m.clientID)).getMap().createChunk(m.xyChunk[0], m.xyChunk[1]);
				}
				m.payload = chunk.getAllTileTypes();
				m.payload2 = chunk.getAllTileSubtypes();
				c.send(m);
				break;
			}
			case DEBUG_PLAYER_INFO: {
				this.getLogger().debug(m.clientID + " - " + m.toString());
				int playerID = userMap.getPlayerIdFromClientId(m.clientID);
				Player p = state.getPlayer(playerID);
				String playerPos = p.getCenter()[0] + "," + p.getCenter()[1];
				
				m = new Message();
				m.type = MessageType.DEBUG_PLAYER_INFO;
				m.payload = playerPos;
				c.send(m);
				break;
			}
			case CHAT_MESSAGE: {
				this.getLogger().info(m.clientID + " - " + m.toString());
				
				ChatMessage chatMessage = (ChatMessage)m.payload;
				if (m.clientID == this.id) {
					chatMessage.source = "SERVER";
				} else {
					int playerID = userMap.getPlayerIdFromClientId(m.clientID);
					Player p = state.getPlayer(playerID);
					chatMessage.source = p.getName();
				}
				chatMessages.addFirst(chatMessage);
				
				if (chatMessage.message.charAt(0) == '/') {
					CommandProcessor.processCommand(this, chatMessage.message, c);
				} else {
					this.sendMessageToAllClients(m);
				}
				break;
			}
			default: {
				this.getLogger().debug(m.clientID + " - " + m.toString());
				break;
			}
		}
	}
	
	public void sendMessageToClientOrServer(Message m, int id) {
		if (id == this.id) {
			System.out.println(((ChatMessage)m.payload).message);
		} else {
			sendMessageToSingleClient(m, id);
		}
	}
	
	public void sendMessageToSingleClient(Message m, int id) {
		ClientThread c = clients.get(id);
		c.send(m);
	}
	
	public void sendMessageToAllClients(Message m) {
		for (ClientThread c : clients.values()) {
			c.send(m);
		}
	}
	
	public State getState() {
		return this.state;
	}

	public void setState(State state) {
		this.state = state;
	}
	
	public UserMap getUserMap() {
		return this.userMap;
	}
	
	public ArrayDeque<ChatMessage> getAllChatMessages() {
		return chatMessages;
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
	
	public ServerSocket getServerSocket() {
		return this.serverSocket;
	}
	
	/*
	 * Methods for managing clients
	 */
	
	public void attachLocalClient(Client c) {
		ClientThread t = new ClientThread(this, c);
		clients.put(t.id, t);
		
		// send connection confirmation message
		Message m = new Message();
		m.type = MessageType.CONNECTION_CONFIRMED;
		m.clientID = t.id;
		m.mapID = state.getSurfaceMap().id;
		t.send(m);
	}
	
	public void addClient(int clientID, ClientThread clientThread) {
		this.clients.put(clientID, clientThread);
	}
	
	public ClientThread getClient(int clientID) {
		return clients.get(clientID);
	}
	
	/*
	 * Methods for generating random values
	 */
	
	public long seed() {
		return this.seed;
	}
	
	public double randomDouble() {
		return this.random.nextDouble();
	}
	
	public long randomLong() {
		return this.random.nextLong();
	}
}
