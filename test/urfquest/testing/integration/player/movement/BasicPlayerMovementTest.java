package urfquest.testing.integration.player.movement;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import urfquest.client.Client;
import urfquest.server.Server;
import urfquest.shared.Constants;

class BasicPlayerMovementTest {
	
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
		s = new Server(0);
		c1 = new Client(s, "Chris");
		c2 = new Client(s, "Nick");
		
		s.attachLocalClient(c1);
		s.attachLocalClient(c2);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testMovementServerSideOneMotion() {
		urfquest.server.entities.mobs.Player p1s = s.getState().getPlayer(s.getUserMap().getPlayerIdFromClientId(c1.getClientID()));
		int numStepsMoved = 10;
		
		// check initial position
		double xPos1s = p1s.getPos()[0];
		double yPos1s = p1s.getPos()[1];
		Assertions.assertTrue(xPos1s > -5.0 && xPos1s < 5.0);
		Assertions.assertTrue(yPos1s > -5.0 && yPos1s < 5.0);

		// move right 10 times
		for (int i = 0; i < numStepsMoved; i++) {
			c1.getState().getPlayer().move(1, 0);
		}
		double xPos2s = p1s.getPos()[0];
		double yPos2s = p1s.getPos()[1];
		Assertions.assertEquals(xPos1s + numStepsMoved*Constants.DEFAULT_PLAYER_VELOCITY, xPos2s, 0.01);
		Assertions.assertEquals(yPos1s, yPos2s, 0.01);
	}

	@Test
	void testMovementServerSideSquare() {
		urfquest.server.entities.mobs.Player p1s = s.getState().getPlayer(s.getUserMap().getPlayerIdFromClientId(c1.getClientID()));
		int numStepsMoved = 10;
		
		// check initial position
		double xPos1s = p1s.getPos()[0];
		double yPos1s = p1s.getPos()[1];
		Assertions.assertTrue(xPos1s > -5.0 && xPos1s < 5.0);
		Assertions.assertTrue(yPos1s > -5.0 && yPos1s < 5.0);

		// move right 10 times
		for (int i = 0; i < numStepsMoved; i++) {
			c1.getState().getPlayer().move(1, 0);
		}
		double xPos2s = p1s.getPos()[0];
		double yPos2s = p1s.getPos()[1];
		Assertions.assertEquals(xPos1s + numStepsMoved*Constants.DEFAULT_PLAYER_VELOCITY, xPos2s, 0.01);
		Assertions.assertEquals(yPos1s, yPos2s, 0.01);

		// move down 10 times
		for (int i = 0; i < numStepsMoved; i++) {
			c1.getState().getPlayer().move(0, 1);
		}
		double xPos3s = p1s.getPos()[0];
		double yPos3s = p1s.getPos()[1];
		Assertions.assertEquals(xPos1s + numStepsMoved*Constants.DEFAULT_PLAYER_VELOCITY, xPos3s, 0.01);
		Assertions.assertEquals(yPos1s + numStepsMoved*Constants.DEFAULT_PLAYER_VELOCITY, yPos3s, 0.01);

		// move left 10 times
		for (int i = 0; i < numStepsMoved; i++) {
			c1.getState().getPlayer().move(-1, 0);
		}
		double xPos4s = p1s.getPos()[0];
		double yPos4s = p1s.getPos()[1];
		Assertions.assertEquals(xPos1s, xPos4s, 0.01);
		Assertions.assertEquals(yPos1s + numStepsMoved*Constants.DEFAULT_PLAYER_VELOCITY, yPos4s, 0.01);

		// move up 10 times
		for (int i = 0; i < numStepsMoved; i++) {
			c1.getState().getPlayer().move(0, -1);
		}
		double xPos5s = p1s.getPos()[0];
		double yPos5s = p1s.getPos()[1];
		Assertions.assertEquals(xPos1s, xPos5s, 0.01);
		Assertions.assertEquals(yPos1s, yPos5s, 0.01);
	}

	@Test
	void testMovementServerSideDiamond() {
		urfquest.server.entities.mobs.Player p1s = s.getState().getPlayer(s.getUserMap().getPlayerIdFromClientId(c1.getClientID()));
		int numStepsMoved = 10;
		double stepDistanceComponent = numStepsMoved*Constants.DEFAULT_PLAYER_VELOCITY*Math.sin(Math.toRadians(45));
		
		// check initial position
		double xPos1s = p1s.getPos()[0];
		double yPos1s = p1s.getPos()[1];
		Assertions.assertTrue(xPos1s > -5.0 && xPos1s < 5.0);
		Assertions.assertTrue(yPos1s > -5.0 && yPos1s < 5.0);

		// move southeast 10 times
		for (int i = 0; i < numStepsMoved; i++) {
			c1.getState().getPlayer().move(1, 1);
		}
		double xPos2s = p1s.getPos()[0];
		double yPos2s = p1s.getPos()[1];
		Assertions.assertEquals(xPos1s + stepDistanceComponent, xPos2s, 0.01);
		Assertions.assertEquals(yPos1s + stepDistanceComponent, yPos2s, 0.01);

		// move southwest 10 times
		for (int i = 0; i < numStepsMoved; i++) {
			c1.getState().getPlayer().move(-1, 1);
		}
		double xPos3s = p1s.getPos()[0];
		double yPos3s = p1s.getPos()[1];
		Assertions.assertEquals(xPos1s, xPos3s, 0.01);
		Assertions.assertEquals(yPos1s + stepDistanceComponent*2, yPos3s, 0.01);

		// move northwest 10 times
		for (int i = 0; i < numStepsMoved; i++) {
			c1.getState().getPlayer().move(-1, -1);
		}
		double xPos4s = p1s.getPos()[0];
		double yPos4s = p1s.getPos()[1];
		Assertions.assertEquals(xPos1s - stepDistanceComponent, xPos4s, 0.01);
		Assertions.assertEquals(yPos1s + stepDistanceComponent, yPos4s, 0.01);

		// move northeast 10 times
		for (int i = 0; i < numStepsMoved; i++) {
			c1.getState().getPlayer().move(1, -1);
		}
		double xPos5s = p1s.getPos()[0];
		double yPos5s = p1s.getPos()[1];
		Assertions.assertEquals(xPos1s, xPos5s, 0.01);
		Assertions.assertEquals(yPos1s, yPos5s, 0.01);
	}

	@Test
	void testMovementClientSideOneMotion() {
		urfquest.client.entities.mobs.Player p1c = c1.getState().getPlayer();
		int numStepsMoved = 10;
		
		// check initial position
		double xPos1c = p1c.getPos()[0];
		double yPos1c = p1c.getPos()[1];
		Assertions.assertTrue(xPos1c > -5.0 && xPos1c < 5.0);
		Assertions.assertTrue(yPos1c > -5.0 && yPos1c < 5.0);

		// move right 10 times
		for (int i = 0; i < numStepsMoved; i++) {
			c1.getState().getPlayer().move(1, 0);
		}
		double xPos2c = p1c.getPos()[0];
		double yPos2c = p1c.getPos()[1];
		Assertions.assertEquals(xPos1c + numStepsMoved*Constants.DEFAULT_PLAYER_VELOCITY, xPos2c, 0.01);
		Assertions.assertEquals(yPos1c, yPos2c, 0.01);
	}

	@Test
	void testMovementClientSideSquare() {
		urfquest.client.entities.mobs.Player p1c = c1.getState().getPlayer();
		int numStepsMoved = 10;
		
		// check initial position
		double xPos1c = p1c.getPos()[0];
		double yPos1c = p1c.getPos()[1];
		Assertions.assertTrue(xPos1c > -5.0 && xPos1c < 5.0);
		Assertions.assertTrue(yPos1c > -5.0 && yPos1c < 5.0);

		// move right 10 times
		for (int i = 0; i < numStepsMoved; i++) {
			c1.getState().getPlayer().move(1, 0);
		}
		double xPos2c = p1c.getPos()[0];
		double yPos2c = p1c.getPos()[1];
		Assertions.assertEquals(xPos1c + numStepsMoved*Constants.DEFAULT_PLAYER_VELOCITY, xPos2c, 0.01);
		Assertions.assertEquals(yPos1c, yPos2c, 0.01);

		// move down 10 times
		for (int i = 0; i < numStepsMoved; i++) {
			c1.getState().getPlayer().move(0, 1);
		}
		double xPos3c = p1c.getPos()[0];
		double yPos3c = p1c.getPos()[1];
		Assertions.assertEquals(xPos1c + numStepsMoved*Constants.DEFAULT_PLAYER_VELOCITY, xPos3c, 0.01);
		Assertions.assertEquals(yPos1c + numStepsMoved*Constants.DEFAULT_PLAYER_VELOCITY, yPos3c, 0.01);

		// move left 10 times
		for (int i = 0; i < numStepsMoved; i++) {
			c1.getState().getPlayer().move(-1, 0);
		}
		double xPos4c = p1c.getPos()[0];
		double yPos4c = p1c.getPos()[1];
		Assertions.assertEquals(xPos1c, xPos4c, 0.01);
		Assertions.assertEquals(yPos1c + numStepsMoved*Constants.DEFAULT_PLAYER_VELOCITY, yPos4c, 0.01);

		// move up 10 times
		for (int i = 0; i < numStepsMoved; i++) {
			c1.getState().getPlayer().move(0, -1);
		}
		double xPos5c = p1c.getPos()[0];
		double yPos5c = p1c.getPos()[1];
		Assertions.assertEquals(xPos1c, xPos5c, 0.01);
		Assertions.assertEquals(yPos1c, yPos5c, 0.01);
	}

	@Test
	void testMovementClientSideDiamond() {
		urfquest.client.entities.mobs.Player p1c = c1.getState().getPlayer();
		int numStepsMoved = 10;
		double stepDistanceComponent = numStepsMoved*Constants.DEFAULT_PLAYER_VELOCITY*Math.sin(Math.toRadians(45));
		
		// check initial position
		double xPos1c = p1c.getPos()[0];
		double yPos1c = p1c.getPos()[1];
		Assertions.assertTrue(xPos1c > -5.0 && xPos1c < 5.0);
		Assertions.assertTrue(yPos1c > -5.0 && yPos1c < 5.0);

		// move southeast 10 times
		for (int i = 0; i < numStepsMoved; i++) {
			c1.getState().getPlayer().move(1, 1);
		}
		double xPos2c = p1c.getPos()[0];
		double yPos2c = p1c.getPos()[1];
		Assertions.assertEquals(xPos1c + stepDistanceComponent, xPos2c, 0.01);
		Assertions.assertEquals(yPos1c + stepDistanceComponent, yPos2c, 0.01);

		// move southwest 10 times
		for (int i = 0; i < numStepsMoved; i++) {
			c1.getState().getPlayer().move(-1, 1);
		}
		double xPos3c = p1c.getPos()[0];
		double yPos3c = p1c.getPos()[1];
		Assertions.assertEquals(xPos1c, xPos3c, 0.01);
		Assertions.assertEquals(yPos1c + stepDistanceComponent*2, yPos3c, 0.01);

		// move northwest 10 times
		for (int i = 0; i < numStepsMoved; i++) {
			c1.getState().getPlayer().move(-1, -1);
		}
		double xPos4c = p1c.getPos()[0];
		double yPos4c = p1c.getPos()[1];
		Assertions.assertEquals(xPos1c - stepDistanceComponent, xPos4c, 0.01);
		Assertions.assertEquals(yPos1c + stepDistanceComponent, yPos4c, 0.01);

		// move northeast 10 times
		for (int i = 0; i < numStepsMoved; i++) {
			c1.getState().getPlayer().move(1, -1);
		}
		double xPos5c = p1c.getPos()[0];
		double yPos5c = p1c.getPos()[1];
		Assertions.assertEquals(xPos1c, xPos5c, 0.01);
		Assertions.assertEquals(yPos1c, yPos5c, 0.01);
	}

}
