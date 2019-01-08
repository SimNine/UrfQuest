package entities.items;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.awt.image.BufferedImage;

import entities.Entity;
import entities.mobs.Mob;
import entities.mobs.Player;
import framework.QuestPanel;
import framework.UrfQuest;

public abstract class Item extends Entity {
	protected BufferedImage itemPic;
	protected static final String assetPath = "/assets/items/";
	
	protected Item(double x, double y) {
		super(x, y);
		bounds = new Rectangle2D.Double(x, y, 1, 1);
	}
	
	public abstract void use(Mob m);
	
	public abstract boolean isConsumable();
	
	public abstract void update();
	
	public abstract int getCooldown();
	
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
		ret.itemPic = this.itemPic;
		ret.bounds = (Double) this.bounds.clone();
		return ret;
	}
	
	// drawing methods
	protected void drawEntity(Graphics g) {
		int tileWidth = QuestPanel.TILE_WIDTH;
		g.drawImage(itemPic, 
					(int)(UrfQuest.panel.dispCenterX - (UrfQuest.game.getPlayer().getPos()[0] - bounds.getX())*tileWidth), 
					(int)(UrfQuest.panel.dispCenterY - (UrfQuest.game.getPlayer().getPos()[1] - bounds.getY())*tileWidth), 
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
	
	public abstract boolean isStackable();
}