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

	// manipulation methods
	public boolean use(Mob m) {
		return false;
	}
	
	public Gem clone() {
		return new Gem(this.getPos()[0], this.getPos()[1]);
	}

	// getters and setters
	public boolean isConsumable() {
		return false;
	}

	public int getMaxCooldown() {
		return -1;
	}

	public int maxStackSize() {
		return 100;
	}

	public int getMaxDurability() {
		return -1;
	}
}