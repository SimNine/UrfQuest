package tiles;

import entities.mobs.Mob;
import game.QuestMap;

public class MapLink extends ActiveTile {
	private QuestMap a;
	private int ax;
	private int ay;
	private QuestMap b;
	private int bx;
	private int by;

	public MapLink(QuestMap a, int ax, int ay, QuestMap b, int bx, int by) {
		this.a = a;
		this.ax = ax;
		this.ay = ay;
		this.b = b;
		this.bx = bx;
		this.by = by;
	}
	
	public QuestMap getOtherMap(QuestMap m) {
		if (m == a) {
			return b;
		} else if (m == b) {
			return a;
		} else {
			throw new IllegalArgumentException();
		}
	}
	
	public int[] getOtherCoordinates(QuestMap m) {
		if (m == a) {
			return new int[] {bx, by};
		} else if (m == b) {
			return new int[] {ax, ay};
		} else {
			throw new IllegalArgumentException();
		}
	}

	public void use(Mob m) {
		int[] coords = getOtherCoordinates(m.getMap());
		m.setPos(coords[0], coords[1]);
		
		m.setMap(getOtherMap(m.getMap()));
	}
}