package xyz.urffer.urfquest.testing.integration.commands;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import xyz.urffer.urfquest.client.Client;
import xyz.urffer.urfquest.server.Server;
import xyz.urffer.urfquest.server.commands.Command;
import xyz.urffer.urfquest.server.commands.CommandPermissions;
import xyz.urffer.urfquest.server.commands.CommandProcessor;
import xyz.urffer.urfquest.shared.ChatMessage;
import xyz.urffer.urfquest.shared.protocol.messages.MessageChat;

class HelpCommandTest {
	
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
	void testHelpCommandPermissionsServer() {
		s.getClient(c1.getClientID()).setCommandPermissions(CommandPermissions.SERVER);
		
		String messageText = "/help";

		Assertions.assertEquals(0, c1.getAllChatMessages().size());
		Assertions.assertEquals(0, s.getAllChatMessages().size());
		Assertions.assertEquals(0, c2.getAllChatMessages().size());
		
		MessageChat m = new MessageChat();
		m.chatMessage = new ChatMessage(null, messageText);
		c1.send(m);

		Assertions.assertEquals(CommandProcessor.commands.size(), c1.getAllChatMessages().size());
		Assertions.assertEquals(1, s.getAllChatMessages().size());
		Assertions.assertEquals(0, c2.getAllChatMessages().size());
		
		Assertions.assertEquals(messageText, s.getAllChatMessages().getFirst().message);
	}

	@Test
	void testHelpCommandPermissionsOp() {
		s.getClient(c1.getClientID()).setCommandPermissions(CommandPermissions.OP);
		
		String messageText = "/help";

		Assertions.assertEquals(0, c1.getAllChatMessages().size());
		Assertions.assertEquals(0, s.getAllChatMessages().size());
		Assertions.assertEquals(0, c2.getAllChatMessages().size());

		MessageChat m = new MessageChat();
		m.chatMessage = new ChatMessage(null, messageText);
		c1.send(m);

		Assertions.assertEquals(9, c1.getAllChatMessages().size());
		Assertions.assertEquals(1, s.getAllChatMessages().size());
		Assertions.assertEquals(0, c2.getAllChatMessages().size());
		
		Assertions.assertEquals(messageText, s.getAllChatMessages().getFirst().message);
	}

	@Test
	void testHelpCommandPermissionsNormal() {
		s.getClient(c1.getClientID()).setCommandPermissions(CommandPermissions.NORMAL);
		
		String messageText = "/help";

		Assertions.assertEquals(0, c1.getAllChatMessages().size());
		Assertions.assertEquals(0, s.getAllChatMessages().size());
		Assertions.assertEquals(0, c2.getAllChatMessages().size());

		MessageChat m = new MessageChat();
		m.chatMessage = new ChatMessage(null, messageText);
		c1.send(m);

		Assertions.assertEquals(4, c1.getAllChatMessages().size());
		Assertions.assertEquals(1, s.getAllChatMessages().size());
		Assertions.assertEquals(0, c2.getAllChatMessages().size());
		
		Assertions.assertEquals(messageText, s.getAllChatMessages().getFirst().message);
	}

	@Test
	void testHelpCommandPermissionsGuest() {
		s.getClient(c1.getClientID()).setCommandPermissions(CommandPermissions.GUEST);
		
		String messageText = "/help";

		Assertions.assertEquals(0, c1.getAllChatMessages().size());
		Assertions.assertEquals(0, s.getAllChatMessages().size());
		Assertions.assertEquals(0, c2.getAllChatMessages().size());

		MessageChat m = new MessageChat();
		m.chatMessage = new ChatMessage(null, messageText);
		c1.send(m);

		Assertions.assertEquals(2, c1.getAllChatMessages().size());
		Assertions.assertEquals(1, s.getAllChatMessages().size());
		Assertions.assertEquals(0, c2.getAllChatMessages().size());
		
		Assertions.assertEquals(messageText, s.getAllChatMessages().getFirst().message);
	}

	@Test
	void testHelpCommandRealCommand() {
		String messageText = "/help help";

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
		Command helpCommand = CommandProcessor.commands.get("help");
		String expectedString = helpCommand.base + " " + helpCommand.usage + " - " + helpCommand.description;
		Assertions.assertEquals(expectedString, c1.getAllChatMessages().getFirst().message);
	}

	@Test
	void testHelpCommandFakeCommand() {
		String messageText = "/help not_a_real_command";

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
		Assertions.assertEquals(CommandProcessor.commandNotRecognized, c1.getAllChatMessages().getFirst().message);
	}

}
