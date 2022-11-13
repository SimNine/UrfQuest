package xyz.urffer.urfquest.client.guis.game;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashSet;

import xyz.urffer.urfquest.client.Client;
import xyz.urffer.urfquest.client.entities.items.ItemStack;
import xyz.urffer.urfquest.client.guis.GUIAnchor;
import xyz.urffer.urfquest.client.guis.GUIContainer;
import xyz.urffer.urfquest.client.guis.GUIObject;

public class CraftingRecipie extends GUIContainer {
	private ArrayList<ItemStack> input = new ArrayList<ItemStack>();
	private ArrayList<ItemStack> output = new ArrayList<ItemStack>();
	
	private HashSet<GUIObject> inputEntries = new HashSet<GUIObject>();
	private GUIContainer inputContainer;

	public CraftingRecipie(Client c, GUIAnchor anchorPoint, int xRel, int yRel, int width, int height, String name,
			GUIObject parent, Color bkg, Color borderColor, int borderThickness, GUIContainer inputContainer) {
		super(c, anchorPoint, xRel, yRel, width, height, name, parent, bkg, borderColor, borderThickness);
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
			int xMouse = client.getPanel().mousePos.x;
			int yMouse = client.getPanel().mousePos.y;
			g.setColor(Color.BLACK);
			g.drawString(getName(), xMouse, yMouse);
			inputContainer.addAllObjects(inputEntries);
		}
	}
	
	public void addInput(ItemStack i) {
		InventoryEntry e = new InventoryEntry(this.client, 
				  GUIAnchor.TOP_LEFT, 
				  5 + input.size()*45, 
				  5, 
				  40, 
				  40, 
				  inputContainer, i);
		input.add(i);
		inputEntries.add(e);
	}
	
	public ArrayList<ItemStack> getInput() {
		return input;
	}
	
	public void addOutput(ItemStack i) {
		InventoryEntry e = new InventoryEntry(this.client, 
				  GUIAnchor.TOP_LEFT, 
				  5 + output.size()*45, 
				  5, 
				  40, 
				  40, 
				  this, i);
		output.add(i);
		guiObjects.add(e);
	}
	
	public ArrayList<ItemStack> getOutput() {
		return output;
	}
	
	public boolean click() {
		this.client.getLogger().debug("crafting attempted");
		this.client.getState().getPlayer().tryCrafting(input, output);
		return true;
	}
}
