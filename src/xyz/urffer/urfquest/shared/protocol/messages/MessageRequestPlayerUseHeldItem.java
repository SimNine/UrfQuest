package xyz.urffer.urfquest.shared.protocol.messages;

import xyz.urffer.urfquest.Logger;
import xyz.urffer.urfquest.shared.protocol.Message;
import xyz.urffer.urfquest.shared.protocol.types.MessageType;

public class MessageRequestPlayerUseHeldItem extends Message {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7441187427984852317L;

	@Override
	public MessageType getType() {
		return MessageType.REQUEST_PLAYER_USE_HELD_ITEM;
	}
	
	@Override
	public void print(String prefix, Logger logger) {
		logger.debug(prefix + "," + this.toString());
	}

	@Override
	public String toString() {
		String ret = "";
		return ret;
	}

}
