package xyz.urffer.urfquest.testing.integration.player.movement;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import xyz.urffer.urfquest.client.Client;
import xyz.urffer.urfquest.server.Server;
import xyz.urffer.urfquest.shared.Constants;
import xyz.urffer.urfquest.shared.Vector;

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
		xyz.urffer.urfquest.server.entities.mobs.Player p1s = s.getState().getPlayer(s.getUserMap().getPlayerIdFromClientId(c1.getClientID()));
		int numStepsMoved = 10;
		
		// check initial position
		double xPos1s = p1s.getPos()[0];
		double yPos1s = p1s.getPos()[1];
		Assertions.assertTrue(xPos1s > -5.0 && xPos1s < 5.0);
		Assertions.assertTrue(yPos1s > -5.0 && yPos1s < 5.0);

		// move east 10 ticks
		c1.getState().getPlayer().setMovementVector(Vector.EAST, Constants.DEFAULT_VELOCITY_PLAYER, true);
		s.tick(numStepsMoved);
		double xPos2s = p1s.getPos()[0];
		double yPos2s = p1s.getPos()[1];
		Assertions.assertEquals(xPos1s + numStepsMoved*Constants.DEFAULT_VELOCITY_PLAYER, xPos2s, Constants.TESTING_POSITION_TOLERANCE);
		Assertions.assertEquals(yPos1s, yPos2s, Constants.TESTING_POSITION_TOLERANCE);
	}

	@Test
	void testMovementServerSideSquare() {
		xyz.urffer.urfquest.server.entities.mobs.Player p1s = s.getState().getPlayer(s.getUserMap().getPlayerIdFromClientId(c1.getClientID()));
		int numStepsMoved = 10;
		
		// check initial position
		double xPos1s = p1s.getPos()[0];
		double yPos1s = p1s.getPos()[1];
		Assertions.assertTrue(xPos1s > -5.0 && xPos1s < 5.0);
		Assertions.assertTrue(yPos1s > -5.0 && yPos1s < 5.0);

		// move east 10 ticks
		c1.getState().getPlayer().setMovementVector(Vector.EAST, Constants.DEFAULT_VELOCITY_PLAYER, true);
		s.tick(numStepsMoved);
		double xPos2s = p1s.getPos()[0];
		double yPos2s = p1s.getPos()[1];
		Assertions.assertEquals(xPos1s + numStepsMoved*Constants.DEFAULT_VELOCITY_PLAYER, xPos2s, Constants.TESTING_POSITION_TOLERANCE);
		Assertions.assertEquals(yPos1s, yPos2s, Constants.TESTING_POSITION_TOLERANCE);

		// move south 10 ticks
		c1.getState().getPlayer().setMovementVector(Vector.SOUTH, Constants.DEFAULT_VELOCITY_PLAYER, true);
		s.tick(numStepsMoved);
		double xPos3s = p1s.getPos()[0];
		double yPos3s = p1s.getPos()[1];
		Assertions.assertEquals(xPos1s + numStepsMoved*Constants.DEFAULT_VELOCITY_PLAYER, xPos3s, Constants.TESTING_POSITION_TOLERANCE);
		Assertions.assertEquals(yPos1s + numStepsMoved*Constants.DEFAULT_VELOCITY_PLAYER, yPos3s, Constants.TESTING_POSITION_TOLERANCE);

		// move west 10 ticks
		c1.getState().getPlayer().setMovementVector(Vector.WEST, Constants.DEFAULT_VELOCITY_PLAYER, true);
		s.tick(numStepsMoved);
		double xPos4s = p1s.getPos()[0];
		double yPos4s = p1s.getPos()[1];
		Assertions.assertEquals(xPos1s, xPos4s, Constants.TESTING_POSITION_TOLERANCE);
		Assertions.assertEquals(yPos1s + numStepsMoved*Constants.DEFAULT_VELOCITY_PLAYER, yPos4s, Constants.TESTING_POSITION_TOLERANCE);

		// move north 10 times
		c1.getState().getPlayer().setMovementVector(Vector.NORTH, Constants.DEFAULT_VELOCITY_PLAYER, true);
		s.tick(numStepsMoved);
		double xPos5s = p1s.getPos()[0];
		double yPos5s = p1s.getPos()[1];
		Assertions.assertEquals(xPos1s, xPos5s, Constants.TESTING_POSITION_TOLERANCE);
		Assertions.assertEquals(yPos1s, yPos5s, Constants.TESTING_POSITION_TOLERANCE);
	}

	@Test
	void testMovementServerSideDiamond() {
		xyz.urffer.urfquest.server.entities.mobs.Player p1s = s.getState().getPlayer(s.getUserMap().getPlayerIdFromClientId(c1.getClientID()));
		int numStepsMoved = 10;
		double stepDistanceComponent = numStepsMoved*Constants.DEFAULT_VELOCITY_PLAYER*Math.sin(Math.toRadians(45));
		
		// check initial position
		double xPos1s = p1s.getPos()[0];
		double yPos1s = p1s.getPos()[1];
		Assertions.assertTrue(xPos1s > -5.0 && xPos1s < 5.0);
		Assertions.assertTrue(yPos1s > -5.0 && yPos1s < 5.0);

		// move southeast 10 ticks
		c1.getState().getPlayer().setMovementVector(Vector.SOUTHEAST, Constants.DEFAULT_VELOCITY_PLAYER, true);
		s.tick(numStepsMoved);
		double xPos2s = p1s.getPos()[0];
		double yPos2s = p1s.getPos()[1];
		Assertions.assertEquals(xPos1s + stepDistanceComponent, xPos2s, Constants.TESTING_POSITION_TOLERANCE);
		Assertions.assertEquals(yPos1s + stepDistanceComponent, yPos2s, Constants.TESTING_POSITION_TOLERANCE);

		// move southwest 10 ticks
		c1.getState().getPlayer().setMovementVector(Vector.SOUTHWEST, Constants.DEFAULT_VELOCITY_PLAYER, true);
		s.tick(numStepsMoved);
		double xPos3s = p1s.getPos()[0];
		double yPos3s = p1s.getPos()[1];
		Assertions.assertEquals(xPos1s, xPos3s, Constants.TESTING_POSITION_TOLERANCE);
		Assertions.assertEquals(yPos1s + stepDistanceComponent*2, yPos3s, Constants.TESTING_POSITION_TOLERANCE);

		// move northwest 10 ticks
		c1.getState().getPlayer().setMovementVector(Vector.NORTHWEST, Constants.DEFAULT_VELOCITY_PLAYER, true);
		s.tick(numStepsMoved);
		double xPos4s = p1s.getPos()[0];
		double yPos4s = p1s.getPos()[1];
		Assertions.assertEquals(xPos1s - stepDistanceComponent, xPos4s, Constants.TESTING_POSITION_TOLERANCE);
		Assertions.assertEquals(yPos1s + stepDistanceComponent, yPos4s, Constants.TESTING_POSITION_TOLERANCE);

		// move east 10 ticks
		c1.getState().getPlayer().setMovementVector(Vector.NORTHEAST, Constants.DEFAULT_VELOCITY_PLAYER, true);
		s.tick(numStepsMoved);
		double xPos5s = p1s.getPos()[0];
		double yPos5s = p1s.getPos()[1];
		Assertions.assertEquals(xPos1s, xPos5s, Constants.TESTING_POSITION_TOLERANCE);
		Assertions.assertEquals(yPos1s, yPos5s, Constants.TESTING_POSITION_TOLERANCE);
	}

	@Test
	void testMovementClientSideOneMotion() {
		xyz.urffer.urfquest.client.entities.mobs.Player p1c = c1.getState().getPlayer();
		int numStepsMoved = 10;
		
		// check initial position
		double xPos1c = p1c.getPos()[0];
		double yPos1c = p1c.getPos()[1];
		Assertions.assertTrue(xPos1c > -5.0 && xPos1c < 5.0);
		Assertions.assertTrue(yPos1c > -5.0 && yPos1c < 5.0);

		// move east 10 ticks
		c1.getState().getPlayer().setMovementVector(Vector.EAST, Constants.DEFAULT_VELOCITY_PLAYER, true);
		s.tick(numStepsMoved);
		double xPos2c = p1c.getPos()[0];
		double yPos2c = p1c.getPos()[1];
		Assertions.assertEquals(xPos1c + numStepsMoved*Constants.DEFAULT_VELOCITY_PLAYER, xPos2c, Constants.TESTING_POSITION_TOLERANCE);
		Assertions.assertEquals(yPos1c, yPos2c, Constants.TESTING_POSITION_TOLERANCE);
	}

	@Test
	void testMovementClientSideSquare() {
		xyz.urffer.urfquest.client.entities.mobs.Player p1c = c1.getState().getPlayer();
		int numStepsMoved = 10;
		
		// check initial position
		double xPos1c = p1c.getPos()[0];
		double yPos1c = p1c.getPos()[1];
		Assertions.assertTrue(xPos1c > -5.0 && xPos1c < 5.0);
		Assertions.assertTrue(yPos1c > -5.0 && yPos1c < 5.0);

		// move east 10 ticks
		c1.getState().getPlayer().setMovementVector(Vector.EAST, Constants.DEFAULT_VELOCITY_PLAYER, true);
		s.tick(numStepsMoved);
		double xPos2c = p1c.getPos()[0];
		double yPos2c = p1c.getPos()[1];
		Assertions.assertEquals(xPos1c + numStepsMoved*Constants.DEFAULT_VELOCITY_PLAYER, xPos2c, Constants.TESTING_POSITION_TOLERANCE);
		Assertions.assertEquals(yPos1c, yPos2c, Constants.TESTING_POSITION_TOLERANCE);

		// move south 10 ticks
		c1.getState().getPlayer().setMovementVector(Vector.SOUTH, Constants.DEFAULT_VELOCITY_PLAYER, true);
		s.tick(numStepsMoved);
		double xPos3c = p1c.getPos()[0];
		double yPos3c = p1c.getPos()[1];
		Assertions.assertEquals(xPos1c + numStepsMoved*Constants.DEFAULT_VELOCITY_PLAYER, xPos3c, Constants.TESTING_POSITION_TOLERANCE);
		Assertions.assertEquals(yPos1c + numStepsMoved*Constants.DEFAULT_VELOCITY_PLAYER, yPos3c, Constants.TESTING_POSITION_TOLERANCE);

		// move west 10 ticks
		c1.getState().getPlayer().setMovementVector(Vector.WEST, Constants.DEFAULT_VELOCITY_PLAYER, true);
		s.tick(numStepsMoved);
		double xPos4c = p1c.getPos()[0];
		double yPos4c = p1c.getPos()[1];
		Assertions.assertEquals(xPos1c, xPos4c, Constants.TESTING_POSITION_TOLERANCE);
		Assertions.assertEquals(yPos1c + numStepsMoved*Constants.DEFAULT_VELOCITY_PLAYER, yPos4c, Constants.TESTING_POSITION_TOLERANCE);

		// move north 10 ticks
		c1.getState().getPlayer().setMovementVector(Vector.NORTH, Constants.DEFAULT_VELOCITY_PLAYER, true);
		s.tick(numStepsMoved);
		double xPos5c = p1c.getPos()[0];
		double yPos5c = p1c.getPos()[1];
		Assertions.assertEquals(xPos1c, xPos5c, Constants.TESTING_POSITION_TOLERANCE);
		Assertions.assertEquals(yPos1c, yPos5c, Constants.TESTING_POSITION_TOLERANCE);
	}

	@Test
	void testMovementClientSideDiamond() {
		xyz.urffer.urfquest.client.entities.mobs.Player p1c = c1.getState().getPlayer();
		int numStepsMoved = 10;
		double stepDistanceComponent = numStepsMoved*Constants.DEFAULT_VELOCITY_PLAYER*Math.sin(Math.toRadians(45));
		
		// check initial position
		double xPos1c = p1c.getPos()[0];
		double yPos1c = p1c.getPos()[1];
		Assertions.assertTrue(xPos1c > -5.0 && xPos1c < 5.0);
		Assertions.assertTrue(yPos1c > -5.0 && yPos1c < 5.0);

		// move southeast 10 ticks
		c1.getState().getPlayer().setMovementVector(Vector.SOUTHEAST, Constants.DEFAULT_VELOCITY_PLAYER, true);
		s.tick(numStepsMoved);
		double xPos2c = p1c.getPos()[0];
		double yPos2c = p1c.getPos()[1];
		Assertions.assertEquals(xPos1c + stepDistanceComponent, xPos2c, Constants.TESTING_POSITION_TOLERANCE);
		Assertions.assertEquals(yPos1c + stepDistanceComponent, yPos2c, Constants.TESTING_POSITION_TOLERANCE);

		// move southwest 10 ticks
		c1.getState().getPlayer().setMovementVector(Vector.SOUTHWEST, Constants.DEFAULT_VELOCITY_PLAYER, true);
		s.tick(numStepsMoved);
		double xPos3c = p1c.getPos()[0];
		double yPos3c = p1c.getPos()[1];
		Assertions.assertEquals(xPos1c, xPos3c, Constants.TESTING_POSITION_TOLERANCE);
		Assertions.assertEquals(yPos1c + stepDistanceComponent*2, yPos3c, Constants.TESTING_POSITION_TOLERANCE);

		// move northwest 10 ticks
		c1.getState().getPlayer().setMovementVector(Vector.NORTHWEST, Constants.DEFAULT_VELOCITY_PLAYER, true);
		s.tick(numStepsMoved);
		double xPos4c = p1c.getPos()[0];
		double yPos4c = p1c.getPos()[1];
		Assertions.assertEquals(xPos1c - stepDistanceComponent, xPos4c, Constants.TESTING_POSITION_TOLERANCE);
		Assertions.assertEquals(yPos1c + stepDistanceComponent, yPos4c, Constants.TESTING_POSITION_TOLERANCE);

		// move northeast 10 ticks
		c1.getState().getPlayer().setMovementVector(Vector.NORTHEAST, Constants.DEFAULT_VELOCITY_PLAYER, true);
		s.tick(numStepsMoved);
		double xPos5c = p1c.getPos()[0];
		double yPos5c = p1c.getPos()[1];
		Assertions.assertEquals(xPos1c, xPos5c, Constants.TESTING_POSITION_TOLERANCE);
		Assertions.assertEquals(yPos1c, yPos5c, Constants.TESTING_POSITION_TOLERANCE);
	}

}
