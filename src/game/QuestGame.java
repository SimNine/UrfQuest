package game;

import entities.Entity;
import entities.characters.Player;
import framework.V;

public class QuestGame {
	
	public QuestGame() {
		V.qGame = this;
		V.player = new Player(V.qMap.getWidth()/2.0, V.qMap.getHeight()/2.0);
	}
	
	public void tick() {
		for (Entity e : V.entities) {
			e.update();
		}
		
		V.player.update();
	}
}