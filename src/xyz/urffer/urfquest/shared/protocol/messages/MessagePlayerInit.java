package xyz.urffer.urfquest.shared.protocol.messages;

import xyz.urffer.urfquest.Logger;
import xyz.urffer.urfquest.shared.PairDouble;
import xyz.urffer.urfquest.shared.protocol.Message;
import xyz.urffer.urfquest.shared.protocol.types.MessageType;

public class MessagePlayerInit extends Message {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8352846007865555290L;
	
	public int clientOwnerID;
	public int entityID;
	public String entityName;
	public PairDouble pos = new PairDouble(0,0);
	public int mapID;

	@Override
	public MessageType getType() {
		return MessageType.PLAYER_INIT;
	}

	@Override
	public void print(String prefix, Logger logger) {
		logger.debug(prefix + "," + this.toString());
	}

	@Override
	public String toString() {
		String ret = 
				"entityID:" + this.entityID + 
				",name:" + this.entityName + 
				",clientOwnerID:" + this.clientOwnerID;
		return ret;
	}

}
