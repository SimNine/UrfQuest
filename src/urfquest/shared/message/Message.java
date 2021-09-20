package urfquest.shared.message;

import java.io.Serializable;

public class Message implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6605902089966153417L;

	public MessageType type = MessageType.PING;
	
	public Object payload = null;
	public Object payload2 = null;
	
	public int[] xyChunk = new int[2];
	
	public double[] pos = new double[2];
	
	public int clientID = 0;
	public int entityID = 0;
	public int mapID = 0;
	
	public String entityName;
	
	public EntityType entityType;
	
	public String toString() {
		String ret = type.name() + " - ";
		
		switch (type) {
		case PING:
			break;
		case CHUNK_LOAD:
			ret += "MapID: " + mapID + ", xChunk:" + xyChunk[0] + ", yChunk:" + xyChunk[1];
			break;
			
		// only sent by client
		case PLAYER_MOVE:
			ret += "xDelt:" + pos[0] + " yDelt:" + pos[1];
			break;
		case PLAYER_REQUEST:
			ret += entityName;
			break;
		
		// only sent by server
		case ENTITY_SET_POS:
			ret += "x:" + pos[0] + " y:" + pos[1];
			break;
		case CONNECTION_CONFIRMED:
			break;
		case ENTITY_INIT:
			break;
		default:
			ret += "unrecognized message";
			break;
		}
		return ret;
	}
}
