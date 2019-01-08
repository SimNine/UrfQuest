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
	
	protected Item(double x, double y) {
		super(x, y);
		bounds = new Rectangle2D.Double(x, y, 1, 1);
	}
	
	public void use() {
		// does nothing by default
	}
	
	public void update() {
		// does nothing by default
	}
	
	public int getCooldown() {
		return -1; // should be overridden if needed; -1 indicated cooldown is not applicable
	}
	
	public Item clone() {
		double[] pos = this.getPos();
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
		} else if (this instanceof Bone) {
			ret = new Bone(pos[0], pos[1]);
		}
		ret.isStackable = this.isStackable;
		ret.itemPic = this.itemPic;
		ret.bounds = (Double) this.bounds.clone();
		return ret;
	}
	
	// drawing methods
	protected void drawEntity(Graphics g) {
		int tileWidth = QuestPanel.TILE_WIDTH;
		g.drawImage(itemPic, 
					(int)(UrfQuest.panel.dispCenterX - (UrfQuest.game.player.getPos()[0] - bounds.getX())*tileWidth), 
					(int)(UrfQuest.panel.dispCenterY - (UrfQuest.game.player.getPos()[1] - bounds.getY())*tileWidth), 
					null);
	}

	public void drawDebug(Graphics g) {
		Player player = UrfQuest.game.getPlayer();
		g.setColor(Color.WHITE);
		g.drawString("bounds coords: " + bounds.getX() + ", " + bounds.getY(),
					 (int)(UrfQuest.panel.dispCenterX - (player.getPos()[0] - bounds.getX())*QuestPanel.TILE_WIDTH),
					 (int)(UrfQuest.panel.dispCenterY - (player.getPos()[1] - bounds.getY())*QuestPanel.TILE_WIDTH));
		g.drawString("bounds dimensions: " + bounds.getWidth() + ", " + bounds.getHeight(),
				 (int)(UrfQuest.panel.dispCenterX - (player.getPos()[0] - bounds.getX())*QuestPanel.TILE_WIDTH),
				 (int)(UrfQuest.panel.dispCenterY - (player.getPos()[1] - bounds.getY())*QuestPanel.TILE_WIDTH)+10);
	};
	
	// getters and setters
	public BufferedImage getPic() {
		return itemPic;
	}
	
	public boolean isStackable() {
		return isStackable;
	};
}