package xyz.urffer.urfquest.testing.client.map;

import java.awt.image.BufferedImage;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import xyz.urffer.urfutils.math.PairInt;

import xyz.urffer.urfquest.client.map.MapChunk;
import xyz.urffer.urfquest.shared.Constants;
import xyz.urffer.urfquest.shared.Tile;
import xyz.urffer.urfquest.shared.protocol.types.ObjectType;
import xyz.urffer.urfquest.shared.protocol.types.TileType;

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

		Assertions.assertEquals(Constants.DEFAULT_CHUNK_TILE, c.getTileAt(new PairInt(0,0)).tileType);
		Assertions.assertEquals(TileType.VOID, c.getTileAt(new PairInt(-1,0)).tileType);
		Assertions.assertEquals(TileType.VOID, c.getTileAt(new PairInt(0,-1)).tileType);
		Assertions.assertEquals(TileType.VOID, c.getTileAt(new PairInt(-1,-1)).tileType);
		Assertions.assertEquals(Constants.DEFAULT_CHUNK_TILE, c.getTileAt(
				new PairInt(Constants.MAP_CHUNK_SIZE - 1, Constants.MAP_CHUNK_SIZE - 1)
		).tileType);
		Assertions.assertEquals(TileType.VOID, c.getTileAt(
				new PairInt(Constants.MAP_CHUNK_SIZE, Constants.MAP_CHUNK_SIZE)
		).tileType);
	}
	
	@Test
	void testCustomSquareChunkSize() {
		MapChunk c = new MapChunk(50, 50);

		Assertions.assertEquals(Constants.DEFAULT_CHUNK_TILE, c.getTileAt(new PairInt(0,0)).tileType);
		Assertions.assertEquals(TileType.VOID, c.getTileAt(new PairInt(-1,0)).tileType);
		Assertions.assertEquals(TileType.VOID, c.getTileAt(new PairInt(0,-1)).tileType);
		Assertions.assertEquals(TileType.VOID, c.getTileAt(new PairInt(-1,-1)).tileType);
		Assertions.assertEquals(Constants.DEFAULT_CHUNK_TILE, c.getTileAt(new PairInt(49,49)).tileType);
		Assertions.assertEquals(TileType.VOID, c.getTileAt(new PairInt(49, 50)).tileType);
		Assertions.assertEquals(TileType.VOID, c.getTileAt(new PairInt(50, 49)).tileType);
		Assertions.assertEquals(TileType.VOID, c.getTileAt(new PairInt(50, 50)).tileType);
	}
	
	@Test
	void testCustomRectangleChunkSize() {
		MapChunk c = new MapChunk(20, 10);

		Assertions.assertEquals(Constants.DEFAULT_CHUNK_TILE, c.getTileAt(new PairInt(0,0)).tileType);
		Assertions.assertEquals(TileType.VOID, c.getTileAt(new PairInt(-1, 0)).tileType);
		Assertions.assertEquals(TileType.VOID, c.getTileAt(new PairInt(0, -1)).tileType);
		Assertions.assertEquals(TileType.VOID, c.getTileAt(new PairInt(-1, -1)).tileType);
		Assertions.assertEquals(Constants.DEFAULT_CHUNK_TILE, c.getTileAt(new PairInt(19, 9)).tileType);
		Assertions.assertEquals(TileType.VOID, c.getTileAt(new PairInt(19, 10)).tileType);
		Assertions.assertEquals(TileType.VOID, c.getTileAt(new PairInt(20, 9)).tileType);
		Assertions.assertEquals(TileType.VOID, c.getTileAt(new PairInt(20, 10)).tileType);
	}
	
	@Test
	void testTinyChunkSize() {
		MapChunk c = new MapChunk(1, 1);

		Assertions.assertEquals(Constants.DEFAULT_CHUNK_TILE, c.getTileAt(new PairInt(0,0)).tileType);
		Assertions.assertEquals(TileType.VOID, c.getTileAt(new PairInt(-1, 0)).tileType);
		Assertions.assertEquals(TileType.VOID, c.getTileAt(new PairInt(0, -1)).tileType);
		Assertions.assertEquals(TileType.VOID, c.getTileAt(new PairInt(-1, -1)).tileType);
		Assertions.assertEquals(Constants.DEFAULT_CHUNK_TILE, c.getTileAt(new PairInt(0,0)).tileType);
		Assertions.assertEquals(TileType.VOID, c.getTileAt(new PairInt(1, 0)).tileType);
		Assertions.assertEquals(TileType.VOID, c.getTileAt(new PairInt(0, 1)).tileType);
		Assertions.assertEquals(TileType.VOID, c.getTileAt(new PairInt(1, 1)).tileType);
	}
	
	@Test
	void testGiantChunkSize() {
		MapChunk c = new MapChunk(3000, 3000);

		Assertions.assertEquals(Constants.DEFAULT_CHUNK_TILE, c.getTileAt(new PairInt(0,0)).tileType);
		Assertions.assertEquals(TileType.VOID, c.getTileAt(new PairInt(-1, 0)).tileType);
		Assertions.assertEquals(TileType.VOID, c.getTileAt(new PairInt(0, -1)).tileType);
		Assertions.assertEquals(TileType.VOID, c.getTileAt(new PairInt(-1, -1)).tileType);
		Assertions.assertEquals(Constants.DEFAULT_CHUNK_TILE, c.getTileAt(new PairInt(2999, 2999)).tileType);
		Assertions.assertEquals(TileType.VOID, c.getTileAt(new PairInt(3000, 2999)).tileType);
		Assertions.assertEquals(TileType.VOID, c.getTileAt(new PairInt(2999, 3000)).tileType);
		Assertions.assertEquals(TileType.VOID, c.getTileAt(new PairInt(3000, 3000)).tileType);
	}
	
	@Test
	void testTileTypeGetters() {
		Assertions.assertEquals(Constants.DEFAULT_CHUNK_TILE, chunk.getTileAt(new PairInt(0,0)).tileType);
		Assertions.assertEquals(ObjectType.VOID, chunk.getTileAt(new PairInt(0,0)).objectType);
		
		Assertions.assertEquals(TileType.VOID, chunk.getTileAt(new PairInt(Constants.MAP_CHUNK_SIZE, Constants.MAP_CHUNK_SIZE)).tileType);
		Assertions.assertEquals(ObjectType.VOID, chunk.getTileAt(new PairInt(Constants.MAP_CHUNK_SIZE, Constants.MAP_CHUNK_SIZE)).objectType);
		
		Assertions.assertEquals(TileType.VOID, chunk.getTileAt(new PairInt(-1, -1)).tileType);
		Assertions.assertEquals(ObjectType.VOID, chunk.getTileAt(new PairInt(-1, -1)).objectType);
	}
	
	@Test
	void testTileTypeSetters() {
		chunk.setTileAt(new PairInt(0, 0), new Tile(TileType.GRASS, ObjectType.VOID));
		Assertions.assertEquals(TileType.GRASS, chunk.getTileAt(new PairInt(0,0)).tileType);
		Assertions.assertEquals(ObjectType.VOID, chunk.getTileAt(new PairInt(0,0)).objectType);

		chunk.setTileAt(new PairInt(0, 0), new Tile(TileType.GRASS, ObjectType.FLOWERS));
		Assertions.assertEquals(TileType.GRASS, chunk.getTileAt(new PairInt(0,0)).tileType);
		Assertions.assertEquals(ObjectType.FLOWERS, chunk.getTileAt(new PairInt(0,0)).objectType);
	}

// TODO: fix this test if it's actually still applicable
//	@Test
//	void testTileTypeBulkSetters() {
//		MapChunk c = new MapChunk(8, 8);
//		
//		c.setAllTiles(sampleTileTypes);
//		Assertions.assertEquals(0, c.getTileTypeAt(new PairInt(0,0)));
//		Assertions.assertEquals(8, c.getTileTypeAt(new PairInt(2, 6)));
//		Assertions.assertEquals(5, c.getTileTypeAt(new PairInt(4, 1)));
//		Assertions.assertEquals(13, c.getTileTypeAt(new PairInt(7, 7)));
//		Assertions.assertEquals(0, c.getObjectTypeAt(new PairInt(0,0)));
//		Assertions.assertEquals(0, c.getObjectTypeAt(new PairInt(2, 6)));
//		Assertions.assertEquals(0, c.getObjectTypeAt(new PairInt(4, 1)));
//		Assertions.assertEquals(0, c.getObjectTypeAt(new PairInt(7, 7)));
//		
//		c.setAllObjectTypes(sampleTileTypes);
//		Assertions.assertEquals(0, c.getTileTypeAt(new PairInt(0,0)));
//		Assertions.assertEquals(8, c.getTileTypeAt(new PairInt(2, 6)));
//		Assertions.assertEquals(5, c.getTileTypeAt(new PairInt(4, 1)));
//		Assertions.assertEquals(13, c.getTileTypeAt(new PairInt(7, 7)));
//		Assertions.assertEquals(0, c.getObjectTypeAt(new PairInt(0,0)));
//		Assertions.assertEquals(8, c.getObjectTypeAt(new PairInt(2, 6)));
//		Assertions.assertEquals(5, c.getObjectTypeAt(new PairInt(4, 1)));
//		Assertions.assertEquals(13, c.getObjectTypeAt(new PairInt(7, 7)));
//	}
	
	@Test
	void testMinimapColor() {
		BufferedImage minimapImage = chunk.getMinimap();
        for (int x = 0; x < minimapImage.getWidth(); x++) {
            for (int y = 0; y < minimapImage.getHeight(); y++) {
                Assertions.assertEquals(Tile.minimapColor(Constants.DEFAULT_CHUNK_TILE, ObjectType.VOID), minimapImage.getRGB(x, y));
            }
        }
	}

}
