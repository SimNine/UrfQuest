package urfquest.client.state;

import urfquest.client.entities.mobs.CameraMob;
import urfquest.client.entities.mobs.Mob;
import urfquest.client.entities.mobs.Player;
import urfquest.client.map.Map;
import urfquest.shared.message.Constants;

public class State {
	
	private boolean isGameRunning;
	private boolean isBuildMode;
	
	private Map currentMap;
	
	private Player player;
	private CameraMob camera;

	public State() {
		this.isGameRunning = false;
		this.currentMap = new Map(Constants.localMapRadius);
		this.camera = new CameraMob(-1, currentMap, 
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
