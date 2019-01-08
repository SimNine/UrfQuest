package entities.items;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import framework.UrfQuest;

public class Key extends Item {
	public static BufferedImage keyPic;

	public Key(double x, double y) {
		super(x, y);
		isStackable = true;
		if (keyPic == null) {
			try {
				keyPic = ImageIO.read(UrfQuest.quest.getClass().getResourceAsStream(assetPath + "key_scaled_30px.png"));
			} catch (IOException e) {
				System.out.println("Image could not be read at: " + assetPath + "key_scaled_30px.png");
				e.printStackTrace();
			}
		}
		itemPic = keyPic;
	}

	@Override
	public void update() {
		// nothing here
	}

}