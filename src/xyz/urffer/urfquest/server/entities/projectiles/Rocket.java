package xyz.urffer.urfquest.server.entities.projectiles;

import xyz.urffer.urfutils.math.PairDouble;

import xyz.urffer.urfquest.server.Server;
import xyz.urffer.urfquest.server.entities.Entity;
import xyz.urffer.urfquest.server.entities.mobs.Mob;
import xyz.urffer.urfquest.server.map.Map;

public class Rocket extends Projectile {

	public Rocket(Server s, Entity source) {
		super(s, source);
		
		this.bounds.setRect(bounds.getX(), bounds.getY(), 0.3, 0.3);
	}

	public void tick() {
		this.incrementPos(this.movementVector);
		Map currMap = this.server.getState().getMapByID(this.mapID);
		if(!currMap.getTileAt(new PairDouble(bounds.x, bounds.y).toInt()).isPenetrable()) {
			// animStage = 1000;
			explode();
		}
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
		RocketExplosion expl = new RocketExplosion(server, this);
		expl.setPos(this.getPos(), this.mapID);
	}

}