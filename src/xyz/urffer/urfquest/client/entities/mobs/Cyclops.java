package xyz.urffer.urfquest.client.entities.mobs;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import xyz.urffer.urfquest.Main;
import xyz.urffer.urfquest.client.Client;
import xyz.urffer.urfquest.shared.Constants;

public class Cyclops extends Mob {

	private static BufferedImage pic;
		
	static {
		try {
			pic = ImageIO.read(Main.self.getClass().getResourceAsStream(assetPath + "cyclops_unscaled.png"));
		} catch (IOException e) {
			e.printStackTrace();
			Main.mainLogger.error("Image could not be read at: " + assetPath + "cyclops_unscaled.png");
		}
	}

	public Cyclops(Client c, int id) {
		super(c, id);
		
		// decide how big to make this mob
		bounds = new Rectangle2D.Double(0, 0, 10, 10);
		
		health = Constants.DEFAULT_HEALTH_MAX_CYCLOPS;
		maxHealth = Constants.DEFAULT_HEALTH_MAX_CYCLOPS;
		mana = Constants.DEFAULT_MANA_MAX_CYCLOPS;
		maxMana = Constants.DEFAULT_MANA_MAX_CYCLOPS;
		fullness = Constants.DEFAULT_FULLNESS_MAX_CYCLOPS;
		maxFullness = Constants.DEFAULT_FULLNESS_MAX_CYCLOPS;
	}

	public void update() {
		// do nothing
	}

	protected void drawEntity(Graphics g) {
		g.drawImage(pic, 
					(int) client.getPanel().gameToWindowX(this.getPos().x), 
					(int) client.getPanel().gameToWindowY(this.getPos().y), 
					null);
		drawHealthBar(g);
	}
}
