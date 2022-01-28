package urfquest.server.map.generator;

import urfquest.server.map.MapChunk;
import urfquest.server.tiles.Tiles;
import urfquest.shared.Constants;

public class TerrainGeneratorTemplate extends TerrainGenerator {
	
	public MapChunk generateChunk(int xChunk, int yChunk) {
		
		MapChunk chunk = new MapChunk();
		
		for (int x = 0; x < Constants.MAP_CHUNK_SIZE; x++) {
			for (int y = 0; y < Constants.MAP_CHUNK_SIZE; y++) {
				chunk.setTileAt(x, y, Tiles.BEDROCK);
			}
		}
		for (int x = 1; x < Constants.MAP_CHUNK_SIZE - 1; x++) {
			for (int y = 1; y < Constants.MAP_CHUNK_SIZE - 1; y++) {
				if (Math.random() < .1) chunk.setTileAt(x, y, Tiles.GRASS);
			}
		}
		for (int x = 2; x < Constants.MAP_CHUNK_SIZE - 2; x++) {
			for (int y = 2; y < Constants.MAP_CHUNK_SIZE - 2; y++) {
				if (Math.random() < .2) chunk.setTileAt(x, y, Tiles.GRASS);
			}
		}
		for (int x = 3; x < Constants.MAP_CHUNK_SIZE - 3; x++) {
			for (int y = 3; y < Constants.MAP_CHUNK_SIZE - 3; y++) {
				if (Math.random() < .4) chunk.setTileAt(x, y, Tiles.GRASS);
			}
		}
		for (int x = 4; x < Constants.MAP_CHUNK_SIZE - 4; x++) {
			for (int y = 4; y < Constants.MAP_CHUNK_SIZE - 4; y++) {
				if (Math.random() < .9) chunk.setTileAt(x, y, Tiles.GRASS);
			}
		}
		for (int x = 5; x < Constants.MAP_CHUNK_SIZE - 5; x++) {
			for (int y = 5; y < Constants.MAP_CHUNK_SIZE - 5; y++) {
				chunk.setTileAt(x, y, Tiles.GRASS);
			}
		}
		
		return chunk;
	}
}
