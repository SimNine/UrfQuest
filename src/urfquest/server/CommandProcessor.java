package urfquest.server;

import java.util.HashMap;

import urfquest.server.entities.mobs.Player;
import urfquest.shared.ChatMessage;
import urfquest.shared.message.Message;
import urfquest.shared.message.MessageType;

public class CommandProcessor {
	
	public static String commandNotRecognized = "Command not recognized. Use '/help' to list commands.";
	
	public static HashMap<String, Command> commands;
	
	static {
		commands = new HashMap<>();
		
		Command helpCommand = new Command("/help", "[command]", 
				"Returns details about the command specified, or a list of commands if none is specified") {
					@Override
					public void runCommand(Server server, String[] args, int clientID) {
						if (args.length > 1) {
							Command c = CommandProcessor.commands.get(args[1]);
							Message m = new Message();
							m.type = MessageType.CHAT_MESSAGE;
							String payloadMessage;
							if (c == null) {
								payloadMessage = CommandProcessor.commandNotRecognized;
							} else {
								payloadMessage = c.base + " " + c.usage + " - " + c.description;
							}
							m.payload = new ChatMessage(ChatMessage.serverSource, payloadMessage);
							server.sendMessageToSingleClient(m, clientID);
						} else {
							for (Command c : CommandProcessor.commands.values()) {
								Message m = new Message();
								m.type = MessageType.CHAT_MESSAGE;
								String payloadMessage = c.base + " " + c.usage + " - " + c.description;
								m.payload = new ChatMessage(ChatMessage.serverSource, payloadMessage);
								server.sendMessageToSingleClient(m, clientID);
							}
						}
					}
		};
		commands.put("help", helpCommand);
		
		Command getPosCommand = new Command("/getpos", "[player]", 
				"Gets the position of the specified player, or the sender's position if no player is specified") {
					@Override
					public void runCommand(Server server, String[] args, int clientID) {
						Player p = null;
						
						Message m = new Message();
						m.type = MessageType.CHAT_MESSAGE;
						
						if (args.length > 1) {
							Integer playerID = server.getUserMap().getPlayerIdFromPlayerName(args[1]);
							if (playerID == null) { // if the specified player wasn't found
								m.payload = new ChatMessage(ChatMessage.serverSource, "Specified player '" + args[1] + "' not found");
							} else {
								p = server.getState().getPlayer(playerID);
								double[] pos = p.getCenter();
								m.payload = new ChatMessage(ChatMessage.serverSource, args[1] + "'s position is (" + pos[0] + "," + pos[1] + ")");
							}
						} else {
							double[] pos = server.getState().getPlayer(server.getUserMap().getPlayerIdFromClientId(clientID)).getCenter();
							m.payload = new ChatMessage(ChatMessage.serverSource, "Your position is (" + pos[0] + "," + pos[1] + ")");
						}

						server.sendMessageToSingleClient(m, clientID);
					}
		};
		commands.put("getpos", getPosCommand);
		
		Command listCommand = new Command("/list", "", 
				"Lists all players currently on the server") {
					@Override
					public void runCommand(Server server, String[] args, int clientID) {
						if (args.length > 1) {
							CommandProcessor.sendIncorrectArgumentsMessage(server, this, clientID);
						}
						
						Message m = new Message();
						m.type = MessageType.CHAT_MESSAGE;
						String payloadString = "";
						for (String s : server.getUserMap().getAllPlayerNames()) {
							payloadString += ", " + s;
						}
						payloadString = "Online players: " + payloadString.substring(2);
						m.payload = new ChatMessage(ChatMessage.serverSource, payloadString);
						server.sendMessageToSingleClient(m, clientID);
					}
		};
		commands.put("list", listCommand);
		
		Command meCommand = new Command("/me", "<message>", 
				"Displays a message in third person from the sender") {
					@Override
					public void runCommand(Server server, String[] args, int clientID) {
						if (args.length == 1) {
							CommandProcessor.sendIncorrectArgumentsMessage(server, this, clientID);
						}
						
						Message m = new Message();
						m.type = MessageType.CHAT_MESSAGE;
						String thisPlayerName = server.getUserMap().getPlayerNameFromClientId(clientID);
						m.payload = new ChatMessage(ChatMessage.serverSource, thisPlayerName + " " + String.join(" ", args));
						server.sendMessageToAllClients(m);
					}
		};
		commands.put("me", meCommand);
		
		Command tpCommand = new Command("/tp", "<x_pos> <y_pos>", 
				"Teleports caller to the specified position") {
					@Override
					public void runCommand(Server server, String[] args, int clientID) {
						double xPos, yPos;
						try {
							xPos = Double.parseDouble(args[1]);
							yPos = Double.parseDouble(args[2]);
						} catch (Exception e) {
							CommandProcessor.sendIncorrectArgumentsMessage(server, this, clientID);
							return;
						}
						
						int thisPlayerID = server.getUserMap().getPlayerIdFromClientId(clientID);
						Player thisPlayer = server.getState().getPlayer(thisPlayerID);
						thisPlayer.setPos(xPos, yPos);
					}
		};
		commands.put("tp", tpCommand);
	}
	
	public static void sendIncorrectArgumentsMessage(Server server, Command command, int clientID) {
		Message m = new Message();
		m.type = MessageType.CHAT_MESSAGE;
		String payloadMessage = "Incorrect usage. '" + command.base + " " + command.usage + "'";
		m.payload = new ChatMessage(ChatMessage.serverSource, payloadMessage);
		server.sendMessageToSingleClient(m, clientID);
	}

	public static void processCommand(Server server, String commandStr, int clientID) {
		server.getLogger().info(clientID + " sent raw command: " + commandStr);
		
		String tokens[] = commandStr.substring(1).split(" ");
		Command command = commands.get(tokens[0]);
		if (command == null) {
			Message m = new Message();
			m.type = MessageType.CHAT_MESSAGE;
			String payloadString = CommandProcessor.commandNotRecognized;
			m.payload = new ChatMessage(ChatMessage.serverSource, payloadString);
			server.sendMessageToSingleClient(m, clientID);
		} else {
			command.runCommand(server, tokens, clientID);
		}
	}
	
}
