package xyz.urffer.urfquest.shared.protocol.messages;

import xyz.urffer.urfquest.Logger;
import xyz.urffer.urfquest.shared.protocol.Message;
import xyz.urffer.urfquest.shared.protocol.types.MessageType;

public class MessagePing extends Message {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4917901194037800769L;

	@Override
	public MessageType getType() {
		return MessageType.PING;
	}
	
	@Override
	public void print(String prefix, Logger logger) {
		// do nothing here. these are ping messages. no need to log
		// logger.info(prefix + "," + this.toString());
	}

	@Override
	public String toString() {
		return "";
	}

}
