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

		Assertions.assertEquals(0, c.getTileTypeAt(MapChunk.CHUNK_SIZE - 1, MapChunk.CHUNK_SIZE - 1));
		Assertions.assertEquals(-1, c.getTileTypeAt(MapChunk.CHUNK_SIZE, MapChunk.CHUNK_SIZE));
	}
	
	@Test
	void testCustomSquareChunkSize() {
		MapChunk c = new MapChunk(50, 50);

		Assertions.assertEquals(0, c.getTileTypeAt(49, 49));
		Assertions.assertEquals(-1, c.getTileTypeAt(49, 50));
		Assertions.assertEquals(-1, c.getTileTypeAt(50, 49));
		Assertions.assertEquals(-1, c.getTileTypeAt(50, 50));
	}
	
	@Test
	void testCustomRectangleChunkSize() {
		MapChunk c = new MapChunk(20, 10);

		Assertions.assertEquals(0, c.getTileTypeAt(19, 9));
		Assertions.assertEquals(-1, c.getTileTypeAt(19, 10));
		Assertions.assertEquals(-1, c.getTileTypeAt(20, 9));
		Assertions.assertEquals(-1, c.getTileTypeAt(20, 10));
	}
	
	@Test
	void testTinyChunkSize() {
		MapChunk c = new MapChunk(1, 1);

		Assertions.assertEquals(0, c.getTileTypeAt(0, 0));
		Assertions.assertEquals(-1, c.getTileTypeAt(1, 0));
		Assertions.assertEquals(-1, c.getTileTypeAt(0, 1));
		Assertions.assertEquals(-1, c.getTileTypeAt(1, 1));
	}
	
	@Test
	void testGiantChunkSize() {
		MapChunk c = new MapChunk(3000, 3000);

		Assertions.assertEquals(0, c.getTileTypeAt(2999, 2999));
		Assertions.assertEquals(-1, c.getTileTypeAt(3000, 2999));
		Assertions.assertEquals(-1, c.getTileTypeAt(2999, 3000));
		Assertions.assertEquals(-1, c.getTileTypeAt(3000, 3000));
	}
	
	@Test
	void testTileTypeGetters() {
		Assertions.assertEquals(0, chunk.getTileTypeAt(0, 0));
		Assertions.assertEquals(0, chunk.getTileSubtypeAt(0, 0));
		Assertions.assertEquals(0, chunk.getTileAt(0, 0)[0]);
		Assertions.assertEquals(0, chunk.getTileAt(0, 0)[1]);
		
		Assertions.assertEquals(-1, chunk.getTileTypeAt(MapChunk.CHUNK_SIZE, MapChunk.CHUNK_SIZE));
		Assertions.assertEquals(0, chunk.getTileSubtypeAt(MapChunk.CHUNK_SIZE, MapChunk.CHUNK_SIZE));
		Assertions.assertEquals(-1, chunk.getTileAt(MapChunk.CHUNK_SIZE, MapChunk.CHUNK_SIZE)[0]);
		Assertions.assertEquals(0, chunk.getTileAt(MapChunk.CHUNK_SIZE, MapChunk.CHUNK_SIZE)[1]);
		
		Assertions.assertEquals(-1, chunk.getTileTypeAt(-1, -1));
		Assertions.assertEquals(0, chunk.getTileSubtypeAt(-1, -1));
		Assertions.assertEquals(-1, chunk.getTileAt(-1, -1)[0]);
		Assertions.assertEquals(0, chunk.getTileAt(-1, -1)[1]);
	}
	
	@Test
	void testTileTypeSetters() {
		chunk.setTileAt(0, 0, 5);
		Assertions.assertEquals(5, chunk.getTileTypeAt(0, 0));
		Assertions.assertEquals(0, chunk.getTileSubtypeAt(0, 0));
		Assertions.assertEquals(5, chunk.getTileAt(0, 0)[0]);
		Assertions.assertEquals(0, chunk.getTileAt(0, 0)[1]);

		chunk.setTileAt(0, 0, 3, 2);
		Assertions.assertEquals(3, chunk.getTileTypeAt(0, 0));
		Assertions.assertEquals(2, chunk.getTileSubtypeAt(0, 0));
		Assertions.assertEquals(3, chunk.getTileAt(0, 0)[0]);
		Assertions.assertEquals(2, chunk.getTileAt(0, 0)[1]);
	}

}
