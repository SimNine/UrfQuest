package urfquest.client.guis.menus;

import java.awt.Graphics;

import urfquest.Logger;
import urfquest.Main;
import urfquest.client.guis.Clickable;
import urfquest.client.guis.GUIAnchor;
import urfquest.client.guis.GUIContainer;

public class ImageButton extends ImageArea implements Clickable {

	public ImageButton(String source, int xDisplacement, int yDisplacement, GUIAnchor anchor, GUIContainer parent) {
		super(source, xDisplacement, yDisplacement, anchor, parent);
	}

	public boolean click() {return true;}

	public void draw(Graphics g) {
		g.drawImage(this.image, bounds.x, bounds.y, null);
		
		if (Main.client.getLogger().getLogLevel().compareTo(Logger.LogLevel.LOG_DEBUG) >= 0) {
			drawDebug(g);
		}
	}

}