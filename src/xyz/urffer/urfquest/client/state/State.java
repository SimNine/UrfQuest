package xyz.urffer.urfquest.client.state;

import xyz.urffer.urfquest.client.Client;
import xyz.urffer.urfquest.client.entities.mobs.CameraMob;
import xyz.urffer.urfquest.client.entities.mobs.Mob;
import xyz.urffer.urfquest.client.entities.mobs.Player;
import xyz.urffer.urfquest.client.map.Map;
import xyz.urffer.urfquest.shared.ArrayUtils;
import xyz.urffer.urfquest.shared.Constants;

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
		this.currentMap = new Map(c, 0, Constants.CLIENT_CACHED_MAP_DIAMETER);
		this.camera = new CameraMob(this.client, -1, currentMap, 
				ArrayUtils.castToDoubleArr(currentMap.getHomeCoords()), 
				CameraMob.STILL_MODE);
	}
	
	public boolean isGameRunning() {
		return isGameRunning;
	}
	
	public boolean isBuildMode() {
		return isBuildMode;
	}
	
	
	/*
	 * Map
	 */
	
	public void createNewMap(int id) {
		this.currentMap = new Map(client, id, Constants.CLIENT_CACHED_MAP_DIAMETER);
	}
	
	public Map getCurrentMap() {
		return currentMap;
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
