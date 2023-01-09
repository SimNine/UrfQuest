package xyz.urffer.urfquest.shared.protocol.messages;

import xyz.urffer.urfquest.Logger;
import xyz.urffer.urfquest.shared.protocol.Message;
import xyz.urffer.urfquest.shared.protocol.types.ItemType;
import xyz.urffer.urfquest.shared.protocol.types.MessageType;

public class MessageInitItem extends Message {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8352846007865555290L;
	
	public int entityID;
	
	public ItemType itemType;
	public int durability;
	public int stacksize;

	@Override
	public MessageType getType() {
		return MessageType.INIT_ITEM;
	}

	@Override
	public void print(String prefix, Logger logger) {
		logger.debug(prefix + "," + this.toString());
	}

	@Override
	public String toString() {
		String ret = 
				"entityID:" + this.entityID + 
				",itemType:" + this.itemType +
				",durability:" + this.durability +
				",stacksize:" + this.stacksize;
		return ret;
	}

}
