package xyz.urffer.urfquest.server.entities.projectiles;

import xyz.urffer.urfutils.math.PairDouble;

import xyz.urffer.urfquest.server.Server;
import xyz.urffer.urfquest.server.entities.Entity;
import xyz.urffer.urfquest.server.entities.mobs.Mob;
import xyz.urffer.urfquest.server.map.Map;
import xyz.urffer.urfquest.shared.protocol.messages.MessageInitProjectile;
import xyz.urffer.urfquest.shared.protocol.types.ProjectileType;

public class Bullet extends Projectile {
	
	public Bullet(Server s, Entity source) {
		super(s, source);
		
		bounds.setFrame(bounds.x, bounds.y, 0.2, 0.2);
		
		MessageInitProjectile mip = new MessageInitProjectile();
		mip.entityID = this.id;
		mip.projectileType = ProjectileType.BULLET;
		s.sendMessageToAllClients(mip);
	}

	public void tick() {
		this.incrementPos(this.movementVector);
		Map currMap = this.server.getState().getMapByID(this.mapID);
		if (currMap.getTileAt(new PairDouble(bounds.x, bounds.y).toInt()).isPenetrable()) {
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