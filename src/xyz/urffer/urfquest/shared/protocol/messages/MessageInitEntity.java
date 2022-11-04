package xyz.urffer.urfquest.shared.protocol.messages;

import xyz.urffer.urfquest.Logger;
import xyz.urffer.urfquest.shared.PairDouble;
import xyz.urffer.urfquest.shared.protocol.Message;
import xyz.urffer.urfquest.shared.protocol.types.EntityType;
import xyz.urffer.urfquest.shared.protocol.types.MessageType;

public class MessageInitEntity extends Message {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8352846007865555290L;
	
	public int entityID;
	public String entityName;
	public EntityType entityType;
	public Object entitySubtype;
	public PairDouble pos = new PairDouble(0,0);
	public int mapID;

	@Override
	public MessageType getType() {
		return MessageType.ENTITY_INIT;
	}

	@Override
	public void print(String prefix, Logger logger) {
		logger.debug(prefix + "," + this.toString());
	}

	@Override
	public String toString() {
		String ret = 
				",entityID:" + this.entityID + 
				",type:" + entityType.toString();
		if (entityType == EntityType.PLAYER) {
			ret += ",name:" + this.entityName;
		}
		return ret;
	}

}
