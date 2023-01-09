package xyz.urffer.urfquest.server.state;

import java.util.HashMap;

import xyz.urffer.urfutils.math.PairDouble;

import xyz.urffer.urfquest.server.Server;
import xyz.urffer.urfquest.server.entities.projectiles.Bullet;
import xyz.urffer.urfquest.server.entities.Entity;
import xyz.urffer.urfquest.server.map.Map;

public class State {
	private Server server;

	private Map surfaceMap;
	private HashMap<Integer, Map> maps = new HashMap<>();
	private HashMap<Integer, Entity> entities = new HashMap<>();

	private int tickCount = 0;

	public State(Server srv) {
		this.server = srv;
		
		this.surfaceMap = new Map(srv, Map.SIMPLEX_MAP);
		this.maps.put(surfaceMap.id, surfaceMap);
	}

	
	/*
	 * Ticker and updater
	 */
	
	public void tick() {
		tickCount++;
		for (Map m : maps.values()) {
			m.tick();
			
			// TODO: remove. temp
			if (tickCount > 100) {
				tickCount = 0;
				//new Bullet(server, null).setPos(new PairDouble(5, 5), m.id);
			}
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
		entities.remove(entityID);
		for (Map m : maps.values()) {
			m.removeEntity(entityID);
		}
	}
}