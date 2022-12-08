package xyz.urffer.urfquest.server.map.generator;

import xyz.urffer.urfutils.math.PairInt;

import xyz.urffer.urfquest.server.map.MapChunk;
import xyz.urffer.urfquest.shared.Constants;
import xyz.urffer.urfquest.shared.Tile;
import xyz.urffer.urfquest.shared.protocol.types.ObjectType;
import xyz.urffer.urfquest.shared.protocol.types.TileType;

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
	
	public MapChunk generateChunk(PairInt chunkPos) {
		
		// get bottom-left corner coord offset
		PairInt rootOffset = chunkPos.multiply(Constants.MAP_CHUNK_SIZE);
		
		// instatiate new chunk
		MapChunk chunk = new MapChunk();
		
		// terrain distortion params
		boolean enableDistortion = true;
		double distortWeight = 0.25;
		boolean enableDistortionDistribution = false;
		
		// generate land and water
		for (int x = 0; x < Constants.MAP_CHUNK_SIZE; x++) {
			for (int y = 0; y < Constants.MAP_CHUNK_SIZE; y++) {
				PairInt pos = new PairInt(x, y);
				float terrainNoiseValue = terrainNoise.getNoiseAt(
					pos.add(rootOffset)
				);
				float distortionNoiseValue = distortionNoise.getNoiseAt(
					pos.add(rootOffset)
				);
				float distortionDistributionValue = distortionDistribution.getNoiseAt(
					pos.add(rootOffset)
				);
				if (enableDistortionDistribution && distortionDistributionValue > .5) {
					distortionNoiseValue *= (distortionDistributionValue - 0.5)*2;
				}
				if (enableDistortion) {
					terrainNoiseValue += distortionNoiseValue*distortWeight;
					terrainNoiseValue /= (1 + distortWeight);
				}
				
				if (terrainNoiseValue > .55f) {
					chunk.setTileAt(pos, new Tile(TileType.GRASS));
				} else if (terrainNoiseValue > .5f) {
					chunk.setTileAt(pos, new Tile(TileType.SAND));
				} else {
					chunk.setTileAt(pos, new Tile(TileType.WATER));
				}
			}
		}
		
		// generate boulders
		for (int x = 0; x < Constants.MAP_CHUNK_SIZE; x++) {
			for (int y = 0; y < Constants.MAP_CHUNK_SIZE; y++) {
				PairInt pos = new PairInt(x, y);
				if (boulderNoise.getNoiseAt(pos.add(rootOffset))*2 - 1.6 > random.nextDouble()) {
					chunk.setTileAt(pos, new Tile(chunk.getTileAt(pos).tileType, ObjectType.BOULDER));
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
				PairInt pos = new PairInt(x, y);
				if (flowerNoise.getNoiseAt(pos.add(rootOffset))*2 - 1.6 > random.nextDouble()) {
					if (chunk.getTileAt(pos).tileType == TileType.GRASS) {
						chunk.setTileAt(pos, new Tile(TileType.GRASS, ObjectType.FLOWERS));
					}
				}
			}
		}
		
		// generate trees (only on grass)
		for (int x = 0; x < Constants.MAP_CHUNK_SIZE; x++) {
			for (int y = 0; y < Constants.MAP_CHUNK_SIZE; y++) {
				PairInt pos = new PairInt(x, y);
				if (treeNoise.getNoiseAt(pos.add(rootOffset))*2 - 1 > random.nextDouble() && 
					chunk.getTileAt(pos).tileType == TileType.GRASS) {
					chunk.setTileAt(pos, new Tile(TileType.GRASS, ObjectType.TREE));
				}
			}
		}
		
		return chunk;
	}
}
