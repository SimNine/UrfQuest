package server.entities.particles;

import java.awt.Color;
import java.awt.Graphics;

import framework.UrfQuest;
import server.game.QuestMap;

public class BulletSplash extends Particle {

	public BulletSplash(double x, double y, QuestMap m) {
		super(x, y, (int)(Math.random()*360.0), 0.04, 10, m);
	}

	protected void drawEntity(Graphics g) {
		g.setColor(Color.RED);
		int x = UrfQuest.panel.gameToWindowX(bounds.getX());
		int y = UrfQuest.panel.gameToWindowY(bounds.getY());
		g.fillOval(x, y, 3, 3);
	}
	
	public void update() {
		this.move(dir, velocity);
		this.animStage++;
	}
}