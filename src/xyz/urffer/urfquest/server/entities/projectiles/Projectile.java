package xyz.urffer.urfquest.server.entities.projectiles;

import xyz.urffer.urfquest.server.Server;
import xyz.urffer.urfquest.server.entities.Entity;
import xyz.urffer.urfquest.server.entities.mobs.Mob;
import xyz.urffer.urfquest.server.map.Map;
import xyz.urffer.urfquest.shared.Vector;

public abstract class Projectile extends Entity {
	protected Vector movementVector;
	protected Entity source;

	protected Projectile(Server s, Map m, double[] pos, Entity source) {
		super(s, m, pos);
		this.source = source;
	}
	
	public abstract void tick();
	
	public abstract boolean isDead();

	public Entity getSource() {
		return source;
	}
	
	public abstract void collideWith(Mob m);
}