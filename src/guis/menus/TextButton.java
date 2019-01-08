package guis.menus;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import framework.UrfQuest;
import guis.Clickable;
import guis.GUIContainer;

public abstract class TextButton extends TextArea implements Clickable {

	public TextButton(String text, int fontSize, int xDisplacement, int yDisplacement, int anchor, GUIContainer parent) {
		super(text, fontSize, xDisplacement, yDisplacement, anchor, parent);
	}
	
	public abstract boolean click();
	
	public void draw(Graphics g) {
		g.setFont(new Font(Font.MONOSPACED, Font.BOLD, fontSize));
		
		if (this.isMouseOver()) g.setColor(Color.LIGHT_GRAY);
		else g.setColor(Color.WHITE);
		
		g.drawString(text, bounds.x, bounds.y + bounds.height);
		
		if (UrfQuest.debug) {
			drawDebug(g);
		}
	}
	
}