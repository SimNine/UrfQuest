package urfquest.client.entities.particles;

import java.awt.Graphics;

import urfquest.client.entities.Entity;
import urfquest.client.map.Map;

public abstract class Particle extends Entity {
	protected int dir;
	protected double velocity;
	private int duration;

	protected Particle(int id, Map m, double x, double y, int dir, double vel, int duration) {
		super(id, m, x, y);
		this.dir = dir;
		this.velocity = vel;
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
