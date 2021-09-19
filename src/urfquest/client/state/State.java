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
		currentMap = new Map(Constants.localMapRadius);
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
