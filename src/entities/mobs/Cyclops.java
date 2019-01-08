package entities.mobs;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import framework.QuestPanel;
import framework.UrfQuest;

public class Cyclops extends Mob {
	private static BufferedImage pic;

	public Cyclops(double x, double y) {
		super(x, y);
		type = "cyclops";
		animStage = (int)(Math.random()*200.0);
		if (pic == null) {
			initCyclops();
		}
		bounds = new Rectangle2D.Double(x, y, 
										pic.getWidth()/(double)QuestPanel.TILE_WIDTH,
										pic.getHeight()/(double)QuestPanel.TILE_WIDTH);
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
					(int)(UrfQuest.panel.dispCenterX - (UrfQuest.game.player.getPosition()[0] - bounds.getX())*tileWidth), 
					(int)(UrfQuest.panel.dispCenterY - (UrfQuest.game.player.getPosition()[1] - bounds.getY())*tileWidth), 
					null);
	}

	public void update() {
		final int INTERVAL_SIZE = 500;
	
		switch (animStage/INTERVAL_SIZE) {
		case 0:
			bounds.setRect(bounds.getX()-0.01, bounds.getY(), bounds.getWidth(), bounds.getHeight());
			break;
		case 1:
			bounds.setRect(bounds.getX(), bounds.getY()-0.01, bounds.getWidth(), bounds.getHeight());
			break;
		case 2:
			bounds.setRect(bounds.getX()+0.01, bounds.getY(), bounds.getWidth(), bounds.getHeight());
			break;
		case 3:
			bounds.setRect(bounds.getX(), bounds.getY()+0.01, bounds.getWidth(), bounds.getHeight());
			break;
		case 4:
			animStage = -1;
			break;
		}
		animStage++;
	}

}