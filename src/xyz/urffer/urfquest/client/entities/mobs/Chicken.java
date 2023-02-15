package xyz.urffer.urfquest.client.entities.mobs;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import xyz.urffer.urfquest.Main;
import xyz.urffer.urfquest.client.Client;
import xyz.urffer.urfquest.shared.Constants;

public class Chicken extends Mob {
	private static BufferedImage pic;
	
	static {
		try {
			pic = ImageIO.read(Main.self.getClass().getResourceAsStream(assetPath + "chicken_scaled_30px.png"));
		} catch (IOException e) {
			e.printStackTrace();
			Main.mainLogger.error("Image could not be read at: " + assetPath + "chicken_scaled_30px.png");
		}
	}
	
	public Chicken(Client c, int id) {
		super(c, id);
		bounds = new Rectangle2D.Double(0, 0, 1, 1);
		
		health = Constants.DEFAULT_HEALTH_MAX_CHICKEN;
		maxHealth = Constants.DEFAULT_HEALTH_MAX_CHICKEN;
		mana = Constants.DEFAULT_MANA_MAX_CHICKEN;
		maxMana = Constants.DEFAULT_MANA_MAX_CHICKEN;
		fullness = Constants.DEFAULT_FULLNESS_MAX_CHICKEN;
		maxFullness = Constants.DEFAULT_FULLNESS_MAX_CHICKEN;
	}

	public void update() {
		if (healthbarVisibility > 0) {
			healthbarVisibility--;
		}
	}

	@Override
	protected void drawEntity(Graphics g) {
		g.drawImage(pic, 
				client.getPanel().gameToWindowX(bounds.getX()), 
				client.getPanel().gameToWindowY(bounds.getY()), 
				null);
		drawHealthBar(g);
	}
}
