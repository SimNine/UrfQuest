package xyz.urffer.urfquest.server.entities.mobs;

import xyz.urffer.urfquest.server.Server;
import xyz.urffer.urfquest.server.entities.Entity;
import xyz.urffer.urfquest.server.entities.mobs.ai.routines.MobRoutine;
import xyz.urffer.urfquest.shared.Vector;
import xyz.urffer.urfquest.shared.protocol.messages.MessageEntitySetStat;
import xyz.urffer.urfquest.shared.protocol.types.StatType;

public abstract class Mob extends Entity {
	protected final static String assetPath = "/assets/entities/";
	
	protected int health;
	protected int mana;
	protected int fullness;
	
	protected int maxHealth;
	protected int maxMana;
	protected int maxFullness;
	
	protected MobRoutine routine;

	protected Mob(Server srv) {
		super(srv);
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
	
	public void setHealth(int h) {
		if (h > this.maxHealth) {
			this.health = this.maxHealth;
		} else {
			this.health = h;
		}
		
		MessageEntitySetStat mess = new MessageEntitySetStat();
		mess.statType = StatType.HEALTH;
		mess.stat = this.health;
		this.server.sendMessageToAllClients(mess);
	}
	
	public void incrementHealth(int amt) {
		setHealth(health + amt);
	}
	
	public int getHealth() {
		return health;
	}
	
	public int getMaxHealth() {
		return this.maxHealth;
	}
	
	
	
	public void setMana(int m) {
		if (m > maxMana) {
			mana = maxMana;
		} else {
			mana = m;
		}
		
		MessageEntitySetStat mess = new MessageEntitySetStat();
		mess.statType = StatType.MANA;
		mess.stat = this.mana;
		this.server.sendMessageToAllClients(mess);
	}
	
	public void incrementMana(int amt) {
		setMana(mana + amt);
	}
	
	public int getMana() {
		return mana;
	}
	
	public int getMaxMana() {
		return maxMana;
	}
	
	
	
	public void setFullness(int f) {
		if (f > maxFullness) {
			fullness = maxFullness;
		} else {
			fullness = f;
		}
		
		MessageEntitySetStat mess = new MessageEntitySetStat();
		mess.statType = StatType.HUNGER;
		mess.stat = this.fullness;
		this.server.sendMessageToAllClients(mess);
	}
	
	public void incrementFullness(int amt) {
		setFullness(fullness + amt);
	}
	
	public int getFullness() {
		return fullness;
	}
	
	public int getMaxFullness() {
		return maxFullness;
	}
	
	
	
	public boolean isDead() {
		return health <= 0;
	}
}
