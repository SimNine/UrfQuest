package entities.items;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import entities.mobs.Mob;
import entities.projectiles.RocketExplosion;
import framework.UrfQuest;
import game.QuestMap;

public class Mic extends Item {
	public static BufferedImage micPic;

	public Mic(double x, double y, QuestMap m) {
		super(x, y, m);
		if (micPic == null) {
			try {
				micPic = ImageIO.read(UrfQuest.quest.getClass().getResourceAsStream(assetPath + "mic_scaled_30px.png"));
			} catch (IOException e) {
				System.out.println("Image could not be read at: " + assetPath + "mic_scaled_30px.png");
				e.printStackTrace();
			}
		}
		itemPic = micPic;
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
		
		for (int i = 0; i < 20; i++) {
			map.addProjectile(new RocketExplosion(bounds.x + (Math.random() - 0.5)*20, bounds.y + (Math.random() - 0.5)*20, this, map));
		}
		return true;
	}
	
	public Mic clone() {
		return new Mic(this.getPos()[0], this.getPos()[1], map);
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