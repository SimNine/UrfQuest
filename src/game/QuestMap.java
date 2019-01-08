package game;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import entities.items.Cheese;
import entities.items.Gem;
import entities.items.Pistol;
import entities.items.SMG;
import entities.mobs.Chicken;
import entities.mobs.Cyclops;
import entities.mobs.Mob;
import entities.items.Item;
import entities.items.Key;
import entities.particles.Particle;
import framework.UrfQuest;
import tiles.Tiles;
import urf.SimplexNoise;

public class QuestMap {
	public static final int EMPTY_MAP = 5000;
	public static final int SIMPLEX_MAP = 5001;
	public static final int SAVANNAH_MAP = 5002;
	public static final int TEMPLATE_MAP = 5003;
	
	private int[][] map;
	private BufferedImage minimap;
	private int[] homeCoords = new int[2];
	public ArrayList<Mob> mobs = new ArrayList<Mob>();
	public ArrayList<Item> items = new ArrayList<Item>();
	public ArrayList<Particle> particles = new ArrayList<Particle>();
	
	public QuestMap(int width, int height, int type) {
		map = new int[width][height];
		
		switch (type) {
		case EMPTY_MAP:
			homeCoords[0] = map.length/2;
			homeCoords[1] = map[0].length/2;
			break;
		case SIMPLEX_MAP:
			generateSimplexNoiseMap();
			break;
		case SAVANNAH_MAP:
			generateSavannahMap();
			break;
		case TEMPLATE_MAP:
			generateTemplateMap();
			break;
		}
		
		if (type != EMPTY_MAP) {
			generateStartingArea();
			generateBorderWall();
			generateMinimap();
		}
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
	
	// updates entities and checks for collisions
	public void update() {
		ArrayList<Item> removeItems = new ArrayList<Item>();
		ArrayList<Particle> removeParticles = new ArrayList<Particle>();
		ArrayList<Mob> removeMobs = new ArrayList<Mob>();
		
		// check for the player colliding with items
		for (Item i : items) {
			if (UrfQuest.game.player.collides(i)) {
				if (UrfQuest.debug) {
					System.out.println("player collided with object: " + i.getClass().getName());
				}
				if (UrfQuest.game.player.addItem(i)) {
					removeItems.add(i);
				} else {
					continue;
				}
			}
		}
		
		// update mobs and check for the player colliding with mobs
		for (Mob m : mobs) {
			m.update();
			if (UrfQuest.game.player.collides(m)) {
				if (UrfQuest.debug) {
					System.out.println("player collided with object: " + m.getClass().getName());
				}
			}
		}
		
		// update particles
		for (Particle p : particles) {
			if (p.isDead()) {
				removeParticles.add(p);
			}
			p.update();
		}
		
		// check for collisions between particles and mobs
		for (Particle p : particles) {
			for (Mob m : mobs) {
				if (p.collides(m)) {
					m.incrementHealth(-5.0);
					removeParticles.add(p);
				}
			}
		}
		
		// clean up dead mobs
		for (Mob m : mobs) {
			if (m.isDead()) {
				m.onDeath();
				removeMobs.add(m);
			}
		}

		particles.removeAll(removeParticles);
		items.removeAll(removeItems);
		mobs.removeAll(removeMobs);
	}
	
	// map generation methods
	public void generateSimplexNoiseMap() {
		int width = map.length;
		int height = map[0].length;
		int[][] end = new int[width][height];
		
		// terrain distortion params
		boolean enableDistortion = true;
		double distortWeight = 0.25;
		int distortFreq = 20;
		boolean enableDistortionDistribution = false;
		int distortDistributionFreq = 20;
		
		// generate land and water
		float[][] terrainNoise = SimplexNoise.generateSimplexNoise(width, height, 5);
		float[][] distortionNoise = SimplexNoise.generateSimplexNoise(width, height, distortFreq);
		float[][] distortionDistribution = SimplexNoise.generateSimplexNoise(width, height, distortDistributionFreq);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if (enableDistortionDistribution && distortionDistribution[x][y] > .5) {
					distortionNoise[x][y] *= (distortionDistribution[x][y] - 0.5)*2;
				}
				if (enableDistortion) {
					terrainNoise[x][y] += distortionNoise[x][y]*distortWeight;
					terrainNoise[x][y] /= (1 + distortWeight);
				}
				
				if (terrainNoise[x][y] > .55f) {
					end[x][y] = 2;
				} else if (terrainNoise[x][y] > .5f) {
					end[x][y] = 9;
				} else {
					end[x][y] = 8;
				}
			}
		}
		
		// generate boulders
		float[][] boulderNoise = SimplexNoise.generateSimplexNoise(width, height, 20);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if (boulderNoise[x][y]*2 - 1.6 > Math.random()) {
					if (end[x][y] == 2) {
						end[x][y] = 10;
					} else if (end[x][y] == 8) {
						end[x][y] = 11;
					} else if (end[x][y] == 9) {
						end[x][y] = 12;
					}
				}
			}
		}
		
		// generate trees (only on land tiles)
		float[][] treeNoise = SimplexNoise.generateSimplexNoise(width, height, 20);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if (treeNoise[x][y]*2 - 1 > Math.random() && end[x][y] == 2) {
					end[x][y] = 7;
				}
			}
		}
		
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
		
		this.map = end;
	}
	
	// helper generation methods
	private void generateStartingArea() {
		int spawnCenterX = map.length/2;
		int spawnCenterY = map[0].length/2;
		
		while (!Tiles.isWalkable(getTileAt(spawnCenterX, spawnCenterY))) {
			spawnCenterX = (int)(Math.random()*(map.length - 20)) + 10;
			spawnCenterY = (int)(Math.random()*(map.length - 20)) + 10;
		}
		
		for (int x = -3; x < 4; x++) {
			for (int y = -3; y < 4; y++) {
				map[spawnCenterX+x][spawnCenterY+y] = 2;
			}
		}
		
		map[spawnCenterX-2][spawnCenterY-2] = 3;
		map[spawnCenterX-2][spawnCenterY+2] = 4;
		map[spawnCenterX+2][spawnCenterY-2] = 5;
		map[spawnCenterX+2][spawnCenterY+2] = 6;
		
		homeCoords[0] = spawnCenterX;
		homeCoords[1] = spawnCenterY;
	}
	
	private void generateBorderWall() {
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
	public void generateMobs() {
		ArrayList<Mob> mobs = new ArrayList<Mob>();
		for (int x = 0; x < map.length; x++) {
			for (int y = 0; y < map[0].length; y++) {
				if (Tiles.isWalkable(map[x][y]) && Math.random() < 0.001) {
					if (Math.random() > .05) {
						mobs.add(new Chicken(x, y));
					} else {
						mobs.add(new Cyclops(x, y));
					}
				}
			}
		}
		this.mobs = mobs;
	}
	
	public void generateItems() {
		ArrayList<Item> items = new ArrayList<Item>();
		for (int x = 0; x < map.length; x++) {
			for (int y = 0; y < map[0].length; y++) {
				if (map[x][y] == 2 && Math.random() < 0.005) {
					double rand = Math.random();
					if (rand > .9975) {
						items.add(new Cheese(x, y));
					} else if (rand > .9875) {
						items.add(new Key(x, y));
					} else if (rand > .9375) {
						if (Math.random() > .1) {
							items.add(new Pistol(x, y));
						} else {
							items.add(new SMG(x, y));
						}
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
	
	public boolean setHomeCoords(int x, int y) {
		if (Tiles.isWalkable(getTileAt(x, y))) {
			homeCoords[0] = x;
			homeCoords[1] = y;
			return true;
		} else {
			return false;
		}
	}
	
	public int[] getHomeCoords() {
		return homeCoords;
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
	
	public void addParticle(Particle p) {
		particles.add(p);
	}
}
