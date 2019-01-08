package entities.items;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import entities.mobs.Mob;
import framework.UrfQuest;
import game.QuestMap;

public class LawRune extends Item {
	public static BufferedImage lawrunePic;

	public LawRune(double x, double y, QuestMap m) {
		super(x, y, m);
		if (lawrunePic == null) {
			try {
				lawrunePic = ImageIO.read(UrfQuest.quest.getClass().getResourceAsStream(assetPath + "lawrune_scaled_30px.png"));
			} catch (IOException e) {
				System.out.println("Image could not be read at: " + assetPath + "lawrune_scaled_30px.png");
				e.printStackTrace();
			}
		}
		itemPic = lawrunePic;
	}
	
	// manipulation methods
	public boolean use(Mob m) {
		if (m.getMana() < 30.0) {
			return false;
		}
		if (cooldown > 0) {
			return false;
		} // else
		cooldown = getMaxCooldown();
		
		m.incrementMana(-30.0);
		int[] home = m.getMap().getHomeCoords();
		m.setPos(home[0], home[1]);
		return true;
	}
	
	public LawRune clone() {
		return new LawRune(this.getPos()[0], this.getPos()[1], map);
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