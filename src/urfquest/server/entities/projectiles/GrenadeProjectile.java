package urfquest.server.entities.projectiles;

import urfquest.server.Server;
import urfquest.server.entities.Entity;
import urfquest.server.entities.mobs.Mob;
import urfquest.server.map.Map;

public class GrenadeProjectile extends Projectile {

	public GrenadeProjectile(Server s, Map m, double[] pos, Entity source) {
		super(s, m, pos, source);
		
		bounds.setFrame(bounds.x, bounds.y, 1, 1);
	}

	public void tick() {
		
	}

	public boolean isDead() {
		// return (animStage > 1000);
		return false;
	}

	public void collideWith(Mob m) {
		// do nothing
	}
}
