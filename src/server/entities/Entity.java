package server.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

import framework.QuestPanel;
import framework.UrfQuest;

import server.game.QuestMap;
import server.tiles.Tiles;

public abstract class Entity {
	protected Rectangle2D.Double bounds;
	protected int animStage = 0;
	protected QuestMap map;
	
	protected Entity(double x, double y, QuestMap m) {
		bounds = new Rectangle2D.Double(x, y, 1, 1);
		map = m;
	}
	
	// Drawing methods
	public void draw(Graphics g) {
		drawEntity(g);
		if (UrfQuest.debug) {
			drawDebug(g);
			drawBounds(g);
		}
	}
	
	protected abstract void drawEntity(Graphics g);
	
	protected abstract void drawDebug(Graphics g);
	
	private void drawBounds(Graphics g) {
		g.setColor(Color.RED);
		g.drawRect((int) UrfQuest.panel.gameToWindowX(bounds.getX()), 
				   (int) UrfQuest.panel.gameToWindowY(bounds.getY()),
				   (int)(bounds.getWidth()*QuestPanel.TILE_WIDTH), 
				   (int)(bounds.getHeight()*QuestPanel.TILE_WIDTH));
	}
	
	// Updating methods
	public abstract void update();
	
	// sets the entity's position, NOT checking for validity of move
	public void setPos(double x, double y) {
		bounds.setRect(x, y, bounds.getWidth(), bounds.getHeight());
	}
	
	// moves the entity, NOT checking for validity of move
	// object's position is incremented according to the parameters
	public void move(double x, double y) {
		bounds.setRect(bounds.getX() + x, bounds.getY() + y, bounds.getWidth(), bounds.getHeight());
	}
	
	// moves the entity, NOT checking for validity of move
	protected void move(int direction, double magnitude) {
		double newX = bounds.getX();
		double newY = bounds.getY();
		double xComp = magnitude*Math.cos(Math.toRadians(direction));
		double yComp = magnitude*Math.sin(Math.toRadians(direction));
		
		newX += xComp;
		newY += yComp;
		
		bounds.setRect(newX, newY, bounds.getWidth(), bounds.getHeight());
	}
	
	// gets the object's position as a double array with length 2 (x, y)
	public double[] getPos() {
		double[] ret = new double[2];
		ret[0] = bounds.getX();
		ret[1] = bounds.getY();
		return ret;
	}
	
	public double[] getCenter() {
		double[] ret = new double[2];
		ret[0] = bounds.getCenterX();
		ret[1] = bounds.getCenterY();
		return ret;
	}
	
	public double getWidth() {
		return bounds.getWidth();
	}
	
	public double getHeight() {
		return bounds.getHeight();
	}

	// map methods
	public QuestMap getMap() {
		return map;
	}
	
	// interactions with other entities
	// -----
	// returns true if this entity's bounds intersect with another entity's bounds
	public boolean collides(Entity e) {
		return bounds.intersects(e.bounds);
	}
	
	// returns whether this entity's center is within a certain distance of another's
	public boolean isWithinDistance(Entity other, double distance) {
		if (other.getCenter()[0] > this.getCenter()[0] + distance ||
			other.getCenter()[0] < this.getCenter()[0] - distance ||
			other.getCenter()[1] > this.getCenter()[1] + distance ||
			other.getCenter()[1] < this.getCenter()[1] - distance) {
			return false;
		} else {
			return (distanceTo(other) <= distance);
		}
	}
	
	// returns the distance from this entity's center to another's
	public double distanceTo(Entity e) {
		return Math.sqrt(
						 Math.pow(this.bounds.getCenterX() - e.bounds.getCenterX(), 2) +
						 Math.pow(this.bounds.getCenterY() - e.bounds.getCenterY(), 2)
						);
	}
	
	// returns the angle (going clockwise, starting at cardinal east) from this entity's center to another
	public int angleTo(Entity e) {
		double denom = this.getCenter()[0] - e.getCenter()[0];
		double num = this.getCenter()[1] - e.getCenter()[1];
		
		int angle = 0;
		if (denom > 0) {
			angle += 180;
		}
		
		angle += (int)(Math.toDegrees(Math.atan(num / denom)));
		return angle;
	}
	
	// returns whether this entity has an unobstructed path to the other
	// WARNING: computationally expensive
	public boolean hasClearPathTo(Entity e) {
		double checkStepSize = 0.5; // raise this to decrease precision and increase performance
		
		int angleTo = angleTo(e);
		double xComp = checkStepSize*Math.cos(Math.toRadians(angleTo));
		double yComp = checkStepSize*Math.sin(Math.toRadians(angleTo));
		
		double distTo = distanceTo(e);
		double numSteps = distTo / checkStepSize;
		
		double xCurr = this.getCenter()[0];
		double yCurr = this.getCenter()[1];
		for (int i = 0; i < numSteps; i++) {
			if (!Tiles.isWalkable(UrfQuest.game.getCurrMap().getTileTypeAt((int) xCurr, (int) yCurr))) {
				return false;
			}
			xCurr += xComp;
			yCurr += yComp;
		}
		
		return true;
	}
	
	// returns whether the given coordinates are within the entity's bounding box
	public boolean containsPoint(double x, double y) {
		return bounds.contains(x, y);
	}
}