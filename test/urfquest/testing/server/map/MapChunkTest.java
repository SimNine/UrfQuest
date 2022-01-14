package urfquest.testing.server.map;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import urfquest.server.map.MapChunk;

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
		chunk = new MapChunk(MapChunk.CHUNK_SIZE, MapChunk.CHUNK_SIZE);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testDefaultChunkSize() {
		MapChunk c = new MapChunk();

		Assertions.assertEquals(c.getTileTypeAt(MapChunk.CHUNK_SIZE - 1, MapChunk.CHUNK_SIZE - 1), 0);
		Assertions.assertEquals(c.getTileTypeAt(MapChunk.CHUNK_SIZE, MapChunk.CHUNK_SIZE), -1);
	}
	
	@Test
	void testCustomSquareChunkSize() {
		MapChunk c = new MapChunk(50, 50);

		Assertions.assertEquals(c.getTileTypeAt(49, 49), 0);
		Assertions.assertEquals(c.getTileTypeAt(49, 50), -1);
		Assertions.assertEquals(c.getTileTypeAt(50, 49), -1);
		Assertions.assertEquals(c.getTileTypeAt(50, 50), -1);
	}
	
	@Test
	void testCustomRectangleChunkSize() {
		MapChunk c = new MapChunk(20, 10);

		Assertions.assertEquals(c.getTileTypeAt(19, 9), 0);
		Assertions.assertEquals(c.getTileTypeAt(19, 10), -1);
		Assertions.assertEquals(c.getTileTypeAt(20, 9), -1);
		Assertions.assertEquals(c.getTileTypeAt(20, 10), -1);
	}
	
	@Test
	void testTinyChunkSize() {
		MapChunk c = new MapChunk(1, 1);

		Assertions.assertEquals(c.getTileTypeAt(0, 0), 0);
		Assertions.assertEquals(c.getTileTypeAt(1, 0), -1);
		Assertions.assertEquals(c.getTileTypeAt(0, 1), -1);
		Assertions.assertEquals(c.getTileTypeAt(1, 1), -1);
	}
	
	@Test
	void testGiantChunkSize() {
		MapChunk c = new MapChunk(3000, 3000);

		Assertions.assertEquals(c.getTileTypeAt(2999, 2999), 0);
		Assertions.assertEquals(c.getTileTypeAt(3000, 2999), -1);
		Assertions.assertEquals(c.getTileTypeAt(2999, 3000), -1);
		Assertions.assertEquals(c.getTileTypeAt(3000, 3000), -1);
	}
	
	@Test
	void testTileTypeGetters() {
		Assertions.assertEquals(chunk.getTileTypeAt(0, 0), 0);
		Assertions.assertEquals(chunk.getTileSubtypeAt(0, 0), 0);
		Assertions.assertEquals(chunk.getTileAt(0, 0)[0], 0);
		Assertions.assertEquals(chunk.getTileAt(0, 0)[1], 0);
		
		Assertions.assertEquals(chunk.getTileTypeAt(MapChunk.CHUNK_SIZE, MapChunk.CHUNK_SIZE), -1);
		Assertions.assertEquals(chunk.getTileSubtypeAt(MapChunk.CHUNK_SIZE, MapChunk.CHUNK_SIZE), 0);
		Assertions.assertEquals(chunk.getTileAt(MapChunk.CHUNK_SIZE, MapChunk.CHUNK_SIZE)[0], -1);
		Assertions.assertEquals(chunk.getTileAt(MapChunk.CHUNK_SIZE, MapChunk.CHUNK_SIZE)[1], 0);
		
		Assertions.assertEquals(chunk.getTileTypeAt(-1, -1), -1);
		Assertions.assertEquals(chunk.getTileSubtypeAt(-1, -1), 0);
		Assertions.assertEquals(chunk.getTileAt(-1, -1)[0], -1);
		Assertions.assertEquals(chunk.getTileAt(-1, -1)[1], 0);
	}
	
	@Test
	void testTileTypeSetters() {
		chunk.setTileAt(0, 0, 5);
		Assertions.assertEquals(chunk.getTileTypeAt(0, 0), 5);
		Assertions.assertEquals(chunk.getTileSubtypeAt(0, 0), 0);
		Assertions.assertEquals(chunk.getTileAt(0, 0)[0], 5);
		Assertions.assertEquals(chunk.getTileAt(0, 0)[1], 0);

		chunk.setTileAt(0, 0, 3, 2);
		Assertions.assertEquals(chunk.getTileTypeAt(0, 0), 3);
		Assertions.assertEquals(chunk.getTileSubtypeAt(0, 0), 2);
		Assertions.assertEquals(chunk.getTileAt(0, 0)[0], 3);
		Assertions.assertEquals(chunk.getTileAt(0, 0)[1], 2);
	}

}
