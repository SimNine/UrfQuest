package entities.items;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import entities.mobs.Mob;
import framework.UrfQuest;

public class Stone extends Item {
	public static BufferedImage stonePic;

	public Stone(double x, double y) {
		super(x, y);
		if (stonePic == null) {
			try {
				stonePic = ImageIO.read(UrfQuest.quest.getClass().getResourceAsStream(assetPath + "stoneitem_scaled_30px.png"));
			} catch (IOException e) {
				System.out.println("Image could not be read at: " + assetPath + "stoneitem_scaled_30px.png");
				e.printStackTrace();
			}
		}
		itemPic = stonePic;
	}

	// manipulation methods
	public boolean use(Mob m) {
		return false;
	}

	public Stone clone() {
		return new Stone(this.getPos()[0], this.getPos()[1]);
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