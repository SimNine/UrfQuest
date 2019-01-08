package entities.items;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import entities.mobs.Mob;
import framework.UrfQuest;

public class Pickaxe extends Item {
	public static BufferedImage pickaxePic;

	public Pickaxe(double x, double y) {
		super(x, y);
		if (pickaxePic == null) {
			try {
				pickaxePic = ImageIO.read(UrfQuest.quest.getClass().getResourceAsStream(assetPath + "pickaxe_scaled_30px.png"));
			} catch (IOException e) {
				System.out.println("Image could not be read at: " + assetPath + "pickaxe_scaled_30px.png");
				e.printStackTrace();
			}
		}
		itemPic = pickaxePic;
	}
	
	// manipulation methods
	public boolean use(Mob m) {
		if (m.tileTypeAtDistance(1.0) == 10) {
			int[] coords = m.tileCoordsAtDistance(1.0);
			UrfQuest.game.getCurrMap().setTileAt(coords[0], coords[1], 2);
			UrfQuest.game.getCurrMap().addItem(new Stone(coords[0], coords[1]));
			return true;
		} else if (m.tileTypeAtDistance(1.0) == 11) {
			int[] coords = m.tileCoordsAtDistance(1.0);
			UrfQuest.game.getCurrMap().setTileAt(coords[0], coords[1], 8);
			UrfQuest.game.getCurrMap().addItem(new Stone(coords[0], coords[1]));
			return true;
		} else if (m.tileTypeAtDistance(1.0) == 12) {
			int[] coords = m.tileCoordsAtDistance(1.0);
			UrfQuest.game.getCurrMap().setTileAt(coords[0], coords[1], 9);
			UrfQuest.game.getCurrMap().addItem(new Stone(coords[0], coords[1]));
			return true;
		} else if (m.tileTypeAtDistance(1.0) == 14) {
			int[] coords = m.tileCoordsAtDistance(1.0);
			UrfQuest.game.getCurrMap().setTileAt(coords[0], coords[1], 0);
			double rand = Math.random();
			if (rand > .95) {
				UrfQuest.game.getCurrMap().addItem(new LawRune(coords[0], coords[1]));
			} else if (rand > .90) {
				UrfQuest.game.getCurrMap().addItem(new CosmicRune(coords[0], coords[1]));
			} else if (rand > .85) {
				UrfQuest.game.getCurrMap().addItem(new AstralRune(coords[0], coords[1]));
			} else if (rand > .82) {
				UrfQuest.game.getCurrMap().addItem(new Shotgun(coords[0], coords[1]));
			} else if (rand > .79) {
				UrfQuest.game.getCurrMap().addItem(new SMG(coords[0], coords[1]));
			} else if (rand > .75) {
				UrfQuest.game.getCurrMap().addItem(new GrenadeItem(coords[0], coords[1]));
			} else {
				UrfQuest.game.getCurrMap().addItem(new Stone(coords[0], coords[1]));
			}
			return true;
		} else {
			return false;
		}
	}

	public Pickaxe clone() {
		return new Pickaxe(this.getPos()[0], this.getPos()[1]);
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