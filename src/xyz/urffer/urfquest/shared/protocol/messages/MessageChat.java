package xyz.urffer.urfquest.shared.protocol.messages;

import xyz.urffer.urfquest.Logger;
import xyz.urffer.urfquest.shared.ChatMessage;
import xyz.urffer.urfquest.shared.protocol.Message;
import xyz.urffer.urfquest.shared.protocol.types.MessageType;

public class MessageChat extends Message {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -114403857313178889L;
	
	public ChatMessage chatMessage;

	@Override
	public MessageType getType() {
		return MessageType.CHAT_MESSAGE;
	}
	
	@Override
	public void print(String prefix, Logger logger) {
		logger.info(prefix + "," + this.toString());
	}

	@Override
	public String toString() {
		return "source:" + chatMessage.source + ",message:" + chatMessage.message;
	}

}
