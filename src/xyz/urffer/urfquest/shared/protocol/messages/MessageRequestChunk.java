package xyz.urffer.urfquest.shared.protocol.messages;

import xyz.urffer.urfquest.Logger;
import xyz.urffer.urfquest.shared.protocol.Message;
import xyz.urffer.urfquest.shared.protocol.types.MessageType;

public class MessageRequestChunk extends Message {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4097591904847999364L;
	
	public int mapID = 0;
	public int[] xyChunk = new int[2];

	@Override
	public MessageType getType() {
		return MessageType.CHUNK_REQUEST;
	}

	@Override
	public void print(String prefix, Logger logger) {
		logger.debug(prefix + "," + this.toString());
	}

	@Override
	public String toString() {
		return "mapID:" + mapID + ",xChunk:" + xyChunk[0] + ",yChunk:" + xyChunk[1];
	}

}
