package guis.game;

import java.awt.Color;

import entities.items.Hatchet;
import entities.items.Log;
import entities.items.Pickaxe;
import entities.items.Stone;
import framework.UrfQuest;
import guis.GUIContainer;
import guis.GUIObject;

public class CraftingOverlay extends GUIContainer {
	
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
		
		// initiate hatchet recipie
		CraftingRecipie hatchet = new CraftingRecipie(GUIObject.TOP_LEFT, 10, 10, 100, 100, 
													  "hatchet", craft, 
													  new Color(255, 255, 255, 128), Color.WHITE, 3);
		
		Log hatchetLog = new Log(0, 0);
		hatchetLog.setStackSize(3);
		hatchet.addInput(hatchetLog);
		
		Stone hatchetStone = new Stone(0, 0);
		hatchetStone.setStackSize(2);
		hatchet.addInput(hatchetStone);
		
		Hatchet hatch = new Hatchet(0, 0);
		hatchet.addOutput(hatch);
		craft.addObject(hatchet);
		
		// initiate pickaxe recipie
		CraftingRecipie pickaxe = new CraftingRecipie(GUIObject.TOP_LEFT, 120, 10, 100, 100, 
				  									  "pickaxe", craft, 
				  									  new Color(255, 255, 255, 128), Color.WHITE, 3);
		
		Log pickaxeLog = new Log(0, 0);
		pickaxeLog.setStackSize(3);
		pickaxe.addInput(pickaxeLog);
		
		Stone pickaxeStone = new Stone(0, 0);
		pickaxeStone.setStackSize(2);
		pickaxe.addInput(pickaxeStone);
		
		Pickaxe pick = new Pickaxe(0, 0);
		pickaxe.addOutput(pick);
		craft.addObject(pickaxe);
		
		// add the crafting container and the inventory bar to this overlay
		guiObjects.add(craft);
		InventoryBar invBar = new InventoryBar(GUIObject.BOTTOM_LEFT, 3, -51 - 3, 40, this);
		guiObjects.add(invBar);
	}
}