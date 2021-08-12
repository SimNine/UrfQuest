package urfquest.client.entities.projectiles;

import java.awt.Color;
import java.awt.Graphics;

import urfquest.Main;
import urfquest.client.QuestPanel;
import urfquest.client.entities.Entity;
import urfquest.client.entities.mobs.Mob;
import urfquest.client.map.Map;
import urfquest.client.tiles.Tiles;

public class Bullet extends Projectile {

	public Bullet(double x, double y, int dir, double velocity, Entity source, Map m) {
		super(x, y, source, m);
		bounds.setRect(bounds.getX(), bounds.getY(), 0.15, 0.15);
		this.velocity = velocity;
		direction = dir;
	}

	public void update() {
		this.move(velocity*Math.cos(Math.toRadians(direction)), velocity*Math.sin(Math.toRadians(direction)));
		if(!Tiles.isPenetrable(map.getTileTypeAt((int)bounds.x, (int)bounds.y))) {
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
		g.fillOval(Main.panel.gameToWindowX(bounds.getX()), 
				   Main.panel.gameToWindowY(bounds.getY()),
				   (int)(bounds.getWidth()*tileWidth), 
				   (int)(bounds.getHeight()*tileWidth));
	}

}