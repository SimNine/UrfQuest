package client.entities.projectiles;

import java.awt.Color;
import java.awt.Graphics;

public class Bullet extends Projectile {

	public void drawEntity(Graphics g) {
		int tileWidth = QuestPanel.TILE_WIDTH;
		g.setColor(Color.BLACK);
		g.fillOval(UrfQuest.panel.gameToWindowX(bounds.getX()), 
				   UrfQuest.panel.gameToWindowY(bounds.getY()),
				   (int)(bounds.getWidth()*tileWidth), 
				   (int)(bounds.getHeight()*tileWidth));
	}
}
