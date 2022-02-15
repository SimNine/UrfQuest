package urfquest.client.guis.menus;

import java.awt.Graphics;

import urfquest.LogLevel;
import urfquest.Logger;
import urfquest.client.Client;
import urfquest.client.guis.GUIAnchor;
import urfquest.client.guis.GUIContainer;

public class ImageBox extends ImageArea {
	
	public ImageBox(Client c, String source, int xDisplacement, int yDisplacement, GUIAnchor anchor, GUIContainer parent) {
		super(c, source, xDisplacement, yDisplacement, anchor, parent);
	}

	public void draw(Graphics g) {
		g.drawImage(this.image,	bounds.x, bounds.y,	null);
		
		if (this.client.getLogger().getLogLevel().compareTo(LogLevel.DEBUG) >= 0) {
			drawDebug(g);
		}
	}
}