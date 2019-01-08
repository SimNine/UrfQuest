package entities.projectiles;

import java.awt.Color;
import java.awt.Graphics;

import entities.Entity;
import framework.QuestPanel;
import framework.UrfQuest;

public class Bullet extends Projectile {

	public Bullet(double x, double y, int dir, Entity source) {
		super(x, y, source);
		bounds.setRect(bounds.getX(), bounds.getY(), 0.15, 0.15);
		velocity = Math.random()*0.03 + 0.07;
		direction = dir;
	}

	public void drawEntity(Graphics g) {
		int tileWidth = QuestPanel.TILE_WIDTH;
		g.setColor(Color.BLACK);
		g.fillOval(UrfQuest.panel.gameToWindowX(bounds.getX()), 
				   UrfQuest.panel.gameToWindowY(bounds.getY()),
				   (int)(bounds.getWidth()*tileWidth), 
				   (int)(bounds.getHeight()*tileWidth));
	}

	public void update() {
		this.move(velocity*Math.cos(Math.toRadians(direction)), velocity*Math.sin(Math.toRadians(direction)));
		animStage++;
	}

	public boolean isDead() {
		return (animStage > 1000);
	}

}