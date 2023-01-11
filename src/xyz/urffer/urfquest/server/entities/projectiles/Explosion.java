package xyz.urffer.urfquest.server.entities.projectiles;

import xyz.urffer.urfutils.math.PairDouble;
import xyz.urffer.urfutils.math.PairInt;

import xyz.urffer.urfquest.server.Server;
import xyz.urffer.urfquest.server.entities.mobs.Mob;
import xyz.urffer.urfquest.server.map.Map;
import xyz.urffer.urfquest.shared.Tile;
import xyz.urffer.urfquest.shared.protocol.messages.MessageInitProjectile;
import xyz.urffer.urfquest.shared.protocol.types.ObjectType;
import xyz.urffer.urfquest.shared.protocol.types.ProjectileType;
import xyz.urffer.urfquest.shared.protocol.types.TileType;

public class Explosion extends Projectile {
	
	private int tickCount = 0;

	public Explosion(Server s, int sourceID) {
		super(s, sourceID);
		
		this.bounds.setRect(bounds.getX(), bounds.getY(), 0.3, 0.3);
		
		MessageInitProjectile mip = new MessageInitProjectile();
		mip.entityID = this.id;
		mip.projectileType = ProjectileType.EXPLOSION;
		s.sendMessageToAllClients(mip);
	}

	public void tick() {
		this.incrementPos(new PairDouble(-0.04, -0.04));
		this.setDims(new PairDouble(bounds.width + 0.08, bounds.height + 0.08));
		this.tickCount++;
		
		// clear trees
		Map currMap = this.server.getState().getMapByID(this.mapID);
		for(int i = 0; i < 20; i++) {
		    int xPos = (int)Math.round(bounds.getCenterX() + bounds.width/2 * Math.cos((Math.PI/10)*i) - 0.5);
		    int yPos = (int)Math.round(bounds.getCenterY() + bounds.width/2 * Math.sin((Math.PI/10)*i) - 0.5);
		    PairInt pos = new PairInt(xPos, yPos);
		    
		    if (currMap.getTileAt(pos).objectType == ObjectType.TREE) {
		    	currMap.setTileAt(pos, new Tile(TileType.GRASS, ObjectType.VOID));
		    }
		}
		
		// Check if this explosion has burnt out
		if (this.tickCount > 100) {
			this.consumed = true;
		}
	}
	
	public void collideWith(Mob m) {
		m.incrementHealth(-0.15);
	}
}
