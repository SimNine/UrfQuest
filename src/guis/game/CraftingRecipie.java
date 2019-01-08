package guis.game;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import entities.items.Item;
import framework.UrfQuest;
import guis.GUIContainer;
import guis.GUIObject;

public class CraftingRecipie extends GUIContainer {
	private ArrayList<Item> input = new ArrayList<Item>();
	private ArrayList<Item> output = new ArrayList<Item>();

	public CraftingRecipie(int anchorPoint, int xRel, int yRel, int width, int height, String name, GUIObject parent,
			Color bkg, Color borderColor, int borderThickness) {
		super(anchorPoint, xRel, yRel, width, height, name, parent, bkg, borderColor, borderThickness);
	}

	public void draw(Graphics g) {
		g.setColor(Color.BLACK);
		g.drawLine(bounds.x, bounds.y + bounds.height/2, bounds.x + bounds.width, bounds.y + bounds.height/2);
		
		if (isMouseOver()) {
			this.setBorderCol(Color.BLACK);
		} else {
			this.setBorderCol(Color.WHITE);
		}
		
		super.draw(g);
	}
	
	public void addInput(Item i) {
		InventoryEntry e = new InventoryEntry(GUIObject.TOP_LEFT, 
											  5 + input.size()*45, 
											  5, 
											  40, 
											  40, 
											  this, 
											  i);
		input.add(i);
		guiObjects.add(e);
	}
	
	public void addOutput(Item i) {
		InventoryEntry e = new InventoryEntry(GUIObject.BOTTOM_LEFT, 
				  5 + output.size()*45, 
				  -45, 
				  40, 
				  40, 
				  this, 
				  i);
		output.add(i);
		guiObjects.add(e);
	}
	
	public boolean click() {
		System.out.println("crafting attempted");
		UrfQuest.game.getPlayer().tryCrafting(input, output);
		return true;
	}
}
