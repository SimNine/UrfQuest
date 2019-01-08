package entities.shapes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

import framework.V;

public class Square extends Shape{

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
		g.fillRect((int)(V.dispCenterX - (V.player.getPosition()[0] - Xpos)*V.tileWidth - V.tileWidth*diameter*.05),
				   (int)(V.dispCenterY - (V.player.getPosition()[1] - Ypos)*V.tileWidth - V.tileWidth*diameter*.05), 
				   (int)(V.tileWidth*0.1*diameter), (int)(V.tileWidth*0.1*diameter));
	}
	
	// Update methods
	public void update() {
		if (Xpos > V.player.getPosition()[0]) {
			Xpos -= Xvelocity;
		} else if (Xpos < V.player.getPosition()[0]) {
			Xpos += Xvelocity;
		}
		if (Ypos > V.player.getPosition()[1]) {
			Ypos -= Yvelocity;
		} else if (Ypos < V.player.getPosition()[1]) {
			Ypos += Yvelocity;
		}
		
		bounds = new Rectangle2D.Double(Xpos - diameter/2, Ypos - diameter/2, diameter, diameter);
	}
}