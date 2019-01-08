package game;

import java.awt.image.BufferedImage;

import entities.items.Item;

public class InventoryEntry {
	private boolean isStack;
	private boolean isEmpty;
	private boolean isSelected;
	private int numItems;
	private int cooldownState;
	private Item item;
	
	public InventoryEntry() {
		this.item = null;
		this.isEmpty = true;
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
	
	public void useItem() {
		if (item.getCooldown() == -1) {
			return; // the item isn't usable
		}
		
		if (this.cooldownState == 0) { // the item is fully cooled down
			item.use();
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
		return isEmpty;
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
