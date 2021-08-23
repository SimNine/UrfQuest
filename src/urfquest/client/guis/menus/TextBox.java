package urfquest.client.guis.menus;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import urfquest.Logger;
import urfquest.Main;
import urfquest.client.guis.GUIContainer;

public class TextBox extends TextArea {
	
	public boolean respondsToMouseover = false;

	public TextBox(String text, int fontSize, int xDisplacement, int yDisplacement, int anchor, GUIContainer parent) {
		super(text, fontSize, xDisplacement, yDisplacement, anchor, parent);
	}
	
	public void draw(Graphics g) {
		g.setFont(new Font(Font.MONOSPACED, Font.BOLD, fontSize));
		
		if (respondsToMouseover && this.isMouseOver()) {
			g.setColor(Color.LIGHT_GRAY);
		} else {
			g.setColor(Color.WHITE);
		}
		
		g.drawString(this.text, bounds.x, bounds.y + bounds.height);
		
		if (Main.logger.getLogLevel() > Logger.LOG_DEBUG) {
			drawDebug(g);
		}
	}
}