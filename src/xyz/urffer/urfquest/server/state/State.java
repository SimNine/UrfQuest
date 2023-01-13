package xyz.urffer.urfquest.server.state;

import java.util.Collection;
import java.util.HashMap;

import xyz.urffer.urfquest.server.Server;
import xyz.urffer.urfquest.server.entities.Entity;
import xyz.urffer.urfquest.server.map.Map;

public class State {
	@SuppressWarnings("unused")
	private Server server;

	private Map surfaceMap;
	private HashMap<Integer, Map> maps = new HashMap<>();
	private HashMap<Integer, Entity> entities = new HashMap<>();

	public State(Server srv) {
		this.server = srv;
		
		this.surfaceMap = new Map(srv, Map.SIMPLEX_MAP);
		this.maps.put(surfaceMap.id, surfaceMap);
	}

	
	/*
	 * Ticker and updater
	 */
	
	public void tick() {
		// Tick entities
		for (Entity e : entities.values()) {
			e.tick();
		}
		
		// Tick maps
		for (Map m : maps.values()) {
			m.tick();
		}
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
	
	
	/*
	 * Entity management
	 */
	
	public void addEntity(Entity e) {
		entities.put(e.id, e);
	}
	
	public Entity getEntity(int entityID) {
		return entities.get(entityID);
	}
	
	public void removeEntity(int entityID) {
		entities.get(entityID).destroy();
		entities.remove(entityID);
		for (Map m : maps.values()) {
			m.removeEntity(entityID);
		}
	}
	
	public Collection<Entity> getAllEntities() {
		return entities.values();
	}
}