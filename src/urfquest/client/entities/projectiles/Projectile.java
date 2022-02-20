package urfquest.client.entities.projectiles;

import java.awt.Color;
import java.awt.Graphics;

import urfquest.client.Client;
import urfquest.client.entities.Entity;
import urfquest.client.entities.mobs.Mob;
import urfquest.client.map.Map;
import urfquest.shared.Vector;

public abstract class Projectile extends Entity {
	protected Vector movementVector;
	protected Entity source;

	protected Projectile(Client c, int id, Map m, double[] pos, Entity source) {
		super(c, id, m, pos);
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
		g.drawString("velocity: " + this.movementVector.magnitude, 
					client.getPanel().gameToWindowX(bounds.getX()),
					client.getPanel().gameToWindowY(bounds.getY()));
		g.drawString("direction: " + this.movementVector.dirRadians, 
					client.getPanel().gameToWindowX(bounds.getX()),
					client.getPanel().gameToWindowY(bounds.getY())+10);
	}
}