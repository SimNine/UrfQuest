package xyz.urffer.urfquest.server.state;

import java.util.HashMap;

import xyz.urffer.urfquest.server.Server;
import xyz.urffer.urfquest.server.entities.mobs.Player;
import xyz.urffer.urfquest.server.map.Map;

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
	
	public Player getPlayer(int entityID) {
		return players.get(entityID);
	}
	
	public Player removePlayer(int entityID) {
		for (Map m : maps.values()) {
			if (m.getPlayers().containsKey(entityID)) {
				m.removePlayer(entityID);
				break;
			}
		}
		return players.remove(entityID);
	}
	
	
	/*
	 * Map management
	 */
	
	public Map getSurfaceMap() {
		return surfaceMap;
	}
	
	public Map getMapByID(int id) {
		return maps.get(id);
	}

	public void setAllMaps(HashMap<Integer, Map> maps) {
		this.maps = maps;
	}
}