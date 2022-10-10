package xyz.urffer.urfquest.testing.client.map;


import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import xyz.urffer.urfquest.client.map.Map;
import xyz.urffer.urfquest.shared.Constants;

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
	void defaultMapSize() {
		Assertions.assertEquals(map.getMapDiameter(), 10);
	}

}
