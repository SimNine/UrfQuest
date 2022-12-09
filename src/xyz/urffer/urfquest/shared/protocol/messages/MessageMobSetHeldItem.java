package xyz.urffer.urfquest.shared.protocol.messages;

import xyz.urffer.urfquest.Logger;
import xyz.urffer.urfquest.shared.protocol.Message;
import xyz.urffer.urfquest.shared.protocol.types.MessageType;

public class MessageMobSetHeldItem extends Message {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7455000429816604000L;
	
	public int entityID;
	public int setHeldSlot;

	@Override
	public MessageType getType() {
		return MessageType.MOB_SET_HELD_ITEM;
	}

	@Override
	public void print(String prefix, Logger logger) {
		logger.debug(prefix + "," + this.toString());
	}

	@Override
	public String toString() {
		return "holderID:" + this.entityID + ",slotNum:" + setHeldSlot;
	}

}
