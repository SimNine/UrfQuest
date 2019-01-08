package game;

import java.awt.image.BufferedImage;

import entities.items.Item;

public class InventoryEntry {
	private boolean isStack;
	private boolean isEmpty;
	private boolean isSelected;
	private int numItems;
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
	
	public String getType() {
		return this.item.getType();
	}
	
	public Item getItem() {
		return item;
	}
	
	public int getNumItems() {
		return numItems;
	}
	
	public void incNumItems(int n) {
		numItems += n;
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
