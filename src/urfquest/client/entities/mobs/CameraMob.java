package urfquest.client.entities.mobs;

import java.awt.Graphics;

import urfquest.client.map.Map;

public class CameraMob extends Mob {
	public static int STILL_MODE = 1301;
	public static int DEMO_MODE = 1302;

	public CameraMob(double x, double y, int mode, Map m) {
		super(x, y, m);
		velocity = 0.01;
		direction = (int)(Math.random()*360.0);
		health = 100.0;
		maxHealth = 100.0;
	}

	public void update() {
		this.move(direction, velocity);
	}

	protected void drawEntity(Graphics g) {
		// do nothing
	}
}
