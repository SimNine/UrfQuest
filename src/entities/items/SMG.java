package entities.items;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import entities.mobs.Mob;
import entities.projectiles.Bullet;
import framework.UrfQuest;
import game.QuestMap;

public class SMG extends Item {
	public static BufferedImage smgPic;

	public SMG(double x, double y, QuestMap m) {
		super(x, y, m);
		if (smgPic == null) {
			try {
				smgPic = ImageIO.read(UrfQuest.quest.getClass().getResourceAsStream(assetPath + "smg_scaled_30px.png"));
			} catch (IOException e) {
				System.out.println("Image could not be read at: " + assetPath + "smg_scaled_30px.png");
				e.printStackTrace();
			}
		}
		itemPic = smgPic;
	}

	// manipulation methods
	public boolean use(Mob m) {
		if (cooldown > 0) {
			return false;
		} // else
		cooldown = getMaxCooldown();
		
		double[] pos = m.getCenter();
		int dir = m.getDirection() + (int)((Math.random() - 0.5)*10);
		m.getMap().addProjectile(new Bullet(pos[0], pos[1], dir, Bullet.getDefaultVelocity(), m, map));
		return true;
	}
	
	public SMG clone() {
		return new SMG(this.getPos()[0], this.getPos()[1], map);
	}
	
	// getters and setters
	public boolean isConsumable() {
		return false;
	}

	public int getMaxCooldown() {
		return 10;
	}

	public int maxStackSize() {
		return 1;
	}

	public int getMaxDurability() {
		return -1;
	}
}