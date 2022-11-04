package xyz.urffer.urfquest.server.entities.projectiles;

import xyz.urffer.urfquest.server.Server;
import xyz.urffer.urfquest.server.entities.Entity;
import xyz.urffer.urfquest.server.entities.mobs.Mob;
import xyz.urffer.urfquest.server.map.Map;
import xyz.urffer.urfquest.shared.PairDouble;
import xyz.urffer.urfquest.shared.Tile;
import xyz.urffer.urfquest.shared.Vector;

public class Rocket extends Projectile {

	public Rocket(Server s, Map m, PairDouble pos, double dirRadians, double velocity, Entity source) {
		super(s, m, pos, source);
		this.bounds.setRect(bounds.getX(), bounds.getY(), 0.3, 0.3);
		this.movementVector = new Vector(dirRadians, velocity);
	}

	public void tick() {
		this.incrementPos(this.movementVector);
		if(!Tile.isPenetrable(map.getTileAt(new PairDouble(bounds.x, bounds.y).toInt()))) {
			// animStage = 1000;
			explode();
		}
	}

	public boolean isDead() {
		// return (animStage > 1000);
		return false;
	}
	
	public double getDefaultVelocity() {
		return server.randomDouble()*0.04 + 0.08;
	}
	
	public void collideWith(Mob m) {
		m.incrementHealth(-5.0);
		explode();
		// animStage = 1001;
	}
	
	private void explode() {
		map.addProjectile(new RocketExplosion(server, this.map, this.getPos(), this));
	}

}