package xyz.urffer.urfquest.client.entities.mobs;

import java.awt.Color;
import java.awt.Graphics;

import xyz.urffer.urfquest.client.Client;
import xyz.urffer.urfquest.client.QuestPanel;
import xyz.urffer.urfquest.client.entities.Entity;

public abstract class Mob extends Entity {
	protected final static String assetPath = "/xyz/urffer/urfquest/assets/entities/";
	
	protected int health;
	protected int mana;
	protected int fullness;
	
	protected int maxHealth;
	protected int maxMana;
	protected int maxFullness;
	
	protected int healthbarVisibility = 0;

	protected Mob(Client c, int id) {
		super(c, id);
	}

	public abstract void update();

	public void onDeath() {
		// do nothing by default
	}

	
	
	public void setHealth(int h) {
		this.health = h;
		this.healthbarVisibility = 500;
	}
	
	public int getHealth() {
		return this.health;
	}
	
	public int getMaxHealth() {
		return this.maxHealth;
	}
	
	
	
	public void setMana(int m) {
		this.mana = m;
	}
	
	public int getMana() {
		return this.mana;
	}
	
	public int getMaxMana() {
		return this.maxMana;
	}
	
	
	
	public void setFullness(int f) {
		this.fullness = f;
	}
	
	public int getFullness() {
		return this.fullness;
	}
	
	public int getMaxFullness() {
		return this.maxFullness;
	}
	
	
	
	public boolean isDead() {
		return this.health <= 0;
	}
	
	
	/*
	 * Drawing methods
	 */
	
	protected abstract void drawEntity(Graphics g);
	
	protected void drawHealthBar(Graphics g) {
		if (healthbarVisibility == 0) {
			return;
		}
		
		int topLeftX = (int)(client.getPanel().gameToWindowX(this.getPos().x) + (this.getDims().x/2.0)*QuestPanel.TILE_WIDTH) - 26;
		int topLeftY = (int)(client.getPanel().gameToWindowY(this.getPos().y) + this.getDims().y*QuestPanel.TILE_WIDTH);
		
		int vis;
		if (healthbarVisibility > 255) {
			vis = 255;
		} else {
			vis = healthbarVisibility;
		}
		
		g.setColor(new Color(0, 0, 0, vis));
		g.fillRect(topLeftX, topLeftY, 52, 5);
		g.setColor(new Color(255, 0, 0, vis));
		g.fillRect(topLeftX+1, topLeftY+1, (int)(50.0*((double)health/maxHealth)), 3);
	}

	protected void drawDebug(Graphics g) {
		g.setColor(Color.WHITE);
		g.drawString("bounds: (" + (int)this.getPos().x + "," + (int)this.getPos().y + ") " + 
					 this.getDims().x + "*" + this.getDims().y,
					(int) client.getPanel().gameToWindowX(this.getPos().x),
					(int) client.getPanel().gameToWindowY(this.getPos().y));
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
