package xyz.urffer.urfquest.testing.integration.commands;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import xyz.urffer.urfutils.math.PairDouble;

import xyz.urffer.urfquest.client.Client;
import xyz.urffer.urfquest.server.Server;
import xyz.urffer.urfquest.shared.ChatMessage;
import xyz.urffer.urfquest.shared.protocol.messages.MessageChat;

class GetposCommandTest {
	
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
	void testGetposCommandSelf() {
		String messageText = "/getpos";

		Assertions.assertEquals(0, c1.getAllChatMessages().size());
		Assertions.assertEquals(0, s.getAllChatMessages().size());
		Assertions.assertEquals(0, c2.getAllChatMessages().size());

		MessageChat m = new MessageChat();
		m.chatMessage = new ChatMessage(null, messageText);
		c1.send(m);

		Assertions.assertEquals(1, c1.getAllChatMessages().size());
		Assertions.assertEquals(1, s.getAllChatMessages().size());
		Assertions.assertEquals(0, c2.getAllChatMessages().size());
		
		Assertions.assertEquals(messageText, s.getAllChatMessages().getFirst().message);
		PairDouble p1pos = c1.getState().getPlayer().getPos();
		Assertions.assertEquals("Your position is (" + p1pos.x + "," + p1pos.y + ")", 
				c1.getAllChatMessages().getFirst().message);
	}

	@Test
	void testGetposCommandOther() {
		String messageText = "/getpos Nick";

		Assertions.assertEquals(0, c1.getAllChatMessages().size());
		Assertions.assertEquals(0, s.getAllChatMessages().size());
		Assertions.assertEquals(0, c2.getAllChatMessages().size());

		MessageChat m = new MessageChat();
		m.chatMessage = new ChatMessage(null, messageText);
		c1.send(m);

		Assertions.assertEquals(1, c1.getAllChatMessages().size());
		Assertions.assertEquals(1, s.getAllChatMessages().size());
		Assertions.assertEquals(0, c2.getAllChatMessages().size());
		
		Assertions.assertEquals(messageText, s.getAllChatMessages().getFirst().message);
		PairDouble p2pos = c2.getState().getPlayer().getPos();
		Assertions.assertEquals("Nick's position is (" + p2pos.x + "," + p2pos.y + ")", 
				c1.getAllChatMessages().getFirst().message);
	}

	@Test
	void testGetposCommandInvalidPlayer() {
		String messageText = "/getpos not_a_real_player";

		Assertions.assertEquals(0, c1.getAllChatMessages().size());
		Assertions.assertEquals(0, s.getAllChatMessages().size());
		Assertions.assertEquals(0, c2.getAllChatMessages().size());

		MessageChat m = new MessageChat();
		m.chatMessage = new ChatMessage(null, messageText);
		c1.send(m);

		Assertions.assertEquals(1, c1.getAllChatMessages().size());
		Assertions.assertEquals(1, s.getAllChatMessages().size());
		Assertions.assertEquals(0, c2.getAllChatMessages().size());
		
		Assertions.assertEquals(messageText, s.getAllChatMessages().getFirst().message);
		Assertions.assertEquals("Specified player 'not_a_real_player' not found", 
				c1.getAllChatMessages().getFirst().message);
	}

}
