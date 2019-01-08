package game;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import entities.Entity;
import entities.items.Gem;
import entities.items.Gun;
import entities.items.Item;
import entities.items.Key;
import framework.SimplexNoise;
import framework.UrfQuest;
import tiles.Tiles;

public class QuestMap {
	
	private int[][] map;
	private BufferedImage minimap;
	public ArrayList<Entity> entities;
	public ArrayList<Item> items;
	
	public QuestMap(int width, int height) {
		map = new int[width][height];
		entities = new ArrayList<Entity>();
		items = new ArrayList<Item>();
	}
	
	public void generateMinimap() {
		minimap = new BufferedImage(map.length, map[0].length, BufferedImage.TYPE_4BYTE_ABGR);
		for (int x = 0; x < map.length; x++) {
			for (int y = 0; y < map[0].length; y++) {
				int color = Tiles.minimapColor(this.getTileAt(x, y));
				minimap.setRGB(x, y, color);
			}
		}
	}
	
	public BufferedImage getMinimap() {
		return minimap;
	}
	
	// checks for collisions
	public void update() {
		ArrayList<Item> remove = new ArrayList<Item>();
		for (Item i : items) {
			if (UrfQuest.game.player.collides(i)) {
				if (UrfQuest.debug) {
					System.out.println("player collided with object: " + i.getType());
				}
				if (UrfQuest.game.player.addItem(i)) {
					remove.add(i);
				} else {
					continue;
				}
			}
		}
		items.removeAll(remove);
		remove.clear();
	}
	
	// map generation methods
	public void generateSimplexNoiseMap() {
		int width = map.length;
		int height = map[0].length;
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
		
		this.map = end;
	}
	
	public void generateSavannahMap() {
		int width = map.length;
		int height = map[0].length;
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
		
		this.map = end;
	}
	
	public void generateTemplateMap() {
		int width = map.length;
		int height = map[0].length;
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
		
		this.map = end;
	}
	
	// helper generation methods
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
	
	// entity generation methods
	public void generateEntities(int num) {
		ArrayList<Entity> entities = new ArrayList<Entity>();
		for (int i = 0; i < num; i++) {
			//nothing, atm
		}
		this.entities = entities;
	}
	
	public void generateItems() {
		ArrayList<Item> items = new ArrayList<Item>();
		for (int x = 0; x < map.length; x++) {
			for (int y = 0; y < map[0].length; y++) {
				if (map[x][y] == 2 && Math.random() < 0.005) {
					double rand = Math.random();
					if (rand > .99) {
						items.add(new Key(x, y));
					} else if (rand > .95) {
						items.add(new Gun(x, y));
					} else {
						items.add(new Gem(x, y));
					}
				}
			}
		}
		this.items = items;
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
	
	public void addItem(Item i) {
		items.add(i);
	}
}
