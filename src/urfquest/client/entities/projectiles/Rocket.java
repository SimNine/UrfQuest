package urfquest.client.entities.projectiles;

import java.awt.Color;
import java.awt.Graphics;

import urfquest.Main;
import urfquest.client.QuestPanel;
import urfquest.client.entities.Entity;
import urfquest.client.entities.mobs.Mob;
import urfquest.client.map.Map;
import urfquest.client.tiles.Tiles;

public class Rocket extends Projectile {

	public Rocket(double x, double y, int dir, double velocity, Entity source, Map m) {
		super(null, x, y, source, m);
		this.bounds.setRect(bounds.getX(), bounds.getY(), 0.3, 0.3);
		this.velocity = velocity;
		this.direction = dir;
	}

	public void update() {
		this.move(velocity*Math.cos(Math.toRadians(direction)), velocity*Math.sin(Math.toRadians(direction)));
		if(!Tiles.isPenetrable(map.getTileTypeAt((int)bounds.x, (int)bounds.y))) {
			// animStage = 1000;
			explode();
		}
	}

	public boolean isDead() {
		// return (animStage > 1000);
		return false;
	}
	
	public static double getDefaultVelocity() {
		return Math.random()*0.04 + 0.08;
	}
	
	public void collideWith(Mob m) {
		m.incrementHealth(-5.0);
		explode();
		// animStage = 1001;
	}
	
	private void explode() {
		map.addProjectile(new RocketExplosion(null, bounds.x, bounds.y, this, map));
	}

	public void drawEntity(Graphics g) {
		int tileWidth = QuestPanel.TILE_WIDTH;
		g.setColor(Color.RED.darker());
		g.fillOval(Main.panel.gameToWindowX(bounds.getX()), 
					Main.panel.gameToWindowY(bounds.getY()),
				   (int)(bounds.getWidth()*tileWidth), 
				   (int)(bounds.getHeight()*tileWidth));
	}

}