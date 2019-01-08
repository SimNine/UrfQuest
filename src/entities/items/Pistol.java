package entities.items;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import framework.UrfQuest;

public class Pistol extends Item {
	public static BufferedImage pistolPic;

	public Pistol(double x, double y) {
		super(x, y);
		type = "pistol";
		isStackable = false;
		if (pistolPic == null) {
			try {
				pistolPic = ImageIO.read(UrfQuest.quest.getClass().getResourceAsStream(assetPath + "gun_scaled_30px.png"));
			} catch (IOException e) {
				System.out.println("Image could not be read at: " + assetPath + "gun_scaled_30px.png");
				e.printStackTrace();
			}
		}
		itemPic = pistolPic;
	}

	@Override
	public void update() {
		// nothing here
	}

}