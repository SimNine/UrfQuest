package guis.game;

import java.awt.Color;
import java.awt.Graphics;

import framework.UrfQuest;
import guis.GUIObject;
import guis.Overlay;

public class GameStatusOverlay extends Overlay {
	private StatusBar healthBar;
	private StatusBar manaBar;
	private InventoryBar invBar;
	private Minimap minimap;
	private boolean minimapShowing = true;
	
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
					return UrfQuest.game.player.getMana()/100.0;
				}
		};
		healthBar = new StatusBar(spacing, -(spacing*2 + statusBarHeight + inventoryBarHeight), 
								  statusBarLength, statusBarHeight, 
								  GUIObject.BOTTOM_LEFT, 
								  new Color(255, 0, 0, 180), 
								  true) {
			public double getPercentage() {
				return UrfQuest.game.player.getHealth()/100.0;
			}
		};
		minimap = new Minimap(-100 - spacing, spacing, 100, 100, GUIObject.TOP_RIGHT, UrfQuest.game.getCurrMap());
		invBar = new InventoryBar(GUIObject.BOTTOM_LEFT, 3, -inventoryBarHeight - spacing, 40);
		
		guiObjects.add(manaBar);
		guiObjects.add(healthBar);
		guiObjects.add(minimap);
		guiObjects.add(invBar);
	}
	
	public void cycleMinimapSize() {
		int mSize;
		
		if (minimapShowing) {
			if (minimap.getSize() == 100) {
				mSize = 200;
			} else if (minimap.getSize() == 200) {
				mSize = 300;
			} else { //if (minimap.getSize() == 300) {
				mSize = 100;
				minimapShowing = false;
			}
			guiObjects.remove(minimap);
			minimap = new Minimap(-(mSize + spacing), spacing, mSize, mSize, GUIObject.TOP_RIGHT, UrfQuest.game.getCurrMap());
			guiObjects.add(minimap);
		} else {
			minimapShowing = true;
		}
	}
	
	public void draw(Graphics g) {
		if (minimapShowing) {
			minimap.draw(g);
		}
		healthBar.draw(g);
		manaBar.draw(g);
		invBar.draw(g);
	}
}
