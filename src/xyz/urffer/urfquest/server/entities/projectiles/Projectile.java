package xyz.urffer.urfquest.server.entities.projectiles;

import xyz.urffer.urfquest.server.Server;
import xyz.urffer.urfquest.server.entities.Entity;
import xyz.urffer.urfquest.server.entities.mobs.Mob;

public abstract class Projectile extends Entity {
	protected Entity source;

	protected Projectile(Server s, Entity source) {
		super(s);
		this.source = source;
	}
	
	public abstract void tick();
	
	public abstract boolean isDead();

	public Entity getSource() {
		return source;
	}
	
	public abstract void collideWith(Mob m);
}
