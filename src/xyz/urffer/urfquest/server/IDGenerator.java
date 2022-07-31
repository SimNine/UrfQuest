package xyz.urffer.urfquest.server;

public class IDGenerator {
	
	private static int currentID = 0;

	public static int newID() {
		currentID++;
		return currentID;
	}
	
}
