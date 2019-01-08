package entities.projectiles;

import java.awt.Color;
import java.awt.Graphics;

import entities.Entity;
import entities.mobs.Mob;
import entities.particles.BulletSplash;
import framework.QuestPanel;
import framework.UrfQuest;
import game.QuestMap;
import tiles.Tiles;

public class Bullet extends Projectile {

	public Bullet(double x, double y, int dir, double velocity, Entity source, QuestMap m) {
		super(x, y, source, m);
		bounds.setRect(bounds.getX(), bounds.getY(), 0.15, 0.15);
		this.velocity = velocity;
		direction = dir;
	}

	public void drawEntity(Graphics g) {
		int tileWidth = QuestPanel.TILE_WIDTH;
		g.setColor(Color.BLACK);
		g.fillOval(UrfQuest.panel.gameToWindowX(bounds.getX()), 
				   UrfQuest.panel.gameToWindowY(bounds.getY()),
				   (int)(bounds.getWidth()*tileWidth), 
				   (int)(bounds.getHeight()*tileWidth));
	}

	public void update() {
		this.move(velocity*Math.cos(Math.toRadians(direction)), velocity*Math.sin(Math.toRadians(direction)));
		if(!Tiles.isPenetrable(map.getTileTypeAt((int)bounds.x, (int)bounds.y))) {
			animStage = 1000;
			splashParticles();
		}
		animStage++;
	}

	public boolean isDead() {
		return (animStage > 1000);
	}
	
	public static double getDefaultVelocity() {
		return Math.random()*0.03 + 0.07;
	}
	
	public void collideWith(Mob m) {
		m.incrementHealth(-5.0);
		splashParticles();
		animStage = 1001;
	}
	
	private void splashParticles() {
		for (int i = 0; i < 10; i++) {
			map.addParticle(new BulletSplash(bounds.x, bounds.y, map));
		}
	}

}