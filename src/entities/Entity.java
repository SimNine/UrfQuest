package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

import entities.characters.Player;
import framework.QuestPanel;
import framework.UrfQuest;

public abstract class Entity {
	protected String type;
	
	protected Rectangle2D.Double bounds;
	
	protected String orientation;
	protected int animStage = 0;
	
	protected Entity(double x, double y) {
		bounds = new Rectangle2D.Double(x, y, 1, 1);
		orientation = "N";
	}
	
	// Drawing methods
	public void draw(Graphics g) {
		drawEntity(g);
		if (UrfQuest.debug) drawDebug(g);
		if (UrfQuest.debug) drawBounds(g);
	}
	
	protected abstract void drawEntity(Graphics g);
	
	protected void drawDebug(Graphics g) {
		Player player = UrfQuest.game.getPlayer();
		g.setColor(Color.WHITE);
		g.drawString("bounds coords: " + bounds.getX() + ", " + bounds.getY(),
					 (int)(UrfQuest.panel.dispCenterX - (player.getPosition()[0] - bounds.getX())*QuestPanel.TILE_WIDTH),
					 (int)(UrfQuest.panel.dispCenterY - (player.getPosition()[1] - bounds.getY())*QuestPanel.TILE_WIDTH));
		g.drawString("bounds dimensions: " + bounds.getWidth() + ", " + bounds.getHeight(),
				 (int)(UrfQuest.panel.dispCenterX - (player.getPosition()[0] - bounds.getX())*QuestPanel.TILE_WIDTH),
				 (int)(UrfQuest.panel.dispCenterY - (player.getPosition()[1] - bounds.getY())*QuestPanel.TILE_WIDTH)+10);
	};
	
	private void drawBounds(Graphics g) {
		Player player = UrfQuest.game.getPlayer();
		g.setColor(Color.RED);
		g.drawRect((int)(UrfQuest.panel.dispCenterX - (player.getPosition()[0] - bounds.getX())*QuestPanel.TILE_WIDTH), 
				   (int)(UrfQuest.panel.dispCenterY - (player.getPosition()[1] - bounds.getY())*QuestPanel.TILE_WIDTH),
				   (int)(bounds.getWidth()*QuestPanel.TILE_WIDTH), (int)(bounds.getHeight()*QuestPanel.TILE_WIDTH));
	}
	
	// Updating methods
	public abstract void update();
	
	public boolean collides(Entity e) {
		return bounds.intersects(e.bounds);
	}
	
	public void setPosition(double x, double y) {
		bounds.setRect(x, y, bounds.getWidth(), bounds.getHeight());
	}
	
	public double[] getPosition() {
		double[] ret = new double[2];
		ret[0] = bounds.getX();
		ret[1] = bounds.getY();
		return ret;
	}
	
	public String getType() {
		return type;
	}
}