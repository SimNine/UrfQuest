package entities.items;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import entities.mobs.Mob;
import entities.projectiles.Bullet;
import framework.UrfQuest;

public class Shotgun extends Item {
	public static BufferedImage shotgunPic;

	public Shotgun(double x, double y) {
		super(x, y);
		if (shotgunPic == null) {
			try {
				shotgunPic = ImageIO.read(UrfQuest.quest.getClass().getResourceAsStream(assetPath + "shotgun_scaled_30px.png"));
			} catch (IOException e) {
				System.out.println("Image could not be read at: " + assetPath + "shotgun_scaled_30px.png");
				e.printStackTrace();
			}
		}
		itemPic = shotgunPic;
	}
	
	// manipulation methods
	public boolean use(Mob m) {
		if (cooldown > 0) {
			return false;
		} // else
		cooldown = getMaxCooldown();
		
		double[] pos = m.getCenter();
		int dir;
		int numShots = 15 + (int)(Math.random()*5);
		
		for (int i = 0; i < numShots; i++) {
			dir = m.getDirection() + (int)((Math.random() - 0.5)*20);
			UrfQuest.game.getCurrMap().addParticle(new Bullet(pos[0], pos[1], dir, m));
		}
		
		return true;
	}
	
	public Shotgun clone() {
		return new Shotgun(this.getPos()[0], this.getPos()[1]);
	}
	
	// getters and setters
	public boolean isConsumable() {
		return false;
	}

	public int getMaxCooldown() {
		return 400;
	}

	public int maxStackSize() {
		return 1;
	}

	public int getMaxDurability() {
		return -1;
	}
}