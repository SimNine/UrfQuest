package guis;

import java.awt.Color;
import java.awt.Graphics;

import framework.UrfQuest;

public class Slider extends GUIObject {
	
	int position = 100;
	
	public Slider(int size, int xDisplacement, int yDisplacement) {
		this.xDisplacement = xDisplacement;
		this.yDisplacement = yDisplacement;
		this.width = size*5;
		this.height = size;
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.WHITE);
		g.drawLine(UrfQuest.panel.dispCenterX + xDisplacement, UrfQuest.panel.dispCenterY + yDisplacement,
				UrfQuest.panel.dispCenterX + xDisplacement, UrfQuest.panel.dispCenterY + yDisplacement - height);
		g.drawLine(UrfQuest.panel.dispCenterX + xDisplacement + width, UrfQuest.panel.dispCenterY + yDisplacement, 
				UrfQuest.panel.dispCenterX + xDisplacement + width, UrfQuest.panel.dispCenterY + yDisplacement - height);
		g.drawLine(UrfQuest.panel.dispCenterX + xDisplacement, UrfQuest.panel.dispCenterY + yDisplacement - height/2, 
				UrfQuest.panel.dispCenterX + xDisplacement + width, UrfQuest.panel.dispCenterY + yDisplacement - height/2);
		
		// Draw the slidebar
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(UrfQuest.panel.dispCenterX + xDisplacement + (int)((this.position/100.0)*(width - 20)), 
				   UrfQuest.panel.dispCenterY + yDisplacement - height, 
				   20, height);
		
		if (UrfQuest.debug) this.drawBoundingBox(g);
	}
	
	/* Gets the position of the mouse as an integer from 0 - 100
	 * representing the percentage of the slider to the left of the mouse
	 */
	public void setSliderPosition() {
		this.position = (int)(((UrfQuest.mousePos[0] - (UrfQuest.panel.dispCenterX + this.xDisplacement))/(width))*100);
	}

	public void click() {}

}