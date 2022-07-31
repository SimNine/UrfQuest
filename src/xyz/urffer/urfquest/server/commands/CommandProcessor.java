package xyz.urffer.urfquest.server.commands;

import java.util.HashMap;

import xyz.urffer.urfquest.server.ClientThread;
import xyz.urffer.urfquest.server.Server;
import xyz.urffer.urfquest.server.entities.mobs.Chicken;
import xyz.urffer.urfquest.server.entities.mobs.Cyclops;
import xyz.urffer.urfquest.server.entities.mobs.NPCHuman;
import xyz.urffer.urfquest.server.entities.mobs.Player;
import xyz.urffer.urfquest.server.map.Map;
import xyz.urffer.urfquest.shared.ChatMessage;
import xyz.urffer.urfquest.shared.message.Message;
import xyz.urffer.urfquest.shared.message.MessageType;

public class CommandProcessor {
	
	public static String commandNotRecognized = "Command not recognized. Use '/help' to list commands.";
	
	public static HashMap<String, Command> commands;
	
	static {
		commands = new HashMap<>();
		
		Command helpCommand = new Command("/help", "[command]", 
				"Returns details about the command specified, or a list of commands if none is specified",
				CommandPermissions.GUEST) {
					@Override
					public void runCommand(Server server, String[] args, ClientThread clientThread) {
						int clientThreadID = (clientThread == null) ? server.getServerID() : clientThread.id;
						
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
							server.sendMessageToClientOrServer(m, clientThreadID);
						} else {
							for (Command c : CommandProcessor.commands.values()) {
								if (clientThread != null && c.permissionLevel < clientThread.getCommandPermissions()) {
									continue;
								}
								
								Message m = new Message();
								m.type = MessageType.CHAT_MESSAGE;
								String payloadMessage = c.base + " " + c.usage + " - " + c.description;
								m.payload = new ChatMessage(ChatMessage.serverSource, payloadMessage);
								server.sendMessageToClientOrServer(m, clientThreadID);
							}
						}
					}
		};
		commands.put("help", helpCommand);
		
		Command getPosCommand = new Command("/getpos", "[player]", 
				"Gets the position of the specified player, or the sender's position if no player is specified",
				CommandPermissions.NORMAL) {
					@Override
					public void runCommand(Server server, String[] args, ClientThread clientThread) {
						Message m = new Message();
						m.type = MessageType.CHAT_MESSAGE;
						
						if (args.length > 1) {
							Integer playerID = server.getUserMap().getPlayerIdFromPlayerName(args[1]);
							if (playerID == null) { // if the specified player wasn't found
								m.payload = new ChatMessage(ChatMessage.serverSource, "Specified player '" + args[1] + "' not found");
							} else {
								Player p = server.getState().getPlayer(playerID);
								double[] pos = p.getPos();
								m.payload = new ChatMessage(ChatMessage.serverSource, args[1] + "'s position is (" + pos[0] + "," + pos[1] + ")");
							}
						} else {
							if (clientThread == null) {
								m.payload = new ChatMessage(ChatMessage.serverSource, "This command must be used with an argument when sent from the server");
							} else {
								double[] pos = server.getState().getPlayer(server.getUserMap().getPlayerIdFromClientId(clientThread.id)).getPos();
								m.payload = new ChatMessage(ChatMessage.serverSource, "Your position is (" + pos[0] + "," + pos[1] + ")");
							}
						}

						int clientThreadID = (clientThread == null) ? server.getServerID() : clientThread.id;
						server.sendMessageToClientOrServer(m, clientThreadID);
					}
		};
		commands.put("getpos", getPosCommand);
		
		Command listCommand = new Command("/list", "", 
				"Lists all players currently on the server",
				CommandPermissions.GUEST) {
					@Override
					public void runCommand(Server server, String[] args, ClientThread clientThread) {
						if (args.length > 1) {
							CommandProcessor.sendIncorrectArgumentsMessage(server, this, clientThread);
						}
						
						Message m = new Message();
						m.type = MessageType.CHAT_MESSAGE;
						String payloadString = "";
						for (String s : server.getUserMap().getAllPlayerNames()) {
							payloadString += ", " + s;
						}
						payloadString = "Online players: " + payloadString.substring(2);
						m.payload = new ChatMessage(ChatMessage.serverSource, payloadString);

						int clientThreadID = (clientThread == null) ? server.getServerID() : clientThread.id;
						server.sendMessageToClientOrServer(m, clientThreadID);
					}
		};
		commands.put("list", listCommand);
		
		Command meCommand = new Command("/me", "<message>",
				"Displays a message in third person from the sender",
				CommandPermissions.NORMAL) {
					@Override
					public void runCommand(Server server, String[] args, ClientThread clientThread) {
						if (args.length == 1) {
							CommandProcessor.sendIncorrectArgumentsMessage(server, this, clientThread);
						}
						
						Message m = new Message();
						m.type = MessageType.CHAT_MESSAGE;
						String thisPlayerName;
						if (clientThread == null) {
							thisPlayerName = "SERVER";
						} else {
							thisPlayerName = server.getUserMap().getPlayerNameFromClientId(clientThread.id);
						}
						args[0] = "";
						m.payload = new ChatMessage(ChatMessage.serverSource, thisPlayerName + String.join(" ", args));
						server.sendMessageToAllClients(m);
					}
		};
		commands.put("me", meCommand);
		
		Command tpCommand = new Command("/tp", "<x_pos> <y_pos>", 
				"Teleports caller to the specified position",
				CommandPermissions.OP) {
					@Override
					public void runCommand(Server server, String[] args, ClientThread clientThread) {
						if (clientThread == null) {
							CommandProcessor.sendSimpleResponseMessage(server, null, "This command cannot be run as the server.");
						}
						if (args.length != 3) {
							CommandProcessor.sendIncorrectArgumentsMessage(server, this, clientThread);
							return;
						}
						
						double xPos, yPos;
						try {
							xPos = Double.parseDouble(args[1]);
							yPos = Double.parseDouble(args[2]);
						} catch (Exception e) {
							CommandProcessor.sendIncorrectArgumentsMessage(server, this, clientThread);
							return;
						}
						
						int thisPlayerID = server.getUserMap().getPlayerIdFromClientId(clientThread.id);
						Player thisPlayer = server.getState().getPlayer(thisPlayerID);
						thisPlayer.setPos(xPos, yPos);
					}
		};
		commands.put("tp", tpCommand);
		
		Command summonCommand = new Command("/summon", "<mob_type> [<xpos> <ypos>]", 
				"Summons a mob of the specified type at the caller's position",
				CommandPermissions.OP) {
					@Override
					public void runCommand(Server server, String[] args, ClientThread clientThread) {
						if (args.length < 2) {
							CommandProcessor.sendIncorrectArgumentsMessage(server, this, clientThread);
							return;
						}
						
						double[] pos = new double[2];
						Map map;
						if (args.length == 2) {
							if (clientThread == null) {
								CommandProcessor.sendSimpleResponseMessage(server, null, "This command must specify position when run as the server.");
							}
							
							int thisPlayerID = server.getUserMap().getPlayerIdFromClientId(clientThread.id);
							Player thisPlayer = server.getState().getPlayer(thisPlayerID);
							map = thisPlayer.getMap();
							pos = thisPlayer.getPos();
						} else {
							try {
								map = server.getState().getSurfaceMap();
								pos[0] = Double.parseDouble(args[2]);
								pos[1] = Double.parseDouble(args[3]);
							} catch (Exception e) {
								CommandProcessor.sendIncorrectArgumentsMessage(server, this, clientThread);
								return;
							}
						}
						
						switch (args[1].toUpperCase()) {
							case "CHICKEN": {
								Chicken c = new Chicken(server, map, pos);
								map.addMob(c);
								break;
							}
							case "CYCLOPS": {
								Cyclops c = new Cyclops(server, map, pos);
								map.addMob(c);
								break;
							}
							case "NPC": {
								NPCHuman n = new NPCHuman(server, map, pos);
								map.addMob(n);
								break;
							}
							default: {
								CommandProcessor.sendIncorrectArgumentsMessage(server, this, clientThread);
								return;
							}
						}
					}
		};
		commands.put("summon", summonCommand);
		
		Command opCommand = new Command("/op", "<player_name>", 
				"Grants the specified player op permissions",
				CommandPermissions.OP) {
					@Override
					public void runCommand(Server server, String[] args, ClientThread clientThread) {
						if (args.length != 2) {
							CommandProcessor.sendIncorrectArgumentsMessage(server, this, clientThread);
							return;
						}

						Message m = new Message();
						m.type = MessageType.CHAT_MESSAGE;
						Integer otherClientID = server.getUserMap().getClientIdFromPlayerName(args[1]);
						if (otherClientID == null) { // if the specified player wasn't found
							m.payload = new ChatMessage(ChatMessage.serverSource, "Specified player \"" + args[1] + "\" not found");
						} else {
							ClientThread c = server.getClient(otherClientID);
							c.setCommandPermissions(CommandPermissions.OP);
							m.payload = new ChatMessage(ChatMessage.serverSource, args[1] + " granted op permissions");
						}

						int clientThreadID = (clientThread == null) ? server.getServerID() : clientThread.id;
						server.sendMessageToClientOrServer(m, clientThreadID);
					}
		};
		commands.put("op", opCommand);
		
		Command kickCommand = new Command("/kick", "<player_name>", 
				"Kicks the specified player from the server",
				CommandPermissions.OP) {
					@Override
					public void runCommand(Server server, String[] args, ClientThread clientThread) {
						if (args.length != 2) {
							CommandProcessor.sendIncorrectArgumentsMessage(server, this, clientThread);
							return;
						}

						Integer otherClientID = server.getUserMap().getClientIdFromPlayerName(args[1]);
						int thisClientID = (clientThread == null) ? server.getServerID() : clientThread.id;
						if (otherClientID == null) { // if the specified player wasn't found
							Message m = new Message();
							m.type = MessageType.CHAT_MESSAGE;
							m.payload = new ChatMessage(ChatMessage.serverSource, "Specified player '" + args[1] + "' not found");
							server.sendMessageToClientOrServer(m, thisClientID);
						} else if (otherClientID == thisClientID) { // specified player is self
							Message m = new Message();
							m.type = MessageType.CHAT_MESSAGE;
							m.payload = new ChatMessage(ChatMessage.serverSource, "You cannot kick yourself");
							server.sendMessageToClientOrServer(m, thisClientID);
						} else {
							Message m = new Message();
							m.type = MessageType.DISCONNECT_CLIENT;
							m.clientID = otherClientID;
							m.payload = "You have been kicked from the server";
							server.intakeMessage(m);
						}
					}
		};
		commands.put("kick", kickCommand);
		
		Command stopCommand = new Command("/stop", "", 
				"Stops the server",
				CommandPermissions.OP) {
					@Override
					public void runCommand(Server server, String[] args, ClientThread clientThread) {
						if (args.length != 1) {
							CommandProcessor.sendIncorrectArgumentsMessage(server, this, clientThread);
							return;
						}
						
						server.shutdown();
					}
		};
		commands.put("stop", stopCommand);
	}
	
	public static void sendIncorrectArgumentsMessage(Server server, Command command, ClientThread clientThread) {
		CommandProcessor.sendSimpleResponseMessage(server, clientThread, "Incorrect usage. '" + command.base + " " + command.usage + "'");
	}
	
	public static void sendSimpleResponseMessage(Server server, ClientThread clientThread, String payloadMessage) {
		Message m = new Message();
		m.type = MessageType.CHAT_MESSAGE;
		m.payload = new ChatMessage(ChatMessage.serverSource, payloadMessage);
		
		int clientThreadID = (clientThread == null) ? server.getServerID() : clientThread.id;
		server.sendMessageToClientOrServer(m, clientThreadID);
	}

	public static void processCommand(Server server, String commandStr, ClientThread clientThread) {
		if (clientThread != null) {
			server.getLogger().info("Client " + clientThread.id + " sent raw command: \"" + commandStr + "\"");
		}
		
		String tokens[] = commandStr.substring(1).split(" ");
		Command command = commands.get(tokens[0]);
		if (command == null) {
			CommandProcessor.sendSimpleResponseMessage(server, clientThread, CommandProcessor.commandNotRecognized);
			return;
		}
		
		// if this was sent by the server console, run it immediately
		if (clientThread == null) {
			command.runCommand(server, tokens, null);
			return;
		}
		
		// if this was sent by a user, check their permissions
		if (clientThread.getCommandPermissions() > command.permissionLevel) {
			CommandProcessor.sendSimpleResponseMessage(server, clientThread, "You do not have permissions to use '" + command.base + "'");
		} else {
			command.runCommand(server, tokens, clientThread);
		}
	}
	
}
