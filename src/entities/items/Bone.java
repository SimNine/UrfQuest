package entities.items;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import entities.mobs.Mob;
import framework.UrfQuest;

public class Bone extends Item {
	public static BufferedImage bonePic;

	public Bone(double x, double y) {
		super(x, y);
		if (bonePic == null) {
			try {
				bonePic = ImageIO.read(UrfQuest.quest.getClass().getResourceAsStream(assetPath + "bone_scaled_30px.png"));
			} catch (IOException e) {
				System.out.println("Image could not be read at: " + assetPath + "bone_scaled_30px.png");
				e.printStackTrace();
			}
		}
		itemPic = bonePic;
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