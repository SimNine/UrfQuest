package urfquest.server.map.generator;

import urfquest.server.map.MapChunk;

public abstract class TerrainGenerator {
	
	public abstract MapChunk generateChunk(int xChunk, int yChunk);
	
}
