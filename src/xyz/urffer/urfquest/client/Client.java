package xyz.urffer.urfquest.client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayDeque;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import xyz.urffer.urfquest.Logger;
import xyz.urffer.urfquest.Main;
import xyz.urffer.urfquest.client.entities.Entity;
import xyz.urffer.urfquest.client.entities.items.ItemStack;
import xyz.urffer.urfquest.client.entities.mobs.Chicken;
import xyz.urffer.urfquest.client.entities.mobs.Cyclops;
import xyz.urffer.urfquest.client.entities.mobs.NPCHuman;
import xyz.urffer.urfquest.client.entities.mobs.Player;
import xyz.urffer.urfquest.client.entities.projectiles.Bullet;
import xyz.urffer.urfquest.client.entities.projectiles.Explosion;
import xyz.urffer.urfquest.client.entities.projectiles.Rocket;
import xyz.urffer.urfquest.client.map.Map;
import xyz.urffer.urfquest.client.state.State;
import xyz.urffer.urfquest.server.Server;
import xyz.urffer.urfquest.shared.ChatMessage;
import xyz.urffer.urfquest.shared.protocol.Message;
import xyz.urffer.urfquest.shared.protocol.Packet;
import xyz.urffer.urfquest.shared.protocol.messages.MessageChat;
import xyz.urffer.urfquest.shared.protocol.messages.MessageInitChunk;
import xyz.urffer.urfquest.shared.protocol.messages.MessageClientConnectionConfirmed;
import xyz.urffer.urfquest.shared.protocol.messages.MessageClientDisconnect;
import xyz.urffer.urfquest.shared.protocol.messages.MessageEntityDestroy;
import xyz.urffer.urfquest.shared.protocol.messages.MessageInitMob;
import xyz.urffer.urfquest.shared.protocol.messages.MessageInitItem;
import xyz.urffer.urfquest.shared.protocol.messages.MessageEntitySetMoveVector;
import xyz.urffer.urfquest.shared.protocol.messages.MessageEntitySetPos;
import xyz.urffer.urfquest.shared.protocol.messages.MessageInitMap;
import xyz.urffer.urfquest.shared.protocol.messages.MessageInitPlayer;
import xyz.urffer.urfquest.shared.protocol.messages.MessageInitProjectile;
import xyz.urffer.urfquest.shared.protocol.messages.MessageItemSetOwner;
import xyz.urffer.urfquest.shared.protocol.messages.MessageMobSetHeldItem;
import xyz.urffer.urfquest.shared.protocol.messages.MessageRequestMap;
import xyz.urffer.urfquest.shared.protocol.messages.MessageRequestPlayer;
import xyz.urffer.urfquest.shared.protocol.messages.MessageServerError;
import xyz.urffer.urfquest.shared.protocol.messages.MessageTileSet;
import xyz.urffer.urfutils.math.PairInt;

public class Client {
	
	private State state;
	
	private Server server;
	
	private Socket socket;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	
	private ArrayDeque<ChatMessage> chatMessages = new ArrayDeque<ChatMessage>();
	
	private Logger logger;
	
	private int clientID;
	private String playerName = "default";

	// for getting graphics properties
    public JFrame frame;
	public boolean isFullscreen;
	private QuestPanel panel;
	private PairInt initWindowDims;
	private PairInt initWindowPos;
	
	private Client(String playerName) {
		this.state = new State(this);
		this.logger = new Logger(Main.debugLevel, "CLIENT(-)");
		this.playerName = playerName;
	}
	
	public Client(Server server, String playerName) {
		this(playerName);
		
		this.server = server;
		this.socket = null;
	}
	
	public Client(Socket socket, String playerName, PairInt initWindowDims, PairInt initWindowPos) {
		this(playerName);
		
		this.initWindowDims = initWindowDims;
		this.initWindowPos = initWindowPos;
		
		// initialize streams on the socket
		this.server = null;
		this.socket = socket;
		try {
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
			
			this.getLogger().debug("Client: initialized streams");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void mainLoop() {
		this.logger.info("Main loop started");
		try {
			while (true) {
				Packet p = (Packet)in.readObject();
				processPacket(p);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public State getState() {
		return state;
	}
	
	public Logger getLogger() {
		return this.logger;
	}
	
	public int getClientID() {
		return this.clientID;
	}
	
	public void processPacket(Packet p) {
		p.print(this.getLogger());
		
		switch (p.getType()) {
			case PING: {
				break;
			}
			case CONNECTION_CONFIRMED: {
				// - Assigns this client its clientID
				// - Informs this client of the surface map's ID
				// - Sends a request to the server to load the current map
				// - Sends a request to the server to create a player
				MessageClientConnectionConfirmed m = (MessageClientConnectionConfirmed)p.getMessage();
				
				this.clientID = m.clientID;
				this.logger.setPrefix("CLIENT(" + this.clientID + ")");
				
				int surfaceMapID = m.startingMapID;
				
				MessageRequestMap mrm = new MessageRequestMap();
				mrm.mapID = surfaceMapID;
				this.send(mrm);
				
				MessageRequestPlayer mrp = new MessageRequestPlayer();
				mrp.entityName = this.playerName;
				this.send(mrp);
				break;
			}
			case INIT_PROJECTILE: {
				// - Initializes a projectile-type entity
				
				MessageInitProjectile mip = (MessageInitProjectile)p.getMessage();
				switch (mip.projectileType) {
					case BULLET: {
						Bullet bullet = new Bullet(this, mip.entityID, mip.sourceEntityID);
						state.addEntity(bullet);
						break;
					}
					case ROCKET: {
						Rocket rocket = new Rocket(this, mip.entityID, mip.sourceEntityID);
						state.addEntity(rocket);
						break;
					}
					case EXPLOSION: {
						Explosion explosion = new Explosion(this, mip.entityID, mip.sourceEntityID);
						state.addEntity(explosion);
						break;
					}
					default: {
						break;
					}
				}
				
				break;
			}
			case INIT_MOB: {
				// - Initializes an entity of the given type
				// -- Adds it to the current state's entity pool but does not put it on a map
				
				MessageInitMob m = (MessageInitMob)p.getMessage();
				switch (m.mobType) {
					case CHICKEN: {
						Chicken chicken = new Chicken(this, m.entityID);
						state.addEntity(chicken);
						break;
					}
					case CYCLOPS: {
						Cyclops cyclops = new Cyclops(this, m.entityID);
						state.addEntity(cyclops);
						break;
					}
					case NPC_HUMAN: {
						NPCHuman npc = new NPCHuman(this, m.entityID, m.mobName);
						state.addEntity(npc);
						break;
					}
					default: {
						break;
					}
				}
				break;
			}
			case INIT_PLAYER: {
				// - Initializes a player
				// -- Assign it to this client
				// -- Initialize this client's frontend
				
				// If this entity is not on the current map, do nothing
				// TODO: must implement MAP_METADATA first
				// if (m.mapID != state.getCurrentMap().id) {
				// 		return;
				// }
				MessageInitPlayer m = (MessageInitPlayer)p.getMessage();
				
				Player player = new Player(this, m.entityID, m.entityName);
				this.state.addEntity(player);
				
				if (m.clientOwnerID == this.clientID) {
					state.setPlayer(player);
					if (this.server == null) {
						this.initFrontend();
						state.getCurrentMap().requestMissingChunks();
					}
				}
				break;
			}
			case INIT_ITEM: {
				// - Initializes an item
				// -- Spawns in (very briefly) at the origin of the surface map
				// TODO: improve that
				MessageInitItem mii = (MessageInitItem)p.getMessage();
				
				ItemStack item = new ItemStack(this, mii.entityID, mii.itemType, mii.stacksize, mii.durability);
				this.state.addEntity(item);
				
				break;
			}
			case ITEM_SET_OWNER: {
				// - Sets the position or owner of an item
				MessageItemSetOwner miso = (MessageItemSetOwner)p.getMessage();
				
				ItemStack item = (ItemStack)state.getEntity(miso.entityID);
				Player owner = (Player)state.getEntity(miso.entityOwnerID);
				
				// Sanity check
				if (item == null || owner == null) {
					break;
				}
				
				// Set the owner of the item
				owner.addItem(item);
	
//				// TODO: look into how to do this properly
//				if (misp.mapID != -1) {
////					Map map = state.getMap(misp.mapID);
//					Map map = state.getCurrentMap();
//					map.getItem(misp.entityID).setPos(misp.pos);
//				} else {
////					Item item = state.getItem(misp.entityID);
//					Map map = state.getCurrentMap();
//					ItemStack item = map.getItem(misp.entityID);
//					map.removeItem(item);
//					Player newOwner = map.getPlayer(misp.entityOwnerID);
//					newOwner.addItem(item);
//				}
				
				break;
			}
			case INIT_CHUNK: {
				// - Loads the payloads of this message into the specified chunk
				MessageInitChunk m = (MessageInitChunk)p.getMessage();
				
				state.getCurrentMap().setChunk(m.xyChunk, m.tiles);
				break;
			}
			case ENTITY_SET_POS: {
				// - Sets the position of the given entity
				MessageEntitySetPos m = (MessageEntitySetPos)p.getMessage();
				
				Entity e = state.getEntity(m.entityID);
				if (e != null) { // Sanity check
					// If the destination isn't the current map, do nothing
					if (m.mapID != state.getCurrentMap().id) {
						break;
					}
					
					// If this entity is not yet on the current map, place it
					if (state.getCurrentMap().getEntity(e.id) == null) {
						state.getCurrentMap().addEntity(e);
					}
					
					// Set the position on the map of this entity
					e.setPos(m.pos);
				}
				break;
			}
			case ENTITY_SET_MOVE_VECTOR: {
				MessageEntitySetMoveVector m = (MessageEntitySetMoveVector)p.getMessage();
				
				Entity e = state.getEntity(m.entityID);
				if (e != null) {
					e.setMovementVector(m.vector);
				} else {
					System.err.println("Entity not found: " + m.entityID);
				}
				break;
			}
			case MOB_SET_HELD_ITEM: {
				// - Sets the held item of the specified mob
				MessageMobSetHeldItem m = (MessageMobSetHeldItem)p.getMessage();
				
				Player player = (Player)state.getEntity(m.entityID);
				if (player != null) {
					player.setSelectedInventoryIndex(m.setHeldSlot, false);
				}
				
				break;
			}
			case INIT_MAP: {
				// - Loads metadata about the current map (id, climate, etc)
				MessageInitMap m = (MessageInitMap)p.getMessage();
				
				state.createNewMap(m.mapID);
				break;
			}
			case DEBUG_PLAYER_INFO: {
				break;
			}
			case CHAT_MESSAGE: {
				MessageChat m = (MessageChat)p.getMessage();
				
				chatMessages.addFirst(m.chatMessage);
				break;
			}
			case SERVER_ERROR: {
				MessageServerError m = (MessageServerError)p.getMessage();
				
				JOptionPane.showMessageDialog(new JFrame(), m.errorMessage);
				System.exit(1);
				break;
			}
			case DISCONNECT_CLIENT: {
				MessageClientDisconnect m = (MessageClientDisconnect)p.getMessage();
				
				if (m.disconnectedClientID == this.clientID) {
					if (this.server == null) {
						JOptionPane.showMessageDialog(new JFrame(), m.reason);
						System.exit(1);
					} else {
						// TODO: find a way to disconnect locally attached clients
					}
				} else {
					chatMessages.addFirst(new ChatMessage("SERVER", m.reason));
				}
				break;
			}
			case ENTITY_DESTROY: {
				MessageEntityDestroy m = (MessageEntityDestroy)p.getMessage();
				
				state.removeEntity(m.entityID);
				break;
			}
			case TILE_SET: {
				MessageTileSet m = (MessageTileSet)p.getMessage();
				
				Map currMap = this.state.getCurrentMap();
				if (currMap.id == m.mapID) {
					currMap.setTileAt(m.pos, m.tile);
				}
			}
			default: {
				break;
			}
		}
	}
	
	public void send(Message m) {
		Packet p = new Packet(this.clientID, m);
		if (socket == null) {
			server.processPacket(p);
		} else {
			try {
				out.writeObject(p);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public ArrayDeque<ChatMessage> getAllChatMessages() {
		return chatMessages;
	}
	
	public QuestPanel getPanel() {
		return this.panel;
	}

    // initialize the display and java swing framework
	public void initFrontend() {
		Client thisClient = this;
		SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
			        panel = new QuestPanel(thisClient);
			        panel.initOverlays();
			        resetFrame(false);
			        panel.renderTimer.start();
			        panel.inputScanTimer.start();
				}
			}
		);
	}
	
	public void resetFrame(boolean fullscreen) {
		if (frame != null) {
			frame.dispose();
		}
		
		frame = new JFrame("UrfQuest");

		if (fullscreen) {
			frame.setResizable(false);
			frame.setUndecorated(true);
			isFullscreen = true;
		} else {
			frame.setMinimumSize(new Dimension(700, 600));
			frame.setResizable(true);
			frame.setExtendedState(JFrame.NORMAL);
			frame.setUndecorated(false);
			isFullscreen = false;
			
			if (initWindowDims != null) {
				frame.setSize(initWindowDims.x, initWindowDims.y);
			}
			if (initWindowPos != null) {
				frame.setLocation(initWindowPos.x, initWindowPos.y);
			}
		}
		// frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setBackground(Color.BLACK);
		
		frame.add(panel);
		frame.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				initWindowDims.x = frame.getWidth();
				initWindowDims.y = frame.getHeight();
				
				int newPanelWidth = frame.getContentPane().getWidth();
				int newPanelHeight = frame.getContentPane().getHeight();
				panel.setSize(newPanelWidth, newPanelHeight);
			}
		});
	}
}
