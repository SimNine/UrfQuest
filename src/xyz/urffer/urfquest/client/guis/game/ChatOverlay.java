package xyz.urffer.urfquest.client.guis.game;

import java.awt.Color;
import java.awt.event.KeyEvent;

import xyz.urffer.urfquest.client.Client;
import xyz.urffer.urfquest.client.guis.GUIAnchor;
import xyz.urffer.urfquest.client.guis.GUIContainer;

public class ChatOverlay extends GUIContainer {
	
	private ChatWindow chatWindow;
	
	public ChatOverlay(Client c) {
		super(c, 
			  GUIAnchor.TOP_LEFT, 
			  0, 
			  0, 
			  0, 
			  0, 
			  "chat", null, null, null, 0);
		
		// Add the chat window
		int chatWindowHeight = 400;
		chatWindow = new ChatWindow(this.client, GUIAnchor.BOTTOM_RIGHT, -610, -chatWindowHeight - 10, 600, chatWindowHeight, 
				"chatmessages", this, new Color(180, 180, 180, 150), null, 3);
		this.addObject(chatWindow);
	}
	
	public void keypress(KeyEvent keyEvent) {
		this.chatWindow.keypress(keyEvent);
	}
	
	public void setOpaqueChatbox(boolean opaque) {
		chatWindow.setOpaque(opaque);
	}
}
