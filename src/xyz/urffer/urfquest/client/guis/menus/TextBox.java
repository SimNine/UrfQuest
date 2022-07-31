package xyz.urffer.urfquest.client.guis.menus;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import xyz.urffer.urfquest.LogLevel;
import xyz.urffer.urfquest.Logger;
import xyz.urffer.urfquest.client.Client;
import xyz.urffer.urfquest.client.guis.GUIAnchor;
import xyz.urffer.urfquest.client.guis.GUIContainer;

public class TextBox extends TextArea {
	
	public boolean respondsToMouseover = false;

	public TextBox(Client c, String text, int fontSize, int xDisplacement, int yDisplacement, GUIAnchor anchor, GUIContainer parent) {
		super(c, text, fontSize, xDisplacement, yDisplacement, anchor, parent);
	}
	
	public void draw(Graphics g) {
		g.setFont(new Font(Font.MONOSPACED, Font.BOLD, fontSize));
		
		if (respondsToMouseover && this.isMouseOver()) {
			g.setColor(Color.LIGHT_GRAY);
		} else {
			g.setColor(Color.WHITE);
		}
		
		g.drawString(this.text, bounds.x, bounds.y + bounds.height);
		
		if (this.client.getLogger().getLogLevel().compareTo(LogLevel.DEBUG) >= 0) {
			drawDebug(g);
		}
	}
}