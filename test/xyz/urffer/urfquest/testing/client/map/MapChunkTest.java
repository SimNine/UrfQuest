package xyz.urffer.urfquest.testing.client.map;

import java.awt.image.BufferedImage;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import xyz.urffer.urfquest.client.map.MapChunk;
import xyz.urffer.urfquest.shared.Constants;
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
		{7, 8, 9, 10, 11, 12, 13, 13}
	};

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		chunk = new MapChunk(Constants.MAP_CHUNK_SIZE, Constants.MAP_CHUNK_SIZE);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testDefaultChunkSize() {
		MapChunk c = new MapChunk();

		Assertions.assertEquals(Constants.DEFAULT_CHUNK_TILE, c.getTileTypeAt(0, 0));
		Assertions.assertEquals(Tile.TILE_VOID, c.getTileTypeAt(-1, 0));
		Assertions.assertEquals(Tile.TILE_VOID, c.getTileTypeAt(0, -1));
		Assertions.assertEquals(Tile.TILE_VOID, c.getTileTypeAt(-1, -1));
		Assertions.assertEquals(Constants.DEFAULT_CHUNK_TILE, c.getTileTypeAt(Constants.MAP_CHUNK_SIZE - 1, Constants.MAP_CHUNK_SIZE - 1));
		Assertions.assertEquals(Tile.TILE_VOID, c.getTileTypeAt(Constants.MAP_CHUNK_SIZE, Constants.MAP_CHUNK_SIZE));
	}
	
	@Test
	void testCustomSquareChunkSize() {
		MapChunk c = new MapChunk(50, 50);

		Assertions.assertEquals(Constants.DEFAULT_CHUNK_TILE, c.getTileTypeAt(0, 0));
		Assertions.assertEquals(Tile.TILE_VOID, c.getTileTypeAt(-1, 0));
		Assertions.assertEquals(Tile.TILE_VOID, c.getTileTypeAt(0, -1));
		Assertions.assertEquals(Tile.TILE_VOID, c.getTileTypeAt(-1, -1));
		Assertions.assertEquals(Constants.DEFAULT_CHUNK_TILE, c.getTileTypeAt(49, 49));
		Assertions.assertEquals(Tile.TILE_VOID, c.getTileTypeAt(49, 50));
		Assertions.assertEquals(Tile.TILE_VOID, c.getTileTypeAt(50, 49));
		Assertions.assertEquals(Tile.TILE_VOID, c.getTileTypeAt(50, 50));
	}
	
	@Test
	void testCustomRectangleChunkSize() {
		MapChunk c = new MapChunk(20, 10);

		Assertions.assertEquals(Constants.DEFAULT_CHUNK_TILE, c.getTileTypeAt(0, 0));
		Assertions.assertEquals(Tile.TILE_VOID, c.getTileTypeAt(-1, 0));
		Assertions.assertEquals(Tile.TILE_VOID, c.getTileTypeAt(0, -1));
		Assertions.assertEquals(Tile.TILE_VOID, c.getTileTypeAt(-1, -1));
		Assertions.assertEquals(Constants.DEFAULT_CHUNK_TILE, c.getTileTypeAt(19, 9));
		Assertions.assertEquals(Tile.TILE_VOID, c.getTileTypeAt(19, 10));
		Assertions.assertEquals(Tile.TILE_VOID, c.getTileTypeAt(20, 9));
		Assertions.assertEquals(Tile.TILE_VOID, c.getTileTypeAt(20, 10));
	}
	
	@Test
	void testTinyChunkSize() {
		MapChunk c = new MapChunk(1, 1);

		Assertions.assertEquals(Constants.DEFAULT_CHUNK_TILE, c.getTileTypeAt(0, 0));
		Assertions.assertEquals(Tile.TILE_VOID, c.getTileTypeAt(-1, 0));
		Assertions.assertEquals(Tile.TILE_VOID, c.getTileTypeAt(0, -1));
		Assertions.assertEquals(Tile.TILE_VOID, c.getTileTypeAt(-1, -1));
		Assertions.assertEquals(Constants.DEFAULT_CHUNK_TILE, c.getTileTypeAt(0, 0));
		Assertions.assertEquals(Tile.TILE_VOID, c.getTileTypeAt(1, 0));
		Assertions.assertEquals(Tile.TILE_VOID, c.getTileTypeAt(0, 1));
		Assertions.assertEquals(Tile.TILE_VOID, c.getTileTypeAt(1, 1));
	}
	
	@Test
	void testGiantChunkSize() {
		MapChunk c = new MapChunk(3000, 3000);

		Assertions.assertEquals(Constants.DEFAULT_CHUNK_TILE, c.getTileTypeAt(0, 0));
		Assertions.assertEquals(Tile.TILE_VOID, c.getTileTypeAt(-1, 0));
		Assertions.assertEquals(Tile.TILE_VOID, c.getTileTypeAt(0, -1));
		Assertions.assertEquals(Tile.TILE_VOID, c.getTileTypeAt(-1, -1));
		Assertions.assertEquals(Constants.DEFAULT_CHUNK_TILE, c.getTileTypeAt(2999, 2999));
		Assertions.assertEquals(Tile.TILE_VOID, c.getTileTypeAt(3000, 2999));
		Assertions.assertEquals(Tile.TILE_VOID, c.getTileTypeAt(2999, 3000));
		Assertions.assertEquals(Tile.TILE_VOID, c.getTileTypeAt(3000, 3000));
	}
	
	@Test
	void testTileTypeGetters() {
		Assertions.assertEquals(Constants.DEFAULT_CHUNK_TILE, chunk.getTileTypeAt(0, 0));
		Assertions.assertEquals(Tile.TILE_VOID, chunk.getObjectTypeAt(0, 0));
		Assertions.assertEquals(Constants.DEFAULT_CHUNK_TILE, chunk.getTileAt(0, 0)[0]);
		Assertions.assertEquals(Tile.TILE_VOID, chunk.getTileAt(0, 0)[1]);
		
		Assertions.assertEquals(Tile.TILE_VOID, chunk.getTileTypeAt(Constants.MAP_CHUNK_SIZE, Constants.MAP_CHUNK_SIZE));
		Assertions.assertEquals(Tile.TILE_VOID, chunk.getObjectTypeAt(Constants.MAP_CHUNK_SIZE, Constants.MAP_CHUNK_SIZE));
		Assertions.assertEquals(Tile.TILE_VOID, chunk.getTileAt(Constants.MAP_CHUNK_SIZE, Constants.MAP_CHUNK_SIZE)[0]);
		Assertions.assertEquals(Tile.TILE_VOID, chunk.getTileAt(Constants.MAP_CHUNK_SIZE, Constants.MAP_CHUNK_SIZE)[1]);
		
		Assertions.assertEquals(Tile.TILE_VOID, chunk.getTileTypeAt(-1, -1));
		Assertions.assertEquals(Tile.TILE_VOID, chunk.getObjectTypeAt(-1, -1));
		Assertions.assertEquals(Tile.TILE_VOID, chunk.getTileAt(-1, -1)[0]);
		Assertions.assertEquals(Tile.TILE_VOID, chunk.getTileAt(-1, -1)[1]);
	}
	
	@Test
	void testTileTypeSetters() {
		chunk.setTileAt(0, 0, Tile.TILE_GRASS, Tile.TILE_VOID);
		Assertions.assertEquals(Tile.TILE_GRASS, chunk.getTileTypeAt(0, 0));
		Assertions.assertEquals(Tile.TILE_VOID, chunk.getObjectTypeAt(0, 0));
		Assertions.assertEquals(Tile.TILE_GRASS, chunk.getTileAt(0, 0)[0]);
		Assertions.assertEquals(Tile.TILE_VOID, chunk.getTileAt(0, 0)[1]);

		chunk.setTileAt(0, 0, Tile.TILE_GRASS, Tile.OBJECT_FLOWERS);
		Assertions.assertEquals(Tile.TILE_GRASS, chunk.getTileTypeAt(0, 0));
		Assertions.assertEquals(Tile.OBJECT_FLOWERS, chunk.getObjectTypeAt(0, 0));
		Assertions.assertEquals(Tile.TILE_GRASS, chunk.getTileAt(0, 0)[0]);
		Assertions.assertEquals(Tile.OBJECT_FLOWERS, chunk.getTileAt(0, 0)[1]);
	}
	
	@Test
	void testTileTypeBulkSetters() {
		MapChunk c = new MapChunk(8, 8);
		
		c.setAllTileTypes(sampleTileTypes);
		Assertions.assertEquals(0, c.getTileTypeAt(0, 0));
		Assertions.assertEquals(8, c.getTileTypeAt(2, 6));
		Assertions.assertEquals(5, c.getTileTypeAt(4, 1));
		Assertions.assertEquals(13, c.getTileTypeAt(7, 7));
		Assertions.assertEquals(0, c.getObjectTypeAt(0, 0));
		Assertions.assertEquals(0, c.getObjectTypeAt(2, 6));
		Assertions.assertEquals(0, c.getObjectTypeAt(4, 1));
		Assertions.assertEquals(0, c.getObjectTypeAt(7, 7));
		
		c.setAllObjectTypes(sampleTileTypes);
		Assertions.assertEquals(0, c.getTileTypeAt(0, 0));
		Assertions.assertEquals(8, c.getTileTypeAt(2, 6));
		Assertions.assertEquals(5, c.getTileTypeAt(4, 1));
		Assertions.assertEquals(13, c.getTileTypeAt(7, 7));
		Assertions.assertEquals(0, c.getObjectTypeAt(0, 0));
		Assertions.assertEquals(8, c.getObjectTypeAt(2, 6));
		Assertions.assertEquals(5, c.getObjectTypeAt(4, 1));
		Assertions.assertEquals(13, c.getObjectTypeAt(7, 7));
	}
	
	@Test
	void testMinimapColor() {
		BufferedImage minimapImage = chunk.getMinimap();
        for (int x = 0; x < minimapImage.getWidth(); x++) {
            for (int y = 0; y < minimapImage.getHeight(); y++) {
                Assertions.assertEquals(Tile.minimapColor(Constants.DEFAULT_CHUNK_TILE, Tile.TILE_VOID), minimapImage.getRGB(x, y));
            }
        }
	}

}
