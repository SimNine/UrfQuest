package xyz.urffer.urfquest.testing.integration.commands;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import xyz.urffer.urfquest.client.Client;
import xyz.urffer.urfquest.client.entities.mobs.Player;
import xyz.urffer.urfquest.server.Server;
import xyz.urffer.urfquest.server.commands.CommandPermissions;
import xyz.urffer.urfquest.shared.ChatMessage;
import xyz.urffer.urfquest.shared.Constants;
import xyz.urffer.urfquest.shared.PairDouble;
import xyz.urffer.urfquest.shared.protocol.messages.MessageChat;

class TpCommandTest {
	
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
	void testTpCommandNumMessages() {
		String messageText = "/tp 0 0";

		Assertions.assertEquals(0, c1.getAllChatMessages().size());
		Assertions.assertEquals(0, s.getAllChatMessages().size());
		Assertions.assertEquals(0, c2.getAllChatMessages().size());

		MessageChat m = new MessageChat();
		m.chatMessage = new ChatMessage(null, messageText);
		c1.send(m);

		Assertions.assertEquals(0, c1.getAllChatMessages().size());
		Assertions.assertEquals(1, s.getAllChatMessages().size());
		Assertions.assertEquals(0, c2.getAllChatMessages().size());
		
		Assertions.assertEquals(messageText, s.getAllChatMessages().getFirst().message);
	}

	@Test
	void testTpCommandShortDistance() {
		String messageText = "/tp 0 0";
		
		Player p1 = c1.getState().getPlayer();
		
		PairDouble p1pos1 = p1.getPos();
		Assertions.assertEquals(s.getState().getSurfaceMap().getHomeCoords().x, p1pos1.x);
		Assertions.assertEquals(s.getState().getSurfaceMap().getHomeCoords().y, p1pos1.y);

		MessageChat m = new MessageChat();
		m.chatMessage = new ChatMessage(null, messageText);
		c1.send(m);
		
		PairDouble p1pos2 = p1.getPos();
		Assertions.assertEquals(0.0, p1pos2.x);
		Assertions.assertEquals(0.0, p1pos2.y);
	}

	@Test
	void testTpCommandMediumDistance() {
		double midDistance = Constants.MAP_CHUNK_SIZE / 2.0;
		String messageText = "/tp " + midDistance + " " + midDistance;
		
		Player p1 = c1.getState().getPlayer();
		
		PairDouble p1pos1 = p1.getPos();
		Assertions.assertEquals(s.getState().getSurfaceMap().getHomeCoords().x, p1pos1.x);
		Assertions.assertEquals(s.getState().getSurfaceMap().getHomeCoords().y, p1pos1.y);

		MessageChat m = new MessageChat();
		m.chatMessage = new ChatMessage(null, messageText);
		c1.send(m);
		
		PairDouble p1pos2 = p1.getPos();
		Assertions.assertEquals(midDistance, p1pos2.x);
		Assertions.assertEquals(midDistance, p1pos2.y);
	}

	@Test
	void testTpCommandFarDistance() {
		double farDistance = Constants.MAP_CHUNK_SIZE * 5.0;
		String messageText = "/tp " + farDistance + " " + farDistance;
		
		Player p1 = c1.getState().getPlayer();
		
		PairDouble p1pos1 = p1.getPos();
		Assertions.assertEquals(s.getState().getSurfaceMap().getHomeCoords().x, p1pos1.x);
		Assertions.assertEquals(s.getState().getSurfaceMap().getHomeCoords().y, p1pos1.y);

		MessageChat m = new MessageChat();
		m.chatMessage = new ChatMessage(null, messageText);
		c1.send(m);
		
		PairDouble p1pos2 = p1.getPos();
		Assertions.assertEquals(farDistance, p1pos2.x);
		Assertions.assertEquals(farDistance, p1pos2.y);
	}

	@Test
	void testTpCommandExtremeDistance() {
		double extremeDistance = 5000.0;
		String messageText = "/tp " + extremeDistance + " " + extremeDistance;
		
		Player p1 = c1.getState().getPlayer();
		
		PairDouble p1pos1 = p1.getPos();
		Assertions.assertEquals(s.getState().getSurfaceMap().getHomeCoords().x, p1pos1.x);
		Assertions.assertEquals(s.getState().getSurfaceMap().getHomeCoords().y, p1pos1.y);

		MessageChat m = new MessageChat();
		m.chatMessage = new ChatMessage(null, messageText);
		c1.send(m);
		
		PairDouble p1pos2 = p1.getPos();
		Assertions.assertEquals(extremeDistance, p1pos2.x);
		Assertions.assertEquals(extremeDistance, p1pos2.y);
	}
}
