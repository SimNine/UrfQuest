package xyz.urffer.urfquest.client.entities.projectiles;

import java.awt.Color;
import java.awt.Graphics;

import xyz.urffer.urfquest.client.Client;
import xyz.urffer.urfquest.client.QuestPanel;
import xyz.urffer.urfquest.client.entities.Entity;
import xyz.urffer.urfquest.client.entities.mobs.Mob;
import xyz.urffer.urfquest.client.map.Map;
import xyz.urffer.urfquest.shared.Tile;
import xyz.urffer.urfquest.shared.Vector;

public class Bullet extends Projectile {

	public Bullet(Client c, int id, Map m, double[] pos, double dir, double velocity, Entity source) {
		super(c, id, m, pos, source);
		bounds.setRect(bounds.getX(), bounds.getY(), 0.15, 0.15);
		this.movementVector = new Vector(dir, velocity);
	}

	public void update() {
		this.incrementPos(this.movementVector);
		if (
			!Tile.isPenetrable(
				map.getTileAt(
					new int[] {(int)bounds.x, (int)bounds.y}
				)
			)
		) {
			// animStage = 1000;
			splashParticles();
		}
		//animStage++;
	}

	public boolean isDead() {
		//return (animStage > 1000);
		return false;
	}
	
	public static double getDefaultVelocity() {
		return Math.random()*0.03 + 0.07;
	}
	
	public void collideWith(Mob m) {
		m.incrementHealth(-5.0);
		splashParticles();
		//animStage = 1001;
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