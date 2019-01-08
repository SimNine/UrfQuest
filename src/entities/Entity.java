package entities;

import java.awt.Graphics;
import framework.V;

public abstract class Entity {
	protected String type;
	
	protected String orientation;
	protected int moveStage = 0;

	protected double Xpos;
	protected double Ypos;
	
	public Entity(double x, double y) {
		Xpos = x;
		Ypos = y;
		orientation = "N";
	}
	
	// Drawing methods
	public void draw(Graphics g) {
		drawEntity(g);
		if (V.debug) drawDebug(g);
	}
	
	protected abstract void drawEntity(Graphics g);
	
	protected abstract void drawDebug(Graphics g);
	
	// Updating methods
	public abstract void update();
	
	public void setPosition(double x, double y) {
		Xpos = x;
		Ypos = y;
	}
	
	public double[] getPosition() {
		double[] ret = new double[2];
		ret[0] = Xpos;
		ret[1] = Ypos;
		return ret;
	}
	
	public String getType() {
		return type;
	}
}