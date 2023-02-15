package xyz.urffer.urfquest.client.entities.items;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import xyz.urffer.urfquest.client.Client;
import xyz.urffer.urfquest.client.entities.Entity;
import xyz.urffer.urfquest.shared.protocol.types.ItemType;

public class ItemStack extends Entity {

	private ItemType itemType;
	private int cooldown;
	private int durability;
	private int stackSize;
		
	public ItemStack(Client c, int id, ItemType type, int stackSize, int durability) {
		super(c, id);
		bounds = new Rectangle2D.Double(0, 0, 1, 1);
		
		this.itemType = type;
		this.cooldown = 0;
		if (this.degrades()) {
			if (durability == -1) {
				this.durability = this.getMaxDurability();
			} else {
				this.durability = durability;
			}
		} else {
			this.durability = -1;
		}
		if (stackSize == -1) {
			this.stackSize = this.maxStackSize();
		} else {
			this.stackSize = stackSize;
		}
	}

	
	
	/*
	 * Manipulation methods
	 */
	
	public void update() {
		if (getMaxCooldown() > -1) {
			if (cooldown > 0) {
				cooldown--;
			}
		}
	}
	
	
	
	/*
	 * Getters and setters
	 */
	
	public boolean isConsumable() {
		return itemType.getConsumable();
	}
	
	public int getMaxCooldown() {
		return itemType.getMaxCooldown();
	}
	
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
	
	public int getMaxDurability() {
		return itemType.getMaxDurability();
	}
	
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
	
	public void setStackSize(int i) {
		if (i < 1) {
			throw new IllegalArgumentException();
		}
		
		if (i > 1 && maxStackSize() == 1) {
			stackSize = 1;
		}
		
		stackSize = i;
	}
	
	public int maxStackSize() {
		return itemType.getMaxStacksize();
	}
	
	public ItemType getType() {
		return itemType;
	}
	
	/*
	 * drawing methods
	 */
	
	protected void drawEntity(Graphics g) {
		g.drawImage(itemType.getImage(), 
					client.getPanel().gameToWindowX(bounds.getX()), 
					client.getPanel().gameToWindowY(bounds.getY()), 
					null);
	}

	public void drawDebug(Graphics g) {
		g.setColor(Color.WHITE);
		g.drawString("bounds coords: " + bounds.getX() + ", " + bounds.getY(),
					 (int) client.getPanel().gameToWindowX(bounds.getX()),
					 (int) client.getPanel().gameToWindowY(bounds.getY()));
		g.drawString("bounds dimensions: " + bounds.getWidth() + ", " + bounds.getHeight(),
				 (int) client.getPanel().gameToWindowX(bounds.getX()),
				 (int) client.getPanel().gameToWindowY(bounds.getY()) + 10);
	};

	public BufferedImage getPic() {
		return itemType.getImage();
	}
}