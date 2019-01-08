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