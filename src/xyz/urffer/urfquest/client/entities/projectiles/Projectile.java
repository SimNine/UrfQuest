package xyz.urffer.urfquest.client.entities.projectiles;

import java.awt.Color;
import java.awt.Graphics;

import xyz.urffer.urfquest.client.Client;
import xyz.urffer.urfquest.client.entities.Entity;
import xyz.urffer.urfquest.client.entities.mobs.Mob;

public abstract class Projectile extends Entity {
	protected int sourceID;

	protected Projectile(Client c, int id, int sourceID) {
		super(c, id);
		this.sourceID = sourceID;
	}
	
	public abstract void update();
	
	public abstract boolean isDead();

	public int getSourceID() {
		return this.sourceID;
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
