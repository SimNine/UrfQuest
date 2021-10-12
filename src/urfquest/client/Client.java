package urfquest.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JOptionPane;

import urfquest.Main;
import urfquest.client.entities.mobs.Player;
import urfquest.client.map.MapChunk;
import urfquest.client.state.State;
import urfquest.shared.message.EntityType;
import urfquest.shared.message.Message;
import urfquest.shared.message.MessageType;

public class Client implements Runnable {
	
	private State state;
	
	private Socket socket;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	
	private int clientID;
	
	public Client(Socket socket) {
		this.socket = socket;
		this.state = new State();
		
		// initialize streams on the socket
		try {
	        out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
			
			Main.logger.debug("Client: initialized streams");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
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
	
	private void processMessage(Message m) {
		switch (m.type) {
		case PING:
			Main.logger.verbose(m.toString());
			break;
		case CONNECTION_CONFIRMED:
			Main.logger.info(m.toString());
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
			
			String playerName = JOptionPane.showInputDialog(Main.frame, "What is your name?");
			m = new Message();
			m.type = MessageType.PLAYER_REQUEST;
			m.entityName = playerName;
			this.send(m);
			break;
		case ENTITY_INIT:
			Main.logger.debug(m.toString());
			// - Initializes an entity of the given type
			// - If the entity is a player with the ID of this client:
			// -- Assign it to this client
			// -- Initialize this client's frontend
			if (m.entityType == EntityType.PLAYER) {
				Player player = new Player(m.entityID, state.getCurrentMap(), m.pos[0], m.pos[1], m.entityName);
				state.getCurrentMap().addPlayer(player);
				player.setMap(state.getCurrentMap());
				
				if (m.clientID == this.clientID) {
					state.setPlayer(player);
					Main.initClientFrontend();
				}
			}
			break;
		case CHUNK_LOAD:
			Main.logger.debug(m.toString());
			// - Loads the payloads of this message into the specified chunk
			MapChunk c = state.getCurrentMap().getChunk(m.xyChunk[0], m.xyChunk[1]);
			if (c == null) {
				c = state.getCurrentMap().createChunk(m.xyChunk[0], m.xyChunk[1]);
			}
			c.setAllTileTypes((int[][])m.payload);
			c.setAllTileSubtypes((int[][])m.payload2);
			state.getCurrentMap().generateMinimap();
			break;
		case ENTITY_SET_POS:
			Main.logger.verbose(m.toString());
			// - Sets the position of the given entity
			if (m.entityType == EntityType.PLAYER) {
				Player p = state.getCurrentMap().getPlayer(m.entityID);
				p.setPos(m.pos[0], m.pos[1]);
			}
			break;
		case MAP_METADATA:
			Main.logger.info(m.toString());
			// - Loads metadata about the current map (id, climate, etc)
			// TODO - currently unused
			break;
		case DEBUG_PLAYER_INFO:
			Main.logger.info(m.toString());
			break;
		default:
			Main.logger.debug(m.toString());
			break;
		}
	}
	
	public void send(Message m) {
		try {
			out.writeObject(m);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
