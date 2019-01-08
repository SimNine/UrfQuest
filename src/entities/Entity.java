package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

import framework.QuestPanel;
import entities.characters.Player;
import framework.UrfQuest;

public abstract class Entity {
	protected String type;
	
	protected Rectangle2D.Double bounds;
	
	protected String orientation;
	protected int moveStage = 0;

	protected double Xpos;
	protected double Ypos;
	
	public Entity(double x, double y) {
		Xpos = x;
		Ypos = y;
		orientation = "N";
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
				   (int)bounds.getWidth(), (int)bounds.getHeight());
	}
	
	// Updating methods
	public abstract void update();
	
	public void setPosition(double x, double y) {
		Xpos = x;
		Ypos = y;
	}
	
	public double[] getPosition() {
		double[] ret = new double[2];
		ret[0] = Xpos;
		ret[1] = Ypos;
		return ret;
	}
	
	public String getType() {
		return type;
	}
}