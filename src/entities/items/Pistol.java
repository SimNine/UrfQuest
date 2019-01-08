package entities.items;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import entities.mobs.Mob;
import entities.projectiles.Bullet;
import framework.UrfQuest;

public class Pistol extends Item {
	public static BufferedImage pistolPic;

	public Pistol(double x, double y) {
		super(x, y);
		if (pistolPic == null) {
			try {
				pistolPic = ImageIO.read(UrfQuest.quest.getClass().getResourceAsStream(assetPath + "gun_scaled_30px.png"));
			} catch (IOException e) {
				System.out.println("Image could not be read at: " + assetPath + "gun_scaled_30px.png");
				e.printStackTrace();
			}
		}
		itemPic = pistolPic;
	}
	
	// manipulation methods
	public boolean use(Mob m) {
		if (cooldown > 0) {
			return false;
		} // else
		cooldown = getMaxCooldown();
		
		double[] pos = m.getCenter();
		int dir = m.getDirection() + (int)((Math.random() - 0.5)*10);
		UrfQuest.game.getCurrMap().addParticle(new Bullet(pos[0], pos[1], dir, m));
		return true;
	}
	
	public Pistol clone() {
		return new Pistol(this.getPos()[0], this.getPos()[1]);
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
		return -1;
	}
}