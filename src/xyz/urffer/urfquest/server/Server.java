package xyz.urffer.urfquest.server;

import java.awt.FlowLayout;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import javax.swing.JFrame;

import xyz.urffer.urfquest.Logger;
import xyz.urffer.urfquest.Main;
import xyz.urffer.urfquest.client.Client;
import xyz.urffer.urfquest.server.commands.Command;
import xyz.urffer.urfquest.server.commands.CommandPermissions;
import xyz.urffer.urfquest.server.commands.CommandProcessor;
import xyz.urffer.urfquest.server.entities.Entity;
import xyz.urffer.urfquest.server.entities.items.ItemStack;
import xyz.urffer.urfquest.server.entities.mobs.Player;
import xyz.urffer.urfquest.server.map.Map;
import xyz.urffer.urfquest.server.map.MapChunk;
import xyz.urffer.urfquest.server.monitoring.EntityInfoPanel;
import xyz.urffer.urfquest.server.monitoring.MapOverviewPanel;
import xyz.urffer.urfquest.server.monitoring.PlayerInfoPanel;
import xyz.urffer.urfquest.server.state.State;
import xyz.urffer.urfquest.shared.ChatMessage;
import xyz.urffer.urfquest.shared.Constants;
import xyz.urffer.urfquest.shared.MessageQueue;
import xyz.urffer.urfquest.shared.protocol.Message;
import xyz.urffer.urfquest.shared.protocol.Packet;
import xyz.urffer.urfquest.shared.protocol.messages.MessageChat;
import xyz.urffer.urfquest.shared.protocol.messages.MessageInitChunk;
import xyz.urffer.urfquest.shared.protocol.messages.MessageClientConnectionConfirmed;
import xyz.urffer.urfquest.shared.protocol.messages.MessagePlayerDebug;
import xyz.urffer.urfquest.shared.protocol.messages.MessageClientDisconnect;
import xyz.urffer.urfquest.shared.protocol.messages.MessageEntitySetMoveVector;
import xyz.urffer.urfquest.shared.protocol.messages.MessageEntitySetPos;
import xyz.urffer.urfquest.shared.protocol.messages.MessageInitMap;
import xyz.urffer.urfquest.shared.protocol.messages.MessageMobSetHeldItem;
import xyz.urffer.urfquest.shared.protocol.messages.MessageRequestPlayerSetMoveVector;
import xyz.urffer.urfquest.shared.protocol.messages.MessageRequestChunk;
import xyz.urffer.urfquest.shared.protocol.messages.MessageRequestMap;
import xyz.urffer.urfquest.shared.protocol.messages.MessageRequestPlayer;
import xyz.urffer.urfquest.shared.protocol.messages.MessageServerError;
import xyz.urffer.urfquest.shared.protocol.types.ItemType;
import xyz.urffer.urfquest.shared.protocol.types.MessageType;

public class Server {
	private long seed;
	private Random random;
	private int id;
    
	private State state;
	
	private ServerSocket serverSocket = null;
	private MessageQueue incomingPackets = new MessageQueue();
	private HashMap<Integer, ClientThread> clients = new HashMap<>();
	private UserMap userMap = new UserMap();
	private HashSet<String> opsList = new HashSet<String>();
	
	private ArrayDeque<ChatMessage> chatMessages = new ArrayDeque<ChatMessage>();
	
	private Logger logger;
	
	private Thread commandParserThread;
	
	private JFrame monitoringFrame = new JFrame();
	
	public Server(long seed) {
		this.seed = seed;
		this.random = new Random(seed);
		this.id = IDGenerator.newID();
		
        this.logger = new Logger(Main.debugLevel, "SERVER(" + this.id + ")");
		
		this.state = new State(this);
        
        this.serverSocket = null;
        
        this.logger.info("Initialized server with ID: " + this.id);
	}

	public Server(long seed, int port) {
		this(seed);
        
		// Try to load list of op-permission players from file
		File opsListFile = new File(Constants.FILE_OPS_LIST);
		if (opsListFile.exists()) {
			try {
				this.logger.info("Loading ops file");
				BufferedReader opsReader = new BufferedReader(new FileReader(opsListFile));
				String line = null;
				while ((line = opsReader.readLine()) != null) {
					opsList.add(line.strip());
				}
				opsReader.close();
			} catch (IOException e) {
				this.logger.error("Malformed ops file");
			}
		}
		
		// launch master listener
		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		// launch command line parser
		commandParserThread = new Thread(new Runnable() {
			public void run() {
				BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
				while (true) {
					try {
						String line = reader.readLine();
						if (line.length() == 0) {
							continue;
						}
						
						MessageChat m = new MessageChat();
						m.chatMessage = new ChatMessage("SERVER", line);
						
						Packet p = new Packet(id, m);
						incomingPackets.add(p);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});
		commandParserThread.start();
		
		// launch monitoring window
		monitoringFrame = new JFrame("ServerMonitor");
		monitoringFrame.setLayout(new FlowLayout());
		monitoringFrame.setVisible(true);
		monitoringFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		MapOverviewPanel mapMonitor = new MapOverviewPanel(this);
		monitoringFrame.add(mapMonitor);
		PlayerInfoPanel serverStats = new PlayerInfoPanel(this);
		monitoringFrame.add(serverStats);
		EntityInfoPanel serverEntityStats = new EntityInfoPanel(this);
		monitoringFrame.add(serverEntityStats);
		
		monitoringFrame.pack();
	}

	public void mainLoop() {
		this.logger.info("Main loop started");
		
		while (true) {
			long startTime = System.currentTimeMillis();
			this.tick();
			long endTime = System.currentTimeMillis();
			
			monitoringFrame.repaint();
			
			long remainingTime = Constants.MILLISECONDS_PER_TICK - (endTime - startTime);
			if (remainingTime > 0) {
				try {
					Thread.sleep(remainingTime);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void tick() {
		while (!incomingPackets.isEmpty()) {
			Packet m = incomingPackets.poll();
			this.processPacket(m);
		}
		
		this.state.tick();
	}
	
	public void tick(int numTicks) {
		for (int i = 0; i < numTicks; i++) {
			this.tick();
		}
	}
	
	public void initListenerThread() {
		new ServerListenerThread(this);
	}
	
	public void intakePacket(Packet m) {
		incomingPackets.add(m);
	}
	
	public void processPacket(Packet p) {
		ClientThread c = clients.get(p.getSenderID());
		if (p.getSenderID() == this.id) {
			if (p.getType() == MessageType.CHAT_MESSAGE) {
				// do nothing
			} else if (p.getType() == MessageType.DISCONNECT_CLIENT) {
				c = clients.get(((MessageClientDisconnect)p.getMessage()).disconnectedClientID);
			} else {
				this.getLogger().error("Non-chat message recieved from server console");
				return;
			}
		} else if (c == null) {
			this.getLogger().warning(p.getType() + " from client " + p.getSenderID() + " skipped; client not found");
			return;
		}
		
		// Log the message
		p.print(this.getLogger());
		
		switch (p.getType()) {
			case REQUEST_PLAYER: {
				// - Creates a player with the requested name
				// - Sends the newly created player to all clients
				// TODO: check if the requesting client already has an assigned player
				MessageRequestPlayer m = (MessageRequestPlayer)p.getMessage();

				String playerName = m.entityName;
				if (this.userMap.containsPlayerName(playerName)) {
					MessageServerError mse = new MessageServerError();
					mse.errorMessage = "A player with the name \"" + playerName + "\" already exists.";
					sendMessageToSingleClient(mse, c.id);
					break;
				}

				Player newPlayer = new Player(this, playerName, c.id);
				userMap.addEntry(c.id, newPlayer.id, newPlayer.getName());

				Map surfaceMap = this.state.getSurfaceMap();
				newPlayer.setPos(surfaceMap.getHomeCoords().toDouble(), surfaceMap.id);
				
				newPlayer.addItem(new ItemStack(this, ItemType.PICKAXE).id);
				newPlayer.addItem(new ItemStack(this, ItemType.HATCHET).id);
				newPlayer.addItem(new ItemStack(this, ItemType.SHOVEL).id);
				newPlayer.addItem(new ItemStack(this, ItemType.PISTOL).id);
				newPlayer.addItem(new ItemStack(this, ItemType.COSMIC_RUNE).id);
				newPlayer.addItem(new ItemStack(this, ItemType.GRENADE_ITEM).id);
				newPlayer.addItem(new ItemStack(this, ItemType.LAW_RUNE).id);
				newPlayer.addItem(new ItemStack(this, ItemType.RPG).id);
				newPlayer.addItem(new ItemStack(this, ItemType.KEY).id);
				
				if (opsList.contains(playerName)) {
					c.setCommandPermissions(CommandPermissions.OP);
				}
				break;
			}
			case REQUEST_PLAYER_USE_HELD_ITEM: {
				Player player = (Player)state.getEntity(userMap.getPlayerIdFromClientId(c.id));
				player.useSelectedItem();
				break;
			}
			case REQUEST_PLAYER_SET_MOVE_VECTOR: {
				// - Recieves a request from a client to set the velocity and direction of their player
				MessageRequestPlayerSetMoveVector m = (MessageRequestPlayerSetMoveVector)p.getMessage();
				
				Player player = (Player)state.getEntity(userMap.getPlayerIdFromClientId(c.id));
				player.setMovementVector(m.vector);
				break;
			}
			case MOB_SET_HELD_ITEM: {
				MessageMobSetHeldItem m = (MessageMobSetHeldItem)p.getMessage();
				
				Player player = (Player)state.getEntity(m.entityID);
				player.setSelectedInventoryIndex(m.setHeldSlot);
				break;
			}
			case REQUEST_MAP: {
				// - Recieves a request from a client to load a new map
				// - Sends metadata of the requested map to the client - TODO
				// - Sends chunks nearby the player to the client
				// - Sends all entities currently on the map
				// TODO: narrow the number of entities that are sent
				MessageRequestMap m = (MessageRequestMap)p.getMessage();
				
				Map map = state.getMapByID(m.mapID);
				
				MessageInitMap mmi = new MessageInitMap();
				mmi.mapID = map.id;
				sendMessageToSingleClient(mmi, c.id);

				// Send a message to init, position, and velocity every entity
				for (Entity e : state.getAllEntities()) {
					Message message = e.initMessage();
					sendMessageToSingleClient(message, c.id);
					
					MessageEntitySetPos mesp = new MessageEntitySetPos();
					mesp.entityID = e.id;
					mesp.mapID = e.getMapID();
					mesp.pos = e.getPos();
					sendMessageToSingleClient(mesp, c.id);
					
					MessageEntitySetMoveVector mesmv = new MessageEntitySetMoveVector();
					mesmv.entityID = e.id;
					mesmv.vector = e.getMovementVector();
					sendMessageToSingleClient(mesmv, c.id);
				}
				break;
			}
			case REQUEST_CHUNK: {
				// - Recieves a request from a client to load a chunk
				// - Sends the chunk data back to the client
				MessageRequestChunk m = (MessageRequestChunk)p.getMessage();
				
				MessageInitChunk mci = new MessageInitChunk();
				MapChunk chunk = ((Player)state.getEntity(userMap.getPlayerIdFromClientId(c.id))).getMap().getChunk(m.xyChunk);
				if (chunk == null) {
					chunk = ((Player)state.getEntity(userMap.getPlayerIdFromClientId(c.id))).getMap().createChunk(m.xyChunk);
				}
				mci.xyChunk = m.xyChunk;
				mci.tiles = chunk.getAllTiles();
				sendMessageToSingleClient(mci, c.id);
				break;
			}
			case DEBUG_PLAYER_INFO: {
				// MessageDebugPlayer m = (MessageDebugPlayer)p.getMessage();
				
				int playerID = userMap.getPlayerIdFromClientId(c.id);
				Player player = (Player)state.getEntity(playerID);
				String playerPos = player.getCenter().x + "," + player.getCenter().y;
				
				MessagePlayerDebug mdp = new MessagePlayerDebug();
				mdp.playerPosString = playerPos;
				sendMessageToSingleClient(mdp, c.id);
				break;
			}
			case CHAT_MESSAGE: {
				MessageChat m = (MessageChat)p.getMessage();
				
				ChatMessage chatMessage = m.chatMessage;
				if (p.getSenderID() != this.id) {
					int playerID = userMap.getPlayerIdFromClientId(p.getSenderID());
					Player player = (Player)state.getEntity(playerID);
					chatMessage.source = player.getName();
				}
				chatMessages.addFirst(chatMessage);
				
				if (chatMessage.message.charAt(0) == Command.COMMAND_PREFIX) {
					CommandProcessor.processCommand(this, chatMessage.message, c);
				} else {
					this.sendMessageToAllClients(m);
				}
				break;
			}
			case DISCONNECT_CLIENT: {
				// - Recieves a request from either this client or the server to disconnect
				// - Cleans up the client's resources
				MessageClientDisconnect m = (MessageClientDisconnect)p.getMessage();
				
				int playerID = userMap.getPlayerIdFromClientId(m.disconnectedClientID);
				String playerName = userMap.getPlayerNameFromClientId(m.disconnectedClientID);
				
				// If the sending client is trying to disconnect someone other than themself, and is not the server,
				// show warning and do nothing
				if (p.getSenderID() != m.disconnectedClientID &&
					p.getSenderID() != this.id) {
					logger.warning("Client " + p.getSenderID() + " sent a MessageDisconnect with non-self target, Client " + m.disconnectedClientID);
					break;
				}
				
				// If client never successfully requested a player, do nothing
				if (playerName == null) {
					logger.warning("Client " + p.getSenderID() + " tried to disconnect without ever being assigned a player");
					break;
				}
				
				// TODO: this is not sustainable design. find a way to clean up entites correctly
				// Clean up this client's Player
				Player player = (Player)this.state.getEntity(playerID);
				this.state.removeEntity(playerID);
				player.destroy();
				
				// Remove this client from the server state
				sendMessageToSingleClient(m, m.disconnectedClientID);
				this.clients.remove(m.disconnectedClientID);
				this.userMap.removeByClientId(m.disconnectedClientID);
				c.stop();
				
				// Alert all other clients that this client has been disconnected
				MessageClientDisconnect md = new MessageClientDisconnect();
				md.reason = playerName + " has been disconnected";
				md.disconnectedClientID = m.disconnectedClientID;
				this.sendMessageToAllClients(md);
				break;
			}
			default: {
				break;
			}
		}
	}
	
	public void sendMessageToClientOrServer(Message m, int id) {
		if (id == this.id) {
			if (m.getType() == MessageType.CHAT_MESSAGE) {
				System.out.println(((MessageChat)m).chatMessage);
			} else {
				this.intakePacket(new Packet(this.id, m));
			}
		} else {
			sendMessageToSingleClient(m, id);
		}
	}
	
	public void sendMessageToSingleClient(Message m, int id) {
		ClientThread c = clients.get(id);
		c.send(m);
	}
	
	public void sendMessageToAllClients(Message m) {
		for (int clientID : clients.keySet()) {
			sendMessageToSingleClient(m, clientID);
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
		
		// send disconnect message to every client
		MessageClientDisconnect m = new MessageClientDisconnect();
		m.reason = "The server has shut down";
		
		// close all sockets
		for (ClientThread t : clients.values()) {
			m.disconnectedClientID = t.id;
			t.send(m);
			t.stop();
		}
	}
	
	public ServerSocket getServerSocket() {
		return this.serverSocket;
	}
	
	public int getServerID() {
		return this.id;
	}
	
	public Logger getLogger() {
		return this.logger;
	}
	
	/*
	 * Methods for managing clients
	 */
	
	public void attachLocalClient(Client c) {
		attachLocalClient(c, CommandPermissions.NORMAL);
	}
	
	public void attachLocalClient(Client c, int commandPermissions) {
		ClientThread t = new ClientThread(this, c);
		clients.put(t.id, t);
		t.setCommandPermissions(commandPermissions);
		
		// send connection confirmation message
		MessageClientConnectionConfirmed m = new MessageClientConnectionConfirmed();
		m.clientID = t.id;
		m.startingMapID = state.getSurfaceMap().id;
		sendMessageToSingleClient(m, t.id);
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
