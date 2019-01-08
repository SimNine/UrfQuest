package entities.items;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import entities.mobs.Mob;
import entities.projectiles.Bullet;
import framework.UrfQuest;
import game.QuestMap;

public class AstralRune extends Item {
	public static BufferedImage astralRunePic;

	public AstralRune(double x, double y, QuestMap m) {
		super(x, y, m);
		if (astralRunePic == null) {
			try {
				astralRunePic = ImageIO.read(UrfQuest.quest.getClass().getResourceAsStream(assetPath + "astralRune_scaled_30px.png"));
			} catch (IOException e) {
				System.out.println("Image could not be read at: " + assetPath + "astralRune_scaled_30px.png");
				e.printStackTrace();
			}
		}
		itemPic = astralRunePic;
	}
	
	// manipulation methods
	public boolean use(Mob m) {
		if (m.getMana() < 50.0) {
			return false;
		} // else
		if (cooldown > 0) {
			return false;
		} // else
		cooldown = getMaxCooldown();
		
		m.incrementMana(-50.0);
		double[] pos = m.getCenter();
		for (int i = 0; i < 180; i++) {
			m.getMap().addProjectile(new Bullet(pos[0], pos[1], i*2, Bullet.getDefaultVelocity(), m, map));
		}
		return true;
	}
	
	public AstralRune clone() {
		return new AstralRune(this.getPos()[0], this.getPos()[1], map);
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