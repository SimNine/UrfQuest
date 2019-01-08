package entities.items;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import entities.particles.Particle;
import framework.UrfQuest;

public class Pistol extends Item {
	public static BufferedImage pistolPic;
	private static final int COOLDOWN = 100;

	public Pistol(double x, double y) {
		super(x, y);
		isStackable = false;
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
	
	public void use() {
		double[] pos = UrfQuest.game.getPlayer().getCenter();
		int dir = UrfQuest.game.getPlayer().getDirection() + (int)((Math.random() - 0.5)*20);
		UrfQuest.game.getCurrMap().addParticle(new Particle(pos[0], pos[1], dir));
	}
	
	// getters and setters
	public int getCooldown() {
		return COOLDOWN;
	}

}