package server.entities.projectiles;

import java.awt.Color;
import java.awt.Graphics;

import server.entities.Entity;
import server.entities.mobs.Mob;
import server.game.QuestMap;
import framework.UrfQuest;

public abstract class Projectile extends Entity {
	protected double velocity;
	protected int direction;
	protected Entity source;

	protected Projectile(double x, double y, Entity source, QuestMap m) {
		super(x, y, m);
		this.source = source;
	}

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

	public abstract void update();
	
	public abstract boolean isDead();

	public Entity getSource() {
		return source;
	}
	
	public abstract void collideWith(Mob m);
}