package urfquest.server.map.generator;

import urfquest.server.map.MapChunk;
import urfquest.server.tiles.Tiles;
import urfquest.shared.Constants;

public class TerrainGeneratorCaves extends TerrainGenerator {

	private SimplexNoiseClass terrainNoise;
	private SimplexNoiseClass distortionNoise;
	private SimplexNoiseClass distortionDistribution;
	
	private SimplexNoiseClass stoneNoise;
	private SimplexNoiseClass oreNoise;
	
	public TerrainGeneratorCaves(long seed) {
		super(seed);
		
		terrainNoise = new SimplexNoiseClass(seed, (float) 0.01);
		distortionNoise = new SimplexNoiseClass(seed, (float) 0.04);
		distortionDistribution = new SimplexNoiseClass(seed, (float) 0.04);

		stoneNoise = new SimplexNoiseClass(seed, (float) 0.04);
		oreNoise = new SimplexNoiseClass(seed, (float) 0.04);
	}
	
	public MapChunk generateChunk(int xChunk, int yChunk) {
		
		MapChunk chunk = new MapChunk();
		
		// terrain distortion params
		boolean enableDistortion = true;
		double distortWeight = 0.25;
		boolean enableDistortionDistribution = false;
		
		// generate accessible areas
		for (int x = 0; x < Constants.MAP_CHUNK_SIZE; x++) {
			for (int y = 0; y < Constants.MAP_CHUNK_SIZE; y++) {
				float terrainNoiseValue = terrainNoise.getNoiseAt(x, y);
				float distortionNoiseValue = distortionNoise.getNoiseAt(x, y);
				float distortionDistributionValue = distortionDistribution.getNoiseAt(x, y);
				if (enableDistortionDistribution && distortionDistributionValue > .5) {
					distortionNoiseValue *= (distortionDistributionValue - 0.5)*2;
				}
				if (enableDistortion) {
					terrainNoiseValue += distortionNoiseValue*distortWeight;
					terrainNoiseValue /= (1 + distortWeight);
				}
				
				if (terrainNoiseValue > .55f) {
					chunk.setTileAt(x, y, Tiles.DIRT);
				} else if (terrainNoiseValue > .5f) {
					chunk.setTileAt(x, y, Tiles.BEDROCK);
				} else {
					chunk.setTileAt(x, y, Tiles.VOID);
				}
			}
		}
		
		// generate stone veins
		for (int x = 0; x < Constants.MAP_CHUNK_SIZE; x++) {
			for (int y = 0; y < Constants.MAP_CHUNK_SIZE; y++) {
				if (stoneNoise.getNoiseAt(x, y)*2 - 1 > random.nextDouble() && chunk.getTileTypeAt(x, y) == Tiles.DIRT) {
					chunk.setTileAt(x, y, Tiles.STONE);
				}
			}
		}
		
		// generate ore (only on stone)
		for (int x = 0; x < Constants.MAP_CHUNK_SIZE; x++) {
			for (int y = 0; y < Constants.MAP_CHUNK_SIZE; y++) {
				if (oreNoise.getNoiseAt(x, y) > 0.80f && chunk.getTileTypeAt(x, y) == Tiles.STONE) {
					chunk.setTileAt(x, y, Tiles.STONE, Tiles.IRONORE_STONE);
				}
			}
		}
		
		return chunk;
	}
}
