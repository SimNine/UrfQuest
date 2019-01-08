package entities.items;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import entities.mobs.Mob;
import entities.mobs.Player;
import framework.UrfQuest;

public class Cheese extends Item {
	public static BufferedImage cheesePic;

	public Cheese(double x, double y) {
		super(x, y);
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
	
	// manipulation methods
	public boolean use(Mob m) {
		if (m instanceof Player) {
			if (((Player) m).getFullness() > 0.95) {
				((Player) m).setFullness(((Player) m).getMaxFullness());
				return true;
			}
		}
		return false;
	}
	
	public Cheese clone() {
		return new Cheese(this.getPos()[0], this.getPos()[1]);
	}
	
	// getters and setters
	public boolean isConsumable() {
		return true;
	}

	public int getMaxCooldown() {
		return 50;
	}

	public int maxStackSize() {
		return 100;
	}

	public int getMaxDurability() {
		return -1;
	}
}