package client.entities;

import java.awt.Color;
import java.awt.Graphics;

import framework.QuestPanel;
import framework.UrfQuest;

public abstract class Entity {
	
	protected int animStage = 0;

	// Drawing methods
	public void draw(Graphics g) {
		drawEntity(g);
		if (UrfQuest.debug) {
			drawDebug(g);
			drawBounds(g);
		}
	}
	
	protected abstract void drawEntity(Graphics g);
	
	protected abstract void drawDebug(Graphics g);
	
	private void drawBounds(Graphics g) {
		g.setColor(Color.RED);
		g.drawRect((int) UrfQuest.panel.gameToWindowX(bounds.getX()), 
				   (int) UrfQuest.panel.gameToWindowY(bounds.getY()),
				   (int)(bounds.getWidth()*QuestPanel.TILE_WIDTH), 
				   (int)(bounds.getHeight()*QuestPanel.TILE_WIDTH));
	}
	
}
