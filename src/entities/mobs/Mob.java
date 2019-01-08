package entities.mobs;

import java.awt.Color;
import java.awt.Graphics;

import entities.Entity;
import framework.QuestPanel;
import framework.UrfQuest;
import game.QuestMap;
import tiles.Tiles;

public abstract class Mob extends Entity {
	protected final static String assetPath = "/assets/entities/";
	protected int direction = 0;
	protected double velocity = 0;
	protected double health = 0;
	protected double maxHealth = 0;
	protected int healthbarVisibility = 0;

	protected Mob(double x, double y) {
		super(x, y);
	}

	public abstract void update();
	
	public void onDeath() {
		// do nothing by default
	}
	
	// returns true if the move is valid (in one or both directions), returns false if not
	protected boolean attemptMove(int dir) {
		QuestMap currMap = UrfQuest.game.getCurrMap();
		double newX = bounds.getX() + bounds.getWidth()/2.0;
		double newY = bounds.getY() + bounds.getHeight()/2.0;
		double xComp = velocity*Math.cos(Math.toRadians(dir));
		double yComp = velocity*Math.sin(Math.toRadians(dir));
		
		boolean ret = false;
		
		// attempt to move on the x-axis
		if (Tiles.isWalkable(currMap.getTileAt((int)(newX + xComp), (int)newY))) {
			newX += xComp;
			ret = true;
		} // else (if collision) do nothing
		
		// attempt to move on the y-axis
		if (Tiles.isWalkable(currMap.getTileAt((int)newX, (int)(newY + yComp)))) {
			newY += yComp;
			ret = true;
		} // else (if collision) do nothing
		
		bounds.setRect(newX - bounds.getWidth()/2.0, newY - bounds.getHeight()/2.0, bounds.getWidth(), bounds.getHeight());
		return ret;
	}

	// drawing methods
	protected abstract void drawEntity(Graphics g);
	
	protected void drawHealthBar(Graphics g) {
		if (healthbarVisibility == 0) {
			return;
		}
		
		Player player = UrfQuest.game.getPlayer();
		int topLeftX = (int)(UrfQuest.panel.dispCenterX + (bounds.getX() - player.getPos()[0] + bounds.getWidth()/2.0)*QuestPanel.TILE_WIDTH) - 26;
		int topLeftY = (int)(UrfQuest.panel.dispCenterY + (bounds.getY() - player.getPos()[1] + bounds.getHeight())*QuestPanel.TILE_WIDTH);
		
		int vis;
		if (healthbarVisibility > 255) {
			vis = 255;
		} else {
			vis = healthbarVisibility;
		}
		
		g.setColor(new Color(0, 0, 0, vis));
		g.fillRect(topLeftX, topLeftY, 52, 5);
		g.setColor(new Color(255, 0, 0, vis));
		g.fillRect(topLeftX+1, topLeftY+1, (int)(50*(health/maxHealth)), 3);
	}

	protected void drawDebug(Graphics g) {
		Player player = UrfQuest.game.getPlayer();
		g.setColor(Color.WHITE);
		g.drawString("bounds coords: " + bounds.getX() + ", " + bounds.getY(),
					(int)(UrfQuest.panel.dispCenterX - (player.getPos()[0] - bounds.getX())*QuestPanel.TILE_WIDTH),
					(int)(UrfQuest.panel.dispCenterY - (player.getPos()[1] - bounds.getY())*QuestPanel.TILE_WIDTH));
		g.drawString("bounds dimensions: " + bounds.getWidth() + ", " + bounds.getHeight(),
				 	(int)(UrfQuest.panel.dispCenterX - (player.getPos()[0] - bounds.getX())*QuestPanel.TILE_WIDTH),
				 	(int)(UrfQuest.panel.dispCenterY - (player.getPos()[1] - bounds.getY())*QuestPanel.TILE_WIDTH)+10);
	};

	// setters, getters, and incrementers
	public void setDirection(int dir) {
		direction = dir % 360;
	}
	
	public void setHealth(double h) {
		health = h;
		healthbarVisibility = 500;
	}
	
	public void setVelocity(double s) {
		velocity = s;
	}
	
	public int getDirection() {
		return direction;
	}
	
	public double getHealth() {
		return health;
	}
	
	public double getVelocity() {
		return velocity;
	}
	
	public void incrementHealth(double amt) {
		setHealth(health + amt);
	}
	
	public void incrementVelocity(double amt) {
		setVelocity(velocity + amt);
	}
	
	public boolean isDead() {
		return health <= 0;
	}
}
