package urfquest.shared;

import java.io.Serializable;

public class ChatMessage implements Serializable {
	
	public static String serverSource = "SERVER";

	/**
	 * 
	 */
	private static final long serialVersionUID = 4895853443945849355L;
	
	public String source;
	public String message;
	
	public ChatMessage(String source, String message) {
		this.source = source;
		this.message = message;
	}
	
}
