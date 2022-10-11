package xyz.urffer.urfquest.shared.protocol.messages;

import xyz.urffer.urfquest.Logger;
import xyz.urffer.urfquest.shared.protocol.Message;
import xyz.urffer.urfquest.shared.protocol.types.MessageType;

public class MessageServerError extends Message {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7370312714830480708L;
	
	public String errorMessage;

	@Override
	public MessageType getType() {
		return MessageType.SERVER_ERROR;
	}

	@Override
	public void print(String prefix, Logger logger) {
		logger.error(prefix + "," + this.toString());
	}

	@Override
	public String toString() {
		return "error:\"" + this.errorMessage + "\",";
	}

}
