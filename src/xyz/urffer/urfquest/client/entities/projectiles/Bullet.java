package xyz.urffer.urfquest.client.entities.projectiles;

import java.awt.Color;
import java.awt.Graphics;

import xyz.urffer.urfquest.client.Client;
import xyz.urffer.urfquest.client.QuestPanel;
import xyz.urffer.urfquest.client.entities.mobs.Mob;

public class Bullet extends Projectile {

	public Bullet(Client c, int id, int sourceID) {
		super(c, id, sourceID);
		
		bounds.setRect(bounds.getX(), bounds.getY(), 0.15, 0.15);
	}

	public void update() {
//		this.incrementPos(this.movementVector);
//		if (map.getTileAt(new PairDouble(bounds.x, bounds.y).toInt()).isPenetrable()) {
//			// animStage = 1000;
//			splashParticles();
//		}
	}

	public boolean isDead() {
		return false;
	}
	
	public static double getDefaultVelocity() {
		return Math.random()*0.03 + 0.07;
	}
	
	public void collideWith(Mob m) {
//		m.incrementHealth(-5.0);
		splashParticles();
	}
	
	private void splashParticles() {
		for (int i = 0; i < 10; i++) {
			//map.addParticle(new BulletSplash(bounds.x, bounds.y, map));
		}
	}

	public void drawEntity(Graphics g) {
		int tileWidth = QuestPanel.TILE_WIDTH;
		g.setColor(Color.BLACK);
		g.fillOval(client.getPanel().gameToWindowX(bounds.getX()), 
				   client.getPanel().gameToWindowY(bounds.getY()),
				   (int)(bounds.getWidth()*tileWidth), 
				   (int)(bounds.getHeight()*tileWidth));
	}

}
