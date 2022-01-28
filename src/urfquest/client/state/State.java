package urfquest.client.state;

import urfquest.client.Client;
import urfquest.client.entities.mobs.CameraMob;
import urfquest.client.entities.mobs.Mob;
import urfquest.client.entities.mobs.Player;
import urfquest.client.map.Map;
import urfquest.shared.Constants;

public class State {
	private Client client;
	
	private boolean isGameRunning;
	private boolean isBuildMode;
	
	private Map currentMap;
	
	private Player player;
	private CameraMob camera;

	public State(Client c) {
		this.client = c;
		this.isGameRunning = false;
		this.currentMap = new Map(c, Constants.CLIENT_CACHED_MAP_DIAMETER);
		this.camera = new CameraMob(this.client, -1, currentMap, 
				currentMap.getHomeCoords()[0], 
				currentMap.getHomeCoords()[1], 
				CameraMob.STILL_MODE);
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
	
	/*
	 * Camera
	 */
	
	public Mob getCamera() {
		if (this.player == null) {
			return this.camera;
		} else {
			return this.player;
		}
	}
	
	public void setCamera(CameraMob c) {
		this.camera = c;
	}
	
}
