package guis.game;

import java.awt.Color;

import framework.UrfQuest;
import game.QuestMap;
import guis.GUIObject;
import guis.Overlay;

public class MapViewOverlay extends Overlay {
	
	public MapViewOverlay() {
		super("map", new Color(128, 128, 128, 128));
		
		// add the map
		QuestMap map = UrfQuest.game.getCurrMap();
		guiObjects.add(new Minimap(-map.getWidth()/2, -map.getHeight()/2, map.getWidth() + 10, map.getHeight() + 10, GUIObject.CENTER));
	}
}