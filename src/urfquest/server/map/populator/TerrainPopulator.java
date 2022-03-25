package urfquest.server.map.populator;

import urfquest.server.Server;
import urfquest.server.map.Map;
import urfquest.server.map.structures.Structure;

public abstract class TerrainPopulator {
	
	protected Server server;
	
	public TerrainPopulator(Server s) {
		this.server = s;
	}
	
	public abstract Structure populateChunk(Map map, int xChunk, int yChunk);
	
	public abstract void generateStructure(Map map, int[] pos, int[] dims);
	
}
