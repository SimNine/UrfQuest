package entities.items;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.awt.image.BufferedImage;

import entities.Entity;
import entities.mobs.Player;
import framework.QuestPanel;
import framework.UrfQuest;

public abstract class Item extends Entity {
	protected BufferedImage itemPic;
	protected boolean isStackable;
	protected static final String assetPath = "/assets/items/";
	
	public Item clone() {
		double[] pos = this.getPosition();
		Item ret = null;
		if (this instanceof Key) {
			ret = new Key(pos[0], pos[1]);
		} else if (this instanceof Pistol) {
			ret = new Pistol(pos[0], pos[1]);
		} else if (this instanceof SMG){
			ret = new SMG(pos[0], pos[1]);
		} else if (this instanceof Gem) {
			ret = new Gem(pos[0], pos[1]);
		} else if (this instanceof Cheese) {
			ret = new Cheese(pos[0], pos[1]);
		}
		ret.type = this.getType();
		ret.isStackable = this.isStackable;
		ret.itemPic = this.itemPic;
		ret.bounds = (Double) this.bounds.clone();
		return ret;
	}
	
	protected Item(double x, double y) {
		super(x, y);
		bounds = new Rectangle2D.Double(x, y, 1, 1);
	}
	
	protected void drawEntity(Graphics g) {
		int tileWidth = QuestPanel.TILE_WIDTH;
		g.drawImage(itemPic, 
					(int)(UrfQuest.panel.dispCenterX - (UrfQuest.game.player.getPosition()[0] - bounds.getX())*tileWidth), 
					(int)(UrfQuest.panel.dispCenterY - (UrfQuest.game.player.getPosition()[1] - bounds.getY())*tileWidth), 
					null);
	}

	public void drawDebug(Graphics g) {
		Player player = UrfQuest.game.getPlayer();
		g.setColor(Color.WHITE);
		g.drawString("bounds coords: " + bounds.getX() + ", " + bounds.getY(),
					 (int)(UrfQuest.panel.dispCenterX - (player.getPosition()[0] - bounds.getX())*QuestPanel.TILE_WIDTH),
					 (int)(UrfQuest.panel.dispCenterY - (player.getPosition()[1] - bounds.getY())*QuestPanel.TILE_WIDTH));
		g.drawString("bounds dimensions: " + bounds.getWidth() + ", " + bounds.getHeight(),
				 (int)(UrfQuest.panel.dispCenterX - (player.getPosition()[0] - bounds.getX())*QuestPanel.TILE_WIDTH),
				 (int)(UrfQuest.panel.dispCenterY - (player.getPosition()[1] - bounds.getY())*QuestPanel.TILE_WIDTH)+10);
	};
	
	public BufferedImage getPic() {
		return itemPic;
	}
	
	public boolean isStackable() {
		return isStackable;
	};
}