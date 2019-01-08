package entities.mobs;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import framework.QuestPanel;
import framework.UrfQuest;

public class Chicken extends Mob {
	private static BufferedImage pic;
	
	public Chicken(double x, double y) {
		super(x, y);
		bounds = new Rectangle2D.Double(x, y, 1, 1);
		animStage = (int)(Math.random()*200.0);
		if (pic == null) {
			initChicken();
		}
		velocity = 0.1;
		health = 10.0;
		maxHealth = 10.0;
	}
	
	public static void initChicken() {
		try {
			pic = ImageIO.read(UrfQuest.quest.getClass().getResourceAsStream(assetPath + "chicken_scaled_30px.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Image could not be read at: " + assetPath + "chicken_scaled_30px.png");
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
		final int INTERVAL_SIZE = 50;
		
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
		attemptMove(direction);
	}
}