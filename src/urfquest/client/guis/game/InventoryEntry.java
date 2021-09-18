package urfquest.client.guis.game;

import java.awt.Color;
import java.awt.Graphics;

import urfquest.Logger;
import urfquest.Main;
import urfquest.client.entities.items.Item;
import urfquest.client.entities.mobs.Player;
import urfquest.client.guis.Clickable;
import urfquest.client.guis.GUIContainer;
import urfquest.client.guis.GUIObject;

public class InventoryEntry extends GUIObject implements Clickable {
	private Item item;
	private Color color;
	private int entryNum;
	
	private static final int INVENTORY = 10;
	private static final int MISC = 11;
	private int type;

	protected InventoryEntry(int anchorPoint, int xRel, int yRel, int width, int height, GUIContainer parent, int entryNum) {
		super(anchorPoint, xRel, yRel, width, height, parent);
		this.entryNum = entryNum;
		this.color = new Color(128, 128, 128, 128);
		
		this.type = INVENTORY;
	}
	
	protected InventoryEntry(int anchorPoint, int xRel, int yRel, int width, int height, GUIContainer parent, Item item) {
		super(anchorPoint, xRel, yRel, width, height, parent);
		this.item = item;
		this.color = new Color(128, 128, 128, 128);
		
		this.type = MISC;
	}

	public void draw(Graphics g) {
		g.setColor(color);
		g.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
		
		Item tempItem;
		if (type == INVENTORY) {
			tempItem = Main.client.getState().getPlayer().getInventoryItems().get(entryNum);
			if (Main.client.getState().getPlayer().getInventory().getSelectedIndex() == entryNum) {
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
		
		if (Main.logger.getLogLevel().compareTo(Logger.LogLevel.LOG_DEBUG) >= 0) {
			this.drawDebug(g);
		}
	}

	public void setBkgColor(Color c) {
		this.color = c;
	}
	
	public Item getItem() {
		return item;
	}

	public boolean click() {
		if (type != INVENTORY) {
			return false;
		}
		
		//System.out.println("ding");
		
		Player p = Main.client.getState().getPlayer();
		Item tempItem = p.getInventory().getItems().get(entryNum);
		Item heldItem = p.getHeldItem();
		if (tempItem == null && heldItem != null) {
			p.getInventory().setItemAtIndex(entryNum, heldItem);
			p.setHeldItem(null);
		} else if (tempItem != null && heldItem == null) {
			p.setHeldItem(tempItem);
			p.getInventory().setItemAtIndex(entryNum, null);
		} else if (tempItem != null && heldItem != null) {
			Item swap = tempItem;
			p.getInventory().setItemAtIndex(entryNum, heldItem);
			p.setHeldItem(swap);
		}
		return true;
	}
}