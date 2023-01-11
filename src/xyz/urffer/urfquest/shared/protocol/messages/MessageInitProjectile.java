package xyz.urffer.urfquest.shared.protocol.messages;

import xyz.urffer.urfquest.Logger;
import xyz.urffer.urfquest.shared.protocol.Message;
import xyz.urffer.urfquest.shared.protocol.types.MessageType;
import xyz.urffer.urfquest.shared.protocol.types.ProjectileType;

public class MessageInitProjectile extends Message {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -994137336855575456L;
	
	public int entityID;
	public int sourceEntityID;
	public ProjectileType projectileType;

	@Override
	public MessageType getType() {
		return MessageType.INIT_PROJECTILE;
	}

	@Override
	public void print(String prefix, Logger logger) {
		logger.debug(prefix + "," + this.toString());
	}

	@Override
	public String toString() {
		String ret = 
				",entityID:" + this.entityID +
				",sourceID:" + this.sourceEntityID +
				",projectileType:" + this.projectileType.toString();
		return ret;
	}

}
