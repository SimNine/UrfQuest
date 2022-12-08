package xyz.urffer.urfquest.testing.client.map;


import java.awt.image.BufferedImage;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import xyz.urffer.urfutils.math.PairInt;

import xyz.urffer.urfquest.client.map.Map;
import xyz.urffer.urfquest.shared.Constants;
import xyz.urffer.urfquest.shared.Tile;
import xyz.urffer.urfquest.shared.protocol.types.ObjectType;
import xyz.urffer.urfquest.shared.protocol.types.TileType;

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
		PairInt expected = new PairInt(-halfCachedMapDiameter, -halfCachedMapDiameter);
		PairInt actual = map.getLocalChunkOrigin();
		Assertions.assertEquals(
			expected.x,
			actual.x
		);
		Assertions.assertEquals(
			expected.y,
			actual.y
		);
	}
	
	@Test
	void testGetTileAtPos() {
		Assertions.assertEquals(map.getTileAt(new PairInt(0,0)).tileType, TileType.VOID);
		
		Tile[][] newTiles = new Tile[Constants.MAP_CHUNK_SIZE][Constants.MAP_CHUNK_SIZE];
		for (int i = 0; i < Constants.MAP_CHUNK_SIZE; i++) {
			for (int j = 0; j < Constants.MAP_CHUNK_SIZE; j++) {
				newTiles[i][j] = new Tile(TileType.GRASS, ObjectType.BOULDER);
			}
		}
		map.setChunk(
			new PairInt(0, 0),
			newTiles
		);
		
		Assertions.assertEquals(map.getTileAt(new PairInt(0,0)).tileType, TileType.GRASS);
	}
	
	@Test
	void testDefaultMinimapColor() {
		// All tile colors will be black since no chunks exist
		BufferedImage minimapImage = map.getMinimap();
        for (int x = 0; x < minimapImage.getWidth(); x++) {
            for (int y = 0; y < minimapImage.getHeight(); y++) {
                Assertions.assertEquals(
                	Tile.minimapColor(
                		TileType.VOID,
                		ObjectType.VOID
                	),
                	minimapImage.getRGB(x, y)
                );
            }
        }
	}

}
