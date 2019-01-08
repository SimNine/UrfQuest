package game;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import entities.items.Item;

public class Inventory {
	private InventoryEntry[] entries = new InventoryEntry[10];
	private HashSet<Integer> occupiedSlots = new HashSet<Integer>();
	private int selectedEntry = 0;
	
	public Inventory() {
		for (int i = 0; i < entries.length; i++) {
			entries[i] = new InventoryEntry();
		}
	}
	
	// gets a collection of all entries in the inventory
	public Collection<InventoryEntry> getInventoryEntries() {
		ArrayList<InventoryEntry> e = new ArrayList<InventoryEntry>();
		for (int i = 0; i < entries.length; i++) {
			if (i == selectedEntry) {
				entries[i].setSelection(true);
			} else {
				entries[i].setSelection(false);
			}
			e.add(entries[i]);
		}
		return e;
	}
	
	// finds either an empty slot for the item, or adds it to a preexisting stack
	public boolean addItem(Item i) {
		if (i.isStackable()) { // if the item is stackable
			if (hasItem(i)) { // if the item already has a stack
				entries[findIndexOfEntry(i)].incNumItems(1);
				return true;
			} else { // if the item doesn't already have a stack
				int place = nextOpenSlot();
				if (place == -1) { // if there's no open slots
					return false;
				} else { // if there are open slots
					entries[place] = new InventoryEntry(i);
					occupiedSlots.add(place);
					return true;
				}
			}
		} else { // if the item isn't stackable
			int place = nextOpenSlot();
			if (place == -1) {
				return false;
			} else {
				entries[place] = new InventoryEntry(i);
				occupiedSlots.add(place);
				return true;
			}
		}
	}
	
	// takes one of the selected item out of the stack and returns it
	// (removes the stack if the item is unstackable)
	public Item removeOneOfSelectedItem() {
		if (!entries[selectedEntry].isEmpty()) {
			Item i = entries[selectedEntry].getItem().clone();
			if (entries[selectedEntry].isStack() && entries[selectedEntry].getNumItems() > 1) {
				entries[selectedEntry].incNumItems(-1);
			} else {
				removeSelectedEntry();
			}
			return i;
		} else {
			return null;
		}
	}
	
	public Item getSelectedItem() {
		return entries[selectedEntry].getItem();
	}
	
	public void removeSelectedEntry() {
		entries[selectedEntry] = new InventoryEntry();
		occupiedSlots.remove(selectedEntry);
	}
	
	public void setSelectedEntry(int i) {
		selectedEntry = i;
	}
	
	public boolean hasItem(Item i) {
		return (findIndexOfEntry(i) != -1);
	}
	
	public void useSelectedItem() {
		entries[selectedEntry].useItem();
	}
	
	// finds index of next open slot. returns -1 if no open slots
	private int nextOpenSlot() {
		for (int i = 0; i < entries.length; i++) {
			if (!occupiedSlots.contains(i)) {
				return i;
			}
		}
		return -1;
	}
	
	// finds the first index of the given type of item, -1 if it isn't in the inventory
	private int findIndexOfEntry(Item i) {
		for (int j = 0; j < entries.length; j++) {
			if (!entries[j].isEmpty() && entries[j].getItem().getClass() == i.getClass()) {
				return j;
			}
		}
		return -1;
	}
}