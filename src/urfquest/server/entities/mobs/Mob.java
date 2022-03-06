package urfquest.server.entities.mobs;

import urfquest.server.Server;
import urfquest.server.entities.Entity;
import urfquest.server.entities.mobs.ai.routines.MobRoutine;
import urfquest.server.map.Map;
import urfquest.shared.Vector;

public abstract class Mob extends Entity {
	protected final static String assetPath = "/assets/entities/";
	
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

	public abstract void tick();
	
	public void onDeath() {
		// do nothing by default
	}
	
	public abstract double getBaseSpeed();
	
	public void updateMovementVector(Vector v) {
		if (v != this.movementVector) {
			if (v.magnitude == 0) {
				v.dirRadians = this.movementVector.dirRadians;
			}
			this.setMovementVector(v);
		}
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
