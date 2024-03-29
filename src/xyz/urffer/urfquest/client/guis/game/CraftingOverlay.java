package xyz.urffer.urfquest.client.guis.game;

import java.awt.Color;
import java.awt.Graphics;

import xyz.urffer.urfquest.client.Client;
import xyz.urffer.urfquest.client.entities.items.ItemStack;
import xyz.urffer.urfquest.client.guis.GUIAnchor;
import xyz.urffer.urfquest.client.guis.GUIContainer;
import xyz.urffer.urfquest.client.guis.menus.TextBox;

public class CraftingOverlay extends GUIContainer {
	private GUIContainer inputs;
	
	public CraftingOverlay(Client c) {
		super(c, 
			  GUIAnchor.TOP_LEFT, 
			  0, 
			  0, 
			  0, 
			  0, 
			  "crafting", 
			  null, new Color(128, 128, 128, 128), null, 0);
//		
//		// initiate crafting container
//		GUIContainer craft = new GUIContainer(c, GUIAnchor.TOP_LEFT, 30, 30, 600, 
//											  400, "craftbox", 
//											  this, new Color(255, 255, 255, 128), Color.WHITE, 3);
//		
//		// initiate input window
//		inputs = new GUIContainer(c, GUIAnchor.BOTTOM_LEFT, 10, -60, 500,
//								  50, "inputWindow",
//								  craft, new Color(255, 255, 255, 128), Color.WHITE, 3);
//		
//		// initiate hatchet recipe
//		CraftingRecipie hatchet = new CraftingRecipie(this.client, GUIAnchor.TOP_LEFT, 10, 10, 50, 
//													  50, "hatchet", 
//													  craft, new Color(255, 255, 255, 128), Color.WHITE, 3, inputs);
//		hatchet.addInput(new Item(this.client, 0, this.client.getState().getPlayer().getMap(), 0, 0, 8, 3, -1)); //logs
//		hatchet.addInput(new Item(this.client, 0, this.client.getState().getPlayer().getMap(), 0, 0, 9, 2, -1)); //stone
//		hatchet.addOutput(new Item(this.client, 0, this.client.getState().getPlayer().getMap(), 0, 0, 18)); //hatchet
//		craft.addObject(hatchet);
//		
//		// initiate pickaxe recipe
//		CraftingRecipie pickaxe = new CraftingRecipie(this.client, GUIAnchor.TOP_LEFT, 70, 10, 50, 
//				  									  50, "pickaxe", 
//				  									  craft, new Color(255, 255, 255, 128), Color.WHITE, 3, inputs);
//		pickaxe.addInput(new Item(this.client, 0, this.client.getState().getPlayer().getMap(), 0, 0, 8, 3, -1)); //logs
//		pickaxe.addInput(new Item(this.client, 0, this.client.getState().getPlayer().getMap(), 0, 0, 9, 2, -1)); //stone
//		pickaxe.addOutput(new Item(this.client, 0, this.client.getState().getPlayer().getMap(), 0, 0, 17));
//		craft.addObject(pickaxe);
//		
//		// initiate shovel recipe
//		CraftingRecipie shovel = new CraftingRecipie(this.client, GUIAnchor.TOP_LEFT, 130, 10, 50, 
//				  									  50, "shovel", 
//				  									  craft, new Color(255, 255, 255, 128), Color.WHITE, 3, inputs);
//		shovel.addInput(new Item(this.client, 0, this.client.getState().getPlayer().getMap(), 0, 0, 8, 2, -1));
//		shovel.addInput(new Item(this.client, 0, this.client.getState().getPlayer().getMap(), 0, 0, 9, 3, -1));
//		shovel.addOutput(new Item(this.client, 0, this.client.getState().getPlayer().getMap(), 0, 0, 19));
//		craft.addObject(shovel);
//		
//		// initiate rpg recipe
//		CraftingRecipie rpg = new CraftingRecipie(this.client, GUIAnchor.TOP_LEFT, 190, 10, 50, 
//				  									  50, "rpg", 
//				  									  craft, new Color(255, 255, 255, 128), Color.WHITE, 3, inputs);
//		rpg.addInput(new Item(this.client, 0, this.client.getState().getPlayer().getMap(), 0, 0, Item.GEM, 100, -1));
//		rpg.addOutput(new Item(this.client, 0, this.client.getState().getPlayer().getMap(), 0, 0, Item.RPG));
//		craft.addObject(rpg);
//		
//		// add the crafting container, input window, and inventory bar to this overlay
//		guiObjects.add(craft);
//		guiObjects.add(new TextBox(c, "Inputs:", 15, 10, -75, GUIAnchor.BOTTOM_LEFT, craft));
//		guiObjects.add(inputs);
//		
//		InventoryBar invBar = new InventoryBar(this.client, GUIAnchor.BOTTOM_LEFT, 3, -51 - 3, 40, this);
//		guiObjects.add(invBar);
	}
	
	public void draw(Graphics g) {
		super.draw(g);
		//inputs.removeAllObjects();
	}
}
