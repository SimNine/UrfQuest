package xyz.urffer.urfquest.shared.protocol.messages;

import xyz.urffer.urfquest.Logger;
import xyz.urffer.urfquest.shared.protocol.Message;
import xyz.urffer.urfquest.shared.protocol.types.MessageType;
import xyz.urffer.urfquest.shared.protocol.types.MobType;

public class MessageInitMob extends Message {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8352846007865555290L;
	
	public int entityID;

	public MobType mobType;
	public String mobName;

	@Override
	public MessageType getType() {
		return MessageType.INIT_MOB;
	}

	@Override
	public void print(String prefix, Logger logger) {
		logger.debug(prefix + "," + this.toString());
	}

	@Override
	public String toString() {
		String ret = 
				",entityID:" + this.entityID +
				",mobType:" + this.mobType.toString() +
				",mobName:" + this.mobName;
		return ret;
	}

}
