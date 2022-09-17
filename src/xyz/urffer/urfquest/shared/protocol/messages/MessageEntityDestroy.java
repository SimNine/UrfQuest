package xyz.urffer.urfquest.shared.protocol.messages;

import xyz.urffer.urfquest.Logger;
import xyz.urffer.urfquest.shared.protocol.Message;
import xyz.urffer.urfquest.shared.protocol.types.EntityType;
import xyz.urffer.urfquest.shared.protocol.types.MessageType;

public class MessageEntityDestroy extends Message {

	/**
	 * 
	 */
	private static final long serialVersionUID = 612261910432126145L;
	
	public int entityID = 0;
	public String entityName;
	public EntityType entityType;
	public Object entitySubtype;

	@Override
	public MessageType getType() {
		return MessageType.ENTITY_DESTROY;
	}

	@Override
	public void print(String prefix, Logger logger) {
		logger.debug(prefix + "," + this.toString());
	}

	@Override
	public String toString() {
		String ret = ",entityID:" + this.entityID + ",";
		if (entityType == EntityType.PLAYER) {
			ret += ",name:" + this.entityName;
		}
		return ret;
	}

}
