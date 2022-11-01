package xyz.urffer.urfquest.server.map.generator;

import xyz.urffer.urfquest.server.map.MapChunk;
import xyz.urffer.urfquest.shared.Constants;
import xyz.urffer.urfquest.shared.PairInt;
import xyz.urffer.urfquest.shared.Tile;
import xyz.urffer.urfquest.shared.protocol.types.ObjectType;
import xyz.urffer.urfquest.shared.protocol.types.TileType;

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
	
	public MapChunk generateChunk(PairInt chunkPos) {
		
		MapChunk chunk = new MapChunk();
		
		// terrain distortion params
		boolean enableDistortion = true;
		double distortWeight = 0.25;
		boolean enableDistortionDistribution = false;
		
		// generate accessible areas
		for (int x = 0; x < Constants.MAP_CHUNK_SIZE; x++) {
			for (int y = 0; y < Constants.MAP_CHUNK_SIZE; y++) {
				PairInt pos = new PairInt(x, y);
				
				float terrainNoiseValue = terrainNoise.getNoiseAt(pos);
				float distortionNoiseValue = distortionNoise.getNoiseAt(pos);
				float distortionDistributionValue = distortionDistribution.getNoiseAt(pos);
				if (enableDistortionDistribution && distortionDistributionValue > .5) {
					distortionNoiseValue *= (distortionDistributionValue - 0.5)*2;
				}
				if (enableDistortion) {
					terrainNoiseValue += distortionNoiseValue*distortWeight;
					terrainNoiseValue /= (1 + distortWeight);
				}
				
				if (terrainNoiseValue > .55f) {
					chunk.setTileAt(pos, new Tile(TileType.DIRT));
				} else if (terrainNoiseValue > .5f) {
					chunk.setTileAt(pos, new Tile(TileType.BEDROCK));
				} else {
					chunk.setTileAt(pos, new Tile(TileType.VOID));
				}
			}
		}
		
		// generate stone veins
		for (int x = 0; x < Constants.MAP_CHUNK_SIZE; x++) {
			for (int y = 0; y < Constants.MAP_CHUNK_SIZE; y++) {
				PairInt pos = new PairInt(x, y);
				if (stoneNoise.getNoiseAt(pos)*2 - 1 > random.nextDouble() && chunk.getTileAt(pos).tileType == TileType.DIRT) {
					chunk.setTileAt(pos, new Tile(TileType.DIRT, ObjectType.STONE));
				}
			}
		}
		
		// generate ore (only on stone)
		for (int x = 0; x < Constants.MAP_CHUNK_SIZE; x++) {
			for (int y = 0; y < Constants.MAP_CHUNK_SIZE; y++) {
				PairInt pos = new PairInt(x, y);
				if (oreNoise.getNoiseAt(pos) > 0.80f && chunk.getTileAt(pos).objectType == ObjectType.STONE) {
					chunk.setTileAt(pos, new Tile(TileType.BEDROCK, ObjectType.IRON_ORE));
				}
			}
		}
		
		return chunk;
	}
}
