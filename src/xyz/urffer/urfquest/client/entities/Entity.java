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
	
	protected Rectangle2D.Double bounds = new Rectangle2D.Double(0, 0, 1, 1);
	protected int mapID = 0;
	protected Vector movementVector = new Vector(0, 0);
	
	public final int id;
	
	protected Entity(Client c, int id) {
		this.client = c;
		this.id = id;
	}
	
	
	
	/*
	 * Position management
	 */
	
	public abstract void update();
	
	public void setPos(PairDouble pos) {
		bounds.setRect(pos.x, pos.y, bounds.getWidth(), bounds.getHeight());
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
	
	public double getDirection() {
		return this.movementVector.dirRadians;
	}
	
	public double getVelocity() {
		return this.movementVector.magnitude;
	}
	
	
	
	/*
	 * Entity size management
	 */
	
	public PairDouble getDims() {
		return new PairDouble(bounds.width, bounds.height);
	}
	
	public void setDims(PairDouble dims) {
		this.bounds.width = dims.x;
		this.bounds.height = dims.y;
	}

	
	
	/*
	 * Misc
	 */
	
	public int getMapID() {
		return this.mapID;
	}
	
	public void setMapID(int mapID) {
		this.mapID = mapID;
	}
	
	protected Map getMap() {
		Map currentClientMap = this.client.getState().getCurrentMap();
		if (this.mapID == currentClientMap.id) {
			return currentClientMap;
		} else {
			return null;
		}
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