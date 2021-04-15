package client.guis.game;

import java.awt.Color;
import java.awt.Graphics;

import entities.items.Item;
import framework.UrfQuest;
import guis.GUIContainer;
import guis.GUIObject;
import guis.menus.TextBox;

public class CraftingOverlay extends GUIContainer {
	private GUIContainer inputs;
	
	public CraftingOverlay() {
		super(GUIObject.TOP_LEFT, 
			  0, 
			  0, 
			  UrfQuest.panel.getWidth(), 
			  UrfQuest.panel.getHeight(), 
			  "crafting", 
			  null, 
			  new Color(128, 128, 128, 128), null, 0);
		
		// initiate crafting container
		GUIContainer craft = new GUIContainer(GUIObject.TOP_LEFT, 30, 30, 600, 400, 
											  "craftbox", this, 
											  new Color(255, 255, 255, 128), Color.WHITE, 3);
		
		// initiate input window
		inputs = new GUIContainer(GUIObject.BOTTOM_LEFT, 10, -60, 500, 50,
								  "inputWindow", craft,
								  new Color(255, 255, 255, 128), Color.WHITE, 3);
		
		// initiate hatchet recipe
		CraftingRecipie hatchet = new CraftingRecipie(GUIObject.TOP_LEFT, 10, 10, 50, 50, 
													  "hatchet", craft, 
													  new Color(255, 255, 255, 128), Color.WHITE, 3, inputs);
		hatchet.addInput(new Item(0, 0, 8, 3, -1, UrfQuest.game.getPlayer().getMap())); //logs
		hatchet.addInput(new Item(0, 0, 9, 2, -1, UrfQuest.game.getPlayer().getMap())); //stone
		hatchet.addOutput(new Item(0, 0, 18, UrfQuest.game.getPlayer().getMap())); //hatchet
		craft.addObject(hatchet);
		
		// initiate pickaxe recipe
		CraftingRecipie pickaxe = new CraftingRecipie(GUIObject.TOP_LEFT, 70, 10, 50, 50, 
				  									  "pickaxe", craft, 
				  									  new Color(255, 255, 255, 128), Color.WHITE, 3, inputs);
		pickaxe.addInput(new Item(0, 0, 8, 3, -1, UrfQuest.game.getPlayer().getMap())); //logs
		pickaxe.addInput(new Item(0, 0, 9, 2, -1, UrfQuest.game.getPlayer().getMap())); //stone
		pickaxe.addOutput(new Item(0, 0, 17, UrfQuest.game.getPlayer().getMap()));
		craft.addObject(pickaxe);
		
		// initiate shovel recipe
		CraftingRecipie shovel = new CraftingRecipie(GUIObject.TOP_LEFT, 130, 10, 50, 50, 
				  									  "shovel", craft, 
				  									  new Color(255, 255, 255, 128), Color.WHITE, 3, inputs);
		shovel.addInput(new Item(0, 0, 8, 2, -1, UrfQuest.game.getPlayer().getMap()));
		shovel.addInput(new Item(0, 0, 9, 3, -1, UrfQuest.game.getPlayer().getMap()));
		shovel.addOutput(new Item(0, 0, 19, UrfQuest.game.getPlayer().getMap()));
		craft.addObject(shovel);
		
		// initiate rpg recipe
		CraftingRecipie rpg = new CraftingRecipie(GUIObject.TOP_LEFT, 190, 10, 50, 50, 
				  									  "rpg", craft, 
				  									  new Color(255, 255, 255, 128), Color.WHITE, 3, inputs);
		rpg.addInput(new Item(0, 0, Item.GEM, 100, -1, UrfQuest.game.getPlayer().getMap()));
		rpg.addOutput(new Item(0, 0, Item.RPG, UrfQuest.game.getPlayer().getMap()));
		craft.addObject(rpg);
		
		// add the crafting container, input window, and inventory bar to this overlay
		guiObjects.add(craft);
		guiObjects.add(new TextBox("Inputs:", 15, 10, -75, GUIObject.BOTTOM_LEFT, craft));
		guiObjects.add(inputs);
		
		InventoryBar invBar = new InventoryBar(GUIObject.BOTTOM_LEFT, 3, -51 - 3, 40, this);
		guiObjects.add(invBar);
	}
	
	public void draw(Graphics g) {
		super.draw(g);
		inputs.removeAllObjects();
	}
}