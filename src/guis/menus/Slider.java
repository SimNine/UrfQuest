package guis.menus;

import java.awt.Color;
import java.awt.Graphics;

import framework.UrfQuest;
import guis.Clickable;
import guis.GUIObject;

public class Slider extends GUIObject implements Clickable {
	private int sliderPos = 100;
	
	public Slider(int size, int xDisp, int yDisp, int anchor) {
		super(anchor, xDisp, yDisp, size*5, size);
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.WHITE);
		g.drawLine(bounds.x, bounds.y, bounds.x, bounds.y + bounds.height);
		g.drawLine(bounds.x + bounds.width, bounds.y, bounds.x + bounds.width, bounds.y + bounds.height);
		g.drawLine(bounds.x, bounds.y + bounds.height/2, bounds.x + bounds.width, bounds.y + bounds.height/2);
		
		// Draw the slidebar
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(bounds.x + (int)((this.sliderPos/100.0)*(bounds.width - 20)), bounds.y, 20, bounds.height);
		
		if (UrfQuest.debug) {
			drawDebug(g);
		};
	}
	
	/* Gets the position of the mouse as an integer from 0 - 100
	 * representing the percentage of the slider to the left of the mouse
	 */
	public void setSliderPosition() {
		this.sliderPos = (int)(((UrfQuest.mousePos[0] - (xAnchor() + this.xDisplacement))/(bounds.width))*100);
	}

	public void click() {}

}