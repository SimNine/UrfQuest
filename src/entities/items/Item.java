package entities.items;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import entities.Entity;
import framework.QuestPanel;
import framework.UrfQuest;

public abstract class Item extends Entity {
	protected static BufferedImage itemPic;
	protected static final String assetPath = "/assets/items/";
	
	public static void initItems() {
		Key.initKey();
	}
	
	public Item(double x, double y) {
		super(x, y);
		if (this.type == null) type = "item";
	}
	
	protected void drawEntity(Graphics g) {
		int tileWidth = QuestPanel.TILE_WIDTH;
		g.drawImage(itemPic, 
					(int)(UrfQuest.panel.dispCenterX - (UrfQuest.game.player.getPosition()[0] - bounds.getX())*tileWidth), 
					(int)(UrfQuest.panel.dispCenterY - (UrfQuest.game.player.getPosition()[1] - bounds.getY())*tileWidth), 
					null);
	}
	
	public static BufferedImage getPic() {
		return itemPic;
	}
}