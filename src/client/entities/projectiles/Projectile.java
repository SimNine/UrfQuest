package client.entities.projectiles;

import java.awt.Color;
import java.awt.Graphics;

import client.entities.Entity;

public abstract class Projectile extends Entity {

	protected abstract void drawEntity(Graphics g);
	
	public void drawDebug(Graphics g) {
		g.setColor(Color.WHITE);
		g.drawString("velocity: " + velocity, 
					 UrfQuest.panel.gameToWindowX(bounds.getX()),
					 UrfQuest.panel.gameToWindowY(bounds.getY()));
		g.drawString("direction: " + direction, 
					 UrfQuest.panel.gameToWindowX(bounds.getX()),
					 UrfQuest.panel.gameToWindowY(bounds.getY())+10);
	}

}
