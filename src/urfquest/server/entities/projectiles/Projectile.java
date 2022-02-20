package urfquest.server.entities.projectiles;

import urfquest.server.Server;
import urfquest.server.entities.Entity;
import urfquest.server.entities.mobs.Mob;
import urfquest.server.map.Map;
import urfquest.shared.Vector;

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