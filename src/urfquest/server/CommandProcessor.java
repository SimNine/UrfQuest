package urfquest.server;

import urfquest.server.entities.mobs.Player;
import urfquest.shared.ChatMessage;
import urfquest.shared.message.Message;
import urfquest.shared.message.MessageType;

public class CommandProcessor {
	
	public static String helpCommandMessage = "/help, /getpos [player_name], /list, /me";

	public static void processCommand(Server server, String commandStr, int clientID) {
		server.getLogger().info(clientID + " sent command: " + commandStr);
		
		String tokens[] = commandStr.split(" ");
		switch (tokens[0]) {
			case "/help": {
				Message m = new Message();
				m.type = MessageType.CHAT_MESSAGE;
				m.payload = new ChatMessage(ChatMessage.serverSource, helpCommandMessage);
				server.sendMessageToSingleClient(m, clientID);
				break;
			}
			case "/getpos": {
				Player p = null;
				
				Message m = new Message();
				m.type = MessageType.CHAT_MESSAGE;
				
				if (tokens.length > 1) {
					Integer playerID = server.getUserMap().getPlayerIdFromPlayerName(tokens[1]);
					if (playerID == null) { // if the specified player wasn't found
						m.payload = new ChatMessage(ChatMessage.serverSource, "Specified player '" + tokens[1] + "' not found");
					} else {
						p = server.getGame().getPlayer(playerID);
						double[] pos = p.getCenter();
						m.payload = new ChatMessage(ChatMessage.serverSource, tokens[1] + "'s position is (" + pos[0] + "," + pos[1] + ")");
					}
				} else {
					double[] pos = server.getGame().getPlayer(server.getUserMap().getPlayerIdFromClientId(clientID)).getCenter();
					m.payload = new ChatMessage(ChatMessage.serverSource, "Your position is (" + pos[0] + "," + pos[1] + ")");
				}

				server.sendMessageToSingleClient(m, clientID);
				break;
			}
			case "/list": {
				Message m = new Message();
				m.type = MessageType.CHAT_MESSAGE;
				String payloadString = "";
				for (String s : server.getUserMap().getAllPlayerNames()) {
					payloadString += s + ", ";
				}
				m.payload = new ChatMessage(ChatMessage.serverSource, payloadString);
				server.sendMessageToSingleClient(m, clientID);
				break;
			}
			case "/me": {
				Message m = new Message();
				m.type = MessageType.CHAT_MESSAGE;
				String thisPlayerName = server.getUserMap().getPlayerNameFromClientId(clientID);
				m.payload = new ChatMessage(ChatMessage.serverSource, thisPlayerName + commandStr.substring(3));
				server.sendMessageToAllClients(m);
				break;
			}
		}
	}
	
}
