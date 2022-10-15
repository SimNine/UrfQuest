package xyz.urffer.urfquest.testing.server.map;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import xyz.urffer.urfquest.server.map.MapChunk;
import xyz.urffer.urfquest.shared.Constants;
import xyz.urffer.urfquest.shared.PairInt;
import xyz.urffer.urfquest.shared.Tile;

class MapChunkTest {
	
	private MapChunk chunk;
	private static int[][] sampleTileTypes = new int[][] {
		{0, 1, 2, 3, 4, 5, 6, 7},
		{1, 2, 3, 4, 5, 6, 7, 8},
		{2, 3, 4, 5, 6, 7, 8, 9},
		{3, 4, 5, 6, 7, 8, 9, 10},
		{4, 5, 6, 7, 8, 9, 10, 11},
		{5, 6, 7, 8, 9, 10, 11, 12},
		{6, 7, 8, 9, 10, 11, 12, 13},
		{7, 8, 9, 10, 11, 12, 13, 14}
	};

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		chunk = new MapChunk(new PairInt(Constants.MAP_CHUNK_SIZE, Constants.MAP_CHUNK_SIZE));
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testDefaultChunkSize() {
		MapChunk c = new MapChunk();

		Assertions.assertEquals(Constants.DEFAULT_CHUNK_TILE, c.getTileTypeAt(new PairInt(Constants.MAP_CHUNK_SIZE - 1, Constants.MAP_CHUNK_SIZE - 1)));
		Assertions.assertEquals(Tile.TILE_VOID, c.getTileTypeAt(new PairInt(Constants.MAP_CHUNK_SIZE, Constants.MAP_CHUNK_SIZE)));
	}
	
	@Test
	void testCustomSquareChunkSize() {
		MapChunk c = new MapChunk(new PairInt(50, 50));

		Assertions.assertEquals(Constants.DEFAULT_CHUNK_TILE, c.getTileTypeAt(new PairInt(49, 49)));
		Assertions.assertEquals(Tile.TILE_VOID, c.getTileTypeAt(new PairInt(49, 50)));
		Assertions.assertEquals(Tile.TILE_VOID, c.getTileTypeAt(new PairInt(50, 49)));
		Assertions.assertEquals(Tile.TILE_VOID, c.getTileTypeAt(new PairInt(50, 50)));
	}
	
	@Test
	void testCustomRectangleChunkSize() {
		MapChunk c = new MapChunk(new PairInt(20, 10));

		Assertions.assertEquals(Constants.DEFAULT_CHUNK_TILE, c.getTileTypeAt(new PairInt(19, 9)));
		Assertions.assertEquals(Tile.TILE_VOID, c.getTileTypeAt(new PairInt(19, 10)));
		Assertions.assertEquals(Tile.TILE_VOID, c.getTileTypeAt(new PairInt(20, 9)));
		Assertions.assertEquals(Tile.TILE_VOID, c.getTileTypeAt(new PairInt(20, 10)));
	}
	
	@Test
	void testTinyChunkSize() {
		MapChunk c = new MapChunk(new PairInt(1, 1));

		Assertions.assertEquals(Constants.DEFAULT_CHUNK_TILE, c.getTileTypeAt(new PairInt(0, 0)));
		Assertions.assertEquals(Tile.TILE_VOID, c.getTileTypeAt(new PairInt(1, 0)));
		Assertions.assertEquals(Tile.TILE_VOID, c.getTileTypeAt(new PairInt(0, 1)));
		Assertions.assertEquals(Tile.TILE_VOID, c.getTileTypeAt(new PairInt(1, 1)));
	}
	
	@Test
	void testGiantChunkSize() {
		MapChunk c = new MapChunk(new PairInt(3000, 3000));

		Assertions.assertEquals(Constants.DEFAULT_CHUNK_TILE, c.getTileTypeAt(new PairInt(2999, 2999)));
		Assertions.assertEquals(Tile.TILE_VOID, c.getTileTypeAt(new PairInt(3000, 2999)));
		Assertions.assertEquals(Tile.TILE_VOID, c.getTileTypeAt(new PairInt(2999, 3000)));
		Assertions.assertEquals(Tile.TILE_VOID, c.getTileTypeAt(new PairInt(3000, 3000)));
	}
	
	@Test
	void testTileTypeGetters() {
		Assertions.assertEquals(Constants.DEFAULT_CHUNK_TILE, chunk.getTileTypeAt(new PairInt(0, 0)));
		Assertions.assertEquals(Tile.TILE_VOID, chunk.getObjectTypeAt(new PairInt(0, 0)));
		Assertions.assertEquals(Constants.DEFAULT_CHUNK_TILE, chunk.getTileAt(new PairInt(0, 0))[0]);
		Assertions.assertEquals(Tile.TILE_VOID, chunk.getTileAt(new PairInt(0, 0))[1]);
		
		Assertions.assertEquals(Tile.TILE_VOID, chunk.getTileTypeAt(new PairInt(Constants.MAP_CHUNK_SIZE, Constants.MAP_CHUNK_SIZE)));
		Assertions.assertEquals(Tile.TILE_VOID, chunk.getObjectTypeAt(new PairInt(Constants.MAP_CHUNK_SIZE, Constants.MAP_CHUNK_SIZE)));
		Assertions.assertEquals(Tile.TILE_VOID, chunk.getTileAt(new PairInt(Constants.MAP_CHUNK_SIZE, Constants.MAP_CHUNK_SIZE))[0]);
		Assertions.assertEquals(Tile.TILE_VOID, chunk.getTileAt(new PairInt(Constants.MAP_CHUNK_SIZE, Constants.MAP_CHUNK_SIZE))[1]);
		
		Assertions.assertEquals(Tile.TILE_VOID, chunk.getTileTypeAt(new PairInt(-1, -1)));
		Assertions.assertEquals(Tile.TILE_VOID, chunk.getObjectTypeAt(new PairInt(-1, -1)));
		Assertions.assertEquals(Tile.TILE_VOID, chunk.getTileAt(new PairInt(-1, -1))[0]);
		Assertions.assertEquals(Tile.TILE_VOID, chunk.getTileAt(new PairInt(-1, -1))[1]);
	}
	
	@Test
	void testTileTypeSetters() {
		chunk.setTileTypeAt(new PairInt(0, 0), Tile.TILE_GRASS);
		Assertions.assertEquals(Tile.TILE_GRASS, chunk.getTileTypeAt(new PairInt(0, 0)));
		Assertions.assertEquals(Tile.TILE_VOID, chunk.getObjectTypeAt(new PairInt(0, 0)));
		Assertions.assertEquals(Tile.TILE_GRASS, chunk.getTileAt(new PairInt(0, 0))[0]);
		Assertions.assertEquals(Tile.TILE_VOID, chunk.getTileAt(new PairInt(0, 0))[1]);

		chunk.setTileAt(new PairInt(0, 0), Tile.TILE_WATER, Tile.OBJECT_BOULDER);
		Assertions.assertEquals(Tile.TILE_WATER, chunk.getTileTypeAt(new PairInt(0, 0)));
		Assertions.assertEquals(Tile.OBJECT_BOULDER, chunk.getObjectTypeAt(new PairInt(0, 0)));
		Assertions.assertEquals(Tile.TILE_WATER, chunk.getTileAt(new PairInt(0, 0))[0]);
		Assertions.assertEquals(Tile.OBJECT_BOULDER, chunk.getTileAt(new PairInt(0, 0))[1]);
	}

}
