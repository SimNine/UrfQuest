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
	
	public boolean addItem(Item i) {
		if (i.isStackable()) { // if the item is stackable
			if (hasItem(i)) { // if the item already has a stack
				entries[findIndexOfEntry(i.getType())].incNumItems(1);
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
	
	public void removeSelectedEntry() {
		entries[selectedEntry] = new InventoryEntry();
		occupiedSlots.remove(selectedEntry);
	}
	
	public void setSelectedEntry(int i) {
		selectedEntry = i;
	}
	
	public boolean hasItem(Item i) {
		return (findIndexOfEntry(i.getType()) != -1);
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
	private int findIndexOfEntry(String s) {
		for (int i = 0; i < entries.length; i++) {
			if (!entries[i].isEmpty() && entries[i].getType().equals(s)) {
				return i;
			}
		}
		return -1;
	}
}