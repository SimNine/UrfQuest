package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

import entities.mobs.Player;
import framework.QuestPanel;
import framework.UrfQuest;

public abstract class Entity {
	protected Rectangle2D.Double bounds;

	// default values
	protected String type;
	protected String orientation = "N";
	protected int animStage = 0;
	
	protected Entity(double x, double y) {
		bounds = new Rectangle2D.Double(x, y, 1, 1);
	}
	
	// Drawing methods
	public void draw(Graphics g) {
		drawEntity(g);
		if (UrfQuest.debug) drawDebug(g);
		if (UrfQuest.debug) drawBounds(g);
	}
	
	protected abstract void drawEntity(Graphics g);
	
	protected abstract void drawDebug(Graphics g);
	
	private void drawBounds(Graphics g) {
		Player player = UrfQuest.game.getPlayer();
		g.setColor(Color.RED);
		g.drawRect((int)(UrfQuest.panel.dispCenterX - (player.getPosition()[0] - bounds.getX())*QuestPanel.TILE_WIDTH), 
				   (int)(UrfQuest.panel.dispCenterY - (player.getPosition()[1] - bounds.getY())*QuestPanel.TILE_WIDTH),
				   (int)(bounds.getWidth()*QuestPanel.TILE_WIDTH), (int)(bounds.getHeight()*QuestPanel.TILE_WIDTH));
	}
	
	// Updating methods
	public abstract void update();
	
	// returns true if this entity's bounds intersect with another entity's bounds
	public boolean collides(Entity e) {
		return bounds.intersects(e.bounds);
	}
	
	// sets the entity's position, NOT checking for validity of move
	public void setPosition(double x, double y) {
		bounds.setRect(x, y, bounds.getWidth(), bounds.getHeight());
	}
	
	// moves the entity, NOT checking for validity of move
	// object's position is incremented according to the parameters
	public void move(double x, double y) {
		bounds.setRect(bounds.getX() + x, bounds.getY() + y, bounds.getWidth(), bounds.getHeight());
	}
	
	// gets the object's position as a double array with length 2 (x, y)
	public double[] getPosition() {
		double[] ret = new double[2];
		ret[0] = bounds.getX();
		ret[1] = bounds.getY();
		return ret;
	}
	
	// returns a string representing the type of entity
	public String getType() {
		return type;
	}
}