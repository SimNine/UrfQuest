package xyz.urffer.urfquest.shared.protocol.messages;

import xyz.urffer.urfquest.Logger;
import xyz.urffer.urfquest.shared.protocol.Message;
import xyz.urffer.urfquest.shared.protocol.types.MessageType;
import xyz.urffer.urfutils.math.PairDouble;

public class MessageEntitySetDims extends Message {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8025720192747481328L;
	
	public int entityID;
	public PairDouble dimensions;

	@Override
	public MessageType getType() {
		return MessageType.ENTITY_SET_DIMS;
	}
	
	@Override
	public void print(String prefix, Logger logger) {
		logger.debug(prefix + "," + this.toString());
	}

	@Override
	public String toString() {
		String ret =
				"entityID:" + this.entityID + 
				",bounds:" + this.dimensions.toString();
		return ret;
	}

}
