package entities.items;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import entities.mobs.Mob;
import framework.UrfQuest;

public class Key extends Item {
	public static BufferedImage keyPic;

	public Key(double x, double y) {
		super(x, y);
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

	public void use(Mob m) {
		// nothing here
	}

	public boolean isConsumable() {
		return false;
	}

	public int getCooldown() {
		return -1;
	}

	public boolean isStackable() {
		return true;
	}

	public void update() {
		// nothing here
	}

}