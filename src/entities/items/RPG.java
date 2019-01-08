package entities.items;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import entities.mobs.Mob;
import entities.projectiles.Rocket;
import framework.UrfQuest;
import game.QuestMap;

public class RPG extends Item {
	public static BufferedImage RPGPic;

	public RPG(double x, double y, QuestMap m) {
		super(x, y, m);
		if (RPGPic == null) {
			try {
				RPGPic = ImageIO.read(UrfQuest.quest.getClass().getResourceAsStream(assetPath + "rocket_scaled_30px.png"));
			} catch (IOException e) {
				System.out.println("Image could not be read at: " + assetPath + "rocket_scaled_30px.png");
				e.printStackTrace();
			}
		}
		itemPic = RPGPic;
	}
	
	// manipulation methods
	public boolean use(Mob m) {
		if (cooldown > 0) {
			return false;
		} // else
		cooldown = getMaxCooldown();
		
		double[] pos = m.getCenter();
		int dir = m.getDirection();
		m.getMap().addProjectile(new Rocket(pos[0], pos[1], dir, Rocket.getDefaultVelocity(), m, m.getMap()));
		
		return true;
	}
	
	public RPG clone() {
		return new RPG(this.getPos()[0], this.getPos()[1], map);
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