package urfquest.shared.message;

import java.io.Serializable;

import urfquest.shared.ChatMessage;

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
	public Object entitySubtype;
	
	public String toString() {
		String ret = type.name() + " - ";
		
		switch (type) {
		case PING:
			break;
		case CHUNK_LOAD:
			ret += "mapID:" + mapID + ",xChunk:" + xyChunk[0] + ",yChunk:" + xyChunk[1];
			break;
		case CHAT_MESSAGE: {
			ChatMessage m = (ChatMessage)payload;
			ret += "Source: " + m.source + ", Message: " + m.message;
			break;
		}
			
		// temporary / debugging
		case DEBUG_PLAYER_INFO:
			ret += "Pos: " + (String)payload;
			break;
			
		// only sent by client
		case PLAYER_MOVE:
			ret += "entityID:" + this.entityID + ",xDelt:" + pos[0] + ",yDelt:" + pos[1];
			break;
		case PLAYER_REQUEST:
			ret += "name:" + entityName;
			break;
		case MAP_REQUEST:
			ret += "mapID:" + this.mapID;
			break;
		
		// only sent by server
		case ENTITY_SET_POS:
			ret += "entityID:" + this.entityID + ",x:" + pos[0] + ",y:" + pos[1];
			break;
		case CONNECTION_CONFIRMED:
			ret += "clientID:" + clientID;
			break;
		case ENTITY_INIT:
			ret += "entityID:" + this.entityID + ",type:" + entityType.toString() + ",";
			if (entityType == EntityType.PLAYER) {
				ret += "name:" + this.entityName;
			}
			break;
		default:
			ret += "unrecognized message";
			break;
		}
		return ret;
	}
}
