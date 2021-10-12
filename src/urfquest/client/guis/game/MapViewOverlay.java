package urfquest.client.guis.game;

import java.awt.Color;

import urfquest.Main;
import urfquest.client.guis.GUIContainer;
import urfquest.client.guis.GUIObject;
import urfquest.client.map.Map;
import urfquest.client.map.MapChunk;

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
		int mapDiameter = map.getMapDiameter() * MapChunk.CHUNK_SIZE;
		guiObjects.add(new Minimap(-mapDiameter/2, 
								   -mapDiameter/2, 
								   mapDiameter + 10, 
								   mapDiameter + 10, 
								   GUIObject.CENTER, 
								   this));
	}
}
