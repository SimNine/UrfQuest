package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

import framework.V;

public class Square extends Entity{

	public Square(double x, double y, Color c, double diameter) {
		super(x, y, c, diameter);
		bounds = new Rectangle2D.Double(x - diameter/2, y - diameter/2, diameter, diameter);
		type = "square";
		Xvelocity = 0.01;
		Yvelocity = 0.01;
	}
	
	// Draw methods
	protected void drawEntity(Graphics g) {
		g.setColor(color);
		g.fillRect((int)(V.dispCenterX - (V.playerPositionX - Xpos)*V.scale*10 - V.scale*diameter*.5),
				   (int)(V.dispCenterY - (V.playerPositionY - Ypos)*V.scale*10 - V.scale*diameter*.5), (int)(V.scale*diameter), (int)(V.scale*diameter));
	}
	
	// Update methods
	public void update() {
		if (Xpos > V.playerPositionX) {
			Xpos -= Xvelocity;
		} else if (Xpos < V.playerPositionX) {
			Xpos += Xvelocity;
		}
		if (Ypos > V.playerPositionY) {
			Ypos -= Yvelocity;
		} else if (Ypos < V.playerPositionY) {
			Ypos += Yvelocity;
		}
		
		bounds = new Rectangle2D.Double(Xpos - diameter/2, Ypos - diameter/2, diameter, diameter);
	}
}