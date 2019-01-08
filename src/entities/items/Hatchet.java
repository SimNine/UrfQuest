package entities.items;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import entities.mobs.Mob;
import framework.UrfQuest;
import game.QuestMap;

public class Hatchet extends Item {
	public static BufferedImage hatchetPic;

	public Hatchet(double x, double y, QuestMap m) {
		super(x, y, m);
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
		if (cooldown > 0) {
			return false;
		} // else
		if (m.tileTypeAtDistance(1.0) == 7) {
			int[] coords = m.tileCoordsAtDistance(1.0);
			m.getMap().setTileAt(coords[0], coords[1], 2);
			m.getMap().addItem(new Log(coords[0], coords[1], map));
			
			cooldown = getMaxCooldown();
			return true;
		} else {
			return false;
		}
	}
	
	public Hatchet clone() {
		return new Hatchet(this.getPos()[0], this.getPos()[1], map);
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