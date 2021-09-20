package urfquest.client.entities.particles;

import java.awt.Color;
import java.awt.Graphics;

import urfquest.Main;
import urfquest.client.QuestPanel;
import urfquest.client.map.Map;

public class RocketExhaust extends Particle {
	private Color color;

	public RocketExhaust(int id, Map m, double x, double y) {
		super(id, m, x, y, (int)(Math.random()*360.0), 0.04, 100);
		color = new Color(255, 255, 255, 255);
		bounds.setFrame(x, y, 0, 0);
	}

	protected void drawEntity(Graphics g) {
		int tileWidth = QuestPanel.TILE_WIDTH;
		g.setColor(color);
		g.fillOval(Main.panel.gameToWindowX(bounds.getX()), 
				   Main.panel.gameToWindowY(bounds.getY()),
				   (int)(bounds.getWidth()*tileWidth), 
				   (int)(bounds.getHeight()*tileWidth));
	}

	public void update() {
		//animStage++;
		
		// try changing color
//		switch (animStage / 20) {
//		case 0:
//			color = new Color(255, 255, 255, 255);
//			break;
//		case 1:
//			color = new Color(200, 200, 200, 200);
//			break;
//		case 2:
//			color = new Color(150, 150, 150, 150);
//			break;
//		case 3:
//			color = new Color(100, 100, 100, 100);
//			break;
//		case 4:
//			color = new Color(50, 50, 50, 50);
//			break;
//		}
		
		// change shape and size
		this.move(-0.02, -0.02);
		bounds.setRect(bounds.x, bounds.y, bounds.width + 0.04, bounds.height + 0.04);
	}
}