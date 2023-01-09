package xyz.urffer.urfquest.client.entities.projectiles;

import java.awt.Graphics;

import xyz.urffer.urfutils.math.PairDouble;

import xyz.urffer.urfquest.client.Client;
import xyz.urffer.urfquest.client.entities.Entity;
import xyz.urffer.urfquest.client.entities.mobs.Mob;
import xyz.urffer.urfquest.client.map.Map;

public class GrenadeProjectile extends Projectile {

	public GrenadeProjectile(Client c, int id, Map m, PairDouble pos, Entity source) {
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
