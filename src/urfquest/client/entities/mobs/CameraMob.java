package urfquest.client.entities.mobs;

import java.awt.Graphics;

import urfquest.client.Client;
import urfquest.client.map.Map;

public class CameraMob extends Mob {
	public static int STILL_MODE = 1301;
	public static int DEMO_MODE = 1302;

	public CameraMob(Client c, int id, Map m, double x, double y, int mode) {
		super(c, id, m, x, y);
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
