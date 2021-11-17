package urfquest.client.guis.game;

import java.awt.Color;
import java.awt.Graphics;

import urfquest.Logger;
import urfquest.Main;
import urfquest.client.guis.GUIAnchor;
import urfquest.client.guis.GUIContainer;
import urfquest.client.guis.GUIObject;

public abstract class StatusBar extends GUIObject {
	private Color color;
	private boolean visibleByDefault = false;
	private int visibility = 0;
	
	public StatusBar(int xDisp, int yDisp, int width, int height, GUIAnchor anchor, Color col, boolean visibleByDefault, GUIContainer parent) {
		super(anchor, xDisp, yDisp, width, height, parent);
		this.color = col;
		this.visibleByDefault = visibleByDefault;
		if (visibleByDefault) {
			visibility = 255;
		}
	}
	
	public void update() {
		if (!visibleByDefault && visibility > 0) {
			visibility--;
		}
	}
	
	public void draw(Graphics g) {
		int borderWidth = 3;
		int gapWidth = 1;

		// draw background
		g.setColor(new Color(255, 255, 255, visibility/2));
		g.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
		
		// draw border
		g.setColor(new Color(255, 255, 255, visibility));
		for (int i = 0; i < borderWidth; i++) {
			g.drawRect(bounds.x + i, bounds.y + i, bounds.width - i*2, bounds.height - i*2);
		}
		
		// draw interior of bar
		g.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), visibility));
		int borderPlusGap = borderWidth + gapWidth;
		int pixelLength = (int)((bounds.width - borderPlusGap*2)*getPercentage());
		g.fillRect(bounds.x + borderPlusGap, bounds.y + borderPlusGap, pixelLength, bounds.height - borderPlusGap*2);
		
		if (Main.logger.getLogLevel().compareTo(Logger.LogLevel.LOG_DEBUG) >= 0) {
			drawDebug(g);
		}
	}
	
	// override this on a per-instance basis; it is needed to calculate the fullness of the bar
	protected abstract double getPercentage();
	
	public int[] getDims() {
		return new int[] {bounds.width, bounds.height};
	}
}