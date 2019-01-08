package entities.shapes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Ellipse2D;

import framework.V;

public class Ball extends Shape {

	public Ball(double x, double y, Color c, double diameter) {
		super(x, y, c, diameter);
		type = "ball";
		Xacceleration = 0.001;
		Yacceleration = 0.001;
	}
	
	// Draw methods
	protected void drawEntity(Graphics g) {
		g.setColor(color);
		g.fillOval((int)(V.dispCenterX - (V.player.getPosition()[0] - Xpos)*V.tileWidth - V.tileWidth*diameter*.05),
				   (int)(V.dispCenterY - (V.player.getPosition()[1] - Ypos)*V.tileWidth - V.tileWidth*diameter*.05), 
				   (int)(V.tileWidth*0.1*diameter), (int)(V.tileWidth*0.1*diameter));
	}
	
	// Update methods
	public void update() {
		if (Xpos > V.player.getPosition()[0]) {
			Xvelocity -= Xacceleration;
		} else if (Xpos < V.player.getPosition()[0]) {
			Xvelocity += Xacceleration;
		}
		if (Ypos > V.player.getPosition()[1]) {
			Yvelocity -= Yacceleration;
		} else if (Ypos < V.player.getPosition()[1]) {
			Yvelocity += Yacceleration;
		}
		
		Xvelocity += -(Xvelocity/1000);
		Yvelocity += -(Yvelocity/1000);
		
		Xpos += Xvelocity;
		Ypos += Yvelocity;
	}
}