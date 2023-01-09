package xyz.urffer.urfquest.server.entities.projectiles;

import xyz.urffer.urfutils.math.PairDouble;
import xyz.urffer.urfutils.math.PairInt;

import xyz.urffer.urfquest.server.Server;
import xyz.urffer.urfquest.server.entities.Entity;
import xyz.urffer.urfquest.server.entities.mobs.Mob;
import xyz.urffer.urfquest.server.map.Map;
import xyz.urffer.urfquest.shared.Tile;
import xyz.urffer.urfquest.shared.Vector;
import xyz.urffer.urfquest.shared.protocol.types.ObjectType;
import xyz.urffer.urfquest.shared.protocol.types.TileType;

public class RocketExplosion extends Projectile {

	public RocketExplosion(Server s, Map m, PairDouble pos, Entity source) {
		super(s, m, pos, source);
		this.bounds.setRect(bounds.getX(), bounds.getY(), 0.3, 0.3);
		this.movementVector = new Vector(0, 0);
	}

	public void tick() {
		this.incrementPos(new PairDouble(-0.04, -0.04));
		bounds.setRect(bounds.x, bounds.y, bounds.width + 0.08, bounds.height + 0.08);
		
		// clear trees
		for(int i = 0; i < 20; i++) {
		    int xPos = (int)Math.round(bounds.getCenterX() + bounds.width/2 * Math.cos((Math.PI/10)*i) - 0.5);
		    int yPos = (int)Math.round(bounds.getCenterY() + bounds.width/2 * Math.sin((Math.PI/10)*i) - 0.5);
		    PairInt pos = new PairInt(xPos, yPos);
		    
		    if (map.getTileAt(pos).objectType == ObjectType.TREE) {
		    	map.setTileAt(pos, new Tile(TileType.GRASS, ObjectType.VOID));
		    }
		}
	}

	public boolean isDead() {
		// return (animStage > 100);
		return false;
	}
	
	public void collideWith(Mob m) {
		m.incrementHealth(-0.15);
	}
}