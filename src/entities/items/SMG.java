package entities.items;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import entities.mobs.Mob;
import entities.particles.Particle;
import framework.UrfQuest;

public class SMG extends Item {
	public static BufferedImage smgPic;

	public SMG(double x, double y) {
		super(x, y);
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

	public void use(Mob m) {
		double[] pos = m.getPos();
		int dir = m.getDirection() + (int)((Math.random() - 0.5)*20);
		UrfQuest.game.getCurrMap().addParticle(new Particle(pos[0], pos[1], dir));
	}
	
	// getters and setters
	public int getCooldown() {
		return 10;
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