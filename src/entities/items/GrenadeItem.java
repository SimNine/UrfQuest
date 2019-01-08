package entities.items;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import entities.mobs.Mob;
import entities.projectiles.GrenadeProjectile;
import framework.UrfQuest;
import game.QuestMap;

public class GrenadeItem extends Item {
	public static BufferedImage grenadeItemPic;

	public GrenadeItem(double x, double y, QuestMap m) {
		super(x, y, m);
		if (grenadeItemPic == null) {
			try {
				grenadeItemPic = ImageIO.read(UrfQuest.quest.getClass().getResourceAsStream(assetPath + "grenade_scaled_30px.png"));
			} catch (IOException e) {
				System.out.println("Image could not be read at: " + assetPath + "grenade_scaled_30px.png");
				e.printStackTrace();
			}
		}
		itemPic = grenadeItemPic;
	}
	
	// manipulation methods
	public boolean use(Mob m) {
		if (cooldown > 0) {
			return false;
		} // else
		cooldown = getMaxCooldown();
		
		m.getMap().addProjectile(new GrenadeProjectile(m.getCenter()[0], m.getCenter()[1], m, m.getMap()));
		return true;
	}
	
	public GrenadeItem clone() {
		return new GrenadeItem(this.getPos()[0], this.getPos()[1], map);
	}
	
	// getters and setters
	public boolean isConsumable() {
		return true;
	}

	public int getMaxCooldown() {
		return 100;
	}

	public int maxStackSize() {
		return 10;
	}

	public int getMaxDurability() {
		return -1;
	}
}