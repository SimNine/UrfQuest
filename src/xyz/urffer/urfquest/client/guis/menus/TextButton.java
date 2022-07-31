package xyz.urffer.urfquest.client.guis.menus;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import xyz.urffer.urfquest.LogLevel;
import xyz.urffer.urfquest.Logger;
import xyz.urffer.urfquest.client.Client;
import xyz.urffer.urfquest.client.guis.Clickable;
import xyz.urffer.urfquest.client.guis.GUIAnchor;
import xyz.urffer.urfquest.client.guis.GUIContainer;

public abstract class TextButton extends TextArea implements Clickable {

	public TextButton(Client c, String text, int fontSize, int xDisplacement, int yDisplacement, GUIAnchor anchor, GUIContainer parent) {
		super(c, text, fontSize, xDisplacement, yDisplacement, anchor, parent);
	}
	
	public abstract boolean click();
	
	public void draw(Graphics g) {
		g.setFont(new Font(Font.MONOSPACED, Font.BOLD, fontSize));
		
		if (this.isMouseOver()) g.setColor(Color.LIGHT_GRAY);
		else g.setColor(Color.WHITE);
		
		g.drawString(text, bounds.x, bounds.y + bounds.height);
		
		if (this.client.getLogger().getLogLevel().compareTo(LogLevel.DEBUG) >= 0) {
			drawDebug(g);
		}
	}
	
}