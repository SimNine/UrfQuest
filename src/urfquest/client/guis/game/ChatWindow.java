package urfquest.client.guis.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import urfquest.client.Client;
import urfquest.client.guis.GUIAnchor;
import urfquest.client.guis.GUIContainer;
import urfquest.client.guis.GUIObject;
import urfquest.shared.ChatMessage;
import urfquest.shared.message.Message;
import urfquest.shared.message.MessageType;

public class ChatWindow extends GUIContainer {
	
	private String currentMessage = "";
	private boolean isOpaque = false;
	private Font chatFont = new Font("Courier", Font.BOLD, 15);

	public ChatWindow(Client c,
					  GUIAnchor anchorPoint, int xRel, int yRel, int width, int height, 
					  String name, GUIObject parent, 
					  Color bkg, Color borderColor, int borderThickness) {
		super(c, anchorPoint, xRel, yRel, width, height, name, parent, bkg, borderColor, borderThickness);
	}
	
	public void keypress(KeyEvent keyEvent) {
		if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
			if (currentMessage.length() > 0) {
				Message m = new Message();
				m.type = MessageType.CHAT_MESSAGE;
				m.payload = new ChatMessage(null, currentMessage);
				this.client.send(m);
			}
			
			currentMessage = "";
		} else if (keyEvent.getKeyCode() == KeyEvent.VK_BACK_SPACE &&
				   currentMessage.length() > 0) {
			currentMessage = currentMessage.substring(0, currentMessage.length() - 1);
		} else if (!keyEvent.isActionKey() &&
					keyEvent.getKeyCode() != KeyEvent.VK_CONTROL &&
					keyEvent.getKeyCode() != KeyEvent.VK_SHIFT &&
					keyEvent.getKeyCode() != KeyEvent.VK_ALT &&
					keyEvent.getKeyCode() != KeyEvent.VK_BACK_SPACE) {
			this.currentMessage += keyEvent.getKeyChar();
		}
	}
	
	public void setOpaque(boolean opaque) {
		if (opaque) {
			this.setBkg(Color.LIGHT_GRAY);
			this.setBorderCol(Color.BLACK);
		} else {
			this.setBkg(new Color(180, 180, 180, 150));
			this.setBorderCol(null);
		}
		this.isOpaque = opaque;
	}
	
	public void draw(Graphics g) {
		super.draw(g);
		
		// set chat text graphics
		g.setColor(Color.BLACK);
		g.setFont(this.chatFont);
		int fontHeight = g.getFontMetrics().getHeight()/2;
		int offset = 5;
		
		// draw the in-progress chat message if the box is currently opaque
		if (isOpaque) {
			g.drawString(currentMessage, this.bounds.x + 5, this.bounds.y + this.bounds.height - offset);
			g.fillRect(this.bounds.x + 5 + g.getFontMetrics().stringWidth(currentMessage),
					   this.bounds.y + this.bounds.height - fontHeight - offset,
					   3, fontHeight);
		}
		
		// draw each of the current chat messages
		int yPos = -g.getFontMetrics().getHeight()/2;
		for (ChatMessage m : this.client.getAllChatMessages()) {
			String toDraw = "";
			if (m.source.equals(ChatMessage.serverSource)) {
				g.setColor(new Color(255, 0, 0));;
			} else {
				g.setColor(Color.BLACK);
				toDraw = m.source + "> ";
			}
			toDraw += m.message;
			g.drawString(toDraw, this.bounds.x + 5, 
						 this.bounds.y + this.bounds.height + yPos - offset);
			yPos += -fontHeight;
			
			if (yPos < (this.bounds.height - fontHeight*2) * -1) {
				break;
			}
		}
	}
}
