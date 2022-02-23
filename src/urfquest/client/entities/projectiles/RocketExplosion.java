package urfquest.client.entities.projectiles;

import java.awt.Graphics;

import urfquest.client.Client;
import urfquest.client.entities.Entity;
import urfquest.client.entities.mobs.Mob;
import urfquest.client.map.Map;
import urfquest.shared.Tile;
import urfquest.shared.Vector;

public class RocketExplosion extends Projectile {

	public RocketExplosion(Client c, int id, double[] pos, Entity source, Map m) {
		super(c, id, m, pos, source);
		this.bounds.setRect(bounds.getX(), bounds.getY(), 0.3, 0.3);
		this.movementVector = new Vector(0, 0);
	}

	public void update() {
		this.incrementPos(-0.04, -0.04);
		bounds.setRect(bounds.x, bounds.y, bounds.width + 0.08, bounds.height + 0.08);
		
		// clear trees
		for(int i = 0; i < 20; i++) {
		    int xPos = (int)Math.round(bounds.getCenterX() + bounds.width/2 * Math.cos((Math.PI/10)*i) - 0.5);
		    int yPos = (int)Math.round(bounds.getCenterY() + bounds.width/2 * Math.sin((Math.PI/10)*i) - 0.5);

		    if (map.getTileTypeAt(xPos, yPos) == Tile.OBJECT_TREE) {
		    	map.setTileAt(xPos, yPos, Tile.TILE_GRASS);
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

	@Override
	protected void drawEntity(Graphics g) {
		// TODO Auto-generated method stub
		
	}
}