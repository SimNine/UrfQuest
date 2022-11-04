package xyz.urffer.urfquest.shared.protocol.messages;

import xyz.urffer.urfquest.Logger;
import xyz.urffer.urfquest.shared.protocol.Message;
import xyz.urffer.urfquest.shared.protocol.types.MessageType;

public class MessageClientConnectionConfirmed extends Message {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2454607223442843988L;
	
	public String reason;
	public int clientID;
	public int startingMapID;

	@Override
	public MessageType getType() {
		return MessageType.CONNECTION_CONFIRMED;
	}
	
	@Override
	public void print(String prefix, Logger logger) {
		logger.info(prefix + "," + this.toString());
	}

	@Override
	public String toString() {
		return "assigned_client_id:" + clientID + ",starting_map_id:" + startingMapID + ",reason:\"" + reason + "\"";
	}

}
