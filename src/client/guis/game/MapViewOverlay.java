package client.guis.game;

import java.awt.Color;

import framework.UrfQuest;
import guis.GUIContainer;
import guis.GUIObject;
import server.game.QuestMap;

public class MapViewOverlay extends GUIContainer {
	
	public MapViewOverlay() {
		super(GUIObject.TOP_LEFT, 
			  0, 
			  0, 
			  UrfQuest.panel.getWidth(), 
			  UrfQuest.panel.getHeight(), 
			  "map", 
			  null, 
			  new Color(128, 128, 128, 128), null, 0);
		
		// add the map
		QuestMap map = UrfQuest.game.getCurrMap();
		guiObjects.add(new Minimap(-map.getWidth()/2, 
								   -map.getHeight()/2, 
								   map.getWidth() + 10, 
								   map.getHeight() + 10, 
								   GUIObject.CENTER, 
								   this));
	}
}