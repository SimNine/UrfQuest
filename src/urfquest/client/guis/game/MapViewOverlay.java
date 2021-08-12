package urfquest.client.guis.game;

import java.awt.Color;

import urfquest.Main;
import urfquest.client.guis.GUIContainer;
import urfquest.client.guis.GUIObject;
import urfquest.client.map.Map;

public class MapViewOverlay extends GUIContainer {
	
	public MapViewOverlay() {
		super(GUIObject.TOP_LEFT, 
			  0, 
			  0, 
			  Main.panel.getWidth(), 
			  Main.panel.getHeight(), 
			  "map", 
			  null, 
			  new Color(128, 128, 128, 128), null, 0);
		
		// add the map
		Map map = Main.client.getState().getCurrentMap();
//		guiObjects.add(new Minimap(-map.getWidth()/2, 
//								   -map.getHeight()/2, 
//								   map.getWidth() + 10, 
//								   map.getHeight() + 10, 
//								   GUIObject.CENTER, 
//								   this));
	}
}