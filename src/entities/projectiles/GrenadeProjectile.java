package entities.projectiles;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import entities.Entity;
import framework.QuestPanel;
import framework.UrfQuest;

public class GrenadeProjectile extends Projectile {
	public static BufferedImage grenadePic;
	public static String assetPath = "/assets/items/";

	public GrenadeProjectile(double x, double y, Entity source) {
		super(x, y, source);
		
		if (grenadePic == null) {
			try {
				grenadePic = ImageIO.read(UrfQuest.quest.getClass().getResourceAsStream(assetPath + "grenade_scaled_30px.png"));
			} catch (IOException e) {
				System.out.println("Image could not be read at: " + assetPath + "grenade_scaled_30px.png");
				e.printStackTrace();
			}
		}
		
		int tilewidth = QuestPanel.TILE_WIDTH;
		bounds.setFrame(bounds.x, bounds.y, grenadePic.getWidth()/tilewidth, grenadePic.getHeight()/tilewidth);
	}

	protected void drawEntity(Graphics g) {
		g.drawImage(grenadePic, 
					UrfQuest.panel.gameToWindowX(bounds.getX()), 
					UrfQuest.panel.gameToWindowY(bounds.getY()), 
					null);
	}

	public void update() {
		animStage++;
		if (animStage > 1000) {
			for (int i = 0; i < 20; i++) {
				UrfQuest.game.getCurrMap().addParticle(new Bullet(bounds.getCenterX(), bounds.getCenterY(), (int)(Math.random()*360), source));
			}
		}
	}

	public boolean isDead() {
		return (animStage > 1000);
	}
}
