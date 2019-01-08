package game;

import java.util.ArrayList;
import java.util.HashSet;

import entities.items.Item;
import framework.UrfQuest;

public class Inventory {
	private Item[] entries = new Item[10];
	private HashSet<Integer> occupiedEntries = new HashSet<Integer>();
	private int selectedEntry = 0;
	
	// gets a collection of all entries in the inventory
	public ArrayList<Item> getItems() {
		ArrayList<Item> e = new ArrayList<Item>();
		for (int i = 0; i < entries.length; i++) {
			e.add(entries[i]);
		}
		return e;
	}
	
	// finds either an empty slot for the item, or adds it to a preexisting stack
	public boolean addItem(Item i) {
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
					occupiedEntries.add(place);
					return true;
				}
			}
		} else { // if the item isn't stackable
			int place = nextOpenSlot();
			if (place == -1) {
				return false;
			} else {
				entries[place] = i;
				occupiedEntries.add(place);
				return true;
			}
		}
	}
	
	// takes one of the selected item out of the stack and returns it
	// (removes the stack if the item is unstackable)
	public Item removeOneOfSelectedItem() {
		if (entries[selectedEntry] != null) {
			Item i = entries[selectedEntry].clone();
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
	
	public Item getSelectedItem() {
		return entries[selectedEntry];
	}
	
	public void removeSelectedEntry() {
		entries[selectedEntry] = null;
		occupiedEntries.remove(selectedEntry);
	}
	
	public void setSelectedEntry(int i) {
		selectedEntry = i;
	}
	
	public boolean hasItem(Item i) {
		return (findIndexOfEntry(i) != -1);
	}
	
	public void useSelectedItem() {
		if (entries[selectedEntry] == null) {
			return;
		}
		if (entries[selectedEntry].isUsable() && entries[selectedEntry].getCooldown() == 0) {// if the item is usable and cooled
			entries[selectedEntry].use(UrfQuest.game.getPlayer());
			entries[selectedEntry].setCooldown(entries[selectedEntry].getMaxCooldown());
			if (entries[selectedEntry].isConsumable()) {
				entries[selectedEntry].incStackSize(-1);
			}
			if (entries[selectedEntry].degrades() && entries[selectedEntry].getDurability() > 0) { // if the item degrades
				entries[selectedEntry].incDurability(-1);
				if (entries[selectedEntry].getDurability() == 0) { // if the item is fully degraded
					entries[selectedEntry].incStackSize(-1);
				}
			}
		}
		
		if (entries[selectedEntry].currStackSize() == 0) {
			removeSelectedEntry();
		}
	}
	
	// finds index of next open slot. returns -1 if no open slots
	private int nextOpenSlot() {
		for (int i = 0; i < entries.length; i++) {
			if (!occupiedEntries.contains(i)) {
				return i;
			}
		}
		return -1;
	}
	
	// finds the first index of the given type of item, -1 if it isn't in the inventory
	private int findIndexOfEntry(Item i) {
		for (int j = 0; j < entries.length; j++) {
			if (entries[j] != null && 
				entries[j].getClass() == i.getClass()) {
				return j;
			}
		}
		return -1;
	}
	
	public int getSelectedIndex() {
		return selectedEntry;
	}
}