package guis.menus;

import java.awt.Graphics;

import framework.UrfQuest;

public class ImageBox extends ImageArea {
	
	public ImageBox(String source, int xDisplacement, int yDisplacement, int anchor) {
		super(source, xDisplacement, yDisplacement, anchor);
	}

	public void draw(Graphics g) {
		g.drawImage(this.image,	bounds.x, bounds.y,	null);
		
		if (UrfQuest.debug) {
			drawDebug(g);
		}
	}

}