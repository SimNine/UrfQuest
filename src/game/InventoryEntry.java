package game;

import java.awt.image.BufferedImage;

import entities.items.Item;
import entities.mobs.Mob;

public class InventoryEntry {
	private boolean isStack;
	private boolean isSelected;
	private int numItems;
	private int cooldownState;
	private Item item;
	
	public InventoryEntry() {
		this.item = null;
		this.isStack = false;
		this.numItems = 0;
		this.isSelected = false;
	}
	
	public InventoryEntry(Item i) {
		this.item = i;
		this.isStack = i.isStackable();
		this.numItems = 1;
	}
	
	public void update() {
		if (cooldownState > 0) {
			cooldownState--;
		}
	}
	
	public void use(Mob m) {
		// if there's nothing in this entry, do nothing
		if (isEmpty()) {
			return;
		}
		
		// if the item isn't cooled down, do nothing
		if (item.getCooldown() == -1) {
			return;
		}
		
		if (this.cooldownState == 0) {
			item.use(m);
			if (item.isConsumable()) {
				if (this.numItems > 0) {
					numItems--;
				}
			}
			cooldownState = item.getCooldown();
		} else {
			return;
		}
	}
	
	// getters and setters
	public Item getItem() {
		return item;
	}
	
	public int getNumItems() {
		return numItems;
	}
	
	public void incNumItems(int n) {
		numItems += n;
	}
	
	public double getCooldownPercentage() {
		return cooldownState/(double)item.getCooldown();
	}
	
	public boolean isStack() {
		return isStack;
	}
	
	public boolean isEmpty() {
		return this.numItems <= 0;
	}
	
	public boolean isSelected() {
		return isSelected;
	}
	
	public void setSelection(boolean s) {
		isSelected = s;
	}
	
	public BufferedImage getPic() {
		return item.getPic();
	}
}
