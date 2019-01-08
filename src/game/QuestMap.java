package game;

import java.awt.Color;
import java.util.ArrayList;

import entities.Entity;
import entities.shapes.Ball;
import entities.shapes.Square;
import framework.V;

@SuppressWarnings("unused")
public class QuestMap {
	
	private int[][] map;
	
	public QuestMap(int width, int height) {
		map = generateMap(width, height);
		
		V.entities = generateEntities(0);
		V.qMap = this;
	}
	
	public QuestMap(String levelfile) {
		//map = load(levelfile);
		//V.qMap = this;
	}
	
	public static int[][] generateMap(int width, int height) {
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
		
		end[245][245] = 3;
		end[245][255] = 4;
		end[255][255] = 5;
		end[255][245] = 6;
		
		return end;
	}
	
	private ArrayList<Entity> generateEntities(int num) {
		ArrayList<Entity> entities = new ArrayList<Entity>();
		for (int i = 0; i < num; i++) {
			entities.add(new Square(Math.random()*map.length, Math.random()*map[0].length,
						 new Color((int)(Math.random()*255), (int)(Math.random()*255), (int)(Math.random()*255)),
						 (int)(Math.random()*100)));
		}
		return entities;
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
