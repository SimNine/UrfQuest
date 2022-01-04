package urfquest.client.guis.menus;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import urfquest.Logger;
import urfquest.Main;
import urfquest.client.guis.Clickable;
import urfquest.client.guis.GUIAnchor;
import urfquest.client.guis.GUIContainer;

public abstract class TextButton extends TextArea implements Clickable {

	public TextButton(String text, int fontSize, int xDisplacement, int yDisplacement, GUIAnchor anchor, GUIContainer parent) {
		super(text, fontSize, xDisplacement, yDisplacement, anchor, parent);
	}
	
	public abstract boolean click();
	
	public void draw(Graphics g) {
		g.setFont(new Font(Font.MONOSPACED, Font.BOLD, fontSize));
		
		if (this.isMouseOver()) g.setColor(Color.LIGHT_GRAY);
		else g.setColor(Color.WHITE);
		
		g.drawString(text, bounds.x, bounds.y + bounds.height);
		
		if (Main.client.getLogger().getLogLevel().compareTo(Logger.LogLevel.LOG_DEBUG) >= 0) {
			drawDebug(g);
		}
	}
	
}