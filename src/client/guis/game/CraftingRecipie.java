package client.guis.game;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashSet;

import entities.items.Item;
import framework.UrfQuest;
import guis.GUIContainer;
import guis.GUIObject;

public class CraftingRecipie extends GUIContainer {
	private ArrayList<Item> input = new ArrayList<Item>();
	private ArrayList<Item> output = new ArrayList<Item>();
	
	private HashSet<GUIObject> inputEntries = new HashSet<GUIObject>();
	private GUIContainer inputContainer;

	public CraftingRecipie(int anchorPoint, int xRel, int yRel, int width, int height, String name, GUIObject parent,
			Color bkg, Color borderColor, int borderThickness, GUIContainer inputContainer) {
		super(anchorPoint, xRel, yRel, width, height, name, parent, bkg, borderColor, borderThickness);
		this.inputContainer = inputContainer;
	}

	public void draw(Graphics g) {
		g.setColor(Color.BLACK);
		if (isMouseOver()) {
			this.setBorderCol(Color.BLACK);
		} else {
			this.setBorderCol(Color.WHITE);
		}
		
		super.draw(g);
		
		if (isMouseOver()) {
			int xMouse = UrfQuest.mousePos[0];
			int yMouse = UrfQuest.mousePos[1];
			g.setColor(Color.BLACK);
			g.drawString(getName(), xMouse, yMouse);
			inputContainer.addAllObjects(inputEntries);
		}
	}
	
	public void addInput(Item i) {
		InventoryEntry e = new InventoryEntry(GUIObject.TOP_LEFT, 
				  5 + input.size()*45, 
				  5, 
				  40, 
				  40, 
				  inputContainer, 
				  i);
		input.add(i);
		inputEntries.add(e);
	}
	
	public ArrayList<Item> getInput() {
		return input;
	}
	
	public void addOutput(Item i) {
		InventoryEntry e = new InventoryEntry(GUIObject.TOP_LEFT, 
				  5 + output.size()*45, 
				  5, 
				  40, 
				  40, 
				  this, 
				  i);
		output.add(i);
		guiObjects.add(e);
	}
	
	public ArrayList<Item> getOutput() {
		return output;
	}
	
	public boolean click() {
		System.out.println("crafting attempted");
		UrfQuest.game.getPlayer().tryCrafting(input, output);
		return true;
	}
}