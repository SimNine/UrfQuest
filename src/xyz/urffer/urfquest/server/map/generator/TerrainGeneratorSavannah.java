package xyz.urffer.urfquest.server.map.generator;

import xyz.urffer.urfutils.math.PairInt;

import xyz.urffer.urfquest.server.map.MapChunk;
import xyz.urffer.urfquest.shared.Constants;
import xyz.urffer.urfquest.shared.Tile;
import xyz.urffer.urfquest.shared.protocol.types.ObjectType;
import xyz.urffer.urfquest.shared.protocol.types.TileType;

public class TerrainGeneratorSavannah extends TerrainGenerator {
	
	public TerrainGeneratorSavannah(long seed) {
		super(seed);
	}

	public MapChunk generateChunk(PairInt chunkPos) {
		
		MapChunk chunk = new MapChunk();
		
		for (int x = 0; x < Constants.MAP_CHUNK_SIZE; x++) {
			for (int y = 0; y < Constants.MAP_CHUNK_SIZE; y++) {
				PairInt pos = new PairInt(x, y);
				chunk.setTileAt(pos, new Tile(TileType.BEDROCK));
			}
		}
		for (int x = 1; x < Constants.MAP_CHUNK_SIZE - 1; x++) {
			for (int y = 1; y < Constants.MAP_CHUNK_SIZE - 1; y++) {
				PairInt pos = new PairInt(x, y);
				if (random.nextDouble() < .1) chunk.setTileAt(pos, new Tile(TileType.DIRT));
			}
		}
		for (int x = 2; x < Constants.MAP_CHUNK_SIZE - 2; x++) {
			for (int y = 2; y < Constants.MAP_CHUNK_SIZE - 2; y++) {
				PairInt pos = new PairInt(x, y);
				if (random.nextDouble() < .2) chunk.setTileAt(pos, new Tile(TileType.DIRT));
			}
		}
		for (int x = 3; x < Constants.MAP_CHUNK_SIZE - 3; x++) {
			for (int y = 3; y < Constants.MAP_CHUNK_SIZE - 3; y++) {
				PairInt pos = new PairInt(x, y);
				if (random.nextDouble() < .4) chunk.setTileAt(pos, new Tile(TileType.DIRT));
			}
		}
		for (int x = 4; x < Constants.MAP_CHUNK_SIZE - 4; x++) {
			for (int y = 4; y < Constants.MAP_CHUNK_SIZE - 4; y++) {
				PairInt pos = new PairInt(x, y);
				if (random.nextDouble() < .9) chunk.setTileAt(pos, new Tile(TileType.DIRT));
			}
		}
		for (int x = 5; x < Constants.MAP_CHUNK_SIZE - 5; x++) {
			for (int y = 5; y < Constants.MAP_CHUNK_SIZE - 5; y++) {
				PairInt pos = new PairInt(x, y);
				if (random.nextDouble() < .1) {
					chunk.setTileAt(pos, new Tile(TileType.DIRT, ObjectType.BOULDER));
				} else {
					chunk.setTileAt(pos, new Tile(TileType.DIRT));
				}
			}
		}
		
		return chunk;
	}
}
