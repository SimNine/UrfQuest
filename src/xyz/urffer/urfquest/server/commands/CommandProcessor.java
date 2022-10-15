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
import xyz.urffer.urfquest.shared.PairDouble;
import xyz.urffer.urfquest.shared.protocol.messages.MessageChat;
import xyz.urffer.urfquest.shared.protocol.messages.MessageDisconnect;

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
							MessageChat mc = new MessageChat();
							String payloadMessage;
							if (c == null) {
								payloadMessage = CommandProcessor.commandNotRecognized;
							} else {
								payloadMessage = c.base + " " + c.usage + " - " + c.description;
							}
							mc.chatMessage = new ChatMessage(ChatMessage.serverSource, payloadMessage);
							server.sendMessageToClientOrServer(mc, clientThreadID);
						} else {
							for (Command c : CommandProcessor.commands.values()) {
								if (clientThread != null && c.permissionLevel < clientThread.getCommandPermissions()) {
									continue;
								}
								
								MessageChat mc = new MessageChat();
								String payloadMessage = c.base + " " + c.usage + " - " + c.description;
								mc.chatMessage = new ChatMessage(ChatMessage.serverSource, payloadMessage);
								server.sendMessageToClientOrServer(mc, clientThreadID);
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
						MessageChat m = new MessageChat();
						
						if (args.length > 1) {
							Integer playerID = server.getUserMap().getPlayerIdFromPlayerName(args[1]);
							if (playerID == null) { // if the specified player wasn't found
								m.chatMessage = new ChatMessage(ChatMessage.serverSource, "Specified player '" + args[1] + "' not found");
							} else {
								Player p = server.getState().getPlayer(playerID);
								PairDouble pos = p.getPos();
								m.chatMessage = new ChatMessage(ChatMessage.serverSource, args[1] + "'s position is (" + pos.x + "," + pos.y + ")");
							}
						} else {
							if (clientThread == null) {
								m.chatMessage = new ChatMessage(ChatMessage.serverSource, "This command must be used with an argument when sent from the server");
							} else {
								PairDouble pos = server.getState().getPlayer(server.getUserMap().getPlayerIdFromClientId(clientThread.id)).getPos();
								m.chatMessage = new ChatMessage(ChatMessage.serverSource, "Your position is (" + pos.x + "," + pos.x + ")");
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
						
						MessageChat m = new MessageChat();
						String payloadString = "";
						for (String s : server.getUserMap().getAllPlayerNames()) {
							payloadString += ", " + s;
						}
						payloadString = "Online players: " + payloadString.substring(2);
						m.chatMessage = new ChatMessage(ChatMessage.serverSource, payloadString);

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
						
						MessageChat m = new MessageChat();
						String thisPlayerName;
						if (clientThread == null) {
							thisPlayerName = "SERVER";
						} else {
							thisPlayerName = server.getUserMap().getPlayerNameFromClientId(clientThread.id);
						}
						args[0] = "";
						m.chatMessage = new ChatMessage(ChatMessage.serverSource, thisPlayerName + String.join(" ", args));
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
						
						PairDouble pos = new PairDouble(0,0);
						try {
							pos.x = Double.parseDouble(args[1]);
							pos.y = Double.parseDouble(args[2]);
						} catch (Exception e) {
							CommandProcessor.sendIncorrectArgumentsMessage(server, this, clientThread);
							return;
						}
						
						int thisPlayerID = server.getUserMap().getPlayerIdFromClientId(clientThread.id);
						Player thisPlayer = server.getState().getPlayer(thisPlayerID);
						thisPlayer.setPos(pos);
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
						
						PairDouble pos = new PairDouble(0,0);
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
								pos.x = Double.parseDouble(args[2]);
								pos.y = Double.parseDouble(args[3]);
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

						MessageChat m = new MessageChat();
						Integer otherClientID = server.getUserMap().getClientIdFromPlayerName(args[1]);
						if (otherClientID == null) { // if the specified player wasn't found
							m.chatMessage = new ChatMessage(ChatMessage.serverSource, "Specified player \"" + args[1] + "\" not found");
						} else {
							ClientThread c = server.getClient(otherClientID);
							c.setCommandPermissions(CommandPermissions.OP);
							m.chatMessage = new ChatMessage(ChatMessage.serverSource, args[1] + " granted op permissions");
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
							MessageChat m = new MessageChat();
							m.chatMessage = new ChatMessage(ChatMessage.serverSource, "Specified player '" + args[1] + "' not found");
							server.sendMessageToClientOrServer(m, thisClientID);
						} else if (otherClientID == thisClientID) { // specified player is self
							MessageChat m = new MessageChat();
							m.chatMessage = new ChatMessage(ChatMessage.serverSource, "You cannot kick yourself");
							server.sendMessageToClientOrServer(m, thisClientID);
						} else {
							MessageDisconnect m = new MessageDisconnect();
							m.disconnectedClientID = otherClientID;
							m.reason = "You have been kicked from the server";
							server.sendMessageToClientOrServer(m, server.getServerID());
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
		MessageChat m = new MessageChat();
		m.chatMessage = new ChatMessage(ChatMessage.serverSource, payloadMessage);
		
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
