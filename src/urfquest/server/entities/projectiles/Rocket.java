package urfquest.server.entities.projectiles;

import urfquest.server.entities.Entity;
import urfquest.server.entities.mobs.Mob;
import urfquest.server.map.Map;
import urfquest.server.tiles.Tiles;

public class Rocket extends Projectile {

	public Rocket(double x, double y, int dir, double velocity, Entity source, Map m) {
		super(x, y, source, m);
		this.bounds.setRect(bounds.getX(), bounds.getY(), 0.3, 0.3);
		this.velocity = velocity;
		this.direction = dir;
	}

	public void update() {
		this.move(velocity*Math.cos(Math.toRadians(direction)), velocity*Math.sin(Math.toRadians(direction)));
		if(!Tiles.isPenetrable(map.getTileTypeAt((int)bounds.x, (int)bounds.y))) {
			// animStage = 1000;
			explode();
		}
	}

	public boolean isDead() {
		// return (animStage > 1000);
		return false;
	}
	
	public static double getDefaultVelocity() {
		return Math.random()*0.04 + 0.08;
	}
	
	public void collideWith(Mob m) {
		m.incrementHealth(-5.0);
		explode();
		// animStage = 1001;
	}
	
	private void explode() {
		map.addProjectile(new RocketExplosion(bounds.x, bounds.y, this, map));
	}

}