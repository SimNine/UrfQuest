package xyz.urffer.urfquest.shared.protocol.messages;

import xyz.urffer.urfquest.Logger;
import xyz.urffer.urfquest.shared.protocol.Message;
import xyz.urffer.urfquest.shared.protocol.types.MessageType;

public class MessageEntitySetPos extends Message {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1065420566717112677L;
	
	public int entityID = 0;
	public double[] pos = new double[2];

	@Override
	public MessageType getType() {
		return MessageType.ENTITY_SET_POS;
	}

	@Override
	public void print(String prefix, Logger logger) {
		logger.verbose(prefix + "," + this.toString());
	}

	@Override
	public String toString() {
		return "entityID:" + this.entityID + ",x:" + pos[0] + ",y:" + pos[1];
	}

}
