package urfquest.server.entities.projectiles;

import urfquest.server.Server;
import urfquest.server.entities.Entity;
import urfquest.server.entities.mobs.Mob;
import urfquest.server.map.Map;
import urfquest.server.tiles.Tiles;
import urfquest.shared.Vector;

public class Rocket extends Projectile {

	public Rocket(Server s, Map m, double[] pos, double dirRadians, double velocity, Entity source) {
		super(s, m, pos, source);
		this.bounds.setRect(bounds.getX(), bounds.getY(), 0.3, 0.3);
		this.movementVector = new Vector(dirRadians, velocity);
	}

	public void tick() {
		this.incrementPos(this.movementVector);
		if(!Tiles.isPenetrable(map.getTileTypeAt((int)bounds.x, (int)bounds.y))) {
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