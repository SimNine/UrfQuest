package entities.items;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import entities.Entity;
import framework.QuestPanel;
import framework.UrfQuest;

public abstract class Item extends Entity {
	public static BufferedImage keyPic, gunPic, gemPic;
	protected BufferedImage itemPic;
	protected boolean isStackable;
	protected static final String assetPath = "/assets/items/";
	
	public static void initItems() {
		try {
			keyPic = ImageIO.read(UrfQuest.quest.getClass().getResourceAsStream(assetPath + "key_scaled_30px.png"));
		} catch (IOException e) {
			System.out.println("Image could not be read at: " + assetPath + "key_scaled_30px.png");
			e.printStackTrace();
		}
		try {
			gunPic = ImageIO.read(UrfQuest.quest.getClass().getResourceAsStream(assetPath + "gun_scaled_30px.png"));
		} catch (IOException e) {
			System.out.println("Image could not be read at: " + assetPath + "gun_scaled.png");
			e.printStackTrace();
		}
		try {
			gemPic = ImageIO.read(UrfQuest.quest.getClass().getResourceAsStream(assetPath + "pink_gem_scaled_30px.png"));
		} catch (IOException e) {
			System.out.println("Image could not be read at: " + assetPath + "gem_scaled_30px.png");
			e.printStackTrace();
		}
	}
	
	public Item clone() {
		double[] pos = this.getPosition();
		Item ret = null;
		if (this instanceof Key) {
			ret = new Key(pos[0], pos[1]);
		} else if (this instanceof Gun) {
			ret = new Gun(pos[0], pos[1]);
		} else if (this instanceof Gem) {
			ret = new Gem(pos[0], pos[1]);
		}
		ret.type = this.getType();
		ret.isStackable = this.isStackable;
		ret.itemPic = this.itemPic;
		ret.bounds = (Double) this.bounds.clone();
		return ret;
	}
	
	protected Item(double x, double y, BufferedImage pic) {
		super(x, y);
		itemPic = pic;
		if (this.type == null) type = "item";
		bounds = new Rectangle2D.Double(x, y,
				((double)itemPic.getWidth())/QuestPanel.TILE_WIDTH,
				((double)itemPic.getHeight())/QuestPanel.TILE_WIDTH);
	}
	
	protected void drawEntity(Graphics g) {
		int tileWidth = QuestPanel.TILE_WIDTH;
		g.drawImage(itemPic, 
					(int)(UrfQuest.panel.dispCenterX - (UrfQuest.game.player.getPosition()[0] - bounds.getX())*tileWidth), 
					(int)(UrfQuest.panel.dispCenterY - (UrfQuest.game.player.getPosition()[1] - bounds.getY())*tileWidth), 
					null);
	}
	
	public BufferedImage getPic() {
		return itemPic;
	}
	
	public boolean isStackable() {
		return isStackable;
	};
}