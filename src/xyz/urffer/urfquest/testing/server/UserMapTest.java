package xyz.urffer.urfquest.testing.server;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import xyz.urffer.urfquest.server.UserMap;

class UserMapTest {
	
	private UserMap map;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		map = new UserMap();
		map.addEntry(0, 4, "Chris");
		map.addEntry(1, 5, "Nick");
		map.addEntry(2, 6, "Dad");
		map.addEntry(3, 7, "Mom");
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testUserMap() {
		UserMap newMap = new UserMap();
		Assertions.assertEquals(0, newMap.getAllClientIDs().size());
		Assertions.assertEquals(0, newMap.getAllPlayerIDs().size());
		Assertions.assertEquals(0, newMap.getAllPlayerNames().size());
	}

	@Test
	void testAddEntry() {
		UserMap newMap = new UserMap();
		Assertions.assertEquals(0, newMap.getAllClientIDs().size());
		
		newMap.addEntry(0, 10, "foo");
		Assertions.assertEquals(1, newMap.getAllClientIDs().size());
		
		newMap.addEntry(1, 92, "bar");
		newMap.addEntry(2, 37, "baz");
		newMap.addEntry(3, 0, "wop");
		Assertions.assertEquals(4, newMap.getAllClientIDs().size());
	}

	@Test
	void testGetPlayerIdFromClientId() {
		Assertions.assertNull(map.getPlayerIdFromClientId(-1));
		Assertions.assertEquals(4, map.getPlayerIdFromClientId(0));
		Assertions.assertEquals(5, map.getPlayerIdFromClientId(1));
		Assertions.assertEquals(6, map.getPlayerIdFromClientId(2));
		Assertions.assertEquals(7, map.getPlayerIdFromClientId(3));
	}

	@Test
	void testGetPlayerNameFromClientId() {
		Assertions.assertNull(map.getPlayerNameFromClientId(-1));
		Assertions.assertEquals("Chris", map.getPlayerNameFromClientId(0));
		Assertions.assertEquals("Nick", map.getPlayerNameFromClientId(1));
		Assertions.assertEquals("Dad", map.getPlayerNameFromClientId(2));
		Assertions.assertEquals("Mom", map.getPlayerNameFromClientId(3));
	}

	@Test
	void testGetClientIdFromPlayerId() {
		Assertions.assertNull(map.getClientIdFromPlayerId(-1));
		Assertions.assertEquals(0, map.getClientIdFromPlayerId(4));
		Assertions.assertEquals(1, map.getClientIdFromPlayerId(5));
		Assertions.assertEquals(2, map.getClientIdFromPlayerId(6));
		Assertions.assertEquals(3, map.getClientIdFromPlayerId(7));
	}

	@Test
	void testGetPlayerNameFromPlayerId() {
		Assertions.assertNull(map.getPlayerNameFromPlayerId(-1));
		Assertions.assertEquals("Chris", map.getPlayerNameFromPlayerId(4));
		Assertions.assertEquals("Nick", map.getPlayerNameFromPlayerId(5));
		Assertions.assertEquals("Dad", map.getPlayerNameFromPlayerId(6));
		Assertions.assertEquals("Mom", map.getPlayerNameFromPlayerId(7));
	}

	@Test
	void testGetClientIdFromPlayerName() {
		Assertions.assertNull(map.getClientIdFromPlayerName("Tim"));
		Assertions.assertEquals(0, map.getClientIdFromPlayerName("Chris"));
		Assertions.assertEquals(1, map.getClientIdFromPlayerName("Nick"));
		Assertions.assertEquals(2, map.getClientIdFromPlayerName("Dad"));
		Assertions.assertEquals(3, map.getClientIdFromPlayerName("Mom"));
	}

	@Test
	void testGetPlayerIdFromPlayerName() {
		Assertions.assertNull(map.getPlayerIdFromPlayerName("Tim"));
		Assertions.assertEquals(4, map.getPlayerIdFromPlayerName("Chris"));
		Assertions.assertEquals(5, map.getPlayerIdFromPlayerName("Nick"));
		Assertions.assertEquals(6, map.getPlayerIdFromPlayerName("Dad"));
		Assertions.assertEquals(7, map.getPlayerIdFromPlayerName("Mom"));
	}

	@Test
	void testGetAllClientIDs() {
		Assertions.assertEquals(4, map.getAllClientIDs().size());
		Assertions.assertEquals(false, map.getAllClientIDs().contains(-1));
		Assertions.assertEquals(true, map.getAllClientIDs().contains(0));
		Assertions.assertEquals(true, map.getAllClientIDs().contains(1));
		Assertions.assertEquals(true, map.getAllClientIDs().contains(2));
		Assertions.assertEquals(true, map.getAllClientIDs().contains(3));
		Assertions.assertEquals(false, map.getAllClientIDs().contains(4));
	}

	@Test
	void testGetAllPlayerIDs() {
		Assertions.assertEquals(4, map.getAllPlayerIDs().size());
		Assertions.assertEquals(false, map.getAllPlayerIDs().contains(-1));
		Assertions.assertEquals(false, map.getAllPlayerIDs().contains(2));
		Assertions.assertEquals(true, map.getAllPlayerIDs().contains(4));
		Assertions.assertEquals(true, map.getAllPlayerIDs().contains(5));
		Assertions.assertEquals(true, map.getAllPlayerIDs().contains(6));
		Assertions.assertEquals(true, map.getAllPlayerIDs().contains(7));
		Assertions.assertEquals(false, map.getAllPlayerIDs().contains(8));
	}

	@Test
	void testGetAllPlayerNames() {
		Assertions.assertEquals(4, map.getAllPlayerNames().size());
		Assertions.assertEquals(false, map.getAllPlayerNames().contains("Tim"));
		Assertions.assertEquals(true, map.getAllPlayerNames().contains("Chris"));
		Assertions.assertEquals(true, map.getAllPlayerNames().contains("Nick"));
		Assertions.assertEquals(true, map.getAllPlayerNames().contains("Dad"));
		Assertions.assertEquals(true, map.getAllPlayerNames().contains("Mom"));
		Assertions.assertEquals(false, map.getAllPlayerNames().contains("Bottery"));
	}

	@Test
	void testRemoveByClientId() {
		Assertions.assertEquals(4, map.getAllClientIDs().size());
		
		map.removeByClientId(0);
		Assertions.assertEquals(3, map.getAllClientIDs().size());
		
		map.removeByClientId(5342);
		Assertions.assertEquals(3, map.getAllClientIDs().size());
	}

	@Test
	void testRemoveByPlayerId() {
		Assertions.assertEquals(4, map.getAllPlayerIDs().size());
		
		map.removeByPlayerId(4);
		Assertions.assertEquals(3, map.getAllPlayerIDs().size());
		
		map.removeByPlayerId(345);
		Assertions.assertEquals(3, map.getAllPlayerIDs().size());
	}

	@Test
	void testRemoveByPlayerName() {
		Assertions.assertEquals(4, map.getAllPlayerNames().size());
		
		map.removeByPlayerName("Chris");
		Assertions.assertEquals(3, map.getAllPlayerNames().size());
		
		map.removeByPlayerName("Bottery");
		Assertions.assertEquals(3, map.getAllPlayerNames().size());
	}

}
