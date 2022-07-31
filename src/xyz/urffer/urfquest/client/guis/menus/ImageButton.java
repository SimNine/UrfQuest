package xyz.urffer.urfquest.client.guis.menus;

import java.awt.Graphics;

import xyz.urffer.urfquest.LogLevel;
import xyz.urffer.urfquest.Logger;
import xyz.urffer.urfquest.client.Client;
import xyz.urffer.urfquest.client.guis.Clickable;
import xyz.urffer.urfquest.client.guis.GUIAnchor;
import xyz.urffer.urfquest.client.guis.GUIContainer;

public class ImageButton extends ImageArea implements Clickable {

	public ImageButton(Client c, String source, int xDisplacement, int yDisplacement, GUIAnchor anchor, GUIContainer parent) {
		super(c, source, xDisplacement, yDisplacement, anchor, parent);
	}

	public boolean click() {return true;}

	public void draw(Graphics g) {
		g.drawImage(this.image, bounds.x, bounds.y, null);
		
		if (this.client.getLogger().getLogLevel().compareTo(LogLevel.DEBUG) >= 0) {
			drawDebug(g);
		}
	}

}