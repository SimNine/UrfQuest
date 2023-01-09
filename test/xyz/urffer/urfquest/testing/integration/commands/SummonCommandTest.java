package xyz.urffer.urfquest.testing.integration.commands;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import xyz.urffer.urfquest.client.Client;
import xyz.urffer.urfquest.client.entities.mobs.Mob;
import xyz.urffer.urfquest.client.entities.mobs.Player;
import xyz.urffer.urfquest.server.Server;
import xyz.urffer.urfquest.server.commands.CommandPermissions;
import xyz.urffer.urfquest.shared.ChatMessage;
import xyz.urffer.urfquest.shared.Constants;
import xyz.urffer.urfquest.shared.protocol.messages.MessageChat;

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

		// NOTE: The given map will start out with three NPCs having already spawned
		Assertions.assertEquals(3, s.getState().getSurfaceMap().getMobs().size());
		Assertions.assertEquals(3, c1.getState().getCurrentMap().getMobs().size());

		MessageChat m = new MessageChat();
		m.chatMessage = new ChatMessage(null, messageText);
		c1.send(m);

		Assertions.assertEquals(0, c1.getAllChatMessages().size());
		Assertions.assertEquals(1, s.getAllChatMessages().size());
		Assertions.assertEquals(0, c2.getAllChatMessages().size());
		
		Assertions.assertEquals(messageText, s.getAllChatMessages().getFirst().message);
		
		Assertions.assertEquals(4, s.getState().getSurfaceMap().getMobs().size());
		Assertions.assertEquals(4, c1.getState().getCurrentMap().getMobs().size());
		Player p1c = c1.getState().getPlayer();
		Mob mob = c1.getState().getCurrentMap().getMobs().values().iterator().next();
		Assertions.assertEquals(p1c.getPos().x, mob.getPos().x, Constants.TESTING_POSITION_TOLERANCE);
		Assertions.assertEquals(p1c.getPos().y, mob.getPos().y, Constants.TESTING_POSITION_TOLERANCE);
	}

	@Test
	void testSummonCommandAtPosition() {
		String messageText = "/summon chicken 5 3";

		Assertions.assertEquals(0, c1.getAllChatMessages().size());
		Assertions.assertEquals(0, s.getAllChatMessages().size());
		Assertions.assertEquals(0, c2.getAllChatMessages().size());
		
		// NOTE: The given map will start out with three NPCs having already spawned
		Assertions.assertEquals(3, s.getState().getSurfaceMap().getMobs().size());
		Assertions.assertEquals(3, c1.getState().getCurrentMap().getMobs().size());

		MessageChat m = new MessageChat();
		m.chatMessage = new ChatMessage(null, messageText);
		c1.send(m);

		Assertions.assertEquals(0, c1.getAllChatMessages().size());
		Assertions.assertEquals(1, s.getAllChatMessages().size());
		Assertions.assertEquals(0, c2.getAllChatMessages().size());
		
		Assertions.assertEquals(messageText, s.getAllChatMessages().getFirst().message);
		
		Assertions.assertEquals(4, s.getState().getSurfaceMap().getMobs().size());
		Assertions.assertEquals(4, c1.getState().getCurrentMap().getMobs().size());
		Mob mob = c1.getState().getCurrentMap().getMobs().values().iterator().next();
		Assertions.assertEquals(5.0, mob.getPos().x, Constants.TESTING_POSITION_TOLERANCE);
		Assertions.assertEquals(3.0, mob.getPos().y, Constants.TESTING_POSITION_TOLERANCE);
	}
}
