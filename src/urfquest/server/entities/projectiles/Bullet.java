package urfquest.server.entities.projectiles;

import urfquest.server.Server;
import urfquest.server.entities.Entity;
import urfquest.server.entities.mobs.Mob;
import urfquest.server.map.Map;
import urfquest.shared.Tile;
import urfquest.shared.Vector;

public class Bullet extends Projectile {
	
	public Bullet(Server s, Map m, double[] pos, Entity source) {
		// TODO: set default movementvector
		//Vector movementVector = new Vector(id, id);
		
		super(s, m, pos, source);
	}

	public Bullet(Server s, Map m, double[] pos, double dirRadians, double velocity, Entity source) {
		super(s, m, pos, source);
		bounds.setRect(bounds.getX(), bounds.getY(), 0.15, 0.15);
		this.movementVector = new Vector(dirRadians, velocity);
	}

	public void tick() {
		this.incrementPos(this.movementVector);
		if(!Tile.isPenetrable(map.getTileAt((int)bounds.x, (int)bounds.y))) {
			// animStage = 1000;
			this.splashParticles();
		}
		//animStage++;
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
		splashParticles();
		//animStage = 1001;
	}
	
	private void splashParticles() {
		for (int i = 0; i < 10; i++) {
			//map.addParticle(new BulletSplash(bounds.x, bounds.y, map));
		}
	}

}