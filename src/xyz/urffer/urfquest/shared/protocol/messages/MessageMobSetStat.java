package xyz.urffer.urfquest.shared.protocol.messages;

import xyz.urffer.urfquest.Logger;
import xyz.urffer.urfquest.shared.protocol.Message;
import xyz.urffer.urfquest.shared.protocol.types.MessageType;
import xyz.urffer.urfquest.shared.protocol.types.StatType;

public class MessageMobSetStat extends Message {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7997875308733096639L;
	
	public int entityID;
	public StatType statType;
	public int stat;

	@Override
	public MessageType getType() {
		return MessageType.MOB_SET_STAT;
	}

	@Override
	public void print(String prefix, Logger logger) {
		logger.debug(prefix + "," + this.toString());
	}

	@Override
	public String toString() {
		String ret =
				"entityID:" + this.entityID +
				",statType:" + this.statType +
				",stat:" + this.stat;
		return ret;
	}

}
