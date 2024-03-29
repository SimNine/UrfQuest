package xyz.urffer.urfquest.client.entities.particles;

import java.awt.Color;
import java.awt.Graphics;

import xyz.urffer.urfquest.client.Client;
import xyz.urffer.urfquest.client.QuestPanel;
import xyz.urffer.urfquest.client.map.Map;

public class RocketExhaust extends Particle {
	private Color color;

	public RocketExhaust(Client c, int id, Map m, double[] pos) {
		super(c, id, m, pos, (Math.random()*Math.PI*2), 0.04, 100);
		color = new Color(255, 255, 255, 255);
		bounds.setFrame(pos[0], pos[1], 0, 0);
	}

	protected void drawEntity(Graphics g) {
		int tileWidth = QuestPanel.TILE_WIDTH;
		g.setColor(color);
		g.fillOval(client.getPanel().gameToWindowX(bounds.getX()), 
				   client.getPanel().gameToWindowY(bounds.getY()),
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
		this.incrementPos(new double[] {-0.02, -0.02});
		bounds.setRect(bounds.x, bounds.y, bounds.width + 0.04, bounds.height + 0.04);
	}
}