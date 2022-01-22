package urfquest.testing.integration.initialization;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import urfquest.client.Client;
import urfquest.server.Server;

class BasicConnectionTest {
	
	private Client c1;
	private Client c2;
	private Server s;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		s = new Server(0, 0);
		c1 = new Client(s, "Chris");
		c2 = new Client(s, "Nick");
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testAddClient() {
		s.attachLocalClient(c1);
		
		Assertions.assertEquals(1, s.getUserMap().getAllClientIDs().size());
	}
	
	@Test
	void testAddMultipleClients() {
		s.attachLocalClient(c1);
		s.attachLocalClient(c2);
		
		Assertions.assertEquals(2, s.getUserMap().getAllClientIDs().size());
	}

}
