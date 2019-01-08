package entities.mobs;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import entities.items.Bone;
import entities.mobs.ai.routines.IdleRoutine;
import framework.QuestPanel;
import framework.UrfQuest;

public class Cyclops extends Mob {
	private static BufferedImage pic;

	public Cyclops(double x, double y) {
		super(x, y);
		animStage = (int)(Math.random()*200.0);
		if (pic == null) {
			initCyclops();
		}
		bounds = new Rectangle2D.Double(x, y, 
										pic.getWidth()/(double)QuestPanel.TILE_WIDTH,
										pic.getHeight()/(double)QuestPanel.TILE_WIDTH);
		velocity = 0.01;
		health = 50.0;
		maxHealth = 50.0;
		
		routine = new IdleRoutine(this);
	}
	
	public static void initCyclops() {
		try {
			pic = ImageIO.read(UrfQuest.quest.getClass().getResourceAsStream(assetPath + "cyclops_unscaled.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Image could not be read at: " + assetPath + "cyclops_unscaled.png");
		}
	}

	protected void drawEntity(Graphics g) {
		int tileWidth = QuestPanel.TILE_WIDTH;
		g.drawImage(pic, 
					(int)(UrfQuest.panel.dispCenterX - (UrfQuest.game.player.getPos()[0] - bounds.getX())*tileWidth), 
					(int)(UrfQuest.panel.dispCenterY - (UrfQuest.game.player.getPos()[1] - bounds.getY())*tileWidth), 
					null);
		drawHealthBar(g);
	}

	public void update() {
		final int INTERVAL_SIZE = 500;
		
		if (healthbarVisibility > 0) {
			healthbarVisibility--;
		}
	
		switch (animStage/INTERVAL_SIZE) {
		case 0:
			direction = 180;
			break;
		case 1:
			direction = 270;
			break;
		case 2:
			direction = 0;
			break;
		case 3:
			direction = 90;
			break;
		case 4:
			animStage = -1;
			break;
		}
		animStage++;
		attemptMove(direction, velocity);
	}
	
	public void onDeath() {
		UrfQuest.game.getCurrMap().addItem(new Bone(bounds.getCenterX(), bounds.getCenterY()));
	}

}