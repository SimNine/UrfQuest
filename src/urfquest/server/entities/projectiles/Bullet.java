package urfquest.server.entities.projectiles;

import urfquest.server.entities.Entity;
import urfquest.server.entities.mobs.Mob;
import urfquest.server.map.Map;
import urfquest.server.tiles.Tiles;

public class Bullet extends Projectile {

	public Bullet(double x, double y, int dir, double velocity, Entity source, Map m) {
		super(x, y, source, m);
		bounds.setRect(bounds.getX(), bounds.getY(), 0.15, 0.15);
		this.velocity = velocity;
		direction = dir;
	}

	public void update() {
		this.move(velocity*Math.cos(Math.toRadians(direction)), velocity*Math.sin(Math.toRadians(direction)));
		if(!Tiles.isPenetrable(map.getTileTypeAt((int)bounds.x, (int)bounds.y))) {
			// animStage = 1000;
			splashParticles();
		}
		//animStage++;
	}

	public boolean isDead() {
		//return (animStage > 1000);
		return false;
	}
	
	public static double getDefaultVelocity() {
		return Math.random()*0.03 + 0.07;
	}
	
	public void collideWith(Mob m) {
		m.incrementHealth(-5.0);
		splashParticles();
		//animStage = 1001;
	}
	
	private void splashParticles() {
		for (int i = 0; i < 10; i++) {
			//map.addParticle(new BulletSplash(bounds.x, bounds.y, map));
		}
	}

}