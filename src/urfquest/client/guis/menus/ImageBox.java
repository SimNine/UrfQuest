package urfquest.client.guis.menus;

import java.awt.Graphics;

import urfquest.Main;
import urfquest.client.guis.GUIContainer;

public class ImageBox extends ImageArea {
	
	public ImageBox(String source, int xDisplacement, int yDisplacement, int anchor, GUIContainer parent) {
		super(source, xDisplacement, yDisplacement, anchor, parent);
	}

	public void draw(Graphics g) {
		g.drawImage(this.image,	bounds.x, bounds.y,	null);
		
		if (Main.debug) {
			drawDebug(g);
		}
	}
}