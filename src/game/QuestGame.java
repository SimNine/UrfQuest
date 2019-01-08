package game;

import java.util.ArrayList;

import entities.mobs.Player;
import entities.items.Item;

public class QuestGame {

	private QuestMap surfaceMap;
	private QuestMap currMap;
	private ArrayList<QuestMap> maps = new ArrayList<QuestMap>();
	private Player player;
	private boolean buildMode = false;

	public QuestGame() {
		surfaceMap = new QuestMap(500, 500, QuestMap.SIMPLEX_MAP);
		surfaceMap.generateItems();
		surfaceMap.generateMobs();
		
		player = new Player(surfaceMap.getHomeCoords()[0], surfaceMap.getHomeCoords()[1]);

		maps.add(surfaceMap);
		currMap = surfaceMap;
	}

	public void tick() {
		currMap.update();
		player.update();
	}
	
	public QuestMap getSurfaceMap() {
		return surfaceMap;
	}

	public QuestMap getCurrMap() {
		return currMap;
	}

	public void setCurrMap(QuestMap map) {
		if (!maps.contains(map)) {
			maps.add(map);
		}
		currMap = map;
	}

	public ArrayList<QuestMap> getAllMaps() {
		return maps;
	}

	public void setAllMaps(ArrayList<QuestMap> maps) {
		this.maps = maps;
	}
	
	public void toggleBuildMode() {
		buildMode = !buildMode;
	}
	
	public boolean isBuildMode() {
		return buildMode;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player p) {
		this.player = p;
	}
	
	public void dropOneOfSelectedItem() {
		Item i = player.dropOneOfSelectedItem();
		
		if (i == null) {
			return;
		}
		
		double[] playerPos = player.getPos();
		i.setPos(playerPos[0], playerPos[1]-1);
		currMap.addItem(i);
	}
	
	public void setSelectedEntry(int i) {
		player.setSelectedEntry(i);
	}
	
	public void tryMapLink() {
		MapLink ml = null;
		for (MapLink l : currMap.getLinks().keySet()) {
			if (l.getMap() == currMap && 
				l.getCoords()[0] == (int)player.getCenter()[0] &&
				l.getCoords()[1] == (int)player.getCenter()[1]) {
				//System.out.println("match found");
				ml = l;
			}
		}
		
		if (ml != null) {
			MapLink endPoint = currMap.getLinks().get(ml);
			currMap = endPoint.getMap();
			player.setPos(endPoint.getCoords()[0], endPoint.getCoords()[1]);
		}
	}
}