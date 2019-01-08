package entities.projectiles;

import java.awt.Color;
import java.awt.Graphics;

import entities.Entity;
import entities.mobs.Mob;
import framework.QuestPanel;
import framework.UrfQuest;
import game.QuestMap;
import tiles.Tiles;

public class RocketExplosion extends Projectile {
	private Color color;

	public RocketExplosion(double x, double y, Entity source, QuestMap m) {
		super(x, y, source, m);
		this.bounds.setRect(bounds.getX(), bounds.getY(), 0.3, 0.3);
		this.velocity = 0;
		this.direction = 0;
		this.color = Color.YELLOW;
	}

	public void drawEntity(Graphics g) {
		int tileWidth = QuestPanel.TILE_WIDTH;
		g.setColor(color);
		g.fillOval(UrfQuest.panel.gameToWindowX(bounds.getX()), 
				   UrfQuest.panel.gameToWindowY(bounds.getY()),
				   (int)(bounds.getWidth()*tileWidth), 
				   (int)(bounds.getHeight()*tileWidth));
	}

	public void update() {
		this.move(-0.04, -0.04);
		bounds.setRect(bounds.x, bounds.y, bounds.width + 0.08, bounds.height + 0.08);
		animStage++;
		switch (animStage/20) {
		case 0:
			color = Color.YELLOW;
			break;
		case 1:
			color = Color.ORANGE.brighter();
			break;
		case 2:
			color = Color.ORANGE;
			break;
		case 3:
			color = Color.RED;
			break;
		case 4:
			color = Color.RED.darker();
			break;
		}
		
		// clear trees
		for(int i = 0; i < 20; i++) {
		    int xPos = (int)Math.round(bounds.getCenterX() + bounds.width/2 * Math.cos((Math.PI/10)*i) - 0.5);
		    int yPos = (int)Math.round(bounds.getCenterY() + bounds.width/2 * Math.sin((Math.PI/10)*i) - 0.5);

		    if (map.getTileTypeAt(xPos, yPos) == Tiles.TREE) {
		    	map.setTileAt(xPos, yPos, Tiles.GRASS);
		    }
		}
	}

	public boolean isDead() {
		return (animStage > 100);
	}
	
	public void collideWith(Mob m) {
		m.incrementHealth(-0.15);
	}
}