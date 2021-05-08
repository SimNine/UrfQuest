package client.entities.mobs;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import client.entities.Entity;

public class Cyclops extends Entity {

	private static BufferedImage pic;
	
	public Cyclops() {
		animStage = (int)(Math.random()*200.0);
		if (pic == null) {
			initCyclops();
		}
	}
	
	public static void initCyclops() {
		try {
			pic = ImageIO.read(UrfQuest.quest.getClass().getResourceAsStream(assetPath + "cyclops_unscaled.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Image could not be read at: " + assetPath + "cyclops_unscaled.png");
		}
	}

	protected void drawEntity(Graphics g) {
		g.drawImage(pic, 
					(int) UrfQuest.panel.gameToWindowX(bounds.getX()), 
					(int) UrfQuest.panel.gameToWindowY(bounds.getY()), 
					null);
		drawHealthBar(g);
	}

	@Override
	protected void drawEntity(Graphics g) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void drawDebug(Graphics g) {
		// TODO Auto-generated method stub

	}

}
