package urfquest.client.guis.game;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import urfquest.client.guis.GUIAnchor;
import urfquest.client.guis.GUIContainer;
import urfquest.client.guis.GUIObject;

public class ChatWindow extends GUIContainer {
	
	private ArrayList<String> chatMessages = new ArrayList<String>();

	public ChatWindow(GUIAnchor anchorPoint, int xRel, int yRel, int width, int height, 
					  String name, GUIObject parent, 
					  Color bkg, Color borderColor, int borderThickness) {
		super(anchorPoint, xRel, yRel, width, height, name, parent, bkg, borderColor, borderThickness);
	}
	
	public void addString(String s) {
		chatMessages.add(s);
	}
	
	public void draw(Graphics g) {
		super.draw(g);
		int yPos = 0;
		for (String s : chatMessages) {
			g.drawString(s, this.bounds.x + 5, this.bounds.y - 5  + yPos);
			yPos += -15;
		}
	}
}
