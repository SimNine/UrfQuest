package urfquest.server.state;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.Timer;

import urfquest.server.entities.mobs.Player;
import urfquest.server.map.Map;

public class State {

	private Map surfaceMap;
	private ArrayList<Map> maps = new ArrayList<Map>();
	private HashMap<Integer, Player> players = new HashMap<Integer, Player>();
	
	private boolean buildMode = false;
	
    public Timer gameTimer = new Timer(5, new ActionListener() { // gameticker
        public void actionPerformed(ActionEvent e) {
        	if (gameTimer.isRunning()) {
                tick();
        	}
        }
    });

	public State() {
		surfaceMap = new Map(Map.SIMPLEX_MAP);
		// surfaceMap.generateItems();
		// surfaceMap.generateChickens();
		maps.add(surfaceMap);

		// TODO: add creation of a new player upon client connect
//		currentPlayer = new Player(surfaceMap.getHomeCoords()[0], surfaceMap.getHomeCoords()[1], surfaceMap, "Chris");
//		otherPlayer = new Player(surfaceMap.getHomeCoords()[0] - 1, surfaceMap.getHomeCoords()[1] - 1, surfaceMap, "Nick");
//		surfaceMap.addPlayer(currentPlayer);
//		surfaceMap.addPlayer(otherPlayer);
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
	public Player createPlayer(int id, String name) {
		Player newPlayer = new Player(surfaceMap.getHomeCoords()[0], surfaceMap.getHomeCoords()[1], 
									  surfaceMap, name, id);
		players.put(id, newPlayer);
		surfaceMap.addPlayer(newPlayer);
		
		return newPlayer;
	}
	
	public Player getPlayer(int id) {
		return players.get(id);
	}
	
	/*
	 * Map management
	 */
	
	public Map getSurfaceMap() {
		return surfaceMap;
	}

	public ArrayList<Map> getAllMaps() {
		return maps;
	}

	public void setAllMaps(ArrayList<Map> maps) {
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