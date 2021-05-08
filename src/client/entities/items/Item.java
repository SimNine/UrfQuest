package client.entities.items;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Item {

	public static final BufferedImage[] itemImages = new BufferedImage[21];
	
	public Item() {
		if (itemImages[type - 1] == null) {
			initItemPics();
		}
	}
	
	/*
	 * Pic initialization
	 */
	
	private static void initItemPics() {
		try {
			Class<?> c = UrfQuest.quest.getClass();
			itemImages[0] = ImageIO.read(c.getResourceAsStream(assetPath + "astralRune_scaled_30px.png"));
			itemImages[1] = ImageIO.read(c.getResourceAsStream(assetPath + "cosmicRune_scaled_30px.png"));
			itemImages[2] = ImageIO.read(c.getResourceAsStream(assetPath + "lawRune_scaled_30px.png"));
			itemImages[3] = ImageIO.read(c.getResourceAsStream(assetPath + "chickenLeg_scaled_30px.png"));
			itemImages[4] = ImageIO.read(c.getResourceAsStream(assetPath + "cheese_scaled_30px.png"));
			itemImages[5] = ImageIO.read(c.getResourceAsStream(assetPath + "bone_scaled_30px.png"));
			itemImages[6] = ImageIO.read(c.getResourceAsStream(assetPath + "pink_gem_scaled_30px.png"));
			itemImages[7] = ImageIO.read(c.getResourceAsStream(assetPath + "log_scaled_30px.png"));
			itemImages[8] = ImageIO.read(c.getResourceAsStream(assetPath + "stoneitem_scaled_30px.png"));
			itemImages[9] = ImageIO.read(c.getResourceAsStream(assetPath + "mic_scaled_30px.png"));
			itemImages[10] = ImageIO.read(c.getResourceAsStream(assetPath + "key_scaled_30px.png"));
			itemImages[11] = ImageIO.read(c.getResourceAsStream(assetPath + "grenade_scaled_30px.png"));
			itemImages[12] = ImageIO.read(c.getResourceAsStream(assetPath + "gun_scaled_30px.png"));
			itemImages[13] = ImageIO.read(c.getResourceAsStream(assetPath + "rocket_scaled_30px.png"));
			itemImages[14] = ImageIO.read(c.getResourceAsStream(assetPath + "shotgun_scaled_30px.png"));
			itemImages[15] = ImageIO.read(c.getResourceAsStream(assetPath + "smg_scaled_30px.png"));
			itemImages[16] = ImageIO.read(c.getResourceAsStream(assetPath + "pickaxe_scaled_30px.png"));
			itemImages[17] = ImageIO.read(c.getResourceAsStream(assetPath + "hatchet_scaled_30px.png"));
			itemImages[18] = ImageIO.read(c.getResourceAsStream(assetPath + "shovel_scaled_30px.png"));
			itemImages[19] = ImageIO.read(c.getResourceAsStream(assetPath + "ironore_scaled_30px.png"));
			itemImages[20] = ImageIO.read(c.getResourceAsStream(assetPath + "copperore_scaled_30px.png"));
		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	/*
	 * drawing methods
	 */
	
	protected void drawEntity(Graphics g) {
		g.drawImage(itemImages[itemType - 1], 
					UrfQuest.panel.gameToWindowX(bounds.getX()), 
					UrfQuest.panel.gameToWindowY(bounds.getY()), 
					null);
	}

	public void drawDebug(Graphics g) {
		g.setColor(Color.WHITE);
		g.drawString("bounds coords: " + bounds.getX() + ", " + bounds.getY(),
					 (int) UrfQuest.panel.gameToWindowX(bounds.getX()),
					 (int) UrfQuest.panel.gameToWindowY(bounds.getY()));
		g.drawString("bounds dimensions: " + bounds.getWidth() + ", " + bounds.getHeight(),
				 (int) UrfQuest.panel.gameToWindowX(bounds.getX()),
				 (int) UrfQuest.panel.gameToWindowY(bounds.getY()) + 10);
	};

	public BufferedImage getPic() {
		return itemImages[itemType - 1];
	}
}
