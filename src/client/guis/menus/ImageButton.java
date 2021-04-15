package client.guis.menus;

import java.awt.Graphics;

import framework.UrfQuest;
import guis.Clickable;
import guis.GUIContainer;

public class ImageButton extends ImageArea implements Clickable {

	public ImageButton(String source, int xDisplacement, int yDisplacement, int anchor, GUIContainer parent) {
		super(source, xDisplacement, yDisplacement, anchor, parent);
	}

	public boolean click() {return true;}

	public void draw(Graphics g) {
		g.drawImage(this.image, bounds.x, bounds.y, null);
		
		if (UrfQuest.debug) {
			drawDebug(g);
		}
	}

}