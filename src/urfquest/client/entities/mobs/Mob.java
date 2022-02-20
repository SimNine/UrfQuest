package urfquest.client.entities.mobs;

import java.awt.Color;
import java.awt.Graphics;

import urfquest.client.Client;
import urfquest.client.QuestPanel;
import urfquest.client.entities.Entity;
import urfquest.client.map.Map;
import urfquest.client.tiles.Tiles;

public abstract class Mob extends Entity {
	protected final static String assetPath = "/assets/entities/";
	
	protected double health;
	protected double mana;
	protected double fullness;
	protected double maxHealth;
	protected double maxMana;
	protected double maxFullness;
	protected int healthbarVisibility = 0;

	protected Mob(Client c, int id, Map m, double[] pos) {
		super(c, id, m, pos);
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
		double xComp = d*Math.cos(this.movementVector.dirRadians);
		double yComp = d*Math.sin(this.movementVector.dirRadians);
		return map.getTileAt((int)(bounds.getCenterX() + xComp), (int)(bounds.getCenterY() + yComp));
	}
	
	// returns the tile coords of the tile at the distance 'd' away form the center of this mob, in the direction it is facing
	public int[] tileCoordsAtDistance(double d) {
		double xComp = d*Math.cos(this.movementVector.dirRadians);
		double yComp = d*Math.sin(this.movementVector.dirRadians);
		
		int[] ret = new int[2];
		ret[0] = (int)(bounds.getCenterX() + xComp);
		ret[1] = (int)(bounds.getCenterY() + yComp);
		
		return ret;
	}

	// setters, getters, and incrementers
	
	public void setHealth(double h) {
		health = h;
		healthbarVisibility = 500;
	}
	
	public double getHealth() {
		return health;
	}
	
	public void incrementHealth(double amt) {
		setHealth(health + amt);
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
		
		int topLeftX = (int)(client.getPanel().gameToWindowX(bounds.getX()) + (bounds.getWidth()/2.0)*QuestPanel.TILE_WIDTH) - 26;
		int topLeftY = (int)(client.getPanel().gameToWindowY(bounds.getY()) + bounds.getHeight()*QuestPanel.TILE_WIDTH);
		
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
					(int) client.getPanel().gameToWindowX(bounds.getX()),
					(int) client.getPanel().gameToWindowY(bounds.getY()));
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
