package xyz.urffer.urfquest.shared.protocol.messages;

import xyz.urffer.urfquest.Logger;
import xyz.urffer.urfquest.shared.protocol.Message;
import xyz.urffer.urfquest.shared.protocol.types.MessageType;

public class MessageClientDisconnect extends Message {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4081635830952669746L;
	
	public String reason;
	public int disconnectedClientID;

	@Override
	public MessageType getType() {
		return MessageType.DISCONNECT_CLIENT;
	}
	
	@Override
	public void print(String prefix, Logger logger) {
		logger.info(prefix + "," + this.toString());
	}

	@Override
	public String toString() {
		return "disconnectedClientID:" + disconnectedClientID + ",reason:\"" + reason + "\"";
	}

}
