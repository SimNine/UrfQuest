package urfquest.client.state;

import urfquest.client.entities.mobs.Player;
import urfquest.client.map.Map;
import urfquest.shared.message.Constants;

public class State {
	
	private boolean isGameRunning;
	private boolean isBuildMode;
	
	private Map currentMap;
	
	private Player player;

	public State() {
		isGameRunning = false;
		
		// TODO: send request to create a player to the server, and only create it when a response is recieved
		player = new Player(0, 0, currentMap, "Chris");
		currentMap = new Map(Constants.localMapRadius);
		
		player.setMap(currentMap);
		currentMap.addPlayer(player);
	}
	
	public boolean isGameRunning() {
		return isGameRunning;
	}
	
	public Map getCurrentMap() {
		return currentMap;
	}
	
	public boolean isBuildMode() {
		return isBuildMode;
	}

	/*
	 * Player
	 */
	
	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player p) {
		this.player = p;
	}
	
}
