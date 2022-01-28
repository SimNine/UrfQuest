package urfquest.server.map.generator;

import urfquest.server.map.MapChunk;
import urfquest.server.map.SimplexNoiseClass;
import urfquest.server.tiles.Tiles;
import urfquest.shared.Constants;

public class TerrainGeneratorSimplex extends TerrainGenerator {

	private SimplexNoiseClass terrainNoise;
	private SimplexNoiseClass distortionNoise;
	private SimplexNoiseClass distortionDistribution;
	
	private SimplexNoiseClass boulderNoise;
	private SimplexNoiseClass flowerNoise;
	private SimplexNoiseClass treeNoise;
	
	public TerrainGeneratorSimplex() {
		terrainNoise = new SimplexNoiseClass((float) 0.01);
		distortionNoise = new SimplexNoiseClass((float) 0.04);
		distortionDistribution = new SimplexNoiseClass((float) 0.04);

		boulderNoise = new SimplexNoiseClass((float) 0.04);
		flowerNoise = new SimplexNoiseClass((float) 0.04);
		treeNoise = new SimplexNoiseClass((float) 0.04);
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
					chunk.setTileAt(x, y, Tiles.GRASS);
				} else if (terrainNoiseValue > .5f) {
					chunk.setTileAt(x, y, Tiles.SAND);
				} else {
					chunk.setTileAt(x, y, Tiles.WATER);
				}
			}
		}
		
		// generate boulders
		for (int x = 0; x < Constants.MAP_CHUNK_SIZE; x++) {
			for (int y = 0; y < Constants.MAP_CHUNK_SIZE; y++) {
				if (boulderNoise.getNoiseAt(x + xRoot, y + yRoot)*2 - 1.6 > Math.random()) {
					if (chunk.getTileTypeAt(x, y) == Tiles.GRASS) {
						chunk.setTileAt(x, y, Tiles.BOULDER, Tiles.GRASS_BOULDER);
					} else if (chunk.getTileTypeAt(x, y) == Tiles.WATER) {
						chunk.setTileAt(x, y, Tiles.BOULDER, Tiles.WATER_BOULDER);
					} else if (chunk.getTileTypeAt(x, y) == Tiles.SAND) {
						chunk.setTileAt(x, y, Tiles.BOULDER, Tiles.SAND_BOULDER);
					}
				}
			}
		}
		
		// generate flowers
		for (int x = 0; x < Constants.MAP_CHUNK_SIZE; x++) {
			for (int y = 0; y < Constants.MAP_CHUNK_SIZE; y++) {
				if (flowerNoise.getNoiseAt(x + xRoot, y + yRoot)*2 - 1.6 > Math.random()) {
					if (chunk.getTileTypeAt(x, y) == Tiles.GRASS) {
						chunk.setTileAt(x, y, Tiles.GRASS, Tiles.GRASS_FLOWERS);
					}
				}
			}
		}
		
		// generate trees (only on land tiles)
		for (int x = 0; x < Constants.MAP_CHUNK_SIZE; x++) {
			for (int y = 0; y < Constants.MAP_CHUNK_SIZE; y++) {
				if (treeNoise.getNoiseAt(x + xRoot, y + yRoot)*2 - 1 > Math.random() && chunk.getTileTypeAt(x, y) == Tiles.GRASS) {
					chunk.setTileAt(x, y, Tiles.TREE);
				}
			}
		}
		
		return chunk;
	}
}
