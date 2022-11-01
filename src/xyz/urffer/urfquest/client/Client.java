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
import xyz.urffer.urfquest.client.entities.mobs.Chicken;
import xyz.urffer.urfquest.client.entities.mobs.Cyclops;
import xyz.urffer.urfquest.client.entities.mobs.NPCHuman;
import xyz.urffer.urfquest.client.entities.mobs.Player;
import xyz.urffer.urfquest.client.state.State;
import xyz.urffer.urfquest.server.Server;
import xyz.urffer.urfquest.shared.ChatMessage;
import xyz.urffer.urfquest.shared.protocol.Message;
import xyz.urffer.urfquest.shared.protocol.Packet;
import xyz.urffer.urfquest.shared.protocol.messages.MessageChat;
import xyz.urffer.urfquest.shared.protocol.messages.MessageChunkInit;
import xyz.urffer.urfquest.shared.protocol.messages.MessageConnectionConfirmed;
import xyz.urffer.urfquest.shared.protocol.messages.MessageDisconnect;
import xyz.urffer.urfquest.shared.protocol.messages.MessageEntityDestroy;
import xyz.urffer.urfquest.shared.protocol.messages.MessageEntityInit;
import xyz.urffer.urfquest.shared.protocol.messages.MessageEntitySetMoveVector;
import xyz.urffer.urfquest.shared.protocol.messages.MessageEntitySetPos;
import xyz.urffer.urfquest.shared.protocol.messages.MessageMapInit;
import xyz.urffer.urfquest.shared.protocol.messages.MessagePlayerInit;
import xyz.urffer.urfquest.shared.protocol.messages.MessageRequestMap;
import xyz.urffer.urfquest.shared.protocol.messages.MessageRequestPlayer;
import xyz.urffer.urfquest.shared.protocol.messages.MessageServerError;
import xyz.urffer.urfquest.shared.protocol.types.EntityType;
import xyz.urffer.urfquest.shared.protocol.types.MobType;

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
	
	public Client(Socket socket, String playerName) {
		this(playerName);
		
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
				MessageConnectionConfirmed m = (MessageConnectionConfirmed)p.getMessage();
				
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
			case ENTITY_INIT: {
				// - Initializes an entity of the given type
				
				// If this entity is not on the current map, do nothing
				// TODO: must implement MAP_METADATA first
				// if (m.mapID != state.getCurrentMap().id) {
				// 		return;
				// }
				
				// - If the entity is a player with the ID of this client:
				// -- Assign it to this client
				// -- Initialize this client's frontend
				MessageEntityInit m = (MessageEntityInit)p.getMessage();
				
				if (m.entityType == EntityType.MOB) {
					// TODO: spawn entity only on the specified map, which should be retrieved based on m.mapID
					switch ((MobType) m.entitySubtype) {
						case CHICKEN: {
							Chicken chicken = new Chicken(
								this,
								m.entityID, 
								state.getCurrentMap(),
								m.pos
							);
							state.getCurrentMap().addMob(chicken);
							break;
						}
						case CYCLOPS: {
							Cyclops cyclops = new Cyclops(
								this,
								m.entityID,
								state.getCurrentMap(),
								m.pos
							);
							state.getCurrentMap().addMob(cyclops);
							break;
						}
						case NPC_HUMAN: {
							NPCHuman npc = new NPCHuman(
								this,
								m.entityID,
								state.getCurrentMap(),
								m.pos,
								m.entityName
							);
							state.getCurrentMap().addMob(npc);
							break;
						}
						default: {
							break;
						}
					}
				}
				break;
			}
			case PLAYER_INIT: {
				// - Initializes a player
				// -- Assign it to this client
				// -- Initialize this client's frontend
				
				// If this entity is not on the current map, do nothing
				// TODO: must implement MAP_METADATA first
				// if (m.mapID != state.getCurrentMap().id) {
				// 		return;
				// }
				MessagePlayerInit m = (MessagePlayerInit)p.getMessage();
				
				Player player = new Player(this, m.entityID, state.getCurrentMap(), m.pos, m.entityName);
				state.getCurrentMap().addPlayer(player);
				player.setMap(state.getCurrentMap());
				
				if (m.clientOwnerID == this.clientID) {
					state.setPlayer(player);
					if (this.server == null) {
						this.initFrontend();
						state.getCurrentMap().requestMissingChunks();
					}
				}
				break;
			}
			case CHUNK_INIT: {
				// - Loads the payloads of this message into the specified chunk
				MessageChunkInit m = (MessageChunkInit)p.getMessage();
				
				state.getCurrentMap().setChunk(
					m.xyChunk,
					m.tileTypes,
					m.objectTypes
				);
				break;
			}
			case ENTITY_SET_POS: {
				// - Sets the position of the given entity
				MessageEntitySetPos m = (MessageEntitySetPos)p.getMessage();
				
				Entity e = state.getCurrentMap().getEntity(m.entityID);
				if (e != null) {
					e.setPos(m.pos);
				}
				break;
			}
			case ENTITY_SET_MOVE_VECTOR: {
				MessageEntitySetMoveVector m = (MessageEntitySetMoveVector)p.getMessage();
				
				Entity e = state.getCurrentMap().getEntity(m.entityID);
				if (e != null) {
					e.setMovementVector(m.vector);
				}
				break;
			}
			case MAP_INIT: {
				// - Loads metadata about the current map (id, climate, etc)
				MessageMapInit m = (MessageMapInit)p.getMessage();
				
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
				MessageDisconnect m = (MessageDisconnect)p.getMessage();
				
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
				
				state.getCurrentMap().removeEntity(m.entityID);
				break;
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
        }
		// frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setBackground(Color.BLACK);
		
		frame.add(panel);
		frame.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                panel.setSize(frame.getContentPane().getWidth(), frame.getContentPane().getHeight());
            }
        });
	}
}
