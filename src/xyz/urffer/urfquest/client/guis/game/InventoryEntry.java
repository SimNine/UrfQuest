package xyz.urffer.urfquest.client.guis.game;

import java.awt.Color;
import java.awt.Graphics;

import xyz.urffer.urfquest.LogLevel;
import xyz.urffer.urfquest.Logger;
import xyz.urffer.urfquest.client.Client;
import xyz.urffer.urfquest.client.entities.items.ItemStack;
import xyz.urffer.urfquest.client.entities.mobs.Player;
import xyz.urffer.urfquest.client.guis.Clickable;
import xyz.urffer.urfquest.client.guis.GUIAnchor;
import xyz.urffer.urfquest.client.guis.GUIContainer;
import xyz.urffer.urfquest.client.guis.GUIObject;

public class InventoryEntry extends GUIObject implements Clickable {
	private ItemStack item;
	private Color color;
	private int entryIndex;
	
	private static final int INVENTORY = 10;
	private static final int MISC = 11;
	private int type;

	protected InventoryEntry(Client c, GUIAnchor anchorPoint, int xRel, int yRel, int width, int height, GUIContainer parent, int entryIndex) {
		super(c, anchorPoint, xRel, yRel, width, height, parent);
		this.entryIndex = entryIndex;
		this.color = new Color(128, 128, 128, 128);
		
		this.type = INVENTORY;
	}
	
	protected InventoryEntry(Client c, GUIAnchor anchorPoint, int xRel, int yRel, int width, int height, GUIContainer parent, ItemStack item) {
		super(c, anchorPoint, xRel, yRel, width, height, parent);
		this.item = item;
		this.color = new Color(128, 128, 128, 128);
		
		this.type = MISC;
	}

	public void draw(Graphics g) {
		g.setColor(color);
		g.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
		
		ItemStack tempItem;
		if (type == INVENTORY) {
			tempItem = this.client.getState().getPlayer().getInventoryItems().get(entryIndex);
			if (this.client.getState().getPlayer().getInventory().getSelectedIndex() == entryIndex) {
				this.setBkgColor(new Color(192, 192, 192));
			} else {
				this.setBkgColor(new Color(128, 128, 128, 128));
			}
		} else if (type == MISC) {
			tempItem = item;
		} else {
			throw new IllegalArgumentException();
		}
		
		if (tempItem != null) { // if this entry contains an tempItem
			g.drawImage(tempItem.getPic(), bounds.x, bounds.y, bounds.width, bounds.height, null);
			if (tempItem.maxStackSize() > 1) { // if this entry contains a stack, draw the number of tempItems
				g.setColor(Color.BLACK);
				g.drawString("" + tempItem.currStackSize(), bounds.x, bounds.y + 10);
			}
			if (tempItem.getCooldownPercentage() != 0) { // if this entry is not cooled down, overlay a red box
				if (tempItem.getCooldown() < 20) {
					g.setColor(new Color(0, 0, 255, (int)(255.0*(tempItem.getCooldown()/20.0))));
				} else {
					g.setColor(new Color(255, 0, 0, (int)(255*tempItem.getCooldownPercentage())));
				}
				g.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
			}
			if (tempItem.degrades()) { // if this tempItem degrades, draw a durability bar
				g.setColor(Color.BLACK);
				g.fillRect(bounds.x, bounds.y + bounds.height - 5, bounds.width, 5);
				g.setColor(Color.GREEN);
				int barLength = (int)(tempItem.getDurabilityPercentage()*(bounds.width - 2));
				g.fillRect(bounds.x + 1, bounds.y + bounds.height - 4, barLength, 3);
			}
		}
		
		if (this.client.getLogger().getLogLevel().compareTo(LogLevel.DEBUG) >= 0) {
			this.drawDebug(g);
		}
	}

	public void setBkgColor(Color c) {
		this.color = c;
	}
	
	public ItemStack getItem() {
		return item;
	}

	public boolean click() {
		return false;
//		
//		if (type != INVENTORY) {
//			return false;
//		}
//		
//		Player p = this.client.getState().getPlayer();
//		ItemStack tempItem = p.getInventory().getItems().get(entryIndex);
//		ItemStack heldItem = p.getSelectedInventoryItem();
//		if (tempItem == null && heldItem != null) {
//			p.getInventory().setItemAtIndex(entryIndex, heldItem);
//			p.setSelectedInventoryIndex(null);
//		} else if (tempItem != null && heldItem == null) {
//			p.setSelectedInventoryIndex(tempItem);
//			p.getInventory().setItemAtIndex(entryIndex, null);
//		} else if (tempItem != null && heldItem != null) {
//			ItemStack swap = tempItem;
//			p.getInventory().setItemAtIndex(entryIndex, heldItem);
//			p.setSelectedInventoryIndex(swap);
//		}
//		return true;
	}
}