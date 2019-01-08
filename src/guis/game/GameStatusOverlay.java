package guis.game;

import java.awt.Color;

import framework.UrfQuest;
import guis.GUIObject;
import guis.Overlay;
//import guis.menus.ImageBox;

public class GameStatusOverlay extends Overlay {
	private StatusBar healthBar;
	private StatusBar manaBar;
	private StatusBar fullnessBar;
	//private ImageBox fullnessIcon;
	private InventoryBar invBar;
	private Minimap minimap;
	
	private int spacing = 3;
	private int statusBarLength = 400;
	private int statusBarHeight = 20;
	private int inventoryBarHeight = 51;

	public GameStatusOverlay() {
		super("status");

		manaBar = new StatusBar(spacing, -(spacing*3 + statusBarHeight*2 + inventoryBarHeight), 
								statusBarLength, statusBarHeight, 
								GUIObject.BOTTOM_LEFT, 
								new Color(0, 0, 255, 180), 
								true) {
				public double getPercentage() {
					return UrfQuest.game.getPlayer().getMana()/100.0;
				}
		};
		healthBar = new StatusBar(spacing, -(spacing*2 + statusBarHeight + inventoryBarHeight), 
								  statusBarLength, statusBarHeight, 
								  GUIObject.BOTTOM_LEFT, 
								  new Color(255, 0, 0, 180), 
								  true) {
			public double getPercentage() {
				return UrfQuest.game.getPlayer().getHealth()/100.0;
			}
		};
		fullnessBar = new StatusBar(spacing, -(spacing*4 + statusBarHeight*3 + inventoryBarHeight), 
				  				  statusBarLength, statusBarHeight, 
				  				  GUIObject.BOTTOM_LEFT, 
				  				  new Color(255, 255, 255, 180), 
				  				  true) {
			public double getPercentage() {
				return UrfQuest.game.getPlayer().getFullness()/100.0;
			}
		};
//		fullnessIcon = new ImageBox("bin/assets/guis/star_white_40px.png", 
//									200, 
//									200,
//									GUIObject.TOP_LEFT);
		minimap = new Minimap(-100 - spacing, spacing, 100, 100, GUIObject.TOP_RIGHT);
		invBar = new InventoryBar(GUIObject.BOTTOM_LEFT, 3, -inventoryBarHeight - spacing, 40);
		
		guiObjects.add(fullnessBar);
		//guiObjects.add(fullnessIcon);
		guiObjects.add(manaBar);
		guiObjects.add(healthBar);
		guiObjects.add(minimap);
		guiObjects.add(invBar);
	}
	
	public void cycleMinimapSize() {
		if (!guiObjects.contains(minimap)) {
			minimap = new Minimap(-(100 + spacing), spacing, 100, 100, GUIObject.TOP_RIGHT);
			guiObjects.add(minimap);
			return;
		}
		
		guiObjects.remove(minimap);
		if (minimap.getSize() == 100) {
			minimap = new Minimap(-(200 + spacing), spacing, 200, 200, GUIObject.TOP_RIGHT);
			guiObjects.add(minimap);
		} else if (minimap.getSize() == 200) {
			minimap = new Minimap(-(300 + spacing), spacing, 300, 300, GUIObject.TOP_RIGHT);
			guiObjects.add(minimap);
		} else { //if (minimap.getSize() == 300) {
			// don't re-add the minimap
		}
	}
}
