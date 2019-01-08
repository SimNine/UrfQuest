package entities.particles;

import java.awt.Color;
import java.awt.Graphics;

import entities.Entity;
import entities.mobs.Player;
import framework.QuestPanel;
import framework.UrfQuest;

public class Particle extends Entity {
	protected double velocity = 0;
	protected int direction = 0;

	public Particle(double x, double y, int dir) {
		super(x, y);
		bounds.setRect(bounds.getX(), bounds.getY(), 0.2, 0.2);
		velocity = Math.random()*0.05 + 0.05;
		direction = dir;
	}

	@Override
	protected void drawEntity(Graphics g) {
		int tileWidth = QuestPanel.TILE_WIDTH;
		g.setColor(Color.BLACK);
		g.fillOval((int)(UrfQuest.panel.dispCenterX - (UrfQuest.game.player.getPos()[0] - bounds.getX())*tileWidth),
				   (int)(UrfQuest.panel.dispCenterY - (UrfQuest.game.player.getPos()[1] - bounds.getY())*tileWidth),
				   (int)(bounds.getWidth()*tileWidth),
				   (int)(bounds.getHeight()*tileWidth));
	}
	
	public void drawDebug(Graphics g) {
		Player player = UrfQuest.game.getPlayer();
		g.setColor(Color.WHITE);
		g.drawString("velocity: " + velocity,
					 (int)(UrfQuest.panel.dispCenterX - (player.getPos()[0] - bounds.getX())*QuestPanel.TILE_WIDTH),
					 (int)(UrfQuest.panel.dispCenterY - (player.getPos()[1] - bounds.getY())*QuestPanel.TILE_WIDTH));
		g.drawString("direction: " + direction,
				 (int)(UrfQuest.panel.dispCenterX - (player.getPos()[0] - bounds.getX())*QuestPanel.TILE_WIDTH),
				 (int)(UrfQuest.panel.dispCenterY - (player.getPos()[1] - bounds.getY())*QuestPanel.TILE_WIDTH)+10);
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