package urfquest.client.entities.mobs;

import java.awt.Graphics;

import urfquest.client.Client;
import urfquest.client.map.Map;
import urfquest.shared.Constants;
import urfquest.shared.Vector;

public class CameraMob extends Mob {
	public static int STILL_MODE = 1301;
	public static int DEMO_MODE = 1302;

	public CameraMob(Client c, int id, Map m, double[] pos, int mode) {
		super(c, id, m, pos);
		this.movementVector = new Vector(Math.random() * Math.PI * 2, Constants.DEFAULT_VELOCITY_CAMERA);
		health = 100.0;
		maxHealth = 100.0;
	}

	public void update() {
		this.incrementPos(this.movementVector);
	}

	protected void drawEntity(Graphics g) {
		// do nothing
	}
}
