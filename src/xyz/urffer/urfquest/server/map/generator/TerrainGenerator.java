package xyz.urffer.urfquest.server.map.generator;

import java.util.Random;

import xyz.urffer.urfquest.server.map.MapChunk;
import xyz.urffer.urfquest.shared.PairInt;

public abstract class TerrainGenerator {
	
	protected Random random;
	
	public TerrainGenerator(long seed) {
		this.random = new Random(seed);
	}
	
	public abstract MapChunk generateChunk(PairInt chunkPos);
	
}
