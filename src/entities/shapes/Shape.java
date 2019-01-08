package entities.shapes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

import entities.Entity;
import framework.V;

public abstract class Shape extends Entity {
	protected Color color;
	protected double diameter;
	protected double Xvelocity = 0;
	protected double Yvelocity = 0;
	protected double Xacceleration = 0;
	protected double Yacceleration = 0;

	public Shape(double x, double y, Color c, double diameter) {
		super(x, y);
		color = c;
		this.diameter = diameter;
		this.bounds = new Rectangle2D.Double(x, y, diameter, diameter);
	}

	@Override
	protected abstract void drawEntity(Graphics g);
	
	protected void drawDebug(Graphics g) {
		g.setColor(Color.BLACK);
		g.drawString("["+Xpos+","+Ypos+"]",
					(int)(V.dispCenterX - (V.player.getPosition()[0] - Xpos)*V.tileWidth),
					(int)(V.dispCenterY - (V.player.getPosition()[1] - Ypos)*V.tileWidth + 10));
		g.drawString("["+Xvelocity+","+Yvelocity+"]",
					(int)(V.dispCenterX - (V.player.getPosition()[0] - Xpos)*V.tileWidth),
					(int)(V.dispCenterY - (V.player.getPosition()[1] - Ypos)*V.tileWidth + 20));
		g.drawString("["+Xacceleration+","+Yacceleration+"]",
					(int)(V.dispCenterX - (V.player.getPosition()[0] - Xpos)*V.tileWidth),
					(int)(V.dispCenterY - (V.player.getPosition()[1] - Ypos)*V.tileWidth + 30));
	}

	@Override
	public abstract void update();
	
	public Color getColor() {
		return color;
	}
	
	public double getDiameter() {
		return diameter;
	}

	public void setVelocity(double x, double y) {
		Xvelocity = x;
		Yvelocity = y;
	}

	public void setAcceleration(double x, double y) {
		Xacceleration = x;
		Yacceleration = y;
	}

	public double[] getVelocity() {
		double[] ret = new double[2];
		ret[0] = Xvelocity;
		ret[1] = Yvelocity;
		return ret;
	}

	public double[] getAcceleration() {
		double[] ret = new double[2];
		ret[0] = Xacceleration;
		ret[1] = Yacceleration;
		return ret;
	}

}
