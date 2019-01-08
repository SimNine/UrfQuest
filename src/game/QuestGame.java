package game;

import java.util.ArrayList;

import entities.mobs.Player;
import entities.items.Item;

public class QuestGame {

	private QuestMap currMap;
	private ArrayList<QuestMap> maps;
	public Player player;

	public QuestGame() {
		currMap = new QuestMap(500, 500, QuestMap.SIMPLEX_MAP);
		currMap.generateItems();
		currMap.generateMobs();
		
		player = new Player(currMap.getHomeCoords()[0], currMap.getHomeCoords()[1]);

		maps = new ArrayList<QuestMap>();
		maps.add(currMap);
	}

	public void tick() {
		currMap.update();
		player.update();
	}

	public QuestMap getCurrMap() {
		return currMap;
	}

	public void setCurrMap(QuestMap map) {
		currMap = map;
	}

	public ArrayList<QuestMap> getAllMaps() {
		return maps;
	}

	public void setAllMaps(ArrayList<QuestMap> maps) {
		this.maps = maps;
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
}