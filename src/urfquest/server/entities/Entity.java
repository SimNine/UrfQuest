package urfquest.server.entities;

import java.awt.geom.Rectangle2D;

import urfquest.IDGenerator;
import urfquest.server.Server;
import urfquest.server.map.Map;
import urfquest.server.tiles.Tiles;
import urfquest.shared.Vector;
import urfquest.shared.message.Message;
import urfquest.shared.message.MessageType;

public abstract class Entity {
	protected Server server;
	protected Map map;
	
	protected Rectangle2D.Double bounds;
	
	public int id;
	
	protected Entity(Server srv, Map m, double[] pos) {
		this.server = srv;
		
		id = IDGenerator.newID();
		
		map = m;
		
		bounds = new Rectangle2D.Double(pos[0], pos[1], 1, 1);
	}
	
	public abstract void tick();
	
	
	
	/*
	 * Position management
	 */
	
	public void setPos(double x, double y) {
		bounds.setRect(x, y, bounds.getWidth(), bounds.getHeight());
		
		Message m = new Message();
		m.type = MessageType.ENTITY_SET_POS;
		m.entityID = this.id;
		m.pos[0] = bounds.getX();
		m.pos[1] = bounds.getY();
		this.server.sendMessageToAllClients(m);
	}
	
	public void incrementPos(double x, double y) {
		this.setPos(bounds.getX() + x, bounds.getY() + y);
	}
	
	protected void incrementPos(Vector vector) {
		double newX = bounds.getX();
		double newY = bounds.getY();
		double xComp = vector.magnitude*Math.cos(vector.dirRadians);
		double yComp = vector.magnitude*Math.sin(vector.dirRadians);
		
		newX += xComp;
		newY += yComp;
		
		this.setPos(newX, newY);
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
	 * Entity angle and collision checking
	 */
	
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
			if (!Tiles.isWalkable(this.server.getState().getSurfaceMap().getTileTypeAt((int) xCurr, (int) yCurr))) {
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
	
	
	
	/*
	 * Misc methods
	 */
	
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
}