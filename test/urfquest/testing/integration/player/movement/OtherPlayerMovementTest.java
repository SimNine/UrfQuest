package urfquest.testing.integration.player.movement;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import urfquest.Logger;
import urfquest.Logger.LogLevel;
import urfquest.Main;
import urfquest.client.Client;
import urfquest.server.Server;
import urfquest.shared.Constants;

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
		
		Main.mainLogger = new Logger(LogLevel.LOG_ALL, "MAIN");
		
		s.attachLocalClient(c1);
		// TODO: The following line is necessary because of a concurrency bug of some kind.
		// It appears that the attachLocal above and the attachLocal below run in such a way that
		// the c1's player is not added to the server map's list of players.
		// With this line added, the server seems to add the clients' players correctly.
		Thread.sleep(100);
		s.attachLocalClient(c2);
	}
	
	@Test
	void testMovementOneMotion() {
		urfquest.client.entities.mobs.Player c1p1 = c1.getState().getPlayer();
		urfquest.client.entities.mobs.Player c2p1 = c2.getState().getCurrentMap().getPlayer(c1p1.id);
		if (c2p1 == null) {
			// TODO: remove this when bug is found
			System.out.println("This print statement is here as a placeholder for a breakpoint that "
					+ "occurs periodically but I can't seem to replicate intentionally. Likely "
					+ "some kind of race condition");
		}
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

		// move right 10 times
		for (int i = 0; i < numStepsMoved; i++) {
			c1.getState().getPlayer().move(1, 0);
		}
		
		// check second position
		double x2c1p1 = c1p1.getPos()[0];
		double y2c1p1 = c1p1.getPos()[1];
		Assertions.assertEquals(x1c1p1 + numStepsMoved*Constants.DEFAULT_PLAYER_VELOCITY, x2c1p1, 0.01);
		Assertions.assertEquals(y1c1p1, y2c1p1, 0.01);
		double x2c2p1 = c2p1.getPos()[0];
		double y2c2p1 = c2p1.getPos()[1];
		Assertions.assertEquals(x1c2p1 + numStepsMoved*Constants.DEFAULT_PLAYER_VELOCITY, x2c2p1, 0.01);
		Assertions.assertEquals(y1c2p1, y2c2p1, 0.01);
	}

	@Test
	void testMovementSquare() {
		urfquest.client.entities.mobs.Player c1p1 = c1.getState().getPlayer();
		urfquest.client.entities.mobs.Player c2p1 = c2.getState().getCurrentMap().getPlayer(c1p1.id);
		if (c2p1 == null) {
			// TODO: remove this when bug is found
			System.out.println("This print statement is here as a placeholder for a breakpoint that "
					+ "occurs periodically but I can't seem to replicate intentionally. Likely "
					+ "some kind of race condition");
		}
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

		// move right 10 times
		for (int i = 0; i < numStepsMoved; i++) {
			c1.getState().getPlayer().move(1, 0);
		}
		
		// check second position
		double x2c1p1 = c1p1.getPos()[0];
		double y2c1p1 = c1p1.getPos()[1];
		Assertions.assertEquals(x1c1p1 + numStepsMoved*Constants.DEFAULT_PLAYER_VELOCITY, x2c1p1, 0.01);
		Assertions.assertEquals(y1c1p1, y2c1p1, 0.01);
		double x2c2p1 = c2p1.getPos()[0];
		double y2c2p1 = c2p1.getPos()[1];
		Assertions.assertEquals(x1c2p1 + numStepsMoved*Constants.DEFAULT_PLAYER_VELOCITY, x2c2p1, 0.01);
		Assertions.assertEquals(y1c2p1, y2c2p1, 0.01);
		
		// move down 10 times
		for (int i = 0; i < numStepsMoved; i++) {
			c1.getState().getPlayer().move(0, 1);
		}
		
		// check third position
		double x3c1p1 = c1p1.getPos()[0];
		double y3c1p1 = c1p1.getPos()[1];
		Assertions.assertEquals(x1c1p1 + numStepsMoved*Constants.DEFAULT_PLAYER_VELOCITY, x3c1p1, 0.01);
		Assertions.assertEquals(y1c1p1 + numStepsMoved*Constants.DEFAULT_PLAYER_VELOCITY, y3c1p1, 0.01);
		double x3c2p1 = c2p1.getPos()[0];
		double y3c2p1 = c2p1.getPos()[1];
		Assertions.assertEquals(x1c2p1 + numStepsMoved*Constants.DEFAULT_PLAYER_VELOCITY, x3c2p1, 0.01);
		Assertions.assertEquals(y1c2p1 + numStepsMoved*Constants.DEFAULT_PLAYER_VELOCITY, y3c2p1, 0.01);
		
		// move left 10 times
		for (int i = 0; i < numStepsMoved; i++) {
			c1.getState().getPlayer().move(-1, 0);
		}
		
		// check fourth position
		double x4c1p1 = c1p1.getPos()[0];
		double y4c1p1 = c1p1.getPos()[1];
		Assertions.assertEquals(x1c1p1, x4c1p1, 0.01);
		Assertions.assertEquals(y1c1p1 + numStepsMoved*Constants.DEFAULT_PLAYER_VELOCITY, y4c1p1, 0.01);
		double x4c2p1 = c2p1.getPos()[0];
		double y4c2p1 = c2p1.getPos()[1];
		Assertions.assertEquals(x1c2p1, x4c2p1, 0.01);
		Assertions.assertEquals(y1c2p1 + numStepsMoved*Constants.DEFAULT_PLAYER_VELOCITY, y4c2p1, 0.01);
		
		// move up 10 times
		for (int i = 0; i < numStepsMoved; i++) {
			c1.getState().getPlayer().move(0, -1);
		}
		
		// check fifth position
		double x5c1p1 = c1p1.getPos()[0];
		double y5c1p1 = c1p1.getPos()[1];
		Assertions.assertEquals(x1c1p1, x5c1p1, 0.01);
		Assertions.assertEquals(y1c1p1, y5c1p1, 0.01);
		double x5c2p1 = c2p1.getPos()[0];
		double y5c2p1 = c2p1.getPos()[1];
		Assertions.assertEquals(x1c2p1, x5c2p1, 0.01);
		Assertions.assertEquals(y1c2p1, y5c2p1, 0.01);
	}

	@Test
	void testMovementDiamond() {
		urfquest.client.entities.mobs.Player c1p1 = c1.getState().getPlayer();
		urfquest.client.entities.mobs.Player c2p1 = c2.getState().getCurrentMap().getPlayer(c1p1.id);
		if (c2p1 == null) {
			// TODO: remove this when bug is found
			System.out.println("This print statement is here as a placeholder for a breakpoint that "
					+ "occurs periodically but I can't seem to replicate intentionally. Likely "
					+ "some kind of race condition");
		}
		Assertions.assertNotNull(c2p1);
		
		int numStepsMoved = 10;
		double stepDistanceComponent = numStepsMoved*Constants.DEFAULT_PLAYER_VELOCITY*Math.sin(Math.toRadians(45));
		
		// check initial position
		double x1c1p1 = c1p1.getPos()[0];
		double y1c1p1 = c1p1.getPos()[1];
		Assertions.assertTrue(x1c1p1 > -5.0 && x1c1p1 < 5.0);
		Assertions.assertTrue(y1c1p1 > -5.0 && y1c1p1 < 5.0);
		double x1c2p1 = c2p1.getPos()[0];
		double y1c2p1 = c2p1.getPos()[1];
		Assertions.assertTrue(x1c2p1 > -5.0 && x1c2p1 < 5.0);
		Assertions.assertTrue(y1c2p1 > -5.0 && y1c2p1 < 5.0);

		// move southeast 10 times
		for (int i = 0; i < numStepsMoved; i++) {
			c1.getState().getPlayer().move(1, 1);
		}
		
		// check second position
		double x2c1p1 = c1p1.getPos()[0];
		double y2c1p1 = c1p1.getPos()[1];
		Assertions.assertEquals(x1c1p1 + stepDistanceComponent, x2c1p1, 0.01);
		Assertions.assertEquals(y1c1p1 + stepDistanceComponent, y2c1p1, 0.01);
		double x2c2p1 = c2p1.getPos()[0];
		double y2c2p1 = c2p1.getPos()[1];
		Assertions.assertEquals(x1c2p1 + stepDistanceComponent, x2c2p1, 0.01);
		Assertions.assertEquals(y1c2p1 + stepDistanceComponent, y2c2p1, 0.01);
		
		// move southwest 10 times
		for (int i = 0; i < numStepsMoved; i++) {
			c1.getState().getPlayer().move(-1, 1);
		}
		
		// check third position
		double x3c1p1 = c1p1.getPos()[0];
		double y3c1p1 = c1p1.getPos()[1];
		Assertions.assertEquals(x1c1p1, x3c1p1, 0.01);
		Assertions.assertEquals(y1c1p1 + stepDistanceComponent*2, y3c1p1, 0.01);
		double x3c2p1 = c2p1.getPos()[0];
		double y3c2p1 = c2p1.getPos()[1];
		Assertions.assertEquals(x1c2p1, x3c2p1, 0.01);
		Assertions.assertEquals(y1c2p1 + stepDistanceComponent*2, y3c2p1, 0.01);
		
		// move northwest 10 times
		for (int i = 0; i < numStepsMoved; i++) {
			c1.getState().getPlayer().move(-1, -1);
		}
		
		// check fourth position
		double x4c1p1 = c1p1.getPos()[0];
		double y4c1p1 = c1p1.getPos()[1];
		Assertions.assertEquals(x1c1p1 - stepDistanceComponent, x4c1p1, 0.01);
		Assertions.assertEquals(y1c1p1 + stepDistanceComponent, y4c1p1, 0.01);
		double x4c2p1 = c2p1.getPos()[0];
		double y4c2p1 = c2p1.getPos()[1];
		Assertions.assertEquals(x1c2p1 - stepDistanceComponent, x4c2p1, 0.01);
		Assertions.assertEquals(y1c2p1 + stepDistanceComponent, y4c2p1, 0.01);
		
		// move northeast 10 times
		for (int i = 0; i < numStepsMoved; i++) {
			c1.getState().getPlayer().move(1, -1);
		}
		
		// check fifth position
		double x5c1p1 = c1p1.getPos()[0];
		double y5c1p1 = c1p1.getPos()[1];
		Assertions.assertEquals(x1c1p1, x5c1p1, 0.01);
		Assertions.assertEquals(y1c1p1, y5c1p1, 0.01);
		double x5c2p1 = c2p1.getPos()[0];
		double y5c2p1 = c2p1.getPos()[1];
		Assertions.assertEquals(x1c2p1, x5c2p1, 0.01);
		Assertions.assertEquals(y1c2p1, y5c2p1, 0.01);
	}

}
