package xyz.urffer.urfquest.client.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

import xyz.urffer.urfutils.math.PairDouble;
import xyz.urffer.urfutils.math.PairInt;

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
	
	protected Entity(Client c, int id, Map m, PairDouble pos) {
		this.client = c;
		this.id = id;
		this.bounds = new Rectangle2D.Double(pos.x, pos.y, 1, 1);
		this.movementVector = new Vector(0, 0);
		this.map = m;
	}
	
	/*
	 * Position management
	 */
	
	public abstract void update();
	
	public void setPos(PairDouble pos) {
		bounds.setRect(pos.x, pos.y, bounds.getWidth(), bounds.getHeight());
	}
	
	public void incrementPos(PairDouble pos) {
		bounds.setRect(
			bounds.getX() + pos.x,
			bounds.getY() + pos.y,
			bounds.getWidth(),
			bounds.getHeight()
		);
	}
	
	protected void incrementPos(Vector v) {
		double xComp = v.magnitude*Math.cos(Math.toRadians(v.magnitude));
		double yComp = v.magnitude*Math.sin(Math.toRadians(v.magnitude));
		this.incrementPos(new PairDouble(xComp, yComp));
	}
	
	public PairDouble getPos() {
		return new PairDouble(bounds.getX(), bounds.getY());
	}
	
	public PairDouble getCenter() {
		return new PairDouble(bounds.getCenterX(), bounds.getCenterY());
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
	
	public PairDouble getDims() {
		return new PairDouble(bounds.width, bounds.height);
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
	public boolean containsPoint(PairDouble pos) {
		return bounds.contains(pos.x, pos.y);
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
		PairDouble boundsPos = new PairDouble(bounds.getX(), bounds.getY());
		PairInt boundsPixel = client.getPanel().gameToWindow(boundsPos);
		g.drawRect(boundsPixel.x, 
				   boundsPixel.y,
				   (int)(bounds.getWidth()*QuestPanel.TILE_WIDTH), 
				   (int)(bounds.getHeight()*QuestPanel.TILE_WIDTH));
	}
}