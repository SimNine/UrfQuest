package urfquest.testing.integration;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import urfquest.Main;
import urfquest.client.Client;
import urfquest.server.Server;
import urfquest.shared.ChatMessage;
import urfquest.shared.message.Message;
import urfquest.shared.message.MessageType;

class BasicChatTest {
	
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
		s = new Server(0, 0);
		c1 = new Client(s);
		c2 = new Client(s);
		
		Main.server = s;
		Main.client = c1;
		
		s.attachLocalClient(c1);
		s.attachLocalClient(c2);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testSendMessage() {
		String messageText = "Here is some sample text that should be translated from chat to chat";

		Assertions.assertEquals(0, c1.getAllChatMessages().size());
		Assertions.assertEquals(0, s.getAllChatMessages().size());
		Assertions.assertEquals(0, c2.getAllChatMessages().size());
		
		Message m = new Message();
		m.type = MessageType.CHAT_MESSAGE;
		m.payload = new ChatMessage(null, messageText);
		c1.send(m);

		Assertions.assertEquals(1, c1.getAllChatMessages().size());
		Assertions.assertEquals(1, s.getAllChatMessages().size());
		Assertions.assertEquals(1, c2.getAllChatMessages().size());
		
		Assertions.assertEquals(messageText, s.getAllChatMessages().getFirst().message);
		Assertions.assertEquals(messageText, c2.getAllChatMessages().getFirst().message);
	}

	@Test
	void testSendCommand() {
		String messageText = "/An example of a command that should be parsed and not passed";

		Assertions.assertEquals(0, c1.getAllChatMessages().size());
		Assertions.assertEquals(0, s.getAllChatMessages().size());
		Assertions.assertEquals(0, c2.getAllChatMessages().size());
		
		Message m = new Message();
		m.type = MessageType.CHAT_MESSAGE;
		m.payload = new ChatMessage(null, messageText);
		c1.send(m);

		Assertions.assertEquals(0, c1.getAllChatMessages().size());
		Assertions.assertEquals(1, s.getAllChatMessages().size());
		Assertions.assertEquals(0, c2.getAllChatMessages().size());
		
		Assertions.assertEquals(messageText, s.getAllChatMessages().getFirst().message);
	}

}
