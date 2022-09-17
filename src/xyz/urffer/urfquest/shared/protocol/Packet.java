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
	private int clientID;
	private Message message;
	
	public Packet(int clientID, Message message) {
		this.type = message.getType();
		this.clientID = clientID;
		this.message = message;
	}
	
	public MessageType getType() {
		return this.type;
	}
	
	public int getClientID() {
		return this.clientID;
	}
	
	public void setClientID(int clientID) {
		this.clientID = clientID;
	}
	
	public Message getMessage() {
		return this.message;
	}
	
	public void print(Logger logger) {
		String ret = "MSG:" + type.name() + ",senderID:" + this.clientID;
		message.print(ret, logger);
	}
}
