package xyz.urffer.urfquest.shared.protocol.messages;

import xyz.urffer.urfquest.Logger;
import xyz.urffer.urfquest.shared.Vector;
import xyz.urffer.urfquest.shared.protocol.Message;
import xyz.urffer.urfquest.shared.protocol.types.MessageType;

public class MessagePlayerSetMoveVector extends Message {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7441187427984852317L;
	
	public int entityID;
	public Vector vector;

	@Override
	public MessageType getType() {
		return MessageType.PLAYER_SET_MOVE_VECTOR;
	}
	
	@Override
	public void print(String prefix, Logger logger) {
		logger.debug(prefix + "," + this.toString());
	}

	@Override
	public String toString() {
		return "entityID:" + this.entityID + ",direction:" + vector.dirRadians + ",velocity:" + vector.magnitude;
	}

}
