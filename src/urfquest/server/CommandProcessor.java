package urfquest.server;

import urfquest.Main;
import urfquest.shared.message.Message;
import urfquest.shared.message.MessageType;

public class CommandProcessor {

	public static void processCommand(Server server, String commandStr, int clientID) {
		Main.server.getLogger().info(clientID + " sent command: " + commandStr);
		
		String tokens[] = commandStr.split(" ");
		switch (tokens[0]) {
			case "/getpos": {
				double[] pos = server.getGame().getPlayer(server.getPlayerMap().get(clientID)).getPos();
				
				Message m = new Message();
				m.type = MessageType.CHAT_MESSAGE;
				m.payload = "position is (" + pos[0] + "," + pos[1] + ")";
				m.entityName = "SERVER";
				
				server.sendMessageToSingleClient(m, clientID);
				break;
			}
		}
	}
	
}
