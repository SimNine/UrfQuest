package entities.items;

import java.awt.geom.Rectangle2D;
import java.io.IOException;

import javax.imageio.ImageIO;

import framework.QuestPanel;
import framework.UrfQuest;

public class Key extends Item {
	
	public static void initKey() {
		try {
			itemPic = ImageIO.read(UrfQuest.quest.getClass().getResourceAsStream(assetPath + "key_scaled_30px.png"));
		} catch (IOException e) {
			System.out.println("Image could not be read at: " + assetPath + "key_scaled_30px.png");
			e.printStackTrace();
		}
	}

	public Key(double x, double y) {
		super(x, y);
		type = "key";
		
		bounds = new Rectangle2D.Double(x, y,
										((double)itemPic.getWidth())/QuestPanel.TILE_WIDTH,
										((double)itemPic.getHeight())/QuestPanel.TILE_WIDTH);
	}

	@Override
	public void update() {
		// nothing here
	}

}