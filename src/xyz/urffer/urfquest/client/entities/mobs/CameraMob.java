package xyz.urffer.urfquest.client.entities.mobs;

import java.awt.Graphics;

import xyz.urffer.urfquest.client.Client;

public class CameraMob extends Mob {
	public static int STILL_MODE = 1301;
	public static int DEMO_MODE = 1302;

	public CameraMob(Client c, int id, int mode) {
		super(c, id);
		health = 1000;
		maxHealth = 1000;
	}

	public void update() {
		this.incrementPos(this.movementVector);
	}

	protected void drawEntity(Graphics g) {
		// do nothing
	}
}
