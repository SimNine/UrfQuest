package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Ellipse2D;

import framework.V;

public class Ball extends Entity {

	public Ball(double x, double y, Color c, double diameter) {
		super(x, y, c, diameter);
		bounds = new Ellipse2D.Double(x - diameter/2, y - diameter/2, diameter, diameter);
		type = "ball";
		Xacceleration = 0.001;
		Yacceleration = 0.001;
	}
	
	// Draw methods
	protected void drawEntity(Graphics g) {
		g.setColor(color);
		g.fillOval((int)(V.dispCenterX - (V.playerPositionX - Xpos)*V.scale*10 - V.scale*diameter*.5),
				   (int)(V.dispCenterY - (V.playerPositionY - Ypos)*V.scale*10 - V.scale*diameter*.5), (int)(V.scale*diameter), (int)(V.scale*diameter));
	}
	
	// Update methods
	public void update() {
		if (Xpos > V.playerPositionX) {
			Xvelocity -= Xacceleration;
		} else if (Xpos < V.playerPositionX) {
			Xvelocity += Xacceleration;
		}
		if (Ypos > V.playerPositionY) {
			Yvelocity -= Yacceleration;
		} else if (Ypos < V.playerPositionY) {
			Yvelocity += Yacceleration;
		}
		
		Xvelocity += -(Xvelocity/1000);
		Yvelocity += -(Yvelocity/1000);
		
		Xpos += Xvelocity;
		Ypos += Yvelocity;
		
		bounds = new Ellipse2D.Double(Xpos - diameter/2, Ypos - diameter/2, diameter, diameter);
	}
}