package urfquest.client.guis.game;

import java.awt.Color;

import urfquest.client.Client;
import urfquest.client.guis.GUIAnchor;
import urfquest.client.guis.GUIContainer;
import urfquest.client.map.Map;
import urfquest.client.map.MapChunk;

public class MapViewOverlay extends GUIContainer {
	
	public MapViewOverlay(Client c) {
		super(c, 
			  GUIAnchor.TOP_LEFT, 
			  0, 
			  0, 
			  0, 
			  0, 
			  "map", 
			  null, new Color(128, 128, 128, 128), null, 0);
		
		// add the map
		Map map = this.client.getState().getCurrentMap();
		int mapDiameter = map.getMapDiameter() * MapChunk.CHUNK_SIZE;
		guiObjects.add(new Minimap(this.client, 
								   -mapDiameter/2, 
								   -mapDiameter/2, 
								   mapDiameter + 10, 
								   mapDiameter + 10, 
								   GUIAnchor.CENTER, this));
	}
}
