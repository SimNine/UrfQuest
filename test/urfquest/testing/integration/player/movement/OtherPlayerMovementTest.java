package urfquest.testing.integration.player.movement;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import urfquest.Main;
import urfquest.client.Client;
import urfquest.server.Server;
import urfquest.shared.message.Constants;

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
		
		Main.server = s;
		Main.client = c1;
		
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

		// move right 10 times
		for (int i = 0; i < numStepsMoved; i++) {
			c1.getState().getPlayer().move(1, 0);
		}
		
		// check second position
		double x2c1p1 = c1p1.getPos()[0];
		double y2c1p1 = c1p1.getPos()[1];
		Assertions.assertEquals(x1c1p1 + numStepsMoved*Constants.playerVelocity, x2c1p1, 0.01);
		Assertions.assertEquals(y1c1p1, y2c1p1, 0.01);
		double x2c2p1 = c2p1.getPos()[0];
		double y2c2p1 = c2p1.getPos()[1];
		Assertions.assertEquals(x1c2p1 + numStepsMoved*Constants.playerVelocity, x2c2p1, 0.01);
		Assertions.assertEquals(y1c2p1, y2c2p1, 0.01);
	}

	@Test
	void testMovementSquare() {
		urfquest.client.entities.mobs.Player c1p1 = c1.getState().getPlayer();
		//System.out.println(c1p1.id);
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

		// move right 10 times
		for (int i = 0; i < numStepsMoved; i++) {
			c1.getState().getPlayer().move(1, 0);
		}
		
		// check second position
		double x2c1p1 = c1p1.getPos()[0];
		double y2c1p1 = c1p1.getPos()[1];
		Assertions.assertEquals(x1c1p1 + numStepsMoved*Constants.playerVelocity, x2c1p1, 0.01);
		Assertions.assertEquals(y1c1p1, y2c1p1, 0.01);
		double x2c2p1 = c2p1.getPos()[0];
		double y2c2p1 = c2p1.getPos()[1];
		Assertions.assertEquals(x1c2p1 + numStepsMoved*Constants.playerVelocity, x2c2p1, 0.01);
		Assertions.assertEquals(y1c2p1, y2c2p1, 0.01);
		
		// move down 10 times
		for (int i = 0; i < numStepsMoved; i++) {
			c1.getState().getPlayer().move(0, 1);
		}
		
		// check third position
		double x3c1p1 = c1p1.getPos()[0];
		double y3c1p1 = c1p1.getPos()[1];
		Assertions.assertEquals(x2c1p1 + numStepsMoved*Constants.playerVelocity, x3c1p1, 0.01);
		Assertions.assertEquals(y2c1p1 + numStepsMoved*Constants.playerVelocity, y3c1p1, 0.01);
		double x3c2p1 = c2p1.getPos()[0];
		double y3c2p1 = c2p1.getPos()[1];
		Assertions.assertEquals(x2c2p1 + numStepsMoved*Constants.playerVelocity, x3c2p1, 0.01);
		Assertions.assertEquals(y2c2p1 + numStepsMoved*Constants.playerVelocity, y3c2p1, 0.01);
	}

}
