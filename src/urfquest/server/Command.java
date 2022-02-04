package urfquest.server;

public abstract class Command {
	
	public String base;
	public String usage;
	public String description;
	
	public Command(String base, String usage, String description) {
		this.base = base;
		this.usage = usage;
		this.description = description;
	}
	
	public abstract void runCommand(Server server, String[] args, int clientID);
	
}
