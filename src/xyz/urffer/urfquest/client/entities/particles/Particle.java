package xyz.urffer.urfquest.client.entities.particles;

import java.awt.Graphics;

import xyz.urffer.urfquest.client.Client;
import xyz.urffer.urfquest.client.entities.Entity;
import xyz.urffer.urfquest.client.map.Map;
import xyz.urffer.urfquest.shared.Vector;

public abstract class Particle extends Entity {
	protected Vector movementVector;
	private int duration;

	protected Particle(Client c, int id, Map m, double[] pos, double dir, double vel, int duration) {
		super(c, id, m, pos);
		this.movementVector = new Vector(dir, vel);
		this.duration = duration;
	}

	protected abstract void drawEntity(Graphics g);

	protected void drawDebug(Graphics g) {
		// nothing
	}

	public abstract void update();

	public boolean isDead() {
		// return (animStage > duration);
		return false;
	}
}
