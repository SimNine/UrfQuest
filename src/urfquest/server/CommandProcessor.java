package urfquest.server;

import urfquest.Main;
import urfquest.server.entities.mobs.Player;
import urfquest.shared.message.Message;
import urfquest.shared.message.MessageType;

public class CommandProcessor {

	public static void processCommand(Server server, String commandStr, int clientID) {
		Main.server.getLogger().info(clientID + " sent command: " + commandStr);
		
		String tokens[] = commandStr.split(" ");
		switch (tokens[0]) {
			case "/help": {
				Message m = new Message();
				m.type = MessageType.CHAT_MESSAGE;
				m.entityName = "SERVER";
				m.payload = "/help, /getpos [player_name], /list, /me";
				server.sendMessageToSingleClient(m, clientID);
				break;
			}
			case "/getpos": {
				Player p = null;
				
				Message m = new Message();
				m.type = MessageType.CHAT_MESSAGE;
				m.entityName = "SERVER";
				
				if (tokens.length > 1) {
					Integer playerID = server.getUserMap().getPlayerIdFromPlayerName(tokens[1]);
					if (playerID == null) { // if the specified player wasn't found
						m.payload = "Specified player '" + tokens[1] + "' not found";
					} else {
						p = server.getGame().getPlayer(playerID);
						double[] pos = p.getCenter();
						m.payload = tokens[1] + "'s position is (" + pos[0] + "," + pos[1] + ")";
					}
				} else {
					double[] pos = server.getGame().getPlayer(server.getUserMap().getPlayerIdFromClientId(clientID)).getCenter();
					m.payload = "Your position is (" + pos[0] + "," + pos[1] + ")";
				}

				server.sendMessageToSingleClient(m, clientID);
				break;
			}
			case "/list": {
				Message m = new Message();
				m.type = MessageType.CHAT_MESSAGE;
				m.entityName = "SERVER";
				m.payload = "";
				for (String s : server.getUserMap().getAllPlayerNames()) {
					m.payload += s + ", ";
				}
				server.sendMessageToSingleClient(m, clientID);
				break;
			}
			case "/me": {
				Message m = new Message();
				m.type = MessageType.CHAT_MESSAGE;
				m.entityName = "SERVER";
				String thisPlayerName = server.getUserMap().getPlayerNameFromClientId(clientID);
				m.payload = thisPlayerName + commandStr.substring(3);
				server.sendMessageToAllClients(m);
				break;
			}
		}
	}
	
}
