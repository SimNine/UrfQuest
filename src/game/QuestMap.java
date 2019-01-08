package game;

public class QuestMap {
	
	private int[][] map;
	
	public QuestMap(int width, int height) {
		map = generate(width, height);
	}
	
	public QuestMap(String levelfile) {
	//	map = load(levelfile);
	}
	
	public static int[][] generate(int width, int height) {
		int[][] end = new int[width][height];
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				end[x][y] = (int)(Math.random()*3);
			}
		}
		return end;
	}
	
	public int getBlockAt(int x, int y) {
		if (x < 0 || y < 0) return -1;
		if (x >= map.length || y >= map[0].length) return -1;
		return map[x][y];
	}
	
	public int getWidth() {
		return map.length;
	}
	
	public int getHeight() {
		return map[0].length;
	}
}
