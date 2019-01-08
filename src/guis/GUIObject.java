package guis;

import java.awt.Color;
import java.awt.Graphics;

import framework.UrfQuest;

public abstract class GUIObject {
	protected int xDisplacement, yDisplacement;
	protected int width, height;
	
	public boolean isMouseOver() {
		return (UrfQuest.mousePos[0] >= UrfQuest.panel.dispCenterX + this.xDisplacement &&
				UrfQuest.mousePos[0] <= UrfQuest.panel.dispCenterX + this.xDisplacement + width &&
				UrfQuest.mousePos[1] >= UrfQuest.panel.dispCenterY + this.yDisplacement - height &&
				UrfQuest.mousePos[1] <= UrfQuest.panel.dispCenterY + this.yDisplacement);
	};
	
	public abstract void click();
	
	public abstract void draw(Graphics g);

	public void drawBoundingBox(Graphics g) {
		if (this.isMouseOver()) g.setColor(Color.GREEN);
		else g.setColor(Color.BLUE);
		
		g.drawRect(UrfQuest.panel.dispCenterX + xDisplacement, 
				   UrfQuest.panel.dispCenterY + yDisplacement - height, 
				   width, height);
	}

}
