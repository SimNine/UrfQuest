package urfquest.client.guis.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;

import urfquest.client.Client;
import urfquest.client.guis.GUIAnchor;
import urfquest.client.guis.GUIContainer;
import urfquest.client.guis.GUIObject;
import urfquest.shared.ChatMessage;
import urfquest.shared.Constants;
import urfquest.shared.message.Message;
import urfquest.shared.message.MessageType;

public class ChatWindow extends GUIContainer {
	
	private String currentMessage = "";
	private boolean isOpaque = false;
	//private Font chatFont = new Font("DialogInput", Font.PLAIN, 20);
	private Font chatFont;

	public ChatWindow(Client c,
					  GUIAnchor anchorPoint, int xRel, int yRel, int width, int height, 
					  String name, GUIObject parent, 
					  Color bkg, Color borderColor, int borderThickness) {
		super(c, anchorPoint, xRel, yRel, width, height, name, parent, bkg, borderColor, borderThickness);
		try {
			chatFont = Font.createFont(Font.TRUETYPE_FONT, new File("MinecraftRegular-Bmg3.otf"));
			chatFont = chatFont.deriveFont(Font.PLAIN, Constants.DEFAULT_TEXT_SIZE);
		} catch (FontFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		
		// Set chat text graphics
		g.setColor(Color.BLACK);
		g.setFont(this.chatFont);
		int offset = 5;
		int fontHeight = g.getFontMetrics().getHeight()/2 + offset;
		
		//int yPos = this.bounds.y + this.bounds.height - offset - fontHeight;
		int yPos = this.bounds.y + this.bounds.height - offset;
		
		// Draw the in-progress chat message if the box is currently opaque
		if (isOpaque) {
			BufferedImage img = this.drawString(currentMessage, this.bounds.width - offset*2, fontHeight);
			yPos -= img.getHeight();
			g.drawImage(img, this.bounds.x + offset, yPos, null);
			
			// Draw cursor
			if (System.currentTimeMillis() % 1000 > 500) {
				g.fillRect(this.bounds.x + 6 + g.getFontMetrics().stringWidth(currentMessage), yPos, 3, Constants.DEFAULT_TEXT_SIZE);
			}
		}
		
		// Draw each of the current chat messages
		for (ChatMessage m : this.client.getAllChatMessages()) {
			String toDraw = "";
			if (m.source.equals(ChatMessage.serverSource)) {
				g.setColor(new Color(255, 0, 0));;
			} else {
				g.setColor(Color.BLACK);
				toDraw = m.source + "> ";
			}
			toDraw += m.message;
			
			BufferedImage img = this.drawString(toDraw, this.bounds.width - offset*2, fontHeight);
			yPos -= img.getHeight();
			g.drawImage(img, this.bounds.x + offset, yPos, null);
			
			if (yPos < this.bounds.y - fontHeight) {
				break;
			}
		}
	}
	
	private BufferedImage drawString(String s, int widthBound, int fontHeight) {
		BufferedImage img = new BufferedImage(widthBound, this.bounds.height, BufferedImage.TYPE_4BYTE_ABGR);
		Graphics2D g2d = (Graphics2D)img.getGraphics();
		
		g2d.setColor(Color.BLACK);
		g2d.setFont(chatFont);
		FontMetrics fontMetrics = g2d.getFontMetrics(chatFont);
		Iterator<String> it = Arrays.stream(s.split(" ")).iterator();
		String accumulation = "";
		int yPos = fontHeight;
		while (it.hasNext()) {
			String nextWord = it.next();
			if (fontMetrics.stringWidth(accumulation + nextWord) < widthBound) {
				accumulation += nextWord + " ";
			} else {
				g2d.drawString(accumulation, 0, yPos);
				yPos += fontHeight;
				accumulation = nextWord;
			}
		}
		if (!accumulation.isBlank()) {
			g2d.drawString(accumulation, 0, yPos);
		}
		
		img = img.getSubimage(0, 0, widthBound, yPos);
		return img;
	}
}
