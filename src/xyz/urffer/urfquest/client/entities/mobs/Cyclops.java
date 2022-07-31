package xyz.urffer.urfquest.client.entities.mobs;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import xyz.urffer.urfquest.Main;
import xyz.urffer.urfquest.client.Client;
import xyz.urffer.urfquest.client.map.Map;

public class Cyclops extends Mob {

	private static BufferedImage pic;
	
	//private Item shotgun;
	
	static {
		try {
			pic = ImageIO.read(Main.self.getClass().getResourceAsStream(assetPath + "cyclops_unscaled.png"));
		} catch (IOException e) {
			e.printStackTrace();
			Main.mainLogger.error("Image could not be read at: " + assetPath + "cyclops_unscaled.png");
		}
	}

	public Cyclops(Client c, int id, Map m, double[] pos) {
		super(c, id, m, pos);
		
		// figure out what scaling this should be
		bounds = new Rectangle2D.Double(pos[0], pos[1], 10, 10);
		//								pic.getWidth()/(double)QuestPanel.TILE_WIDTH,
		//								pic.getHeight()/(double)QuestPanel.TILE_WIDTH);
		
		health = 50.0;
		maxHealth = 50.0;
		mana = 0.0;
		maxMana = 0.0;
		fullness = 0.0;
		maxFullness = 0.0;
		
		//shotgun = new Item(0, 0, 15, m);
	}

	public void update() {
//		if (healthbarVisibility > 0) {
//			healthbarVisibility--;
//		}
//	
//		attemptMove(direction, velocity);
	}

	protected void drawEntity(Graphics g) {
		g.drawImage(pic, 
					(int) client.getPanel().gameToWindowX(bounds.getX()), 
					(int) client.getPanel().gameToWindowY(bounds.getY()), 
					null);
		drawHealthBar(g);
	}
}