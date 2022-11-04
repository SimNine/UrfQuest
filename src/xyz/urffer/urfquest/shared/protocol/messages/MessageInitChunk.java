package xyz.urffer.urfquest.shared.protocol.messages;

import xyz.urffer.urfquest.Logger;
import xyz.urffer.urfquest.shared.PairInt;
import xyz.urffer.urfquest.shared.Tile;
import xyz.urffer.urfquest.shared.protocol.Message;
import xyz.urffer.urfquest.shared.protocol.types.MessageType;

public class MessageInitChunk extends Message {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3540742496325805936L;
	
	public int mapID = 0;
	public PairInt xyChunk = new PairInt(0, 0);
	public Tile[][] tiles;

	@Override
	public MessageType getType() {
		return MessageType.CHUNK_INIT;
	}

	@Override
	public void print(String prefix, Logger logger) {
		logger.verbose(prefix + "," + this.toString());
	}

	@Override
	public String toString() {
		return "mapID:" + mapID + ",xChunk:" + xyChunk.x + ",yChunk:" + xyChunk.y;
	}

}