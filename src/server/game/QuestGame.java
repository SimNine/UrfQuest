package server.game;

import java.util.ArrayList;

import entities.mobs.Player;
import game.QuestMap;

public class QuestGame {

	private QuestMap surfaceMap;
	private ArrayList<QuestMap> maps = new ArrayList<QuestMap>();
	
	private Player currentPlayer;
	private Player otherPlayer;
	
	private boolean buildMode = false;

	public QuestGame() {
		surfaceMap = new QuestMap(500, 500, QuestMap.SIMPLEX_MAP);
		surfaceMap.generateItems();
		surfaceMap.generateChickens();
		maps.add(surfaceMap);

		currentPlayer = new Player(surfaceMap.getHomeCoords()[0], surfaceMap.getHomeCoords()[1], surfaceMap, "Chris");
		otherPlayer = new Player(surfaceMap.getHomeCoords()[0] - 1, surfaceMap.getHomeCoords()[1] - 1, surfaceMap, "Nick");
		surfaceMap.addPlayer(currentPlayer);
		surfaceMap.addPlayer(otherPlayer);
	}

	/*
	 * Ticker and updater
	 */
	
	public void tick() {
		currentPlayer.getMap().update();
		
		currentPlayer.update();
		otherPlayer.update();
	}
	
	/*
	 * Map management
	 */
	
	public QuestMap getSurfaceMap() {
		return surfaceMap;
	}

	public QuestMap getCurrMap() {
		return currentPlayer.getMap();
	}

	public void setCurrMap(QuestMap map) {
		if (!maps.contains(map)) {
			maps.add(map);
		}
		currentPlayer.setMap(map);
	}

	public ArrayList<QuestMap> getAllMaps() {
		return maps;
	}

	public void setAllMaps(ArrayList<QuestMap> maps) {
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
	 * Player
	 */
	
	public Player getPlayer() {
		return currentPlayer;
	}

	public void setPlayer(Player p) {
		this.currentPlayer = p;
	}
	
	public void switchPlayer() {
		Player temp = currentPlayer;
		currentPlayer = otherPlayer;
		otherPlayer = temp;
	}
}