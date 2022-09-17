package xyz.urffer.urfquest.shared.protocol.messages;

import xyz.urffer.urfquest.Logger;
import xyz.urffer.urfquest.shared.Vector;
import xyz.urffer.urfquest.shared.protocol.Message;
import xyz.urffer.urfquest.shared.protocol.types.MessageType;

public class MessageEntitySetMoveVector extends Message {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8025720192747481328L;
	
	public int entityID;
	public Vector vector;

	@Override
	public MessageType getType() {
		return MessageType.ENTITY_SET_MOVE_VECTOR;
	}
	
	@Override
	public void print(String prefix, Logger logger) {
		logger.verbose(prefix + "," + this.toString());
	}

	@Override
	public String toString() {
		return "entityID:" + this.entityID + ",vector:" + this.vector;
	}

}
