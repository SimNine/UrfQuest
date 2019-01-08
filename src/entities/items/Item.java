package entities.items;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import entities.Entity;
import entities.mobs.Mob;
import framework.UrfQuest;

public abstract class Item extends Entity {
	protected BufferedImage itemPic;
	protected static final String assetPath = "/assets/items/";
	protected int cooldown = 0;
	protected int durability = 1;
	protected int stackSize = 1;
	
	protected Item(double x, double y) {
		super(x, y);
		bounds = new Rectangle2D.Double(x, y, 1, 1);
		if (this.degrades()) {
			durability = this.getMaxDurability();
		}
	}
	
	// manipulation methods
	
	// returns true if used successfully, false if otherwise
	public abstract boolean use(Mob m);
	
	public void update() {
		if (getMaxCooldown() > -1) {
			if (cooldown > 0) {
				cooldown--;
			}
		}
	}
	
	public abstract Item clone();
	
	// drawing methods
	protected void drawEntity(Graphics g) {
		g.drawImage(itemPic, 
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
	
	// getters and setters
	public BufferedImage getPic() {
		return itemPic;
	}
	
	public abstract boolean isConsumable();
	
	public abstract int getMaxCooldown();
	
	public int getCooldown() {
		return cooldown;
	}
	
	public void setCooldown(int i) {
		if (getMaxCooldown() > -1) {
			cooldown = i;
		} else {
			throw new IllegalArgumentException("This item has no cooldown");
		}
	}
	
	public double getCooldownPercentage() {
		return (cooldown/(double)getMaxCooldown());
	}
	
	public boolean isUsable() {
		return (getMaxCooldown() > -1);
	}
	
	public abstract int getMaxDurability();
	
	public int getDurability() {
		return durability;
	}
	
	public void setDurability(int i) {
		if (getMaxDurability() > -1) {
			if (i < 0) {
				durability = 0;
			} else {
				durability = i;
			}
		} else {
			throw new IllegalArgumentException("This item has no durability");
		}
	}
	
	public void incDurability(int i) {
		setDurability(durability + i);
	}
	
	public double getDurabilityPercentage() {
		return (durability/(double)getMaxDurability());
	}
	
	public boolean degrades() {
		return (getMaxDurability() > -1);
	}
	
	public int currStackSize() {
		return stackSize;
	}
	
	public void incStackSize(int i) {
		if (stackSize + i < 0) {
			stackSize = 0;
		} else {
			stackSize += i;
		}
	}
	
	public abstract int maxStackSize();
}