package xyz.urffer.urfquest.shared.protocol.messages;

import xyz.urffer.urfquest.Logger;
import xyz.urffer.urfquest.shared.protocol.Message;
import xyz.urffer.urfquest.shared.protocol.types.MessageType;

public class MessagePlayerDebug extends Message {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4453066880209203871L;
	
	public String playerPosString;

	@Override
	public MessageType getType() {
		return MessageType.DEBUG_PLAYER_INFO;
	}
	
	@Override
	public void print(String prefix, Logger logger) {
		logger.debug(prefix + "," + this.toString());
	}

	@Override
	public String toString() {
		return "pos:" + this.playerPosString;
	}

}
