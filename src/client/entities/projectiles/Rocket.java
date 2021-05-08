package client.entities.projectiles;

import java.awt.Color;
import java.awt.Graphics;

import server.entities.projectiles.RocketExhaust;

public class Rocket extends Projectile {

	public void drawEntity(Graphics g) {
		int tileWidth = QuestPanel.TILE_WIDTH;
		g.setColor(Color.RED.darker());
		g.fillOval(UrfQuest.panel.gameToWindowX(bounds.getX()), 
				   UrfQuest.panel.gameToWindowY(bounds.getY()),
				   (int)(bounds.getWidth()*tileWidth), 
				   (int)(bounds.getHeight()*tileWidth));
	}
	
	public void update() {

		animStage++;
		if (animStage % 10 == 0) {
			map.addParticle(new RocketExhaust(bounds.getCenterX(), bounds.getCenterY(), map));
		}
	}

}
