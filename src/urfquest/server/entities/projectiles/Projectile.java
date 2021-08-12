package urfquest.server.entities.projectiles;

import urfquest.server.entities.Entity;
import urfquest.server.entities.mobs.Mob;
import urfquest.server.map.Map;

public abstract class Projectile extends Entity {
	protected double velocity;
	protected int direction;
	protected Entity source;

	protected Projectile(double x, double y, Entity source, Map m) {
		super(x, y, m);
		this.source = source;
	}
	
	public abstract void update();
	
	public abstract boolean isDead();

	public Entity getSource() {
		return source;
	}
	
	public abstract void collideWith(Mob m);
}