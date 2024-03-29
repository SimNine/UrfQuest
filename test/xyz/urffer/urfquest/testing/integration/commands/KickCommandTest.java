package xyz.urffer.urfquest.testing.integration.commands;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import xyz.urffer.urfquest.client.Client;
import xyz.urffer.urfquest.server.Server;
import xyz.urffer.urfquest.server.commands.CommandPermissions;
import xyz.urffer.urfquest.shared.ChatMessage;
import xyz.urffer.urfquest.shared.protocol.messages.MessageChat;

class KickCommandTest {
	
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
	void testKickCommandSelf() {
		String messageText = "/kick Chris";

		Assertions.assertEquals(0, c1.getAllChatMessages().size());
		Assertions.assertEquals(0, s.getAllChatMessages().size());
		Assertions.assertEquals(0, c2.getAllChatMessages().size());
		
		Assertions.assertEquals(2, s.getUserMap().size());

		MessageChat m = new MessageChat();
		m.chatMessage = new ChatMessage(null, messageText);
		c1.send(m);
		
		s.tick(1);

		Assertions.assertEquals(1, c1.getAllChatMessages().size());
		Assertions.assertEquals(1, s.getAllChatMessages().size());
		Assertions.assertEquals(0, c2.getAllChatMessages().size());
		
		Assertions.assertEquals(2, s.getUserMap().size());

		Assertions.assertEquals("You cannot kick yourself", c1.getAllChatMessages().getFirst().message);
		Assertions.assertEquals(messageText, s.getAllChatMessages().getFirst().message);
	}

	@Test
	void testKickCommandOther() {
		String messageText = "/kick Nick";

		Assertions.assertEquals(0, c1.getAllChatMessages().size());
		Assertions.assertEquals(0, s.getAllChatMessages().size());
		Assertions.assertEquals(0, c2.getAllChatMessages().size());
		
		Assertions.assertEquals(2, s.getUserMap().size());

		MessageChat m = new MessageChat();
		m.chatMessage = new ChatMessage(null, messageText);
		c1.send(m);
		
		s.tick(1);

		Assertions.assertEquals(1, c1.getAllChatMessages().size());
		Assertions.assertEquals(1, s.getAllChatMessages().size());
		Assertions.assertEquals(0, c2.getAllChatMessages().size());
		
		Assertions.assertEquals(1, s.getUserMap().size());

		Assertions.assertEquals("Nick has been disconnected", c1.getAllChatMessages().getFirst().message);
		Assertions.assertEquals(messageText, s.getAllChatMessages().getFirst().message);
	}

	@Test
	void testKickCommandInvalidPlayer() {
		String messageText = "/kick Tim";

		Assertions.assertEquals(0, c1.getAllChatMessages().size());
		Assertions.assertEquals(0, s.getAllChatMessages().size());
		Assertions.assertEquals(0, c2.getAllChatMessages().size());
		
		Assertions.assertEquals(2, s.getUserMap().size());

		MessageChat m = new MessageChat();
		m.chatMessage = new ChatMessage(null, messageText);
		c1.send(m);
		
		s.tick(1);

		Assertions.assertEquals(1, c1.getAllChatMessages().size());
		Assertions.assertEquals(1, s.getAllChatMessages().size());
		Assertions.assertEquals(0, c2.getAllChatMessages().size());
		
		Assertions.assertEquals(2, s.getUserMap().size());

		Assertions.assertEquals(messageText, s.getAllChatMessages().getFirst().message);
	}
}
