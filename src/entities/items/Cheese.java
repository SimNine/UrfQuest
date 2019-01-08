package entities.items;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import framework.UrfQuest;

public class Cheese extends Item {
	public static BufferedImage cheesePic;

	public Cheese(double x, double y) {
		super(x, y);
		type = "cheese";
		isStackable = true;
		if (cheesePic == null) {
			try {
				cheesePic = ImageIO.read(UrfQuest.quest.getClass().getResourceAsStream(assetPath + "cheese_scaled_30px.png"));
			} catch (IOException e) {
				System.out.println("Image could not be read at: " + assetPath + "cheese_scaled_30px.png");
				e.printStackTrace();
			}
		}
		itemPic = cheesePic;
	}

	@Override
	public void update() {
		// nothing here
	}

}