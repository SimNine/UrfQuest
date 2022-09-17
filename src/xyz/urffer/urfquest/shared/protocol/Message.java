package xyz.urffer.urfquest.shared.protocol;

import java.io.Serializable;

import xyz.urffer.urfquest.Logger;
import xyz.urffer.urfquest.shared.protocol.types.MessageType;

public abstract class Message implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8958245565003341109L;

	public abstract MessageType getType();

	public abstract void print(String prefix, Logger logger);
	
	public abstract String toString();

}
