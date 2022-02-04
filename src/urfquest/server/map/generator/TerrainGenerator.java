package urfquest.server.map.generator;

import java.util.Random;

import urfquest.server.map.MapChunk;

public abstract class TerrainGenerator {
	
	protected Random random;
	
	public TerrainGenerator(long seed) {
		this.random = new Random(seed);
	}
	
	public abstract MapChunk generateChunk(int xChunk, int yChunk);
	
}
