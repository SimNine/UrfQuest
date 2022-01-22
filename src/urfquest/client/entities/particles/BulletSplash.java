package urfquest.client.entities.particles;

import java.awt.Color;
import java.awt.Graphics;

import urfquest.Main;
import urfquest.client.Client;
import urfquest.client.map.Map;

public class BulletSplash extends Particle {

	public BulletSplash(Client c, int id, Map m, double x, double y) {
		super(c, id, m, x, y, (int)(Math.random()*360.0), 0.04, 10);
	}

	protected void drawEntity(Graphics g) {
		g.setColor(Color.RED);
		int x = Main.panel.gameToWindowX(bounds.getX());
		int y = Main.panel.gameToWindowY(bounds.getY());
		g.fillOval(x, y, 3, 3);
	}
	
	public void update() {
		this.move(dir, velocity);
		//this.animStage++;
	}
}