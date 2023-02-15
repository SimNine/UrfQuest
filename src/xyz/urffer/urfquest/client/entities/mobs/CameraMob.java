package xyz.urffer.urfquest.client.entities.mobs;

import java.awt.Graphics;

import xyz.urffer.urfquest.client.Client;
import xyz.urffer.urfquest.shared.Constants;

public class CameraMob extends Mob {
	public static int STILL_MODE = 1301;
	public static int DEMO_MODE = 1302;

	public CameraMob(Client c, int id, int mode) {
		super(c, id);
		health = Constants.DEFAULT_HEALTH_MAX_PLAYER;
		maxHealth = Constants.DEFAULT_HEALTH_MAX_PLAYER;
	}

	public void update() {
		// do nothing
	}

	protected void drawEntity(Graphics g) {
		// do nothing
	}
}
