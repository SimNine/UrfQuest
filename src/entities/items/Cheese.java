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
	
	public boolean isConsumable() {
		return true;
	}
	
	public void use(Mob m) {
		if (m instanceof Player) {
			((Player) m).setFullness(((Player) m).getMaxFullness());
		}
	}

	public void update() {
		// nothing here
	}

	public int getCooldown() {
		return 50;
	}

	public boolean isStackable() {
		return true;
	}

}