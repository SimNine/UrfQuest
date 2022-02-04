package urfquest.testing.integration.commands;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import urfquest.client.Client;
import urfquest.client.entities.mobs.Player;
import urfquest.server.Server;
import urfquest.shared.ChatMessage;
import urfquest.shared.Constants;
import urfquest.shared.message.Message;
import urfquest.shared.message.MessageType;

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
		
		s.attachLocalClient(c1);
		s.attachLocalClient(c2);
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
		
		Message m = new Message();
		m.type = MessageType.CHAT_MESSAGE;
		m.payload = new ChatMessage(null, messageText);
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
		
		double[] p1pos1 = p1.getPos();
		Assertions.assertEquals(s.getState().getSurfaceMap().getHomeCoords()[0], p1pos1[0]);
		Assertions.assertEquals(s.getState().getSurfaceMap().getHomeCoords()[1], p1pos1[1]);
		
		Message m = new Message();
		m.type = MessageType.CHAT_MESSAGE;
		m.payload = new ChatMessage(null, messageText);
		c1.send(m);
		
		double[] p1pos2 = p1.getPos();
		Assertions.assertEquals(0.0, p1pos2[0]);
		Assertions.assertEquals(0.0, p1pos2[1]);
	}

	@Test
	void testTpCommandMediumDistance() {
		double midDistance = Constants.MAP_CHUNK_SIZE / 2.0;
		String messageText = "/tp " + midDistance + " " + midDistance;
		
		Player p1 = c1.getState().getPlayer();
		
		double[] p1pos1 = p1.getPos();
		Assertions.assertEquals(s.getState().getSurfaceMap().getHomeCoords()[0], p1pos1[0]);
		Assertions.assertEquals(s.getState().getSurfaceMap().getHomeCoords()[1], p1pos1[1]);
		
		Message m = new Message();
		m.type = MessageType.CHAT_MESSAGE;
		m.payload = new ChatMessage(null, messageText);
		c1.send(m);
		
		double[] p1pos2 = p1.getPos();
		Assertions.assertEquals(midDistance, p1pos2[0]);
		Assertions.assertEquals(midDistance, p1pos2[1]);
	}

	@Test
	void testTpCommandFarDistance() {
		double farDistance = Constants.MAP_CHUNK_SIZE * 5.0;
		String messageText = "/tp " + farDistance + " " + farDistance;
		
		Player p1 = c1.getState().getPlayer();
		
		double[] p1pos1 = p1.getPos();
		Assertions.assertEquals(s.getState().getSurfaceMap().getHomeCoords()[0], p1pos1[0]);
		Assertions.assertEquals(s.getState().getSurfaceMap().getHomeCoords()[1], p1pos1[1]);
		
		Message m = new Message();
		m.type = MessageType.CHAT_MESSAGE;
		m.payload = new ChatMessage(null, messageText);
		c1.send(m);
		
		double[] p1pos2 = p1.getPos();
		Assertions.assertEquals(farDistance, p1pos2[0]);
		Assertions.assertEquals(farDistance, p1pos2[1]);
	}

	@Test
	void testTpCommandExtremeDistance() {
		double extremeDistance = 5000.0;
		String messageText = "/tp " + extremeDistance + " " + extremeDistance;
		
		Player p1 = c1.getState().getPlayer();
		
		double[] p1pos1 = p1.getPos();
		Assertions.assertEquals(s.getState().getSurfaceMap().getHomeCoords()[0], p1pos1[0]);
		Assertions.assertEquals(s.getState().getSurfaceMap().getHomeCoords()[1], p1pos1[1]);
		
		Message m = new Message();
		m.type = MessageType.CHAT_MESSAGE;
		m.payload = new ChatMessage(null, messageText);
		c1.send(m);
		
		double[] p1pos2 = p1.getPos();
		Assertions.assertEquals(extremeDistance, p1pos2[0]);
		Assertions.assertEquals(extremeDistance, p1pos2[1]);
	}
}
