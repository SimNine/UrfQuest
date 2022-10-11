package xyz.urffer.urfquest.testing.client.map;


import java.awt.image.BufferedImage;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import xyz.urffer.urfquest.client.map.Map;
import xyz.urffer.urfquest.shared.Constants;
import xyz.urffer.urfquest.shared.Tile;

class MapTest {
	
	private Map map;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		map = new Map(null, 0, Constants.CLIENT_CACHED_MAP_DIAMETER);
	}

	@AfterEach
	void tearDown() throws Exception {
	}
	
	@Test
	void testDefaultMapSize() {
		Assertions.assertEquals(
			Constants.CLIENT_CACHED_MAP_DIAMETER,
			map.getMapDiameter()
		);
	}
	
	@Test
	void testDefaultMapOrigin() {
		int halfCachedMapDiameter = Constants.CLIENT_CACHED_MAP_DIAMETER / 2;
		int[] expected = new int[]{ -halfCachedMapDiameter, -halfCachedMapDiameter };
		int[] actual = map.getLocalChunkOrigin();
		Assertions.assertEquals(
			expected[0],
			actual[0]
		);
		Assertions.assertEquals(
			expected[1],
			actual[1]
		);
	}
	
	@Test
	void testGetTileAtPos() {
		Assertions.assertEquals(map.getTileAt(new int[] {0,0})[0], Tile.TILE_VOID);
		
		int[][] newTiles = new int[Constants.MAP_CHUNK_SIZE][Constants.MAP_CHUNK_SIZE];
		int[][] newObjects = new int[Constants.MAP_CHUNK_SIZE][Constants.MAP_CHUNK_SIZE];
		for (int i = 0; i < Constants.MAP_CHUNK_SIZE; i++) {
			for (int j = 0; j < Constants.MAP_CHUNK_SIZE; j++) {
				newTiles[i][j] = Tile.TILE_GRASS;
				newObjects[i][j] = Tile.OBJECT_BOULDER;
			}
		}
		map.setChunk(
			new int[]{0, 0},
			newTiles,
			newObjects
		);
		
		Assertions.assertEquals(map.getTileAt(new int[] {0,0})[0], Tile.TILE_GRASS);
	}
	
	@Test
	void testDefaultMinimapColor() {
		// All tile colors will be black since no chunks exist
		BufferedImage minimapImage = map.getMinimap();
        for (int x = 0; x < minimapImage.getWidth(); x++) {
            for (int y = 0; y < minimapImage.getHeight(); y++) {
                Assertions.assertEquals(
                	Tile.minimapColor(
                		Tile.TILE_VOID,
                		Tile.TILE_VOID
                	),
                	minimapImage.getRGB(x, y)
                );
            }
        }
	}

}
