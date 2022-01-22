package urfquest.client.guis.menus;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import urfquest.Logger;
import urfquest.client.Client;
import urfquest.client.guis.GUIAnchor;
import urfquest.client.guis.GUIContainer;

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
		
		if (this.client.getLogger().getLogLevel().compareTo(Logger.LogLevel.LOG_DEBUG) >= 0) {
			drawDebug(g);
		}
	}
}