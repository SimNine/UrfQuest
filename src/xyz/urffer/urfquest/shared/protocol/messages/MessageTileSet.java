package xyz.urffer.urfquest.shared.protocol.messages;

import xyz.urffer.urfquest.Logger;
import xyz.urffer.urfquest.shared.Tile;
import xyz.urffer.urfquest.shared.protocol.Message;
import xyz.urffer.urfquest.shared.protocol.types.MessageType;
import xyz.urffer.urfutils.math.PairInt;

public class MessageTileSet extends Message {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1065420566717112677L;
	
	public int mapID;
	public PairInt pos;
	
	public Tile tile;

	@Override
	public MessageType getType() {
		return MessageType.TILE_SET;
	}

	@Override
	public void print(String prefix, Logger logger) {
		logger.verbose(prefix + "," + this.toString());
	}

	@Override
	public String toString() {
		String ret = 
				"mapID:" + this.mapID + 
				",pos:" + this.pos.toString() +
				",tile:" + this.tile.toString();
		return ret;
	}

}
