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
	
	public void use(Mob m) {
		if (m.tileTypeAtDistance(1.0) == 7) {
			int[] coords = m.tileCoordsAtDistance(1.0);
			UrfQuest.game.getCurrMap().setTileAt(coords[0], coords[1], 2);
			UrfQuest.game.getCurrMap().addItem(new Log(coords[0], coords[1]));
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