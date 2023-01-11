package xyz.urffer.urfquest.server.state;

import java.util.ArrayList;

import xyz.urffer.urfquest.server.Server;
import xyz.urffer.urfquest.server.entities.items.ItemStack;
import xyz.urffer.urfquest.shared.protocol.types.ItemType;

public class Inventory {
	private Server server;
	private ItemStack[] entries;
	private int selectedEntry = 0;
	private int ownerID;
	
	public Inventory(Server s, int ownerID, int size) {
		this.server = s;
		this.entries = new ItemStack[size];
		for (int i = 0; i < this.entries.length; i++) {
			this.entries[i] = null;
		}
		this.ownerID = ownerID;
	}
	
	// gets an arrayList of all entries in the inventory
	public ArrayList<ItemStack> getItems() {
		ArrayList<ItemStack> e = new ArrayList<ItemStack>();
		for (int i = 0; i < entries.length; i++) {
			e.add(entries[i]);
		}
		return e;
	}
	
	// finds either an empty slot for the item, or adds it to a preexisting stack
	public boolean addItem(ItemStack i) {
		if (i.maxStackSize() > 1) { // if the item is stackable
			if (hasItem(i)) { // if the item already has a stack
				entries[findIndexOfEntry(i)].incStackSize(1);
				return true;
			} else { // if the item doesn't already have a stack
				int place = nextOpenSlot();
				if (place == -1) { // if there's no open slots
					return false;
				} else { // if there are open slots
					entries[place] = i;
					return true;
				}
			}
		} else { // if the item isn't stackable
			int place = nextOpenSlot();
			if (place == -1) {
				return false;
			} else {
				entries[place] = i;
				return true;
			}
		}
	}
	
	// takes one of the selected item out of the stack and returns it
	// (removes the stack if the item is unstackable)
	public ItemStack removeOneOfSelectedItem() {
		if (entries[selectedEntry] != null) {
			if (entries[selectedEntry].maxStackSize() == 1) {
				ItemStack temp = entries[selectedEntry];
				removeSelectedEntry();
				return temp;
			}
			ItemStack i = entries[selectedEntry].clone();
			if (entries[selectedEntry].currStackSize() > 1) {
				entries[selectedEntry].incStackSize(-1);
			} else {
				removeSelectedEntry();
			}
			return i;
		} else {
			return null;
		}
	}
	
	public ItemStack getSelectedItem() {
		return entries[selectedEntry];
	}
	
	public void removeSelectedEntry() {
		entries[selectedEntry] = null;
	}
	
	private void removeEntry(int index) {
		entries[index] = null;
	}
	
	public void setSelectedEntry(int i) {
		selectedEntry = i;
	}
	
	public boolean hasItem(ItemStack i) {
		return (findIndexOfEntry(i) != -1);
	}
	
	public void useSelectedItem() {
		ItemStack entry = entries[selectedEntry];
		
		if (entry == null) {
			return;
		}
		
		if (entry.canUse(this.ownerID)) {
			entry.use(this.ownerID);
			if (entry.isConsumable()) {
				entry.incStackSize(-1);
			}
			if (entry.degrades() && entry.getDurability() > 0) { // if the item degrades
				entry.incDurability(-1);
				if (entry.getDurability() == 0) { // if the item is fully degraded
					entry.incStackSize(-1);
				}
			}
		}
		
		if (entry.currStackSize() == 0) {
			removeSelectedEntry();
		}
	}
	
	// Finds index of next open slot. Returns -1 if no open slots
	private int nextOpenSlot() {
		for (int i = 0; i < entries.length; i++) {
			if (entries[i] == null || entries[i].getType() == ItemType.EMPTY_ITEM) {
				return i;
			}
		}
		return -1;
	}
	
	// finds the first index of the given type of item, -1 if it isn't in the inventory
	private int findIndexOfEntry(ItemStack i) {
		for (int j = 0; j < entries.length; j++) {
			if (entries[j] != null && 
				entries[j].getType() == i.getType()) {
				return j;
			}
		}
		return -1;
	}
	
	private void removeItemsOfEntry(int index, int number) {
		if (entries[index].currStackSize() == number) {
			this.removeEntry(index);
		} else if (entries[index].currStackSize() > number) {
			entries[index].incStackSize(-number);
		} else {
			throw new IllegalArgumentException();
		}
	}
	
	// attempts to craft, using the given inputs and outputs
//	public void tryCrafting(Collection<ItemStack> input, Collection<ItemStack> output) {
//		// check to see if the recipie is craftable
//		for (ItemStack i : input) {
//			int index = findIndexOfEntry(i);
//			if (index == -1) {
//				return;
//			} else if (entries[index].currStackSize() < i.currStackSize()) {
//				return;
//			}
//		}
//		if (output.size() > 10 - occupiedEntries.size()) {
//			return;
//		}
//		
//		//at this point, the recipie is craftable
//		for (ItemStack i : input) {
//			int index = findIndexOfEntry(i);
//			removeItemsOfEntry(index, i.currStackSize());
//		}
//		
//		for (ItemStack i : output) {
//			int index = nextOpenSlot();
//			entries[index] = i;
//			occupiedEntries.add(index);
//		}
//	}
	
	public int getSelectedIndex() {
		return selectedEntry;
	}
	
	public void setItemAtIndex(int index, ItemStack i) {
		entries[index] = i;
	}
}