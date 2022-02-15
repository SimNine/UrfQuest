package urfquest.server.state;

import java.util.HashMap;

import urfquest.server.Server;
import urfquest.server.entities.mobs.Player;
import urfquest.server.map.Map;

public class State {
	private Server server;

	private Map surfaceMap;
	private HashMap<Integer, Map> maps = new HashMap<Integer, Map>(); // mapID to map
	private HashMap<Integer, Player> players = new HashMap<Integer, Player>(); // playerID to player

	public State(Server srv) {
		this.server = srv;
		
		this.surfaceMap = new Map(srv, Map.SIMPLEX_MAP);
		this.maps.put(surfaceMap.id, surfaceMap);
	}

	/*
	 * Ticker and updater
	 */
	
	public void tick() {
		for (Map m : maps.values()) {
			m.tick();
		}
	}
	
	/*
	 * Player management
	 */
	
	public void addPlayer(Player p) {
		players.put(p.id, p);
	}
	
	public Player getPlayer(int id) {
		return players.get(id);
	}
	
	public Player removePlayer(int id) {
		return players.remove(id);
	}
	
	/*
	 * Map management
	 */
	
	public Map getSurfaceMap() {
		return surfaceMap;
	}

	public HashMap<Integer, Map> getAllMaps() {
		return maps;
	}

	public void setAllMaps(HashMap<Integer, Map> maps) {
		this.maps = maps;
	}
}