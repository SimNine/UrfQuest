package urfquest.client.entities.projectiles;

import java.awt.Graphics;

import urfquest.client.Client;
import urfquest.client.entities.Entity;
import urfquest.client.entities.mobs.Mob;
import urfquest.client.map.Map;

public class GrenadeProjectile extends Projectile {

	public GrenadeProjectile(Client c, int id, Map m, double[] pos, Entity source) {
		super(c, id, m, pos, source);
		
		bounds.setFrame(bounds.x, bounds.y, 1, 1);
	}

	public void update() {
		
	}

	public boolean isDead() {
		// return (animStage > 1000);
		return false;
	}

	public void collideWith(Mob m) {
		// do nothing
	}

	@Override
	protected void drawEntity(Graphics g) {
		// TODO Auto-generated method stub
		
	}
}
