package entities.items;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import entities.mobs.Mob;
import framework.UrfQuest;

public class Gem extends Item {
	public static BufferedImage gemPic;

	public Gem(double x, double y) {
		super(x, y);
		if (gemPic == null) {
			try {
				gemPic = ImageIO.read(UrfQuest.quest.getClass().getResourceAsStream(assetPath + "pink_gem_scaled_30px.png"));
			} catch (IOException e) {
				System.out.println("Image could not be read at: " + assetPath + "pink_gem_scaled_30px.png");
				e.printStackTrace();
			}
		}
		itemPic = gemPic;
	}

	public void update() {
		// nothing here
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

}