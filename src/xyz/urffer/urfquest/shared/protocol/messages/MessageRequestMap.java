package xyz.urffer.urfquest.shared.protocol.messages;

import xyz.urffer.urfquest.Logger;
import xyz.urffer.urfquest.shared.protocol.Message;
import xyz.urffer.urfquest.shared.protocol.types.MessageType;

public class MessageRequestMap extends Message {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1513101611927834419L;
	
	public int mapID;

	@Override
	public MessageType getType() {
		return MessageType.MAP_REQUEST;
	}

	@Override
	public void print(String prefix, Logger logger) {
		logger.info(prefix + "," + this.toString());
	}

	@Override
	public String toString() {
		return "mapID:" + this.mapID;
	}

}
