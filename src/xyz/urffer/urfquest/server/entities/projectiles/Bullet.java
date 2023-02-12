package xyz.urffer.urfquest.server.entities.projectiles;

import xyz.urffer.urfquest.server.Server;
import xyz.urffer.urfquest.server.entities.mobs.Mob;
import xyz.urffer.urfquest.server.map.Map;
import xyz.urffer.urfquest.shared.Tile;
import xyz.urffer.urfquest.shared.protocol.Message;
import xyz.urffer.urfquest.shared.protocol.messages.MessageInitProjectile;
import xyz.urffer.urfquest.shared.protocol.types.ProjectileType;
import xyz.urffer.urfutils.math.PairDouble;

public class Bullet extends Projectile {
	
	public Bullet(Server s, int sourceID) {
		super(s, sourceID);
		
		PairDouble defaultBounds = this.getDefaultBounds();
		bounds.setFrame(bounds.x, bounds.y, defaultBounds.x, defaultBounds.y);
		
		this.server.sendMessageToAllClients(this.initMessage());
	}
	
	public Message initMessage() {
		MessageInitProjectile mip = new MessageInitProjectile();
		mip.entityID = this.id;
		mip.projectileType = ProjectileType.BULLET;
		return mip;
	}

	public void tick() {
		this.incrementPos(this.movementVector);

		Map currMap = this.server.getState().getMapByID(this.mapID);
		Tile currTile = currMap.getTileAt(this.getCenter().floor());
		if (!currTile.isPenetrable()) {
			this.consumed = true;
		}
	}
	
	public void collideWith(Mob m) {
		m.incrementHealth(-50);
		this.consumed = true;
	}
	
	public double getDefaultVelocity() {
		return server.randomDouble()*0.03 + 0.2;
	}

	@Override
	public PairDouble getDefaultBounds() {
		return new PairDouble(0.2, 0.2);
	}

}
