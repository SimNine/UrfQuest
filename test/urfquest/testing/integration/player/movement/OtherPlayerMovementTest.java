package urfquest.testing.integration.player.movement;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import urfquest.Logger;
import urfquest.LogLevel;
import urfquest.Main;
import urfquest.client.Client;
import urfquest.server.Server;
import urfquest.shared.Constants;
import urfquest.shared.Vector;

/*
 * Test that the movement of one player is reflected by the client of another player
 */
class OtherPlayerMovementTest {
	
	private Client c1;
	private Client c2;
	private Server s;

	@BeforeEach
	void setUp() throws Exception {
		s = new Server(0);
		c1 = new Client(s, "Chris");
		c2 = new Client(s, "Nick");
		
		Main.mainLogger = new Logger(LogLevel.ALL, "MAIN");
		
		s.attachLocalClient(c1);
		s.attachLocalClient(c2);
	}
	
	@Test
	void testMovementOneMotion() {
		urfquest.client.entities.mobs.Player c1p1 = c1.getState().getPlayer();
		urfquest.client.entities.mobs.Player c2p1 = c2.getState().getCurrentMap().getPlayer(c1p1.id);
		Assertions.assertNotNull(c2p1);
		
		int numStepsMoved = 10;
		
		// check initial position
		double x1c1p1 = c1p1.getPos()[0];
		double y1c1p1 = c1p1.getPos()[1];
		Assertions.assertTrue(x1c1p1 > -5.0 && x1c1p1 < 5.0);
		Assertions.assertTrue(y1c1p1 > -5.0 && y1c1p1 < 5.0);
		double x1c2p1 = c2p1.getPos()[0];
		double y1c2p1 = c2p1.getPos()[1];
		Assertions.assertTrue(x1c2p1 > -5.0 && x1c2p1 < 5.0);
		Assertions.assertTrue(y1c2p1 > -5.0 && y1c2p1 < 5.0);

		// move east 10 ticks
		c1.getState().getPlayer().setMovementVector(Vector.EAST, Constants.DEFAULT_VELOCITY_PLAYER, true);
		s.tick(numStepsMoved);
		
		// check second position
		double x2c1p1 = c1p1.getPos()[0];
		double y2c1p1 = c1p1.getPos()[1];
		Assertions.assertEquals(x1c1p1 + numStepsMoved*Constants.DEFAULT_VELOCITY_PLAYER, x2c1p1, Constants.TESTING_POSITION_TOLERANCE);
		Assertions.assertEquals(y1c1p1, y2c1p1, Constants.TESTING_POSITION_TOLERANCE);
		double x2c2p1 = c2p1.getPos()[0];
		double y2c2p1 = c2p1.getPos()[1];
		Assertions.assertEquals(x1c2p1 + numStepsMoved*Constants.DEFAULT_VELOCITY_PLAYER, x2c2p1, Constants.TESTING_POSITION_TOLERANCE);
		Assertions.assertEquals(y1c2p1, y2c2p1, Constants.TESTING_POSITION_TOLERANCE);
	}

	@Test
	void testMovementSquare() {
		urfquest.client.entities.mobs.Player c1p1 = c1.getState().getPlayer();
		urfquest.client.entities.mobs.Player c2p1 = c2.getState().getCurrentMap().getPlayer(c1p1.id);
		Assertions.assertNotNull(c2p1);
		
		int numStepsMoved = 10;
		
		// check initial position
		double x1c1p1 = c1p1.getPos()[0];
		double y1c1p1 = c1p1.getPos()[1];
		Assertions.assertTrue(x1c1p1 > -5.0 && x1c1p1 < 5.0);
		Assertions.assertTrue(y1c1p1 > -5.0 && y1c1p1 < 5.0);
		double x1c2p1 = c2p1.getPos()[0];
		double y1c2p1 = c2p1.getPos()[1];
		Assertions.assertTrue(x1c2p1 > -5.0 && x1c2p1 < 5.0);
		Assertions.assertTrue(y1c2p1 > -5.0 && y1c2p1 < 5.0);

		// move east 10 ticks
		c1.getState().getPlayer().setMovementVector(Vector.EAST, Constants.DEFAULT_VELOCITY_PLAYER, true);
		s.tick(numStepsMoved);
		
		// check second position
		double x2c1p1 = c1p1.getPos()[0];
		double y2c1p1 = c1p1.getPos()[1];
		Assertions.assertEquals(x1c1p1 + numStepsMoved*Constants.DEFAULT_VELOCITY_PLAYER, x2c1p1, Constants.TESTING_POSITION_TOLERANCE);
		Assertions.assertEquals(y1c1p1, y2c1p1, Constants.TESTING_POSITION_TOLERANCE);
		double x2c2p1 = c2p1.getPos()[0];
		double y2c2p1 = c2p1.getPos()[1];
		Assertions.assertEquals(x1c2p1 + numStepsMoved*Constants.DEFAULT_VELOCITY_PLAYER, x2c2p1, Constants.TESTING_POSITION_TOLERANCE);
		Assertions.assertEquals(y1c2p1, y2c2p1, Constants.TESTING_POSITION_TOLERANCE);

		// move south 10 ticks
		c1.getState().getPlayer().setMovementVector(Vector.SOUTH, Constants.DEFAULT_VELOCITY_PLAYER, true);
		s.tick(numStepsMoved);
		
		// check third position
		double x3c1p1 = c1p1.getPos()[0];
		double y3c1p1 = c1p1.getPos()[1];
		Assertions.assertEquals(x1c1p1 + numStepsMoved*Constants.DEFAULT_VELOCITY_PLAYER, x3c1p1, Constants.TESTING_POSITION_TOLERANCE);
		Assertions.assertEquals(y1c1p1 + numStepsMoved*Constants.DEFAULT_VELOCITY_PLAYER, y3c1p1, Constants.TESTING_POSITION_TOLERANCE);
		double x3c2p1 = c2p1.getPos()[0];
		double y3c2p1 = c2p1.getPos()[1];
		Assertions.assertEquals(x1c2p1 + numStepsMoved*Constants.DEFAULT_VELOCITY_PLAYER, x3c2p1, Constants.TESTING_POSITION_TOLERANCE);
		Assertions.assertEquals(y1c2p1 + numStepsMoved*Constants.DEFAULT_VELOCITY_PLAYER, y3c2p1, Constants.TESTING_POSITION_TOLERANCE);

		// move west 10 ticks
		c1.getState().getPlayer().setMovementVector(Vector.WEST, Constants.DEFAULT_VELOCITY_PLAYER, true);
		s.tick(numStepsMoved);
		
		// check fourth position
		double x4c1p1 = c1p1.getPos()[0];
		double y4c1p1 = c1p1.getPos()[1];
		Assertions.assertEquals(x1c1p1, x4c1p1, Constants.TESTING_POSITION_TOLERANCE);
		Assertions.assertEquals(y1c1p1 + numStepsMoved*Constants.DEFAULT_VELOCITY_PLAYER, y4c1p1, Constants.TESTING_POSITION_TOLERANCE);
		double x4c2p1 = c2p1.getPos()[0];
		double y4c2p1 = c2p1.getPos()[1];
		Assertions.assertEquals(x1c2p1, x4c2p1, Constants.TESTING_POSITION_TOLERANCE);
		Assertions.assertEquals(y1c2p1 + numStepsMoved*Constants.DEFAULT_VELOCITY_PLAYER, y4c2p1, Constants.TESTING_POSITION_TOLERANCE);

		// move north 10 ticks
		c1.getState().getPlayer().setMovementVector(Vector.NORTH, Constants.DEFAULT_VELOCITY_PLAYER, true);
		s.tick(numStepsMoved);
		
		// check fifth position
		double x5c1p1 = c1p1.getPos()[0];
		double y5c1p1 = c1p1.getPos()[1];
		Assertions.assertEquals(x1c1p1, x5c1p1, Constants.TESTING_POSITION_TOLERANCE);
		Assertions.assertEquals(y1c1p1, y5c1p1, Constants.TESTING_POSITION_TOLERANCE);
		double x5c2p1 = c2p1.getPos()[0];
		double y5c2p1 = c2p1.getPos()[1];
		Assertions.assertEquals(x1c2p1, x5c2p1, Constants.TESTING_POSITION_TOLERANCE);
		Assertions.assertEquals(y1c2p1, y5c2p1, Constants.TESTING_POSITION_TOLERANCE);
	}

	@Test
	void testMovementDiamond() {
		urfquest.client.entities.mobs.Player c1p1 = c1.getState().getPlayer();
		urfquest.client.entities.mobs.Player c2p1 = c2.getState().getCurrentMap().getPlayer(c1p1.id);
		Assertions.assertNotNull(c2p1);
		
		int numStepsMoved = 10;
		double stepDistanceComponent = numStepsMoved*Constants.DEFAULT_VELOCITY_PLAYER*Math.sin(Math.toRadians(45));
		
		// check initial position
		double x1c1p1 = c1p1.getPos()[0];
		double y1c1p1 = c1p1.getPos()[1];
		Assertions.assertTrue(x1c1p1 > -5.0 && x1c1p1 < 5.0);
		Assertions.assertTrue(y1c1p1 > -5.0 && y1c1p1 < 5.0);
		double x1c2p1 = c2p1.getPos()[0];
		double y1c2p1 = c2p1.getPos()[1];
		Assertions.assertTrue(x1c2p1 > -5.0 && x1c2p1 < 5.0);
		Assertions.assertTrue(y1c2p1 > -5.0 && y1c2p1 < 5.0);

		// move southeast 10 ticks
		c1.getState().getPlayer().setMovementVector(Vector.SOUTHEAST, Constants.DEFAULT_VELOCITY_PLAYER, true);
		s.tick(numStepsMoved);
		
		// check second position
		double x2c1p1 = c1p1.getPos()[0];
		double y2c1p1 = c1p1.getPos()[1];
		Assertions.assertEquals(x1c1p1 + stepDistanceComponent, x2c1p1, Constants.TESTING_POSITION_TOLERANCE);
		Assertions.assertEquals(y1c1p1 + stepDistanceComponent, y2c1p1, Constants.TESTING_POSITION_TOLERANCE);
		double x2c2p1 = c2p1.getPos()[0];
		double y2c2p1 = c2p1.getPos()[1];
		Assertions.assertEquals(x1c2p1 + stepDistanceComponent, x2c2p1, Constants.TESTING_POSITION_TOLERANCE);
		Assertions.assertEquals(y1c2p1 + stepDistanceComponent, y2c2p1, Constants.TESTING_POSITION_TOLERANCE);

		// move southwest 10 ticks
		c1.getState().getPlayer().setMovementVector(Vector.SOUTHWEST, Constants.DEFAULT_VELOCITY_PLAYER, true);
		s.tick(numStepsMoved);
		
		// check third position
		double x3c1p1 = c1p1.getPos()[0];
		double y3c1p1 = c1p1.getPos()[1];
		Assertions.assertEquals(x1c1p1, x3c1p1, Constants.TESTING_POSITION_TOLERANCE);
		Assertions.assertEquals(y1c1p1 + stepDistanceComponent*2, y3c1p1, Constants.TESTING_POSITION_TOLERANCE);
		double x3c2p1 = c2p1.getPos()[0];
		double y3c2p1 = c2p1.getPos()[1];
		Assertions.assertEquals(x1c2p1, x3c2p1, Constants.TESTING_POSITION_TOLERANCE);
		Assertions.assertEquals(y1c2p1 + stepDistanceComponent*2, y3c2p1, Constants.TESTING_POSITION_TOLERANCE);

		// move northwest 10 ticks
		c1.getState().getPlayer().setMovementVector(Vector.NORTHWEST, Constants.DEFAULT_VELOCITY_PLAYER, true);
		s.tick(numStepsMoved);
		
		// check fourth position
		double x4c1p1 = c1p1.getPos()[0];
		double y4c1p1 = c1p1.getPos()[1];
		Assertions.assertEquals(x1c1p1 - stepDistanceComponent, x4c1p1, Constants.TESTING_POSITION_TOLERANCE);
		Assertions.assertEquals(y1c1p1 + stepDistanceComponent, y4c1p1, Constants.TESTING_POSITION_TOLERANCE);
		double x4c2p1 = c2p1.getPos()[0];
		double y4c2p1 = c2p1.getPos()[1];
		Assertions.assertEquals(x1c2p1 - stepDistanceComponent, x4c2p1, Constants.TESTING_POSITION_TOLERANCE);
		Assertions.assertEquals(y1c2p1 + stepDistanceComponent, y4c2p1, Constants.TESTING_POSITION_TOLERANCE);

		// move northeast 10 ticks
		c1.getState().getPlayer().setMovementVector(Vector.NORTHEAST, Constants.DEFAULT_VELOCITY_PLAYER, true);
		s.tick(numStepsMoved);
		
		// check fifth position
		double x5c1p1 = c1p1.getPos()[0];
		double y5c1p1 = c1p1.getPos()[1];
		Assertions.assertEquals(x1c1p1, x5c1p1, Constants.TESTING_POSITION_TOLERANCE);
		Assertions.assertEquals(y1c1p1, y5c1p1, Constants.TESTING_POSITION_TOLERANCE);
		double x5c2p1 = c2p1.getPos()[0];
		double y5c2p1 = c2p1.getPos()[1];
		Assertions.assertEquals(x1c2p1, x5c2p1, Constants.TESTING_POSITION_TOLERANCE);
		Assertions.assertEquals(y1c2p1, y5c2p1, Constants.TESTING_POSITION_TOLERANCE);
	}

}
