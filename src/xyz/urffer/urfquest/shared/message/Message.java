package xyz.urffer.urfquest.shared.message;

import java.io.Serializable;

import xyz.urffer.urfquest.Logger;
import xyz.urffer.urfquest.shared.ChatMessage;
import xyz.urffer.urfquest.shared.Vector;

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
	
	public void print(Logger logger) {
		String ret = "MSG " + type.name() + " clientID:" + this.clientID;
		
		switch (type) {
		case PING:
			logger.verbose(ret);
			break;
		case CHAT_MESSAGE: {
			ChatMessage m = (ChatMessage)payload;
			ret += ",source:" + m.source + ",message:" + m.message;
			logger.info(ret);
			break;
		}
		case DISCONNECT_CLIENT: {
			ret += ",reason:\"" + (String)this.payload + "\"";
			logger.info(ret);
			break;
		}
			
		// temporary / debugging
		case DEBUG_PLAYER_INFO:
			ret += ",pos:" + (String)payload;
			logger.info(ret);
			break;
			
		// only sent by client
		case PLAYER_SET_MOVE_VECTOR: {
			Vector v = this.vector;
			ret += ",entityID:" + this.entityID + ",direction:" + v.dirRadians + ",velocity:" + v.magnitude;
			logger.debug(ret);
			break;
		}
		case PLAYER_REQUEST:
			ret += ",name:" + entityName;
			logger.info(ret);
			break;
		case MAP_REQUEST:
			ret += ",mapID:" + this.mapID;
			logger.info(ret);
			break;
		case CHUNK_REQUEST:
			ret += ",mapID:" + mapID + ",xChunk:" + xyChunk[0] + ",yChunk:" + xyChunk[1];
			logger.debug(ret);
			break;
		
		// only sent by server
		case ENTITY_SET_POS:
			ret += ",entityID:" + this.entityID + ",x:" + pos[0] + ",y:" + pos[1];
			logger.verbose(ret);
			break;
		case ENTITY_SET_MOVE_VECTOR: {
			ret += ",entityID:" + this.entityID + ",vector:" + this.vector;
			logger.verbose(ret);
			break;
		}
		case CONNECTION_CONFIRMED:
			logger.info(ret);
			break;
		case ENTITY_INIT:
			ret += ",entityID:" + this.entityID + ",type:" + entityType.toString() + ",";
			if (entityType == EntityType.PLAYER) {
				ret += ",name:" + this.entityName;
			}
			logger.debug(ret);
			break;
		case ENTITY_DESTROY: {
			ret += ",entityID:" + this.entityID + ",";
			if (entityType == EntityType.PLAYER) {
				ret += ",name:" + this.entityName;
			}
			logger.debug(ret);
			break;
		}
		case MAP_INIT: {
			ret += ",mapID:" + this.mapID;
			logger.info(ret);
			break;
		}
		case CHUNK_INIT: {
			ret += ",mapID:" + mapID + ",xChunk:" + xyChunk[0] + ",yChunk:" + xyChunk[1];
			logger.verbose(ret);
			break;
		}
		case SERVER_ERROR: {
			ret += ",error:\"" + (String)this.payload + "\",";
			logger.all(ret);
			break;
		}
		default:
			ret += ",unrecognized_message";
			logger.debug(ret);
			break;
		}
	}
}
