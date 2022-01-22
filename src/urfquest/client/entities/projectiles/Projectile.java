package urfquest.client.entities.projectiles;

import java.awt.Color;
import java.awt.Graphics;

import urfquest.Main;
import urfquest.client.Client;
import urfquest.client.entities.Entity;
import urfquest.client.entities.mobs.Mob;
import urfquest.client.map.Map;

public abstract class Projectile extends Entity {
	protected double velocity;
	protected int direction;
	protected Entity source;

	protected Projectile(Client c, double x, double y, Entity source, Map m) {
		super(c, m, x, y);
		this.source = source;
	}
	
	public abstract void update();
	
	public abstract boolean isDead();

	public Entity getSource() {
		return source;
	}
	
	public abstract void collideWith(Mob m);

	protected abstract void drawEntity(Graphics g);
	
	public void drawDebug(Graphics g) {
		g.setColor(Color.WHITE);
		g.drawString("velocity: " + velocity, 
					 Main.panel.gameToWindowX(bounds.getX()),
					 Main.panel.gameToWindowY(bounds.getY()));
		g.drawString("direction: " + direction, 
					Main.panel.gameToWindowX(bounds.getX()),
					Main.panel.gameToWindowY(bounds.getY())+10);
	}
}