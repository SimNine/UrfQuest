package xyz.urffer.urfquest.shared.protocol.messages;

import xyz.urffer.urfutils.math.PairDouble;

import xyz.urffer.urfquest.Logger;
import xyz.urffer.urfquest.shared.protocol.Message;
import xyz.urffer.urfquest.shared.protocol.types.MessageType;

public class MessageEntitySetPos extends Message {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1065420566717112677L;
	
	public int entityID;
	
	public PairDouble pos;
	public int mapID;

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
		String ret =
				"entityID:" + this.entityID +
				",pos:" + this.pos.toString() +
				",mapID:" + this.mapID;
		return ret;
	}

}
