package entities.mobs;

import java.awt.Color;
import java.awt.Graphics;

import entities.Entity;
import framework.QuestPanel;
import framework.UrfQuest;

public abstract class Mob extends Entity {
	protected final static String assetPath = "/assets/entities/";

	protected Mob(double x, double y) {
		super(x, y);
	}

	protected abstract void drawEntity(Graphics g);

	public abstract void update();

	@Override
	public void drawDebug(Graphics g) {
		Player player = UrfQuest.game.getPlayer();
		g.setColor(Color.WHITE);
		g.drawString("bounds coords: " + bounds.getX() + ", " + bounds.getY(),
					(int)(UrfQuest.panel.dispCenterX - (player.getPosition()[0] - bounds.getX())*QuestPanel.TILE_WIDTH),
					(int)(UrfQuest.panel.dispCenterY - (player.getPosition()[1] - bounds.getY())*QuestPanel.TILE_WIDTH));
		g.drawString("bounds dimensions: " + bounds.getWidth() + ", " + bounds.getHeight(),
				 	(int)(UrfQuest.panel.dispCenterX - (player.getPosition()[0] - bounds.getX())*QuestPanel.TILE_WIDTH),
				 	(int)(UrfQuest.panel.dispCenterY - (player.getPosition()[1] - bounds.getY())*QuestPanel.TILE_WIDTH)+10);
	};

}
