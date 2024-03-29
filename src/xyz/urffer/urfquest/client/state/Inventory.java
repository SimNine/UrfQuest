package xyz.urffer.urfquest.client.state;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import xyz.urffer.urfquest.client.entities.items.ItemStack;
import xyz.urffer.urfquest.client.entities.mobs.Mob;

public class Inventory {
	private ItemStack[] entries;
	private HashSet<Integer> occupiedEntries = new HashSet<Integer>();
	private int selectedEntry = 0;
	private Mob owner;
	
	public Inventory(Mob owner, int size) {
		entries = new ItemStack[size];
		this.owner = owner;
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
	public ItemStack removeOneOfSelectedItem() {
//		if (entries[selectedEntry] != null) {
//			if (entries[selectedEntry].maxStackSize() == 1) {
//				Item temp = entries[selectedEntry];
//				removeSelectedEntry();
//				return temp;
//			}
//			Item i = entries[selectedEntry].clone();
//			if (entries[selectedEntry].currStackSize() > 1) {
//				entries[selectedEntry].incStackSize(-1);
//			} else {
//				removeSelectedEntry();
//			}
//			return i;
//		} else {
//			return null;
//		}
		return null;
	}
	
	public ItemStack getSelectedItem() {
		return entries[selectedEntry];
	}
	
	public void removeSelectedEntry() {
		entries[selectedEntry] = null;
		occupiedEntries.remove(selectedEntry);
	}
	
	private void removeEntry(int index) {
		entries[index] = null;
		occupiedEntries.remove(index);
	}
	
	public void setSelectedEntry(int i) {
		selectedEntry = i;
	}
	
	public boolean hasItem(ItemStack i) {
		return (findIndexOfEntry(i) != -1);
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
	public void tryCrafting(Collection<ItemStack> input, Collection<ItemStack> output) {
		// check to see if the recipie is craftable
		for (ItemStack i : input) {
			int index = findIndexOfEntry(i);
			if (index == -1) {
				return;
			} else if (entries[index].currStackSize() < i.currStackSize()) {
				return;
			}
		}
		if (output.size() > 10 - occupiedEntries.size()) {
			return;
		}
		
		//at this point, the recipie is craftable
		for (ItemStack i : input) {
			int index = findIndexOfEntry(i);
			removeItemsOfEntry(index, i.currStackSize());
		}
		
		for (ItemStack i : output) {
			int index = nextOpenSlot();
			entries[index] = i;
			occupiedEntries.add(index);
		}
	}
	
	public int getSelectedIndex() {
		return selectedEntry;
	}
	
	public void setItemAtIndex(int index, ItemStack i) {
		if (i == null) {
			occupiedEntries.remove(index);
			entries[index] = null;
		} else {
			occupiedEntries.add(index);
			entries[index] = i;
		}
	}
}