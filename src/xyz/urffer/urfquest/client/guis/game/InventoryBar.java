package xyz.urffer.urfquest.client.guis.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import xyz.urffer.urfquest.client.Client;
import xyz.urffer.urfquest.client.guis.GUIAnchor;
import xyz.urffer.urfquest.client.guis.GUIContainer;
import xyz.urffer.urfquest.client.guis.GUIObject;

public class InventoryBar extends GUIContainer {
	private final static int borderWidth = 3;
	private final static int gapWidth = 2;
	
	private int entrySize;

	public InventoryBar(Client c, GUIAnchor anchorPoint, int xRel, int yRel, int entrySize, GUIObject parent) {
		super(c, anchorPoint, xRel, 
			  yRel, 10*(entrySize + gapWidth) + gapWidth + borderWidth*2, 
			  borderWidth*2 + gapWidth*2 + entrySize, null, 
			  parent, new Color(255, 255, 255, 128), Color.WHITE, borderWidth);
		
		this.entrySize = entrySize;
		
		// compute entries
		int xTemp = borderWidth + gapWidth;
		for (int z = 0; z < this.client.getState().getPlayer().getInventoryItems().size(); z++) { // for each entry
			InventoryEntry e = new InventoryEntry(this.client, GUIAnchor.TOP_LEFT, xTemp, 
												  borderWidth + gapWidth, entrySize, entrySize, this, z);
			guiObjects.add(e);
			xTemp += entrySize + gapWidth;
		}
	}

	public void draw(Graphics g) {
		g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 15));
		super.draw(g);
	}
}