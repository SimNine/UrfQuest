package xyz.urffer.urfquest.server.entities.projectiles;

import xyz.urffer.urfquest.server.Server;
import xyz.urffer.urfquest.server.entities.Entity;
import xyz.urffer.urfquest.server.entities.mobs.Mob;

public abstract class Projectile extends Entity {
	protected int sourceID;
	protected boolean consumed;

	protected Projectile(Server s, int sourceID) {
		super(s);
		this.sourceID = sourceID;
		this.consumed = false;
	}
	
	public abstract void tick();

	public boolean isConsumed() {
		return this.consumed;
	}
	
	public int getSourceID() {
		return sourceID;
	}
	
	public abstract void collideWith(Mob m);
}
