package server.entities.projectiles;

import java.awt.Color;
import java.awt.Graphics;

import server.entities.Entity;
import server.entities.mobs.Mob;
import server.entities.particles.RocketExhaust;
import server.game.QuestMap;
import server.tiles.Tiles;

import framework.QuestPanel;
import framework.UrfQuest;

public class Rocket extends Projectile {

	public Rocket(double x, double y, int dir, double velocity, Entity source, QuestMap m) {
		super(x, y, source, m);
		this.bounds.setRect(bounds.getX(), bounds.getY(), 0.3, 0.3);
		this.velocity = velocity;
		this.direction = dir;
	}

	public void drawEntity(Graphics g) {
		int tileWidth = QuestPanel.TILE_WIDTH;
		g.setColor(Color.RED.darker());
		g.fillOval(UrfQuest.panel.gameToWindowX(bounds.getX()), 
				   UrfQuest.panel.gameToWindowY(bounds.getY()),
				   (int)(bounds.getWidth()*tileWidth), 
				   (int)(bounds.getHeight()*tileWidth));
	}

	public void update() {
		this.move(velocity*Math.cos(Math.toRadians(direction)), velocity*Math.sin(Math.toRadians(direction)));
		if(!Tiles.isPenetrable(map.getTileTypeAt((int)bounds.x, (int)bounds.y))) {
			animStage = 1000;
			explode();
		}
		animStage++;
		if (animStage % 10 == 0) {
			map.addParticle(new RocketExhaust(bounds.getCenterX(), bounds.getCenterY(), map));
		}
	}

	public boolean isDead() {
		return (animStage > 1000);
	}
	
	public static double getDefaultVelocity() {
		return Math.random()*0.04 + 0.08;
	}
	
	public void collideWith(Mob m) {
		m.incrementHealth(-5.0);
		explode();
		animStage = 1001;
	}
	
	private void explode() {
		map.addProjectile(new RocketExplosion(bounds.x, bounds.y, this, map));
	}

}