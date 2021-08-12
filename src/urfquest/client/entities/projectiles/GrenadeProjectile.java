package urfquest.client.entities.projectiles;

import urfquest.client.entities.Entity;
import urfquest.client.entities.mobs.Mob;
import urfquest.client.map.Map;

public class GrenadeProjectile extends Projectile {

	public GrenadeProjectile(double x, double y, Entity source, Map m) {
		super(x, y, source, m);
		
		bounds.setFrame(bounds.x, bounds.y, 1, 1);
	}

	public void update() {
		
	}

	public boolean isDead() {
		// return (animStage > 1000);
		return false;
	}

	public void collideWith(Mob m) {
		// do nothing
	}
}
