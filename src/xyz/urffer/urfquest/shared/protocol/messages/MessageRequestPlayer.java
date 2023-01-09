package xyz.urffer.urfquest.shared.protocol.messages;

import xyz.urffer.urfquest.Logger;
import xyz.urffer.urfquest.shared.protocol.Message;
import xyz.urffer.urfquest.shared.protocol.types.MessageType;

public class MessageRequestPlayer extends Message {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1943889197088404442L;
	
	public String entityName;

	@Override
	public MessageType getType() {
		return MessageType.REQUEST_PLAYER;
	}

	@Override
	public void print(String prefix, Logger logger) {
		logger.info(prefix + "," + this.toString());
	}

	@Override
	public String toString() {
		return "name:" + entityName;
	}

}
