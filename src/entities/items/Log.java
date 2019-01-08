package entities.items;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import entities.mobs.Mob;
import framework.UrfQuest;
import game.QuestMap;

public class Log extends Item {
	public static BufferedImage logPic;

	public Log(double x, double y, QuestMap m) {
		super(x, y, m);
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

	// manipulation methods
	public boolean use(Mob m) {
		return false;
	}

	public Log clone() {
		return new Log(this.getPos()[0], this.getPos()[1], map);
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