package entities.items;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import entities.mobs.Chicken;
import entities.mobs.Mob;
import framework.UrfQuest;
import game.QuestMap;

public class CosmicRune extends Item {
	public static BufferedImage cosmicRunePic;

	public CosmicRune(double x, double y, QuestMap m) {
		super(x, y, m);
		if (cosmicRunePic == null) {
			try {
				cosmicRunePic = ImageIO.read(UrfQuest.quest.getClass().getResourceAsStream(assetPath + "cosmicrune_scaled_30px.png"));
			} catch (IOException e) {
				System.out.println("Image could not be read at: " + assetPath + "cosmicrune_scaled_30px.png");
				e.printStackTrace();
			}
		}
		itemPic = cosmicRunePic;
	}
	
	// manipulation methods
	public boolean use(Mob m) {
		if (m.getMana() < 5.0) {
			return false;
		} // else
		if (cooldown > 0) {
			return false;
		} // else
		cooldown = getMaxCooldown();
		
		m.incrementMana(-5.0);
		double[] pos = m.getPos();
		m.getMap().addMob(new Chicken(pos[0], pos[1], map));
		return true;
	}
	
	public CosmicRune clone() {
		return new CosmicRune(this.getPos()[0], this.getPos()[1], map);
	}
	
	// getters and setters
	public boolean isConsumable() {
		return true;
	}

	public int getMaxCooldown() {
		return 1000;
	}

	public int maxStackSize() {
		return 10;
	}

	public int getMaxDurability() {
		return -1;
	}

}