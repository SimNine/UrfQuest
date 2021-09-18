package urfquest.client.guis.menus;

import java.awt.Color;
import java.awt.Graphics;

import urfquest.Logger;
import urfquest.Main;
import urfquest.client.guis.Clickable;
import urfquest.client.guis.GUIContainer;
import urfquest.client.guis.GUIObject;

public class Slider extends GUIObject implements Clickable {
	private int sliderPos = 0;
	private int lineThickness = 3;
	
	public Slider(int size, int xDisp, int yDisp, int anchor, GUIContainer parent) {
		super(anchor, xDisp, yDisp, size*5, size, parent);
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.WHITE);
		
		// draw crossbar
		for (int i = -lineThickness/2; i < lineThickness/2 + 1; i++) {
			g.drawLine(bounds.x, bounds.y + bounds.height/2 + i, bounds.x + bounds.width, bounds.y + bounds.height/2 + i);
		}
		
		// draw left side bar
		for (int i = 0; i < lineThickness; i++) {
			g.drawLine(bounds.x + i, bounds.y, bounds.x + i, bounds.y + bounds.height);
		}
		
		// draw right side bar
		for (int i = 0; i < lineThickness; i++) {
			g.drawLine(bounds.x + bounds.width - i, bounds.y, bounds.x + bounds.width - i, bounds.y + bounds.height);
		}
		
		// Draw the slider
		int sliderWidth = 20;
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(bounds.x + sliderPos - sliderWidth/2, bounds.y, sliderWidth, bounds.height + 1);
		
		if (Main.logger.getLogLevel().compareTo(Logger.LogLevel.LOG_DEBUG) >= 0) {
			drawDebug(g);
		};
	}
	
	/* Gets the position of the mouse as an integer from 0 - 100
	 * representing the percentage of the slider to the left of the mouse
	 */
	public void setSliderPosition() {
		this.sliderPos = Main.panel.mousePos[0] - bounds.x;
		//this.sliderPos = (int)(((UrfQuest.mousePos[0] - (xAnchor() + this.xDisplacement))/(bounds.width))*100);
	}
	
	public double getPercentage() {
		return (((double)sliderPos - bounds.x)/(double)bounds.width);
	}

	public boolean click() {return true;}

}