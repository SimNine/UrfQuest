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

class OpCommandTest {
	
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
		s.attachLocalClient(c2, CommandPermissions.NORMAL);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testOpCommandSelf() {
		String messageText = "/op Chris";

		Assertions.assertEquals(0, c1.getAllChatMessages().size());
		Assertions.assertEquals(0, s.getAllChatMessages().size());
		Assertions.assertEquals(0, c2.getAllChatMessages().size());
		
		Assertions.assertEquals(CommandPermissions.OP, s.getClient(c1.getClientID()).getCommandPermissions());
		Assertions.assertEquals(CommandPermissions.NORMAL, s.getClient(c2.getClientID()).getCommandPermissions());

		MessageChat m = new MessageChat();
		m.chatMessage = new ChatMessage(null, messageText);
		c1.send(m);

		Assertions.assertEquals(1, c1.getAllChatMessages().size());
		Assertions.assertEquals(1, s.getAllChatMessages().size());
		Assertions.assertEquals(0, c2.getAllChatMessages().size());

		Assertions.assertEquals("Chris granted op permissions", c1.getAllChatMessages().getFirst().message);
		Assertions.assertEquals(messageText, s.getAllChatMessages().getFirst().message);
		
		Assertions.assertEquals(CommandPermissions.OP, s.getClient(c1.getClientID()).getCommandPermissions());
		Assertions.assertEquals(CommandPermissions.NORMAL, s.getClient(c2.getClientID()).getCommandPermissions());
	}

	@Test
	void testOpCommandOther() {
		String messageText = "/op Nick";

		Assertions.assertEquals(0, c1.getAllChatMessages().size());
		Assertions.assertEquals(0, s.getAllChatMessages().size());
		Assertions.assertEquals(0, c2.getAllChatMessages().size());
		
		Assertions.assertEquals(CommandPermissions.OP, s.getClient(c1.getClientID()).getCommandPermissions());
		Assertions.assertEquals(CommandPermissions.NORMAL, s.getClient(c2.getClientID()).getCommandPermissions());

		MessageChat m = new MessageChat();
		m.chatMessage = new ChatMessage(null, messageText);
		c1.send(m);

		Assertions.assertEquals(1, c1.getAllChatMessages().size());
		Assertions.assertEquals(1, s.getAllChatMessages().size());
		Assertions.assertEquals(0, c2.getAllChatMessages().size());

		Assertions.assertEquals("Nick granted op permissions", c1.getAllChatMessages().getFirst().message);
		Assertions.assertEquals(messageText, s.getAllChatMessages().getFirst().message);
		
		Assertions.assertEquals(CommandPermissions.OP, s.getClient(c1.getClientID()).getCommandPermissions());
		Assertions.assertEquals(CommandPermissions.OP, s.getClient(c2.getClientID()).getCommandPermissions());
	}

	@Test
	void testOpCommandInvalidPlayer() {
		String messageText = "/op Tim";

		Assertions.assertEquals(0, c1.getAllChatMessages().size());
		Assertions.assertEquals(0, s.getAllChatMessages().size());
		Assertions.assertEquals(0, c2.getAllChatMessages().size());
		
		Assertions.assertEquals(CommandPermissions.OP, s.getClient(c1.getClientID()).getCommandPermissions());
		Assertions.assertEquals(CommandPermissions.NORMAL, s.getClient(c2.getClientID()).getCommandPermissions());

		MessageChat m = new MessageChat();
		m.chatMessage = new ChatMessage(null, messageText);
		c1.send(m);

		Assertions.assertEquals(1, c1.getAllChatMessages().size());
		Assertions.assertEquals(1, s.getAllChatMessages().size());
		Assertions.assertEquals(0, c2.getAllChatMessages().size());

		Assertions.assertEquals("Specified player \"Tim\" not found", c1.getAllChatMessages().getFirst().message);
		Assertions.assertEquals(messageText, s.getAllChatMessages().getFirst().message);
		
		Assertions.assertEquals(CommandPermissions.OP, s.getClient(c1.getClientID()).getCommandPermissions());
		Assertions.assertEquals(CommandPermissions.NORMAL, s.getClient(c2.getClientID()).getCommandPermissions());
	}
}
