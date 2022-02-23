package urfquest.server.map.generator;

import urfquest.server.map.MapChunk;
import urfquest.shared.Constants;
import urfquest.shared.Tile;

public class TerrainGeneratorSimplex extends TerrainGenerator {

	private SimplexNoiseClass terrainNoise;
	private SimplexNoiseClass distortionNoise;
	private SimplexNoiseClass distortionDistribution;
	
	private SimplexNoiseClass boulderNoise;
	private SimplexNoiseClass flowerNoise;
	private SimplexNoiseClass treeNoise;
	
	public TerrainGeneratorSimplex(long seed) {
		super(seed);
		
		terrainNoise = new SimplexNoiseClass(seed, (float) 0.01);
		distortionNoise = new SimplexNoiseClass(seed, (float) 0.04);
		distortionDistribution = new SimplexNoiseClass(seed, (float) 0.04);

		boulderNoise = new SimplexNoiseClass(seed, (float) 0.04);
		flowerNoise = new SimplexNoiseClass(seed, (float) 0.04);
		treeNoise = new SimplexNoiseClass(seed, (float) 0.04);
	}
	
	public MapChunk generateChunk(int xChunk, int yChunk) {
		
		// get bottom-left corner coord offset
		int xRoot = xChunk * Constants.MAP_CHUNK_SIZE;
		int yRoot = yChunk * Constants.MAP_CHUNK_SIZE;
		
		// instatiate new chunk
		MapChunk chunk = new MapChunk();
		
		// terrain distortion params
		boolean enableDistortion = true;
		double distortWeight = 0.25;
		boolean enableDistortionDistribution = false;
		
		// generate land and water
		for (int x = 0; x < Constants.MAP_CHUNK_SIZE; x++) {
			for (int y = 0; y < Constants.MAP_CHUNK_SIZE; y++) {
				float terrainNoiseValue = terrainNoise.getNoiseAt(x + xRoot, y + yRoot);
				float distortionNoiseValue = distortionNoise.getNoiseAt(x + xRoot, y + yRoot);
				float distortionDistributionValue = distortionDistribution.getNoiseAt(x + xRoot, y + yRoot);
				if (enableDistortionDistribution && distortionDistributionValue > .5) {
					distortionNoiseValue *= (distortionDistributionValue - 0.5)*2;
				}
				if (enableDistortion) {
					terrainNoiseValue += distortionNoiseValue*distortWeight;
					terrainNoiseValue /= (1 + distortWeight);
				}
				
				if (terrainNoiseValue > .55f) {
					chunk.setTileTypeAt(x, y, Tile.TILE_GRASS);
				} else if (terrainNoiseValue > .5f) {
					chunk.setTileTypeAt(x, y, Tile.TILE_SAND);
				} else {
					chunk.setTileTypeAt(x, y, Tile.TILE_WATER);
				}
			}
		}
		
		// generate boulders
		for (int x = 0; x < Constants.MAP_CHUNK_SIZE; x++) {
			for (int y = 0; y < Constants.MAP_CHUNK_SIZE; y++) {
				if (boulderNoise.getNoiseAt(x + xRoot, y + yRoot)*2 - 1.6 > random.nextDouble()) {
					chunk.setObjectTypeAt(x, y, Tile.OBJECT_BOULDER);
//					if (chunk.getTileTypeAt(x, y) == Tile.TILE_GRASS) {
//						chunk.setTileAt(x, y, Tile.TILE_GRASS, Tile.OBJECT_BOULDER);
//					} else if (chunk.getTileTypeAt(x, y) == Tile.TILE_WATER) {
//						chunk.setTileAt(x, y, Tile.TILE_WATER, Tile.OBJECT_BOULDER);
//					} else if (chunk.getTileTypeAt(x, y) == Tile.TILE_SAND) {
//						chunk.setTileAt(x, y, Tile.TILE_SAND, Tile.OBJECT_BOULDER);
//					}
				}
			}
		}
		
		// generate flowers (only on grass)
		for (int x = 0; x < Constants.MAP_CHUNK_SIZE; x++) {
			for (int y = 0; y < Constants.MAP_CHUNK_SIZE; y++) {
				if (flowerNoise.getNoiseAt(x + xRoot, y + yRoot)*2 - 1.6 > random.nextDouble()) {
					if (chunk.getTileTypeAt(x, y) == Tile.TILE_GRASS) {
						chunk.setObjectTypeAt(x, y, Tile.OBJECT_FLOWERS);
					}
				}
			}
		}
		
		// generate trees (only on grass)
		for (int x = 0; x < Constants.MAP_CHUNK_SIZE; x++) {
			for (int y = 0; y < Constants.MAP_CHUNK_SIZE; y++) {
				if (treeNoise.getNoiseAt(x + xRoot, y + yRoot)*2 - 1 > random.nextDouble() && chunk.getTileTypeAt(x, y) == Tile.TILE_GRASS) {
					chunk.setObjectTypeAt(x, y, Tile.OBJECT_TREE);
				}
			}
		}
		
		return chunk;
	}
}
