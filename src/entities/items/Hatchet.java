package entities.items;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import entities.mobs.Mob;
import framework.UrfQuest;

public class Hatchet extends Item {
	public static BufferedImage hatchetPic;

	public Hatchet(double x, double y) {
		super(x, y);
		if (hatchetPic == null) {
			try {
				hatchetPic = ImageIO.read(UrfQuest.quest.getClass().getResourceAsStream(assetPath + "hatchet_scaled_30px.png"));
			} catch (IOException e) {
				System.out.println("Image could not be read at: " + assetPath + "hatchet_scaled_30px.png");
				e.printStackTrace();
			}
		}
		itemPic = hatchetPic;
	}
	
	// manipulation methods
	public boolean use(Mob m) {
		if (m.tileTypeAtDistance(1.0) == 7) {
			int[] coords = m.tileCoordsAtDistance(1.0);
			UrfQuest.game.getCurrMap().setTileAt(coords[0], coords[1], 2);
			UrfQuest.game.getCurrMap().addItem(new Log(coords[0], coords[1]));
			return true;
		} else {
			return false;
		}
	}
	
	public Hatchet clone() {
		return new Hatchet(this.getPos()[0], this.getPos()[1]);
	}
	
	// getters and setters
	public boolean isConsumable() {
		return false;
	}

	public int getMaxCooldown() {
		return 100;
	}

	public int maxStackSize() {
		return 1;
	}

	public int getMaxDurability() {
		return 100;
	}
}