package xyz.urffer.urfquest.shared.protocol.messages;

import xyz.urffer.urfquest.Logger;
import xyz.urffer.urfquest.shared.protocol.Message;
import xyz.urffer.urfquest.shared.protocol.types.MessageType;

public class MessageMapInit extends Message {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9048739423684645757L;
	
	public int mapID = 0;

	@Override
	public MessageType getType() {
		return MessageType.MAP_INIT;
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
