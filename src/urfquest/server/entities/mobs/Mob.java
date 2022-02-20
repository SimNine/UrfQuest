package urfquest.server.entities.mobs;

import urfquest.server.Server;
import urfquest.server.entities.Entity;
import urfquest.server.entities.mobs.ai.routines.MobRoutine;
import urfquest.server.map.Map;
import urfquest.server.tiles.Tiles;
import urfquest.shared.Vector;

public abstract class Mob extends Entity {
	protected final static String assetPath = "/assets/entities/";
	
	protected Vector movementVector = new Vector(0.0, 0.0);
	
	protected double defaultVelocity;
	
	protected double health;
	protected double mana;
	protected double fullness;
	protected double maxHealth;
	protected double maxMana;
	protected double maxFullness;
	protected int healthbarVisibility = 0;
	
	protected MobRoutine routine;

	protected Mob(Server srv, Map m, double[] pos) {
		super(srv, m, pos);
	}
	
	public void setMovementVector(Vector vector) {
		this.movementVector = vector;
	}

	public abstract void tick();
	
	public void onDeath() {
		// do nothing by default
	}
	
	/*
	 * Position management
	 */
	
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
		if (!Tiles.isWalkable(map.getTileTypeAt((int)Math.floor(newX), (int)Math.floor(bounds.getCenterY())))) {
			canMove = false;
		}
		
		// check if this move is valid on the y-axis
		if (!Tiles.isWalkable(map.getTileTypeAt((int)Math.floor(bounds.getCenterX()), (int)Math.floor(newY)))) {
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
		movementVector.dirRadians = dirRadians;
	}
	
	public double getVelocity() {
		return movementVector.magnitude;
	}
	
	public double getDefaultVelocity() {
		return defaultVelocity;
	}
	
	public void setVelocity(double s) {
		movementVector.magnitude = s;
	}
	
	public void incrementVelocity(double amt) {
		setVelocity(movementVector.magnitude + amt);
	}
	
	/*
	 * Stat management
	 */
	
	public void setHealth(double h) {
		health = h;
		healthbarVisibility = 500;
	}
	
	public double getHealth() {
		return health;
	}
	
	public void incrementHealth(double amt) {
		setHealth(health + amt);
	}
	
	public void incrementMana(double amt) {
		setMana(mana + amt);
	}
	
	public void setMana(double m) {
		if (m > maxMana) {
			mana = maxMana;
		} else {
			mana = m;
		}
	}
	
	public double getMana() {
		return mana;
	}
	
	public double getMaxMana() {
		return maxMana;
	}
	
	public void incrementFullness(double amt) {
		setFullness(fullness + amt);
	}
	
	public void setFullness(double f) {
		if (f > maxFullness) {
			fullness = maxFullness;
		} else {
			fullness = f;
		}
	}
	
	public double getFullness() {
		return fullness;
	}
	
	public double getMaxFullness() {
		return maxFullness;
	}
	
	public boolean isDead() {
		return health <= 0;
	}
	
	public void setMap(Map m) {
		map.removeMob(this);
		m.addMob(this);
		map = m;
	}
}
