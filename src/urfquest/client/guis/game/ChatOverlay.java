package urfquest.client.guis.game;

import java.awt.Color;
import java.awt.event.KeyEvent;

import urfquest.client.guis.GUIAnchor;
import urfquest.client.guis.GUIContainer;

public class ChatOverlay extends GUIContainer {
	
	private ChatWindow chatWindow;
	
	public ChatOverlay() {
		super(GUIAnchor.TOP_LEFT, 
			  0, 
			  0, 
			  0, 
			  0, 
			  "chat", 
			  null, null, null, 0);
		
		// add the chat window
		chatWindow = new ChatWindow(GUIAnchor.BOTTOM_RIGHT, -310, -160, 300, 150, 
				"chatmessages", this, new Color(180, 180, 180, 150), null, 3);
		this.addObject(chatWindow);
	}
	
	public void keypress(KeyEvent keyEvent) {
		this.chatWindow.keypress(keyEvent);
	}
	
	public void setOpaqueChatbox(boolean opaque) {
		chatWindow.setOpaque(opaque);
	}
	
	public void addMessage(String m) {
		chatWindow.addMessage(m);
	}
}
