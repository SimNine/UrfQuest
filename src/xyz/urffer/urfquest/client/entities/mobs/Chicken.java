package xyz.urffer.urfquest.client.entities.mobs;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import xyz.urffer.urfquest.Main;
import xyz.urffer.urfquest.client.Client;

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
		
		health = 10.0;
		maxHealth = 10.0;
		mana = 0.0;
		maxMana = 0.0;
		fullness = 0.0;
		maxFullness = 0.0;
	}

	public void update() {
		if (healthbarVisibility > 0) {
			healthbarVisibility--;
		}
		
		// execute the current action
		// routine.update();
		// attemptMove(routine.suggestedDirection(), routine.suggestedVelocity());
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