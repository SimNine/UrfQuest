package xyz.urffer.urfquest.server.entities;

import java.awt.geom.Rectangle2D;

import xyz.urffer.urfquest.server.IDGenerator;
import xyz.urffer.urfquest.server.Server;
import xyz.urffer.urfquest.server.map.Map;
import xyz.urffer.urfquest.shared.Tile;
import xyz.urffer.urfquest.shared.Vector;
import xyz.urffer.urfquest.shared.protocol.messages.MessageEntityDestroy;
import xyz.urffer.urfquest.shared.protocol.messages.MessageEntitySetMoveVector;
import xyz.urffer.urfquest.shared.protocol.messages.MessageEntitySetPos;

public abstract class Entity {
	protected Server server;
	protected Map map;
	
	protected Rectangle2D.Double bounds;
	protected Vector movementVector;
	
	public int id;
	
	protected Entity(Server srv, Map m, double[] pos) {
		this.server = srv;
		
		this.id = IDGenerator.newID();
		
		this.bounds = new Rectangle2D.Double(pos[0], pos[1], 1, 1);
		this.movementVector = new Vector(0.0, 0.0);
		
		this.map = m;
	}
	
	public abstract void tick();
	
	public void destroy() {
		MessageEntityDestroy m = new MessageEntityDestroy();
		m.entityID = this.id;
		server.sendMessageToAllClients(m);
	}
	
	
	
	/*
	 * Position management
	 */
	
	public Vector getMovementVector() {
		return this.movementVector;
	}
	
	public void setMovementVector(double dirRadians, double magnitude) {
		this.setMovementVector(new Vector(dirRadians, magnitude));
	}
	
	public void setMovementVector(Vector vector) {
		this.movementVector = vector;
		
		MessageEntitySetMoveVector m = new MessageEntitySetMoveVector();
		m.vector = this.movementVector;
		m.entityID = this.id;
		server.sendMessageToAllClients(m);
	}
	
	public void attemptIncrementPos() {
		if (this.movementVector.magnitude == 0.0) {
			return;
		} else {
			double xComp = movementVector.magnitude*Math.cos(movementVector.dirRadians);
			double yComp = movementVector.magnitude*Math.sin(movementVector.dirRadians);
			attemptIncrementPos(xComp, yComp);
		}
	}
	
	public void attemptIncrementPos(double x, double y) {
		double newX = bounds.getCenterX() + x;
		double newY = bounds.getCenterY() + y;
		
		boolean canMove = true;
		
		// check if this move is valid on the x-axis
		if (!Tile.isWalkable(map.getTileAt((int)Math.floor(newX), (int)Math.floor(bounds.getCenterY())))) {
			canMove = false;
		}
		
		// check if this move is valid on the y-axis
		if (!Tile.isWalkable(map.getTileAt((int)Math.floor(bounds.getCenterX()), (int)Math.floor(newY)))) {
			canMove = false;
		}
				
		if (canMove) {
			this.incrementPos(x, y);
		} else {
			this.setPos(bounds.getX(), bounds.getY());
		}
	}
	
	// returns the tile at distance 'd' away from the center of this mob, in the direction it is facing
	public int[] tileAtDistance(double d) {
		double xComp = d*Math.cos(movementVector.dirRadians);
		double yComp = d*Math.sin(movementVector.dirRadians);
		return map.getTileAt((int)(bounds.getCenterX() + xComp), (int)(bounds.getCenterY() + yComp));
	}
	
	// returns the tile coords of the tile at the distance 'd' away form the center of this mob, in the direction it is facing
	public int[] tileCoordsAtDistance(double d) {
		double xComp = d*Math.cos(movementVector.dirRadians);
		double yComp = d*Math.sin(movementVector.dirRadians);
		
		int[] ret = new int[2];
		ret[0] = (int)(bounds.getCenterX() + xComp);
		ret[1] = (int)(bounds.getCenterY() + yComp);
		
		return ret;
	}
	
	public double getDirection() {
		return movementVector.dirRadians;
	}

	public void setDirection(double dirRadians) {
		this.setMovementVector(dirRadians, movementVector.magnitude);
	}
	
	public double getVelocity() {
		return movementVector.magnitude;
	}
	
	public void setVelocity(double velocity) {
		this.setMovementVector(movementVector.dirRadians, velocity);
	}
	
	public void incrementVelocity(double amt) {
		this.setVelocity(movementVector.magnitude + amt);
	}
	
	public void setPos(double x, double y) {
		bounds.setRect(x, y, bounds.getWidth(), bounds.getHeight());
		
		MessageEntitySetPos m = new MessageEntitySetPos();
		m.entityID = this.id;
		m.pos[0] = bounds.getX();
		m.pos[1] = bounds.getY();
		this.server.sendMessageToAllClients(m);
	}
	
	public void incrementPos(double x, double y) {
		this.setPos(bounds.getX() + x, bounds.getY() + y);
	}
	
	protected void incrementPos(Vector vector) {
		double xComp = vector.magnitude*Math.cos(vector.dirRadians);
		double yComp = vector.magnitude*Math.sin(vector.dirRadians);
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
			if (!Tile.isWalkable(this.server.getState().getSurfaceMap().getTileAt((int) xCurr, (int) yCurr))) {
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