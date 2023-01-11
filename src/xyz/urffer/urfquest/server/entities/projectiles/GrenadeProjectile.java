package xyz.urffer.urfquest.server.entities.projectiles;

import xyz.urffer.urfquest.server.Server;
import xyz.urffer.urfquest.server.entities.Entity;
import xyz.urffer.urfquest.server.entities.mobs.Mob;

public class GrenadeProjectile extends Projectile {

	public GrenadeProjectile(Server s, Entity source) {
		super(s, source);
		
		bounds.setFrame(bounds.x, bounds.y, 1, 1);
		
		// TODO: add message for initialization
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
