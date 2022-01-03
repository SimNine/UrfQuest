package urfquest.client.guis.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayDeque;

import urfquest.Main;
import urfquest.client.guis.GUIAnchor;
import urfquest.client.guis.GUIContainer;
import urfquest.client.guis.GUIObject;
import urfquest.shared.message.Message;
import urfquest.shared.message.MessageType;

public class ChatWindow extends GUIContainer {
	
	private ArrayDeque<String> chatMessages = new ArrayDeque<String>();
	private String currentMessage = "";

	public ChatWindow(GUIAnchor anchorPoint, int xRel, int yRel, int width, int height, 
					  String name, GUIObject parent, 
					  Color bkg, Color borderColor, int borderThickness) {
		super(anchorPoint, xRel, yRel, width, height, name, parent, bkg, borderColor, borderThickness);
		
		chatMessages = new ArrayDeque<String>();
	}
	
	public void keypress(KeyEvent keyEvent) {
		if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
			if (currentMessage.length() > 0) {
				Message m = new Message();
				m.type = MessageType.CHAT_MESSAGE;
				m.payload = currentMessage;
				Main.client.send(m);
				
				chatMessages.addFirst(currentMessage);
			}
			
			currentMessage = "";
		} else if (keyEvent.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
			currentMessage = currentMessage.substring(0, currentMessage.length() - 1);
		} else if (!keyEvent.isActionKey() &&
					keyEvent.getKeyCode() != KeyEvent.VK_CONTROL &&
					keyEvent.getKeyCode() != KeyEvent.VK_SHIFT &&
					keyEvent.getKeyCode() != KeyEvent.VK_ALT) {
			this.currentMessage += keyEvent.getKeyChar();
		}
	}
	
	public void draw(Graphics g) {
		super.draw(g);
		
		// draw the in-progress chat message if there is one
		g.drawString(currentMessage, this.bounds.x + 5, this.bounds.y - 5);
		
		// draw each of the current chat messages
		int yPos = -15;
		for (String s : chatMessages) {
			g.drawString(s, this.bounds.x + 5, this.bounds.y - 5  + yPos);
			yPos += -15;
		}
	}
}
