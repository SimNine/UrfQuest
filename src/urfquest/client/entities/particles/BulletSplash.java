package urfquest.client.entities.particles;

import java.awt.Color;
import java.awt.Graphics;

import urfquest.client.Client;
import urfquest.client.map.Map;

public class BulletSplash extends Particle {

	public BulletSplash(Client c, int id, Map m, double[] pos) {
		super(c, id, m, pos, (Math.random()*Math.PI*2), 0.04, 10);
	}

	protected void drawEntity(Graphics g) {
		g.setColor(Color.RED);
		int x = client.getPanel().gameToWindowX(bounds.getX());
		int y = client.getPanel().gameToWindowY(bounds.getY());
		g.fillOval(x, y, 3, 3);
	}
	
	public void update() {
		//this.move(dir, velocity);
		//this.animStage++;
	}
}