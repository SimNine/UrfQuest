package xyz.urffer.urfquest.server.entities;

import java.awt.geom.Rectangle2D;

import xyz.urffer.urfquest.server.IDGenerator;
import xyz.urffer.urfquest.server.Server;
import xyz.urffer.urfquest.server.map.Map;
import xyz.urffer.urfquest.shared.PairDouble;
import xyz.urffer.urfquest.shared.PairInt;
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
	
	protected Entity(Server srv, Map m, PairDouble pos) {
		this.server = srv;
		
		this.id = IDGenerator.newID();
		
		this.bounds = new Rectangle2D.Double(pos.x, pos.y, 1, 1);
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
			attemptIncrementPos(new PairDouble(xComp, yComp));
		}
	}
	
	public void attemptIncrementPos(PairDouble delta) {
		double newX = bounds.getCenterX() + delta.x;
		double newY = bounds.getCenterY() + delta.y;
		
		boolean canMove = true;
		
		// check if this move is valid on the x-axis
		if (!Tile.isWalkable(map.getTileAt(new PairDouble(newX, bounds.getCenterY()).floor()))) {
			canMove = false;
		}
		
		// check if this move is valid on the y-axis
		if (!Tile.isWalkable(map.getTileAt(new PairDouble(bounds.getCenterX(), newY).floor()))) {
			canMove = false;
		}
				
		if (canMove) {
			this.incrementPos(delta);
		} else {
			this.setPos(this.getPos());
		}
	}
	
	// returns the tile at distance 'd' away from the center of this mob, in the direction it is facing
	public Tile tileAtDistance(double d) {
		double xComp = d*Math.cos(movementVector.dirRadians);
		double yComp = d*Math.sin(movementVector.dirRadians);
		return map.getTileAt(new PairDouble(bounds.getCenterX() + xComp, bounds.getCenterY() + yComp).floor());
	}
	
	// returns the tile coords of the tile at the distance 'd' away form the center of this mob, in the direction it is facing
	public PairInt tileCoordsAtDistance(double d) {
		double xComp = d*Math.cos(movementVector.dirRadians);
		double yComp = d*Math.sin(movementVector.dirRadians);
		
		PairInt ret = new PairDouble(
			bounds.getCenterX() + xComp,
			bounds.getCenterY() + yComp
		).toInt();
		
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
	
	public void setPos(PairDouble pos) {
		bounds.setRect(pos.x, pos.y, bounds.getWidth(), bounds.getHeight());
		
		MessageEntitySetPos m = new MessageEntitySetPos();
		m.entityID = this.id;
		m.pos = this.getPos();
		this.server.sendMessageToAllClients(m);
	}
	
	public void incrementPos(PairDouble delta) {
		this.setPos(this.getPos().add(delta));
	}
	
	protected void incrementPos(Vector vector) {
		double xComp = vector.magnitude*Math.cos(vector.dirRadians);
		double yComp = vector.magnitude*Math.sin(vector.dirRadians);
		this.incrementPos(new PairDouble(xComp, yComp));
	}
	
	public PairDouble getPos() {
		PairDouble ret = new PairDouble(
			bounds.getX(),
			bounds.getY()
		);
		return ret;
	}
	
	public PairDouble getCenter() {
		PairDouble ret = new PairDouble(
			bounds.getCenterX(),
			bounds.getCenterY()
		);
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
		if (other.getCenter().x > this.getCenter().x + distance ||
			other.getCenter().x < this.getCenter().x - distance ||
			other.getCenter().y > this.getCenter().y + distance ||
			other.getCenter().y < this.getCenter().y - distance) {
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
		double denom = this.getCenter().x - e.getCenter().x;
		double num = this.getCenter().y - e.getCenter().y;
		
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
		PairDouble step = new PairDouble (xComp, yComp);
		
		double distTo = distanceTo(e);
		double numSteps = distTo / checkStepSize;
		
		PairDouble posCurr = this.getCenter();
		for (int i = 0; i < numSteps; i++) {
			if (!Tile.isWalkable(this.server.getState().getSurfaceMap().getTileAt(posCurr.toInt()))) {
				return false;
			}
			posCurr = posCurr.add(step);
		}
		
		return true;
	}
	
	// returns whether the given coordinates are within the entity's bounding box
	public boolean containsPoint(PairDouble pos) {
		return bounds.contains(pos.x, pos.y);
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