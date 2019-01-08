package guis.menus;

import java.awt.Graphics;

import framework.UrfQuest;
import guis.Clickable;

public class ImageButton extends ImageArea implements Clickable {

	public ImageButton(String source, int xDisplacement, int yDisplacement, int anchor) {
		super(source, xDisplacement, yDisplacement, anchor);
	}

	public void click() {}

	public void draw(Graphics g) {
		g.drawImage(this.image, bounds.x, bounds.y, null);
		
		if (UrfQuest.debug) {
			drawDebug(g);
		}
	}

}