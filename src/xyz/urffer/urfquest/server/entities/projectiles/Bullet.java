package xyz.urffer.urfquest.server.entities.projectiles;

import xyz.urffer.urfutils.math.PairDouble;

import xyz.urffer.urfquest.server.Server;
import xyz.urffer.urfquest.server.entities.Entity;
import xyz.urffer.urfquest.server.entities.mobs.Mob;
import xyz.urffer.urfquest.server.map.Map;
import xyz.urffer.urfquest.shared.Vector;

public class Bullet extends Projectile {
	
	public Bullet(Server s, Map m, PairDouble pos, Entity source) {
		this(s, m, pos, new Vector(s.randomDouble()*3.0, 0.05), source);
	}

	public Bullet(Server s, Map m, PairDouble pos, Vector movementVector, Entity source) {
		super(s, m, pos, movementVector, source);
		bounds.setRect(bounds.getX(), bounds.getY(), 0.15, 0.15);
	}

	public void tick() {
		this.incrementPos(this.movementVector);
		if (map.getTileAt(new PairDouble(bounds.x, bounds.y).toInt()).isPenetrable()) {
			// TODO: mark as dead
		}
	}

	public boolean isDead() {
		//return (animStage > 1000);
		return false;
	}
	
	public double getDefaultVelocity() {
		return server.randomDouble()*0.03 + 0.07;
	}
	
	public void collideWith(Mob m) {
		m.incrementHealth(-5.0);
		//animStage = 1001;
	}

}