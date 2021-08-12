package urfquest.client.entities.mobs;

import java.awt.Color;
import java.awt.Graphics;

import urfquest.Main;
import urfquest.client.QuestPanel;
import urfquest.client.entities.Entity;
import urfquest.client.map.Map;
import urfquest.client.tiles.Tiles;

public abstract class Mob extends Entity {
	protected final static String assetPath = "/assets/entities/";
	protected int direction = 0;
	protected double velocity;
	protected double defaultVelocity;
	
	protected double health;
	protected double mana;
	protected double fullness;
	protected double maxHealth;
	protected double maxMana;
	protected double maxFullness;
	protected int healthbarVisibility = 0;

	protected Mob(double x, double y, Map m) {
		super(x, y, m);
	}

	public abstract void update();
	
	public void onDeath() {
		// do nothing by default
	}
	
	// returns true if the move is valid (in one or both directions), returns false if not
	// if move is valid, moves the mob
	protected boolean attemptMove(int dir, double velocity) {
		double newX = bounds.getCenterX();
		double newY = bounds.getCenterY();
		double xComp = velocity*Math.cos(Math.toRadians(dir));
		double yComp = velocity*Math.sin(Math.toRadians(dir));
		
		boolean ret = false;
		
		// attempt to move on the x-axis
		if (Tiles.isWalkable(map.getTileTypeAt((int)(newX + xComp), (int)newY))) {
			newX += xComp;
			ret = true;
		} // else (if collision) do nothing
		
		// attempt to move on the y-axis
		if (Tiles.isWalkable(map.getTileTypeAt((int)newX, (int)(newY + yComp)))) {
			newY += yComp;
			ret = true;
		} // else (if collision) do nothing
		
		bounds.setRect(newX - bounds.getWidth()/2.0, newY - bounds.getHeight()/2.0, bounds.getWidth(), bounds.getHeight());
		return ret;
	}
	
	// returns the tile at distance 'd' away from the center of this mob, in the direction it is facing
	public int[] tileAtDistance(double d) {
		double xComp = d*Math.cos(Math.toRadians(direction));
		double yComp = d*Math.sin(Math.toRadians(direction));
		return map.getTileAt((int)(bounds.getCenterX() + xComp), (int)(bounds.getCenterY() + yComp));
	}
	
	// returns the tile coords of the tile at the distance 'd' away form the center of this mob, in the direction it is facing
	public int[] tileCoordsAtDistance(double d) {
		double xComp = d*Math.cos(Math.toRadians(direction));
		double yComp = d*Math.sin(Math.toRadians(direction));
		
		int[] ret = new int[2];
		ret[0] = (int)(bounds.getCenterX() + xComp);
		ret[1] = (int)(bounds.getCenterY() + yComp);
		
		return ret;
	}

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
	
	public double getDefaultVelocity() {
		return defaultVelocity;
	}
	
	public void incrementHealth(double amt) {
		setHealth(health + amt);
	}
	
	public void incrementVelocity(double amt) {
		setVelocity(velocity + amt);
	}
	
	public void incrementMana(double amt) {
		setMana(mana + amt);
	}
	
	public void setMana(double m) {
		if (m > maxMana) {
			mana = maxMana;
		} else {
			mana = m;
		}
	}
	
	public double getMana() {
		return mana;
	}
	
	public double getMaxMana() {
		return maxMana;
	}
	
	public void incrementFullness(double amt) {
		setFullness(fullness + amt);
	}
	
	public void setFullness(double f) {
		if (f > maxFullness) {
			fullness = maxFullness;
		} else {
			fullness = f;
		}
	}
	
	public double getFullness() {
		return fullness;
	}
	
	public double getMaxFullness() {
		return maxFullness;
	}
	
	public boolean isDead() {
		return health <= 0;
	}
	
	public void setMap(Map m) {
		map.removeMob(this);
		m.addMob(this);
		map = m;
	}

	/*
	 * Drawing methods
	 */
	
	protected abstract void drawEntity(Graphics g);
	
	protected void drawHealthBar(Graphics g) {
		if (healthbarVisibility == 0) {
			return;
		}
		
		int topLeftX = (int)(Main.panel.gameToWindowX(bounds.getX()) + (bounds.getWidth()/2.0)*QuestPanel.TILE_WIDTH) - 26;
		int topLeftY = (int)(Main.panel.gameToWindowY(bounds.getY()) + bounds.getHeight()*QuestPanel.TILE_WIDTH);
		
		int vis;
		if (healthbarVisibility > 255) {
			vis = 255;
		} else {
			vis = healthbarVisibility;
		}
		
		g.setColor(new Color(0, 0, 0, vis));
		g.fillRect(topLeftX, topLeftY, 52, 5);
		g.setColor(new Color(255, 0, 0, vis));
		g.fillRect(topLeftX+1, topLeftY+1, (int)(50.0*(health/maxHealth)), 3);
	}

	protected void drawDebug(Graphics g) {
		g.setColor(Color.WHITE);
		g.drawString("bounds: (" + (int)bounds.getX() + "," + (int)bounds.getY() + ") " + bounds.getWidth() + "*" + bounds.getHeight(),
					(int) Main.panel.gameToWindowX(bounds.getX()),
					(int) Main.panel.gameToWindowY(bounds.getY()));
//		g.drawString(("routine: " + routine.getClass().getSimpleName()),
//			 		 (int) UrfQuestClient.panel.gameToWindowX(bounds.getX()),
//			 		 (int) UrfQuestClient.panel.gameToWindowY(bounds.getY()) +10);
//		g.drawString(("action: " + routine.getCurrentAction().getClass().getSimpleName() + " duration: " + routine.getCurrentAction().getDuration()),
//		 		 (int) UrfQuestClient.panel.gameToWindowX(bounds.getX()),
//		 		 (int) UrfQuestClient.panel.gameToWindowY(bounds.getY()) + 20);
//		g.drawString(("angleTo: " + player.angleTo(this)),
//		 		 (int)(UrfQuest.panel.dispCenterX - (player.getPos()[0] - bounds.getX())*QuestPanel.TILE_WIDTH),
//		 		 (int)(UrfQuest.panel.dispCenterY - (player.getPos()[1] - bounds.getY())*QuestPanel.TILE_WIDTH)+30);
//		g.drawString(("distTo: " + player.distanceTo(this)),
//		 		 (int)(UrfQuest.panel.dispCenterX - (player.getPos()[0] - bounds.getX())*QuestPanel.TILE_WIDTH),
//		 		 (int)(UrfQuest.panel.dispCenterY - (player.getPos()[1] - bounds.getY())*QuestPanel.TILE_WIDTH)+40);
//		g.drawString(("hasClearPath: " + this.hasClearPathTo(player)),
//		 		 (int)(UrfQuest.panel.dispCenterX - (player.getPos()[0] - bounds.getX())*QuestPanel.TILE_WIDTH),
//		 		 (int)(UrfQuest.panel.dispCenterY - (player.getPos()[1] - bounds.getY())*QuestPanel.TILE_WIDTH)+50);
	};
}
