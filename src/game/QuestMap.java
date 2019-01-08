package game;

import java.util.ArrayList;

import entities.Entity;
import entities.items.Item;
import entities.items.Key;
import framework.SimplexNoise;
import framework.UrfQuest;

@SuppressWarnings("unused")
public class QuestMap {
	
	private int[][] map;
	public ArrayList<Entity> entities;
	public ArrayList<Item> items;
	
	public QuestMap(int width, int height) {
		map = generateSimplexNoiseMap(width, height);
		entities = generateEntities(0);
		
		items = generateKeys();
	}
	
	public void update() {
		ArrayList<Item> remove = new ArrayList<Item>();
		for (Item i : items) {
			if (UrfQuest.game.player.collides(i) && i.getType().equals("key")) {
				remove.add(i);
				UrfQuest.game.incKeyCount(1);
				System.out.println("collides key");
			}
		}
		items.removeAll(remove);
		remove.clear();
	}
	
	private static int[][] generateSimplexNoiseMap(int width, int height) {
		int[][] end = new int[width][height];
		
		// generate land and water
		float[][] terrainNoise = SimplexNoise.generateSimplexNoise(width, height);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if (terrainNoise[x][y] > .5f) {
					end[x][y] = 2;
				} else {
					end[x][y] = 8;
				}
			}
		}
		
		// generate trees (only on land tiles)
		float[][] treeNoise = SimplexNoise.generateSimplexNoise(width, height, 20);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if (treeNoise[x][y]*2 -1 > Math.random() && end[x][y] == 2) {
					end[x][y] = 7;
				}
			}
		}
		
		generateStartingArea(end);
		generateBorderWall(end);
		
		return end;
	}
	
	private static int[][] generateSavannahMap(int width, int height) {
		int[][] end = new int[width][height];
		
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				end[x][y] = 1;
			}
		}
		for (int x = 1; x < width - 1; x++) {
			for (int y = 1; y < height - 1; y++) {
				if (Math.random() < .1) end[x][y] = 2;
			}
		}
		for (int x = 2; x < width - 2; x++) {
			for (int y = 2; y < height - 2; y++) {
				if (Math.random() < .2) end[x][y] = 2;
			}
		}
		for (int x = 3; x < width - 3; x++) {
			for (int y = 3; y < height - 3; y++) {
				if (Math.random() < .4) end[x][y] = 2;
			}
		}
		for (int x = 4; x < width - 4; x++) {
			for (int y = 4; y < height - 4; y++) {
				if (Math.random() < .9) end[x][y] = 2;
			}
		}
		for (int x = 5; x < width - 5; x++) {
			for (int y = 5; y < height - 5; y++) {
				if (Math.random() < .1) end[x][y] = 7;
				else end[x][y] = 2;
			}
		}
		
		generateStartingArea(end);
		generateBorderWall(end);
		
		return end;
	}
	
	private static int[][] generateTemplateMap(int width, int height) {
		int[][] end = new int[width][height];
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				end[x][y] = 1;
			}
		}
		for (int x = 1; x < width - 1; x++) {
			for (int y = 1; y < height - 1; y++) {
				if (Math.random() < .1) end[x][y] = 2;
			}
		}
		for (int x = 2; x < width - 2; x++) {
			for (int y = 2; y < height - 2; y++) {
				if (Math.random() < .2) end[x][y] = 2;
			}
		}
		for (int x = 3; x < width - 3; x++) {
			for (int y = 3; y < height - 3; y++) {
				if (Math.random() < .4) end[x][y] = 2;
			}
		}
		for (int x = 4; x < width - 4; x++) {
			for (int y = 4; y < height - 4; y++) {
				if (Math.random() < .9) end[x][y] = 2;
			}
		}
		for (int x = 5; x < width - 5; x++) {
			for (int y = 5; y < height - 5; y++) {
				end[x][y] = 2;
			}
		}
		
		generateStartingArea(end);
		generateBorderWall(end);
		
		return end;
	}
	
	private ArrayList<Entity> generateEntities(int num) {
		ArrayList<Entity> entities = new ArrayList<Entity>();
		for (int i = 0; i < num; i++) {
			//nothing, atm
		}
		return entities;
	}
	
	private ArrayList<Item> generateKeys() {
		ArrayList<Item> items = new ArrayList<Item>();
		for (int x = 0; x < map.length; x++) {
			for (int y = 0; y < map[0].length; y++) {
				if (map[x][y] == 2 && Math.random() < 0.005) {
					items.add(new Key(x, y));
				}
			}
		}
		return items;
	}
	
	private static void generateStartingArea(int[][] map) {
		for (int x = -3; x < 4; x++) {
			for (int y = -3; y < 4; y++) {
				map[map.length/2+x][map[0].length/2+y] = 2;
			}
		}
		
		map[map.length/2-2][map[0].length/2-2] = 3;
		map[map.length/2-2][map[0].length/2+2] = 4;
		map[map.length/2+2][map[0].length/2-2] = 5;
		map[map.length/2+2][map[0].length/2+2] = 6;
	}
	
	private static void generateBorderWall(int[][] map) {
		for (int i = 0; i < map.length; i++) {
			map[i][0] = 1;
			map[i][map[0].length-1] = 1;
		}
		
		for (int i = 0; i < map[0].length; i++) {
			map[0][i] = 1;
			map[map.length-1][i] = 1;
		}
	}
	
	// Getters and setters
	public int getTileAt(int x, int y) {
		if (x < 0 || y < 0) return -1;
		if (x >= map.length || y >= map[0].length) return -1;
		return map[x][y];
	}
	
	public void setTileAt(int x, int y, int t) {
		map[x][y] = t;
	}
	
	public void setNewMap(int w, int h) {
		map = new int[w][h];
	}
	
	public int getWidth() {
		return map.length;
	}
	
	public int getHeight() {
		return map[0].length;
	}
}
