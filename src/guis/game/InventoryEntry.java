package guis.game;

import java.awt.Color;
import java.awt.Graphics;

import entities.items.Item;
import framework.UrfQuest;
import guis.GUIContainer;
import guis.GUIObject;

public class InventoryEntry extends GUIObject {
	private Item item;
	private Color color;

	protected InventoryEntry(int anchorPoint, int xRel, int yRel, int width, int height, GUIContainer parent, Item item) {
		super(anchorPoint, xRel, yRel, width, height, parent);
		this.item = item;
		this.color = new Color(128, 128, 128, 128);
	}

	public void draw(Graphics g) {
		g.setColor(color);
		g.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
		if (item != null) { // if this entry contains an item
			g.drawImage(item.getPic(), bounds.x, bounds.y, bounds.width, bounds.height, null);
			if (item.maxStackSize() > 1) { // if this entry contains a stack, draw the number of items
				g.setColor(Color.BLACK);
				g.drawString("" + item.currStackSize(), bounds.x, bounds.y + 10);
			}
			if (item.getCooldownPercentage() != 0) { // if this entry is not cooled down, overlay a red box
				if (item.getCooldown() < 20) {
					g.setColor(new Color(0, 0, 255, (int)(255.0*(item.getCooldown()/20.0))));
				} else {
					g.setColor(new Color(255, 0, 0, (int)(255*item.getCooldownPercentage())));
				}
				g.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
			}
			if (item.degrades()) { // if this item degrades, draw a durability bar
				g.setColor(Color.BLACK);
				g.fillRect(bounds.x, bounds.y + bounds.height - 5, bounds.width, 5);
				g.setColor(Color.GREEN);
				int barLength = (int)(item.getDurabilityPercentage()*(bounds.width - 2));
				g.fillRect(bounds.x + 1, bounds.y + bounds.height - 4, barLength, 3);
			}
		}
		
		if (UrfQuest.debug) {
			this.drawDebug(g);
		}
	}

	public void setBkgColor(Color c) {
		this.color = c;
	}
	
	public Item getItem() {
		return item;
	}
}