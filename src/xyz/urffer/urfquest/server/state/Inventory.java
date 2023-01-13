package xyz.urffer.urfquest.server.state;

import xyz.urffer.urfquest.server.Server;
import xyz.urffer.urfquest.server.entities.items.ItemStack;

public class Inventory {
	private Server server;
	private int ownerID;
	
	private int[] entries;
	private int selectedEntry = 0;
	
	public Inventory(Server s, int ownerID, int size) {
		this.server = s;
		this.entries = new int[size];
		for (int i = 0; i < this.entries.length; i++) {
			this.entries[i] = 0;
		}
		this.ownerID = ownerID;
	}
	
	// Finds an empty slot for the item
	public void addItem(int itemID) {
		int place = nextOpenSlot();
		if (place != -1) {
			entries[place] = itemID;
		}
	}
	
	public int getSelectedItemID() {
		return entries[selectedEntry];
	}
	
	public void removeSelectedEntry() {
		entries[selectedEntry] = 0;
	}
	
	public void setSelectedEntry(int i) {
		selectedEntry = i;
	}
	
	public void useSelectedItem() {
		int itemID = entries[selectedEntry];
		ItemStack item = (ItemStack)this.server.getState().getEntity(itemID);
		
		if (item == null) {
			return;
		}
		
		if (item.canUse(this.ownerID)) {
			item.use(this.ownerID);
		}
		
		if (item.currStackSize() == 0) {
			entries[selectedEntry] = 0;
			
			this.server.getState().removeEntity(itemID);
		}
	}
	
	// Finds index of next open slot. Returns -1 if no open slots
	private int nextOpenSlot() {
		for (int i = 0; i < entries.length; i++) {
			if (entries[i] == 0) {
				return i;
			}
		}
		return -1;
	}
}
