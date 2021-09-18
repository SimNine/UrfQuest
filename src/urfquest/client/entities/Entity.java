package urfquest.client.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

import urfquest.Logger;
import urfquest.Main;
import urfquest.client.QuestPanel;
import urfquest.client.map.Map;

public abstract class Entity {
	protected Rectangle2D.Double bounds;
	protected Map map;
	
	protected Entity(double x, double y, Map m) {
		bounds = new Rectangle2D.Double(x, y, 1, 1);
		map = m;
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
	public Map getMap() {
		return map;
	}
	
	// interactions with other entities
	// -----
	// returns true if this entity's bounds intersect with another entity's bounds
	public boolean collides(Entity e) {
		return bounds.intersects(e.bounds);
	}
	
	// returns whether the given coordinates are within the entity's bounding box
	public boolean containsPoint(double x, double y) {
		return bounds.contains(x, y);
	}

	/*
	 * Drawing methods
	 */
	public void draw(Graphics g) {
		drawEntity(g);
		if (Main.logger.getLogLevel().compareTo(Logger.LogLevel.LOG_DEBUG) >= 0) {
			drawDebug(g);
			drawBounds(g);
		}
	}
	
	protected abstract void drawEntity(Graphics g);
	
	protected abstract void drawDebug(Graphics g);
	
	private void drawBounds(Graphics g) {
		g.setColor(Color.RED);
		g.drawRect((int) Main.panel.gameToWindowX(bounds.getX()), 
				   (int) Main.panel.gameToWindowY(bounds.getY()),
				   (int)(bounds.getWidth()*QuestPanel.TILE_WIDTH), 
				   (int)(bounds.getHeight()*QuestPanel.TILE_WIDTH));
	}
}