package xyz.urffer.urfquest.shared.protocol;

import java.io.Serializable;

import xyz.urffer.urfquest.Logger;
import xyz.urffer.urfquest.shared.protocol.types.MessageType;

public class Packet implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6605902089966153417L;

	private MessageType type;
	private int senderID;
	private Message message;
	
	public Packet(int senderID, Message message) {
		this.type = message.getType();
		this.senderID = senderID;
		this.message = message;
	}
	
	public MessageType getType() {
		return this.type;
	}
	
	public int getSenderID() {
		return this.senderID;
	}
	
	public void setSenderID(int senderID) {
		this.senderID = senderID;
	}
	
	public Message getMessage() {
		return this.message;
	}
	
	public void print(Logger logger) {
		String ret = "MSG:" + type.name() + ",senderID:" + this.senderID;
		message.print(ret, logger);
	}
}
