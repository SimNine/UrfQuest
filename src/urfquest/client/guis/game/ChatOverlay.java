package urfquest.client.guis.game;

import java.awt.Color;
import java.awt.event.KeyEvent;

import urfquest.Main;
import urfquest.client.guis.GUIAnchor;
import urfquest.client.guis.GUIContainer;

public class ChatOverlay extends GUIContainer {
	
	private ChatWindow chatWindow;
	
	public ChatOverlay() {
		super(GUIAnchor.TOP_LEFT, 
			  0, 
			  0, 
			  Main.panel.getWidth(), 
			  Main.panel.getHeight(), 
			  "chat", 
			  null, 
			  new Color(128, 128, 128, 128), null, 0);
		
		// add the chat window
		chatWindow = new ChatWindow(GUIAnchor.BOTTOM_RIGHT, -310, -160, 300, 150, 
				"chatmessages", this, Color.LIGHT_GRAY, Color.BLACK, 3);
		this.addObject(chatWindow);
	}
	
	public void keypress(KeyEvent keyEvent) {
		this.chatWindow.keypress(keyEvent);
	}
	
	public void newMessage() {
		this.chatWindow.newMessage();
	}
}
