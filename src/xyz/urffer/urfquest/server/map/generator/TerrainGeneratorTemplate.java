package xyz.urffer.urfquest.server.map.generator;

import xyz.urffer.urfquest.server.map.MapChunk;
import xyz.urffer.urfquest.shared.Constants;
import xyz.urffer.urfquest.shared.Tile;

public class TerrainGeneratorTemplate extends TerrainGenerator {
	
	public TerrainGeneratorTemplate(long seed) {
		super(seed);
	}

	public MapChunk generateChunk(int xChunk, int yChunk) {
		
		MapChunk chunk = new MapChunk();
		
		for (int x = 0; x < Constants.MAP_CHUNK_SIZE; x++) {
			for (int y = 0; y < Constants.MAP_CHUNK_SIZE; y++) {
				chunk.setTileTypeAt(x, y, Tile.TILE_BEDROCK);
			}
		}
		for (int x = 1; x < Constants.MAP_CHUNK_SIZE - 1; x++) {
			for (int y = 1; y < Constants.MAP_CHUNK_SIZE - 1; y++) {
				if (random.nextDouble() < .1) chunk.setTileTypeAt(x, y, Tile.TILE_GRASS);
			}
		}
		for (int x = 2; x < Constants.MAP_CHUNK_SIZE - 2; x++) {
			for (int y = 2; y < Constants.MAP_CHUNK_SIZE - 2; y++) {
				if (random.nextDouble() < .2) chunk.setTileTypeAt(x, y, Tile.TILE_GRASS);
			}
		}
		for (int x = 3; x < Constants.MAP_CHUNK_SIZE - 3; x++) {
			for (int y = 3; y < Constants.MAP_CHUNK_SIZE - 3; y++) {
				if (random.nextDouble() < .4) chunk.setTileTypeAt(x, y, Tile.TILE_GRASS);
			}
		}
		for (int x = 4; x < Constants.MAP_CHUNK_SIZE - 4; x++) {
			for (int y = 4; y < Constants.MAP_CHUNK_SIZE - 4; y++) {
				if (random.nextDouble() < .9) chunk.setTileTypeAt(x, y, Tile.TILE_GRASS);
			}
		}
		for (int x = 5; x < Constants.MAP_CHUNK_SIZE - 5; x++) {
			for (int y = 5; y < Constants.MAP_CHUNK_SIZE - 5; y++) {
				chunk.setTileTypeAt(x, y, Tile.TILE_GRASS);
			}
		}
		
		return chunk;
	}
}
