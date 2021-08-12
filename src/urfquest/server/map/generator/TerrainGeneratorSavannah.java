package urfquest.server.map.generator;

import urfquest.server.map.MapChunk;
import urfquest.server.tiles.Tiles;

public class TerrainGeneratorSavannah extends TerrainGenerator {
	
	public MapChunk generateChunk(int xChunk, int yChunk) {
		
		MapChunk chunk = new MapChunk();
		
		for (int x = 0; x < MapChunk.CHUNK_SIZE; x++) {
			for (int y = 0; y < MapChunk.CHUNK_SIZE; y++) {
				chunk.setTileAt(x, y, Tiles.BEDROCK);
			}
		}
		for (int x = 1; x < MapChunk.CHUNK_SIZE - 1; x++) {
			for (int y = 1; y < MapChunk.CHUNK_SIZE - 1; y++) {
				if (Math.random() < .1) chunk.setTileAt(x, y, Tiles.DIRT);
			}
		}
		for (int x = 2; x < MapChunk.CHUNK_SIZE - 2; x++) {
			for (int y = 2; y < MapChunk.CHUNK_SIZE - 2; y++) {
				if (Math.random() < .2) chunk.setTileAt(x, y, Tiles.DIRT);
			}
		}
		for (int x = 3; x < MapChunk.CHUNK_SIZE - 3; x++) {
			for (int y = 3; y < MapChunk.CHUNK_SIZE - 3; y++) {
				if (Math.random() < .4) chunk.setTileAt(x, y, Tiles.DIRT);
			}
		}
		for (int x = 4; x < MapChunk.CHUNK_SIZE - 4; x++) {
			for (int y = 4; y < MapChunk.CHUNK_SIZE - 4; y++) {
				if (Math.random() < .9) chunk.setTileAt(x, y, Tiles.DIRT);
			}
		}
		for (int x = 5; x < MapChunk.CHUNK_SIZE - 5; x++) {
			for (int y = 5; y < MapChunk.CHUNK_SIZE - 5; y++) {
				if (Math.random() < .1) chunk.setTileAt(x, y, Tiles.DIRT_BOULDER);
				else chunk.setTileAt(x, y, Tiles.DIRT);
			}
		}
		
		return chunk;
	}
}
