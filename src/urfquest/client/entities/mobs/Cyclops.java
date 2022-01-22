package urfquest.client.entities.mobs;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import urfquest.Main;
import urfquest.client.Client;
import urfquest.client.map.Map;

public class Cyclops extends Mob {

	private static BufferedImage pic;
	
	//private Item shotgun;

	public Cyclops(Client c, int id, Map m, double x, double y) {
		super(c, id, m, x, y);
		
		// figure out what scaling this should be
		bounds = new Rectangle2D.Double(x, y, 10, 10);
		//								pic.getWidth()/(double)QuestPanel.TILE_WIDTH,
		//								pic.getHeight()/(double)QuestPanel.TILE_WIDTH);
		
		velocity = 0.01;
		defaultVelocity = 0.01;
		
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
	
	public static void initCyclops() {
		try {
			pic = ImageIO.read(Main.mainLogger.getClass().getResourceAsStream(assetPath + "cyclops_unscaled.png"));
		} catch (IOException e) {
			e.printStackTrace();
			Main.mainLogger.error("Image could not be read at: " + assetPath + "cyclops_unscaled.png");
		}
	}

	protected void drawEntity(Graphics g) {
		g.drawImage(pic, 
					(int) Main.panel.gameToWindowX(bounds.getX()), 
					(int) Main.panel.gameToWindowY(bounds.getY()), 
					null);
		drawHealthBar(g);
	}
}