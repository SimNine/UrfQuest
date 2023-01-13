package xyz.urffer.urfquest.server.entities.projectiles;

import xyz.urffer.urfquest.server.Server;
import xyz.urffer.urfquest.server.entities.mobs.Mob;
import xyz.urffer.urfquest.shared.protocol.Message;
import xyz.urffer.urfquest.shared.protocol.messages.MessageInitProjectile;
import xyz.urffer.urfquest.shared.protocol.types.ProjectileType;
import xyz.urffer.urfutils.math.PairDouble;

public class GrenadeProjectile extends Projectile {

	public GrenadeProjectile(Server s, int sourceID) {
		super(s, sourceID);

		PairDouble defaultBounds = this.getDefaultBounds();
		bounds.setFrame(bounds.x, bounds.y, defaultBounds.x, defaultBounds.y);
		
		this.server.sendMessageToAllClients(this.initMessage());
	}
	
	public Message initMessage() {
		MessageInitProjectile m = new MessageInitProjectile();
		m.entityID = this.id;
		m.projectileType = ProjectileType.GRENADE;
		m.sourceEntityID = this.sourceID;
		return m;
	}

	public void tick() {
		
	}

	public void collideWith(Mob m) {
		// do nothing
	}

	@Override
	public double getDefaultVelocity() {
		return 0.1;
	}

	@Override
	public PairDouble getDefaultBounds() {
		return new PairDouble(0.5, 0.5);
	}
}
