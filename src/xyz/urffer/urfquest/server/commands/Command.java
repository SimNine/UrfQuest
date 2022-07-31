package xyz.urffer.urfquest.server.commands;

import xyz.urffer.urfquest.server.ClientThread;
import xyz.urffer.urfquest.server.Server;

public abstract class Command {
	
	public String base;
	public String usage;
	public String description;
	public int permissionLevel;
	
	public Command(String base, String usage, String description, int permissionLevel) {
		this.base = base;
		this.usage = usage;
		this.description = description;
		this.permissionLevel = permissionLevel;
	}
	
	public abstract void runCommand(Server server, String[] args, ClientThread clientThread);
	
}
