package urfquest.server.state;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.Timer;

import urfquest.server.Server;
import urfquest.server.entities.mobs.Player;
import urfquest.server.map.Map;

public class State {
	private Server server;

	private Map surfaceMap;
	private HashMap<Integer, Map> maps = new HashMap<Integer, Map>(); // mapID to map
	private HashMap<Integer, Player> players = new HashMap<Integer, Player>(); // playerID to player
	
	private boolean buildMode = false;
	
    public Timer gameTimer = new Timer(5, new ActionListener() { // gameticker
        public void actionPerformed(ActionEvent e) {
        	if (gameTimer.isRunning()) {
                tick();
        	}
        }
    });

	public State(Server srv) {
		this.server = srv;
		this.surfaceMap = new Map(srv, Map.SIMPLEX_MAP);
		// surfaceMap.generateItems();
		// surfaceMap.generateChickens();
		this.maps.put(surfaceMap.id, surfaceMap);
	}

	/*
	 * Ticker and updater
	 */
	
	public void tick() {
		surfaceMap.update();
		
		// TODO: move player updating procedure to Map
		// currentPlayer.update();
		// otherPlayer.update();
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
	
	/*
	 * Build mode
	 */
	
	public void toggleBuildMode() {
		buildMode = !buildMode;
	}
	
	public boolean isBuildMode() {
		return buildMode;
	}
	
	/*
	 * GameRunning
	 */
	
	public void setGameRunning(boolean running) {
		if (gameTimer.isRunning() && !running)
			gameTimer.stop();
		else if (!gameTimer.isRunning() && running)
			gameTimer.start();
	}
	
	public boolean isGameRunning() {
		return gameTimer.isRunning();
	}
}