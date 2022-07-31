package xyz.urffer.urfquest.client.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

import xyz.urffer.urfquest.LogLevel;
import xyz.urffer.urfquest.client.Client;
import xyz.urffer.urfquest.client.QuestPanel;
import xyz.urffer.urfquest.client.map.Map;
import xyz.urffer.urfquest.shared.Vector;

public abstract class Entity {
	protected Client client;
	
	protected Rectangle2D.Double bounds;
	protected Vector movementVector;
	
	protected Map map;
	
	public final int id;
	
	protected Entity(Client c, int id, Map m, double[] pos) {
		this.client = c;
		this.id = id;
		this.bounds = new Rectangle2D.Double(pos[0], pos[1], 1, 1);
		this.movementVector = new Vector(0, 0);
		this.map = m;
	}
	
	/*
	 * Position management
	 */
	
	public abstract void update();
	
	public void setPos(double x, double y) {
		bounds.setRect(x, y, bounds.getWidth(), bounds.getHeight());
	}
	
	public void incrementPos(double x, double y) {
		bounds.setRect(bounds.getX() + x, bounds.getY() + y, bounds.getWidth(), bounds.getHeight());
	}
	
	protected void incrementPos(Vector v) {
		double xComp = v.magnitude*Math.cos(Math.toRadians(v.magnitude));
		double yComp = v.magnitude*Math.sin(Math.toRadians(v.magnitude));
		this.incrementPos(xComp, yComp);
	}
	
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
	
	/*
	 * MovementVector management
	 */
	
	public void setMovementVector(Vector v) {
		this.movementVector = v;
	}
	
	public void setMovementVector(double dirRadians, double velocity) {
		this.movementVector = new Vector(dirRadians, velocity);
	}
	
	public void setDirection(double dirRadians) {
		this.movementVector.dirRadians = dirRadians;
	}
	
	public double getDirection() {
		return this.movementVector.dirRadians;
	}
	
	public void setVelocity(double s) {
		this.movementVector.magnitude = s;
	}
	
	public double getVelocity() {
		return this.movementVector.magnitude;
	}
	
	public void incrementVelocity(double amt) {
		setVelocity(this.movementVector.magnitude + amt);
	}
	
	/*
	 * Entity size management
	 */
	
	public double getWidth() {
		return bounds.getWidth();
	}
	
	public double getHeight() {
		return bounds.getHeight();
	}

	/*
	 * Misc
	 */
	
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
		if (this.client.getLogger().getLogLevel().compareTo(LogLevel.DEBUG) >= 0) {
			drawDebug(g);
			drawBounds(g);
		}
	}
	
	protected abstract void drawEntity(Graphics g);
	
	protected abstract void drawDebug(Graphics g);
	
	private void drawBounds(Graphics g) {
		g.setColor(Color.RED);
		g.drawRect((int) client.getPanel().gameToWindowX(bounds.getX()), 
				   (int) client.getPanel().gameToWindowY(bounds.getY()),
				   (int)(bounds.getWidth()*QuestPanel.TILE_WIDTH), 
				   (int)(bounds.getHeight()*QuestPanel.TILE_WIDTH));
	}
}