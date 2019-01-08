package game;

public class MapLink {
	private QuestMap m;
	private int x;
	private int y;

	public MapLink(QuestMap m, int x, int y) {
		this.m = m;
		this.x = x;
		this.y = y;
	}
	
	public QuestMap getMap() {
		return m;
	}
	
	public int[] getCoords() {
		return new int[]{x, y};
	}
	
	public boolean equals(Object l) {
		if (this.getClass() != l.getClass()) {
			return false;
		} else {
			MapLink ml = (MapLink) l;
			return (this.m == ml.m && this.x == ml.x && this.y == ml.y);
		}
	}
}