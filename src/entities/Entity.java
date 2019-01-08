package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Shape;
import java.awt.geom.Area;
import java.util.ArrayList;

import framework.V;

public abstract class Entity {
	protected String type;
	
	protected String orientation;
	protected Shape bounds;
	protected double moveStage = 0;

	protected double Xpos;
	protected double Ypos;
	protected double Xvelocity = 0;
	protected double Yvelocity = 0;
	protected double Xacceleration = 0;
	protected double Yacceleration = 0;

	public Entity(double x, double y) {
		Xpos = x;
		Ypos = y;
	}
	
	// Drawing methods
	public void draw(Graphics g) {
		drawEntity(g);
		if (V.debug) drawPhysics(g);
	}
	
	protected abstract void drawEntity(Graphics g);
	
	private void drawPhysics(Graphics g) {
		g.setColor(Color.BLACK);
		g.drawString("["+Xpos+","+Ypos+"]",
					(int)(V.dispCenterX - (V.player.getPosition()[0] - Xpos)*V.scale*10),
					(int)(V.dispCenterY - (V.player.getPosition()[1] - Ypos)*V.scale*10 + 10));
		g.drawString("["+Xvelocity+","+Yvelocity+"]",
					(int)(V.dispCenterX - (V.player.getPosition()[0] - Xpos)*V.scale*10),
					(int)(V.dispCenterY - (V.player.getPosition()[1] - Ypos)*V.scale*10 + 20));
		g.drawString("["+Xacceleration+","+Yacceleration+"]",
					(int)(V.dispCenterX - (V.player.getPosition()[0] - Xpos)*V.scale*10),
					(int)(V.dispCenterY - (V.player.getPosition()[1] - Ypos)*V.scale*10 + 30));
	}
	
	// Updating methods
	public abstract void update();
	
	public void setPosition(double x, double y) {
		Xpos = x;
		Ypos = y;
	}
	
	public void setVelocity(double x, double y) {
		Xvelocity = x;
		Yvelocity = y;
	}
	
	public void setAcceleration(double x, double y) {
		Xacceleration = x;
		Yacceleration = y;
	}
	
	public double[] getPosition() {
		double[] ret = new double[2];
		ret[0] = Xpos;
		ret[1] = Ypos;
		return ret;
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
	
	public String getType() {
		return type;
	}
}