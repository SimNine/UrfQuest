package xyz.urffer.urfquest.server.entities.projectiles;

import xyz.urffer.urfquest.server.Server;
import xyz.urffer.urfquest.server.entities.Entity;
import xyz.urffer.urfquest.server.entities.mobs.Mob;

public abstract class Projectile extends Entity {
	protected Entity source;
	protected boolean consumed;

	protected Projectile(Server s, Entity source) {
		super(s);
		this.source = source;
		this.consumed = false;
	}
	
	public abstract void tick();

	public boolean isConsumed() {
		return this.consumed;
	}
	
	public Entity getSource() {
		return source;
	}
	
	public abstract void collideWith(Mob m);
}
