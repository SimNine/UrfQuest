package entities.particles;

import java.awt.Graphics;

import entities.Entity;
import game.QuestMap;

public abstract class Particle extends Entity {
	protected int dir;
	protected double velocity;
	private int duration;

	protected Particle(double x, double y, int dir, double vel, int duration, QuestMap m) {
		super(x, y, m);
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
		return (animStage > duration);
	}
}
