package urfquest.testing.integration.commands;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import urfquest.client.Client;
import urfquest.client.entities.mobs.Mob;
import urfquest.client.entities.mobs.Player;
import urfquest.server.Server;
import urfquest.server.commands.CommandPermissions;
import urfquest.shared.ChatMessage;
import urfquest.shared.Constants;
import urfquest.shared.message.Message;
import urfquest.shared.message.MessageType;

class SummonCommandTest {
	
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
		
		s.attachLocalClient(c1, CommandPermissions.OP);
		s.attachLocalClient(c2, CommandPermissions.OP);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testSummonCommandAtPlayer() {
		String messageText = "/summon chicken";

		Assertions.assertEquals(0, c1.getAllChatMessages().size());
		Assertions.assertEquals(0, s.getAllChatMessages().size());
		Assertions.assertEquals(0, c2.getAllChatMessages().size());
		
		Assertions.assertEquals(0, s.getState().getSurfaceMap().getNumMobs());
		Assertions.assertEquals(0, c1.getState().getCurrentMap().getNumMobs());
		
		Message m = new Message();
		m.type = MessageType.CHAT_MESSAGE;
		m.payload = new ChatMessage(null, messageText);
		c1.send(m);

		Assertions.assertEquals(0, c1.getAllChatMessages().size());
		Assertions.assertEquals(1, s.getAllChatMessages().size());
		Assertions.assertEquals(0, c2.getAllChatMessages().size());
		
		Assertions.assertEquals(messageText, s.getAllChatMessages().getFirst().message);
		
		Assertions.assertEquals(1, s.getState().getSurfaceMap().getNumMobs());
		Assertions.assertEquals(1, c1.getState().getCurrentMap().getNumMobs());
		Player p1c = c1.getState().getPlayer();
		Mob mob = c1.getState().getCurrentMap().getMobs().values().iterator().next();
		Assertions.assertEquals(p1c.getPos()[0], mob.getPos()[0], Constants.TESTING_POSITION_TOLERANCE);
		Assertions.assertEquals(p1c.getPos()[1], mob.getPos()[1], Constants.TESTING_POSITION_TOLERANCE);
	}

	@Test
	void testSummonCommandAtPosition() {
		String messageText = "/summon chicken 5 3";

		Assertions.assertEquals(0, c1.getAllChatMessages().size());
		Assertions.assertEquals(0, s.getAllChatMessages().size());
		Assertions.assertEquals(0, c2.getAllChatMessages().size());
		
		Assertions.assertEquals(0, s.getState().getSurfaceMap().getNumMobs());
		Assertions.assertEquals(0, c1.getState().getCurrentMap().getNumMobs());
		
		Message m = new Message();
		m.type = MessageType.CHAT_MESSAGE;
		m.payload = new ChatMessage(null, messageText);
		c1.send(m);

		Assertions.assertEquals(0, c1.getAllChatMessages().size());
		Assertions.assertEquals(1, s.getAllChatMessages().size());
		Assertions.assertEquals(0, c2.getAllChatMessages().size());
		
		Assertions.assertEquals(messageText, s.getAllChatMessages().getFirst().message);
		
		Assertions.assertEquals(1, s.getState().getSurfaceMap().getNumMobs());
		Assertions.assertEquals(1, c1.getState().getCurrentMap().getNumMobs());
		Mob mob = c1.getState().getCurrentMap().getMobs().values().iterator().next();
		Assertions.assertEquals(5.0, mob.getPos()[0], Constants.TESTING_POSITION_TOLERANCE);
		Assertions.assertEquals(3.0, mob.getPos()[1], Constants.TESTING_POSITION_TOLERANCE);
	}
}
