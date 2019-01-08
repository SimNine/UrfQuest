package guis.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import entities.items.Item;
import framework.UrfQuest;
import guis.GUIObject;

public class InventoryBar extends GUIObject {
	private final int borderWidth = 3;
	private final int gapWidth = 2;
	
	private int entrySize;

	public InventoryBar(int anchorPoint, int xRel, int yRel, int entrySize) {
		super(anchorPoint, xRel, yRel, 0, 0); // dummy constructor call
		
		this.entrySize = entrySize;
		int width = 10*(entrySize + gapWidth) + gapWidth + borderWidth*2;
		int height = borderWidth*2 + gapWidth*2 + entrySize;
		
		setBounds(xRel, yRel, width, height);
	}

	public void draw(Graphics g) {
		// draw background
		g.setColor(new Color(255, 255, 255, 128));
		g.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
		
		// draw border
		g.setColor(Color.WHITE);
		for (int i = 0; i < borderWidth; i++) {
			g.drawRect(bounds.x + i, bounds.y + i, bounds.width - i*2, bounds.height - i*2);
		}
		
		// draw entries
		int xTemp = bounds.x + borderWidth + gapWidth;
		g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 15));
		for (int z = 0; z < UrfQuest.game.getPlayer().getInventoryItems().size(); z++) { // for each entry
			Item i = UrfQuest.game.getPlayer().getInventoryItems().get(z);
			if (z == UrfQuest.game.getPlayer().getInventory().getSelectedIndex()) { // draw the grey box (lighter if this entry is selected)
				g.setColor(new Color(192, 192, 192));
			} else {
				g.setColor(new Color(128, 128, 128, 128));
			}
			g.fillRect(xTemp, bounds.y + borderWidth + gapWidth, entrySize, entrySize);
			if (i != null) { // if this entry contains an item
				g.drawImage(i.getPic(), xTemp, bounds.y + borderWidth + gapWidth, entrySize, entrySize, null);
				if (i.maxStackSize() > 1) { // if this entry contains a stack, draw the number of items
					g.setColor(Color.BLACK);
					g.drawString("" + i.currStackSize(), xTemp, bounds.y + borderWidth + gapWidth + 10);
				}
				if (i.getCooldownPercentage() != 0) { // if this entry is not cooled down, overlay a red box
					if (i.getCooldown() < 20) {
						g.setColor(new Color(0, 0, 255, (int)(255.0*(i.getCooldown()/20.0))));
					} else {
						g.setColor(new Color(255, 0, 0, (int)(255*i.getCooldownPercentage())));
					}
					g.fillRect(xTemp, bounds.y + borderWidth + gapWidth, entrySize, entrySize);
				}
				if (i.degrades()) { // if this item degrades, draw a durability bar
					g.setColor(Color.BLACK);
					g.fillRect(xTemp, bounds.y + borderWidth + gapWidth + entrySize - 5, entrySize, 5);
					g.setColor(Color.GREEN);
					int barLength = (int)(i.getDurabilityPercentage()*(entrySize - 2));
					g.fillRect(xTemp + 1, bounds.y + borderWidth + gapWidth + entrySize - 4, barLength, 3);
				}
			}
			xTemp += entrySize + gapWidth;
		}
		
		if (UrfQuest.debug) {
			drawDebug(g);
		}
	}
}