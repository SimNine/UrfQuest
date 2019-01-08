package entities.particles;

import java.awt.Color;
import java.awt.Graphics;

import entities.Entity;
import framework.QuestPanel;
import framework.UrfQuest;

public class Particle extends Entity {
	protected double velocity = 0;
	protected int direction = 0;

	public Particle(double x, double y, int dir) {
		super(x, y);
		bounds.setRect(bounds.getX(), bounds.getY(), 0.15, 0.15);
		velocity = Math.random()*0.05 + 0.05;
		direction = dir;
	}

	@Override
	protected void drawEntity(Graphics g) {
		int tileWidth = QuestPanel.TILE_WIDTH;
		g.setColor(Color.BLACK);
		g.fillOval(UrfQuest.panel.gameToWindowX(bounds.getX()), 
				   UrfQuest.panel.gameToWindowY(bounds.getY()),
				   (int)(bounds.getWidth()*tileWidth), 
				   (int)(bounds.getHeight()*tileWidth));
	}
	
	public void drawDebug(Graphics g) {
		g.setColor(Color.WHITE);
		g.drawString("velocity: " + velocity, 
					 UrfQuest.panel.gameToWindowX(bounds.getX()),
					 UrfQuest.panel.gameToWindowY(bounds.getY()));
		g.drawString("direction: " + direction, 
					 UrfQuest.panel.gameToWindowX(bounds.getX()),
					 UrfQuest.panel.gameToWindowY(bounds.getY())+10);
	}

	@Override
	public void update() {
		this.move(velocity*Math.cos(Math.toRadians(direction)), velocity*Math.sin(Math.toRadians(direction)));
		animStage++;
	}
	
	public boolean isDead() {
		return (animStage > 1000);
	}

}