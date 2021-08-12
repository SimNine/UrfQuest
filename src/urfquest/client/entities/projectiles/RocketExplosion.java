package urfquest.client.entities.projectiles;

import urfquest.client.entities.Entity;
import urfquest.client.entities.mobs.Mob;
import urfquest.client.map.Map;
import urfquest.client.tiles.Tiles;

public class RocketExplosion extends Projectile {

	public RocketExplosion(double x, double y, Entity source, Map m) {
		super(x, y, source, m);
		this.bounds.setRect(bounds.getX(), bounds.getY(), 0.3, 0.3);
		this.velocity = 0;
		this.direction = 0;
	}

	public void update() {
		this.move(-0.04, -0.04);
		bounds.setRect(bounds.x, bounds.y, bounds.width + 0.08, bounds.height + 0.08);
		
		// clear trees
		for(int i = 0; i < 20; i++) {
		    int xPos = (int)Math.round(bounds.getCenterX() + bounds.width/2 * Math.cos((Math.PI/10)*i) - 0.5);
		    int yPos = (int)Math.round(bounds.getCenterY() + bounds.width/2 * Math.sin((Math.PI/10)*i) - 0.5);

		    if (map.getTileTypeAt(xPos, yPos) == Tiles.TREE) {
		    	map.setTileAt(xPos, yPos, Tiles.GRASS);
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