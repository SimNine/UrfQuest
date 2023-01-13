package xyz.urffer.urfquest.shared.protocol.messages;

import xyz.urffer.urfquest.Logger;
import xyz.urffer.urfquest.shared.protocol.Message;
import xyz.urffer.urfquest.shared.protocol.types.MessageType;

public class MessageItemResetCooldown extends Message {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1454553272373133474L;
	public int entityID = 0;

	@Override
	public MessageType getType() {
		return MessageType.ITEM_RESET_COOLDOWN;
	}

	@Override
	public void print(String prefix, Logger logger) {
		logger.debug(prefix + "," + this.toString());
	}

	@Override
	public String toString() {
		return "entityID:" + this.entityID;
	}

}
