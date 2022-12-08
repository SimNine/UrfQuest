package xyz.urffer.urfquest.shared.protocol.messages;

import xyz.urffer.urfutils.math.PairDouble;

import xyz.urffer.urfquest.Logger;
import xyz.urffer.urfquest.shared.protocol.Message;
import xyz.urffer.urfquest.shared.protocol.types.MessageType;

public class MessageItemSetPos extends Message {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1065420566717112677L;
	
	public int entityID = 0;
	
	public int mapID;
	public PairDouble pos;
	public int entityOwnerID;

	@Override
	public MessageType getType() {
		return MessageType.ITEM_SET_POS;
	}

	@Override
	public void print(String prefix, Logger logger) {
		logger.verbose(prefix + "," + this.toString());
	}

	@Override
	public String toString() {
		return "entityID:" + this.entityID + 
			   ",pos:" + pos +
			   ",owner:" + entityOwnerID;
	}

}
