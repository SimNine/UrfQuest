package urfquest.shared.message;

import java.io.Serializable;

import urfquest.shared.ChatMessage;
import urfquest.shared.Vector;

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
	public Vector vector = null;
	
	public int clientID = 0;
	public int entityID = 0;
	public int mapID = 0;
	
	public String entityName;
	
	public EntityType entityType;
	public Object entitySubtype;
	
	public String toString() {
		String ret = "MSG " + type.name() + " clientID:" + this.clientID;
		
		switch (type) {
		case PING:
			break;
		case CHUNK_LOAD:
			ret += ",mapID:" + mapID + ",xChunk:" + xyChunk[0] + ",yChunk:" + xyChunk[1];
			break;
		case CHAT_MESSAGE: {
			ChatMessage m = (ChatMessage)payload;
			ret += ",source:" + m.source + ",message:" + m.message;
			break;
		}
		case DISCONNECT_CLIENT: {
			ret += ",reason:\"" + (String)this.payload + "\"";
			break;
		}
			
		// temporary / debugging
		case DEBUG_PLAYER_INFO:
			ret += ",pos:" + (String)payload;
			break;
			
		// only sent by client
		case PLAYER_SET_MOVE_VECTOR: {
			Vector v = this.vector;
			ret += ",entityID:" + this.entityID + ",direction:" + v.dirRadians + ",velocity:" + v.magnitude;
			break;
		}
		case PLAYER_REQUEST:
			ret += ",name:" + entityName;
			break;
		case MAP_REQUEST:
			ret += ",mapID:" + this.mapID;
			break;
		
		// only sent by server
		case ENTITY_SET_POS:
			ret += ",entityID:" + this.entityID + ",x:" + pos[0] + ",y:" + pos[1];
			break;
		case CONNECTION_CONFIRMED:
			break;
		case ENTITY_INIT:
			ret += ",entityID:" + this.entityID + ",type:" + entityType.toString() + ",";
			if (entityType == EntityType.PLAYER) {
				ret += ",name:" + this.entityName;
			}
			break;
		case ENTITY_DESTROY: {
			ret += ",entityID:" + this.entityID + ",";
			if (entityType == EntityType.PLAYER) {
				ret += ",name:" + this.entityName;
			}
			break;
		}
		case SERVER_ERROR: {
			ret += ",error:\"" + (String)this.payload + "\",";
			break;
		}
		default:
			ret += ",unrecognized_message";
			break;
		}
		return ret;
	}
}
