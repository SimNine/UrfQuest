package xyz.urffer.urfquest.server.map.populator;

import xyz.urffer.urfquest.server.Server;
import xyz.urffer.urfquest.server.map.Map;
import xyz.urffer.urfquest.server.map.structures.Structure;

public abstract class TerrainPopulator {
	
	protected Server server;
	
	public TerrainPopulator(Server s) {
		this.server = s;
	}
	
	public abstract Structure populateChunk(Map map, int xChunk, int yChunk);
	
	public abstract void generateStructure(Map map, int[] pos, int[] dims);
	
}
