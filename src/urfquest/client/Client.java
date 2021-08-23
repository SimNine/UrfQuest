package urfquest.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import urfquest.Main;
import urfquest.client.map.MapChunk;
import urfquest.client.state.State;
import urfquest.shared.message.Message;

public class Client implements Runnable {
	
	private State state;
	
	private Socket socket;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	
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
		case CHUNK_LOAD:
			Main.logger.debug(m.toString());
			MapChunk c = state.getCurrentMap().getChunk(m.xyChunk[0], m.xyChunk[1]);
			if (c == null) {
				c = state.getCurrentMap().createChunk(m.xyChunk[0], m.xyChunk[1]);
			}
			c.setAllTileTypes((int[][])m.payload);
			c.setAllTileSubtypes((int[][])m.payload2);
			break;
		case PLAYER_SET_POS:
			Main.logger.verbose(m.toString());
			state.getPlayer().setPos(m.pos[0], m.pos[1]);
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
