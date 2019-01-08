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
		type = "chicken";
		bounds = new Rectangle2D.Double(x, y, 1, 1);
		animStage = (int)(Math.random()*200.0);
		if (pic == null) {
			initChicken();
		}
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
					(int)(UrfQuest.panel.dispCenterX - (UrfQuest.game.player.getPosition()[0] - bounds.getX())*tileWidth), 
					(int)(UrfQuest.panel.dispCenterY - (UrfQuest.game.player.getPosition()[1] - bounds.getY())*tileWidth), 
					null);
	}

	public void update() {
		final int INTERVAL_SIZE = 50;
		
		switch (animStage/INTERVAL_SIZE) {
		case 0:
			bounds.setRect(bounds.getX()-0.1, bounds.getY(), bounds.getWidth(), bounds.getHeight());
			break;
		case 1:
			bounds.setRect(bounds.getX(), bounds.getY()-0.1, bounds.getWidth(), bounds.getHeight());
			break;
		case 2:
			bounds.setRect(bounds.getX()+0.1, bounds.getY(), bounds.getWidth(), bounds.getHeight());
			break;
		case 3:
			bounds.setRect(bounds.getX(), bounds.getY()+0.1, bounds.getWidth(), bounds.getHeight());
			break;
		case 4:
			animStage = -1;
			break;
		}
		animStage++;
	}
}