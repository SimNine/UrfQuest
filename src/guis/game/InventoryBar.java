package guis.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import entities.items.Item;
import framework.UrfQuest;
import guis.GUIContainer;
import guis.GUIObject;

public class InventoryBar extends GUIContainer {
	private final static int borderWidth = 3;
	private final static int gapWidth = 2;
	
	private int entrySize;

	public InventoryBar(int anchorPoint, int xRel, int yRel, int entrySize, GUIObject parent) {
		super(anchorPoint, xRel, yRel, 
			  10*(entrySize + gapWidth) + gapWidth + borderWidth*2, borderWidth*2 + gapWidth*2 + entrySize, 
			  null, parent, 
			  new Color(255, 255, 255, 128), Color.WHITE, borderWidth);
		
		this.entrySize = entrySize;
	}

	public void draw(Graphics g) {
		// compute entries
		int xTemp = borderWidth + gapWidth;
		g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 15));
		for (int z = 0; z < UrfQuest.game.getPlayer().getInventoryItems().size(); z++) { // for each entry
			Item i = UrfQuest.game.getPlayer().getInventoryItems().get(z);
			InventoryEntry e = new InventoryEntry(GUIObject.TOP_LEFT, xTemp, borderWidth + gapWidth, 
												  entrySize, entrySize, this, i);
			if (z == UrfQuest.game.getPlayer().getInventory().getSelectedIndex()) { // draw the grey box (lighter if this entry is selected)
				e.setBkgColor(new Color(192, 192, 192));
			} else {
				e.setBkgColor(new Color(128, 128, 128, 128));
			}
			guiObjects.add(e);
			xTemp += entrySize + gapWidth;
		}
		
		// draws background, border, and objects (entries)
		super.draw(g);
		
		// must clear entries for each draw
		guiObjects.clear();
	}
}