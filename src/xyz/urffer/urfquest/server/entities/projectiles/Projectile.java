package xyz.urffer.urfquest.server.entities.projectiles;

import xyz.urffer.urfutils.math.PairDouble;

import xyz.urffer.urfquest.server.Server;
import xyz.urffer.urfquest.server.entities.Entity;
import xyz.urffer.urfquest.server.entities.mobs.Mob;
import xyz.urffer.urfquest.server.map.Map;
import xyz.urffer.urfquest.shared.Vector;
import xyz.urffer.urfquest.shared.protocol.messages.MessageInitProjectile;
import xyz.urffer.urfquest.shared.protocol.types.ProjectileType;

public abstract class Projectile extends Entity {
	protected Entity source;

	protected Projectile(Server s, Map m, PairDouble pos, Vector movementVector, Entity source) {
		super(s, m, pos);
		this.source = source;
		
		MessageInitProjectile mip = new MessageInitProjectile();
		mip.entityID = this.id;
		mip.projectileType = ProjectileType.BULLET; // TODO: change
		s.sendMessageToAllClients(mip);
		
		this.setMovementVector(movementVector);
	}
	
	public abstract void tick();
	
	public abstract boolean isDead();

	public Entity getSource() {
		return source;
	}
	
	public abstract void collideWith(Mob m);
}
