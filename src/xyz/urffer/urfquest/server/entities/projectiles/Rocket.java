package xyz.urffer.urfquest.server.entities.projectiles;

import xyz.urffer.urfquest.server.Server;
import xyz.urffer.urfquest.server.entities.mobs.Mob;
import xyz.urffer.urfquest.server.map.Map;
import xyz.urffer.urfquest.shared.protocol.messages.MessageInitProjectile;
import xyz.urffer.urfquest.shared.protocol.types.ProjectileType;

public class Rocket extends Projectile {

	public Rocket(Server s, int sourceID) {
		super(s, sourceID);
		
		this.bounds.setRect(bounds.getX(), bounds.getY(), 0.3, 0.3);
		
		MessageInitProjectile mip = new MessageInitProjectile();
		mip.entityID = this.id;
		mip.projectileType = ProjectileType.ROCKET;
		s.sendMessageToAllClients(mip);
	}
	
	public void destroy() {
		super.destroy();
		
		Explosion expl = new Explosion(server, this.sourceID);
		expl.setPos(this.getPos(), this.mapID);
	}

	public void tick() {
		this.incrementPos(this.movementVector);
		
		Map currMap = this.server.getState().getMapByID(this.mapID);
		if (!currMap.getTileAt(this.getCenter().floor()).isPenetrable()) {
			this.consumed = true;
		}
	}
	
	public double getDefaultVelocity() {
		return server.randomDouble()*0.04 + 0.08;
	}
	
	public void collideWith(Mob m) {
		m.incrementHealth(-5.0);
		this.consumed = true;
	}

}
