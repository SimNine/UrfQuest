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
	
	public void use(Mob m) {
		if (m.tileTypeAtDistance(1.0) == 10) {
			int[] coords = m.tileCoordsAtDistance(1.0);
			UrfQuest.game.getCurrMap().setTileAt(coords[0], coords[1], 2);
			UrfQuest.game.getCurrMap().addItem(new Stone(coords[0], coords[1]));
		} else if (m.tileTypeAtDistance(1.0) == 11) {
			int[] coords = m.tileCoordsAtDistance(1.0);
			UrfQuest.game.getCurrMap().setTileAt(coords[0], coords[1], 8);
			UrfQuest.game.getCurrMap().addItem(new Stone(coords[0], coords[1]));
		} else if (m.tileTypeAtDistance(1.0) == 12) {
			int[] coords = m.tileCoordsAtDistance(1.0);
			UrfQuest.game.getCurrMap().setTileAt(coords[0], coords[1], 9);
			UrfQuest.game.getCurrMap().addItem(new Stone(coords[0], coords[1]));
		} else if (m.tileTypeAtDistance(1.0) == 14) {
			int[] coords = m.tileCoordsAtDistance(1.0);
			UrfQuest.game.getCurrMap().setTileAt(coords[0], coords[1], 0);
			UrfQuest.game.getCurrMap().addItem(new Stone(coords[0], coords[1]));
		}
	}
	
	// getters and setters
	public int getCooldown() {
		return 100;
	}

	public boolean isConsumable() {
		return false;
	}

	public boolean isStackable() {
		return false;
	}

	public void update() {
		// nothing here
	}

}