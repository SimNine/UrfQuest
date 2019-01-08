package entities.particles;

import java.awt.Color;
import java.awt.Graphics;

import entities.Entity;
import entities.mobs.Player;
import framework.QuestPanel;
import framework.UrfQuest;

public class Particle extends Entity {
	// default values
	protected double velocity = 0;
	protected int direction = 0;

	public Particle(double x, double y) {
		super(x, y);
		bounds.setRect(bounds.getX(), bounds.getY(), 0.2, 0.2);
		velocity = Math.random()*0.05 + 0.05;
		direction = (int)(Math.random()*360);
	}

	@Override
	protected void drawEntity(Graphics g) {
		int tileWidth = QuestPanel.TILE_WIDTH;
		g.setColor(Color.BLACK);
		g.fillRect((int)(UrfQuest.panel.dispCenterX - (UrfQuest.game.player.getPosition()[0] - bounds.getX())*tileWidth),
				   (int)(UrfQuest.panel.dispCenterY - (UrfQuest.game.player.getPosition()[1] - bounds.getY())*tileWidth),
				   (int)(bounds.getWidth()*tileWidth),
				   (int)(bounds.getHeight()*tileWidth));
	}
	
	public void drawDebug(Graphics g) {
		Player player = UrfQuest.game.getPlayer();
		g.setColor(Color.WHITE);
		g.drawString("velocity: " + velocity,
					 (int)(UrfQuest.panel.dispCenterX - (player.getPosition()[0] - bounds.getX())*QuestPanel.TILE_WIDTH),
					 (int)(UrfQuest.panel.dispCenterY - (player.getPosition()[1] - bounds.getY())*QuestPanel.TILE_WIDTH));
		g.drawString("direction: " + direction,
				 (int)(UrfQuest.panel.dispCenterX - (player.getPosition()[0] - bounds.getX())*QuestPanel.TILE_WIDTH),
				 (int)(UrfQuest.panel.dispCenterY - (player.getPosition()[1] - bounds.getY())*QuestPanel.TILE_WIDTH)+10);
	}

	@Override
	public void update() {
		this.move(velocity*Math.cos(direction), velocity*Math.sin(direction));
		animStage++;
	}
	
	public boolean isDead() {
		return (animStage == 1000);
	}

}