package client.entities.mobs;

import java.awt.Color;
import java.awt.Graphics;

import client.entities.Entity;

public abstract class Mob extends Entity {

	// drawing methods
	protected abstract void drawEntity(Graphics g);
	
	protected void drawHealthBar(Graphics g) {
		if (healthbarVisibility == 0) {
			return;
		}
		
		int topLeftX = (int)(UrfQuest.panel.gameToWindowX(bounds.getX()) + (bounds.getWidth()/2.0)*QuestPanel.TILE_WIDTH) - 26;
		int topLeftY = (int)(UrfQuest.panel.gameToWindowY(bounds.getY()) + bounds.getHeight()*QuestPanel.TILE_WIDTH);
		
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
					(int) UrfQuest.panel.gameToWindowX(bounds.getX()),
					(int) UrfQuest.panel.gameToWindowY(bounds.getY()));
		g.drawString(("routine: " + routine.getClass().getSimpleName()),
			 		 (int) UrfQuest.panel.gameToWindowX(bounds.getX()),
			 		 (int) UrfQuest.panel.gameToWindowY(bounds.getY()) +10);
		g.drawString(("action: " + routine.getCurrentAction().getClass().getSimpleName() + " duration: " + routine.getCurrentAction().getDuration()),
		 		 (int) UrfQuest.panel.gameToWindowX(bounds.getX()),
		 		 (int) UrfQuest.panel.gameToWindowY(bounds.getY()) + 20);
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
