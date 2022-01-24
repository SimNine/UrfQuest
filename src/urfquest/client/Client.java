package urfquest.client;

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
import javax.swing.SwingUtilities;

import urfquest.Logger;
import urfquest.Logger.LogLevel;
import urfquest.client.entities.mobs.Player;
import urfquest.client.map.MapChunk;
import urfquest.client.state.State;
import urfquest.server.Server;
import urfquest.shared.ChatMessage;
import urfquest.shared.message.EntityType;
import urfquest.shared.message.Message;
import urfquest.shared.message.MessageType;

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
	
	public Client(Server server, String playerName) {
		this.state = new State(this);
		this.logger = new Logger(LogLevel.LOG_DEBUG, "CLIENT");
		this.playerName = playerName;
		
		this.server = server;
		this.socket = null;
	}
	
	public Client(Socket socket, String playerName) {
		this.state = new State(this);
		this.logger = new Logger(LogLevel.LOG_DEBUG, "CLIENT");
		this.playerName = playerName;
		
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
		this.logger.all("Main loop started");
		try {
			while (true) {
				Message m = (Message)in.readObject();
				processMessage(m);
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
	
	public void processMessage(Message m) {
		switch (m.type) {
			case PING: {
				this.getLogger().verbose(m.toString());
				break;
			}
			case CONNECTION_CONFIRMED: {
				this.getLogger().info(m.toString());
				// - Assigns this client its clientID
				// - Informs this client of the surface map's ID
				// - Sends a request to the server to load the current map
				// - Sends a request to the server to create a player
				this.clientID = m.clientID;
				int surfaceMapID = m.mapID;
				
				m = new Message();
				m.type = MessageType.MAP_REQUEST;
				m.mapID = surfaceMapID;
				this.send(m);
				
				m = new Message();
				m.type = MessageType.PLAYER_REQUEST;
				m.entityName = this.playerName;
				this.send(m);
				break;
			}
			case ENTITY_INIT: {
				this.getLogger().debug(m.toString());
				// - Initializes an entity of the given type
				// - If the entity is a player with the ID of this client:
				// -- Assign it to this client
				// -- Initialize this client's frontend
				if (m.entityType == EntityType.PLAYER) {
					Player player = new Player(this, m.entityID, state.getCurrentMap(), m.pos[0], m.pos[1], m.entityName);
					state.getCurrentMap().addPlayer(player);
					player.setMap(state.getCurrentMap());
					
					if (m.clientID == this.clientID) {
						state.setPlayer(player);
						if (this.server == null) {
							this.initFrontend();
						}
					}
				}
				break;
			}
			case CHUNK_LOAD: {
				this.getLogger().debug(m.toString());
				// - Loads the payloads of this message into the specified chunk
				MapChunk c = state.getCurrentMap().getChunk(m.xyChunk[0], m.xyChunk[1]);
				if (c == null) {
					c = state.getCurrentMap().createChunk(m.xyChunk[0], m.xyChunk[1]);
				}
				c.setAllTileTypes((int[][])m.payload);
				c.setAllTileSubtypes((int[][])m.payload2);
				state.getCurrentMap().generateMinimap();
				break;
			}
			case ENTITY_SET_POS: {
				this.getLogger().verbose(m.toString());
				// - Sets the position of the given entity
				if (m.entityType == EntityType.PLAYER) {
					Player p = state.getCurrentMap().getPlayer(m.entityID);
					if (p != null) {
						p.setPos(m.pos[0], m.pos[1]);
					}
				}
				break;
			}
			case MAP_METADATA: {
				this.getLogger().info(m.toString());
				// - Loads metadata about the current map (id, climate, etc)
				// TODO - currently unused
				break;
			}
			case DEBUG_PLAYER_INFO: {
				this.getLogger().info(m.toString());
				break;
			}
			case CHAT_MESSAGE: {
				this.getLogger().info(m.toString());
				chatMessages.addFirst((ChatMessage)m.payload);
				break;
			}
			default: {
				this.getLogger().debug(m.toString());
				break;
			}
		}
	}
	
	public void send(Message m) {
		if (socket == null) {
			m.clientID = this.clientID;
			server.processMessage(m);
		} else {
			try {
				out.writeObject(m);
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
