package entities.items;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import entities.mobs.Mob;
import framework.UrfQuest;

public class Log extends Item {
	public static BufferedImage logPic;

	public Log(double x, double y) {
		super(x, y);
		if (logPic == null) {
			try {
				logPic = ImageIO.read(UrfQuest.quest.getClass().getResourceAsStream(assetPath + "log_scaled_30px.png"));
			} catch (IOException e) {
				System.out.println("Image could not be read at: " + assetPath + "log_scaled_30px.png");
				e.printStackTrace();
			}
		}
		itemPic = logPic;
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