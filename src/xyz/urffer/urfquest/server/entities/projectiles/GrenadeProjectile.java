package xyz.urffer.urfquest.server.entities.projectiles;

import xyz.urffer.urfquest.server.Server;
import xyz.urffer.urfquest.server.entities.Entity;
import xyz.urffer.urfquest.server.entities.mobs.Mob;
import xyz.urffer.urfquest.server.map.Map;

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
