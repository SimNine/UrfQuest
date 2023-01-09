package xyz.urffer.urfquest.shared.protocol.messages;

import xyz.urffer.urfquest.Logger;
import xyz.urffer.urfquest.shared.protocol.Message;
import xyz.urffer.urfquest.shared.protocol.types.MessageType;

public class MessageInitPlayer extends Message {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8352846007865555290L;
	
	public int entityID;
	
	public int clientOwnerID;
	public String entityName;

	@Override
	public MessageType getType() {
		return MessageType.INIT_PLAYER;
	}

	@Override
	public void print(String prefix, Logger logger) {
		logger.info(prefix + "," + this.toString());
	}

	@Override
	public String toString() {
		String ret = 
				"entityID:" + this.entityID + 
				",clientOwnerID:" + this.clientOwnerID +
				",entityName:" + this.entityName;
		return ret;
	}

}
