package urfquest.server.entities.projectiles;

import urfquest.server.entities.Entity;
import urfquest.server.entities.mobs.Mob;
import urfquest.server.map.Map;
import urfquest.server.state.State;

public class GrenadeProjectile extends Projectile {

	public GrenadeProjectile(State s, Map m, double x, double y, Entity source) {
		super(s, m, x, y, source);
		
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
