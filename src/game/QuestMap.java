package game;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashSet;

import entities.mobs.Chicken;
import entities.mobs.Cyclops;
import entities.mobs.Mob;
import entities.mobs.Player;
import entities.mobs.Rogue;
import entities.particles.Particle;
import entities.projectiles.Projectile;
import entities.items.Item;
import framework.UrfQuest;
import tiles.ActiveTile;
import tiles.Tiles;
import urf.SimplexNoise;
import urf.Pair;

public class QuestMap {
	public static final int EMPTY_MAP = 5000;
	public static final int SIMPLEX_MAP = 5001;
	public static final int SAVANNAH_MAP = 5002;
	public static final int TEMPLATE_MAP = 5003;
	public static final int CAVE_MAP = 5004;
	
	private int[][] tileTypes;
	private int[][] tileSubtypes;
	
	private ActiveTile[][] activeTiles;
	
	private BufferedImage minimap;
	private int[] homeCoords = new int[2];

	private ArrayList<Player> players = new ArrayList<Player>();
	private ArrayList<Mob> mobs = new ArrayList<Mob>();
	private ArrayList<Item> items = new ArrayList<Item>();
	private ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
	private ArrayList<Particle> particles = new ArrayList<Particle>();

	private ArrayList<Player> addPlayers = new ArrayList<Player>();
	private ArrayList<Mob> addMobs = new ArrayList<Mob>();
	private ArrayList<Item> addItems = new ArrayList<Item>();
	private ArrayList<Projectile> addProjectiles = new ArrayList<Projectile>();
	private ArrayList<Particle> addParticles = new ArrayList<Particle>();

	private ArrayList<Player> removePlayers = new ArrayList<Player>();
	private ArrayList<Item> removeItems = new ArrayList<Item>();
	private ArrayList<Mob> removeMobs = new ArrayList<Mob>();
	private ArrayList<Projectile> removeProjectiles = new ArrayList<Projectile>();
	private ArrayList<Particle> removeParticles = new ArrayList<Particle>();
	
	public QuestMap(int width, int height, int type) {
		tileTypes = new int[width][height];
		tileSubtypes = new int[width][height];
		activeTiles = new ActiveTile[width][height];
		
		generateMinimap();
		
		switch (type) {
		case EMPTY_MAP:
			homeCoords[0] = tileTypes.length/2;
			homeCoords[1] = tileTypes[0].length/2;
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
		case CAVE_MAP:
			generateCaveMap();
			generateRogues();
			generateCyclopses();
			break;
		}
		
		if (type != EMPTY_MAP) {
			if (type == CAVE_MAP) {
				findHomeCoords();
			} else {
				generateStartingArea();
			}
			generateBorderWall();
		}
	}
	
	/*
	 * Tick updater
	 */
	
	public void update() {
		// update particles
		for (Particle p : particles) {
			p.update();
		}
		
		// update projectiles
		for (Projectile p : projectiles) {
			p.update();
		}
		
		// update mobs
		for (Mob m : mobs) {
			m.update();
		}
		
		// update items
		for (Item i : items) {
			i.update();
		}
		
		// check for items near players
		for (Player p : players) {
			for (Item i : items) {
				if (p.isWithinDistance(i, p.getPickupRange()) && i.isPickupable()) {
					i.accelerateTowards(p);
				}
			}
		}
		
		// check for players colliding with items
		for (Player p : players) {
			HashSet<Item> removeNow = new HashSet<Item>();
			for (Item i : items) {
				if (p.collides(i) && i.isPickupable()) {
					if (UrfQuest.debug) {
						System.out.println(p.getName() + " collided with object: " + i.getClass().getName());
					}
					if (p.addItem(i)) {
						removeNow.add(i);
					} else {
						continue;
					}
				}
			}
			items.removeAll(removeNow);
		}
		
		// check for the player colliding with mobs
		for (Mob m : mobs) {
			for (Player p : players) {
				if (p.collides(m)) {
					if (UrfQuest.debug) {
						System.out.println(p.getName() + " collided with object: " + m.getClass().getName());
					}
				}
			}
		}
		
		// check for collisions between projectiles and mobs
		for (Projectile p : projectiles) {
			for (Mob m : mobs) {
				if (p.getSource() == m) {
					continue;
				} else if (p.collides(m)) {
					p.collideWith(m);
				}
			}
		}
		
		// check for collisions between projectiles and players
		for (Player p : players) {
			for (Projectile j : projectiles) {
				if (j.getSource() == p) {
					continue;
				} else if (j.collides(p)) {
					j.collideWith(p);
				}
			}
		}
		
		// clean up dead particles
		for (Particle p : particles) {
			if (p.isDead()) {
				removeParticles.add(p);
			}
		}
		
		// clean up dead projectiles
		for (Projectile p : projectiles) {
			if (p.isDead()) {
				removeProjectiles.add(p);
			}
		}
		
		// clean up dead mobs
		for (Mob m : mobs) {
			if (m.isDead()) {
				m.onDeath();
				removeMobs.add(m);
			}
		}

		items.removeAll(removeItems);
		mobs.removeAll(removeMobs);
		projectiles.removeAll(removeProjectiles);
		players.removeAll(removePlayers);
		particles.removeAll(removeParticles);
		
		removeItems.clear();
		removeMobs.clear();
		removeProjectiles.clear();
		removePlayers.clear();
		removeParticles.clear();
		
		items.addAll(addItems);
		mobs.addAll(addMobs);
		projectiles.addAll(addProjectiles);
		players.addAll(addPlayers);
		particles.addAll(addParticles);
		
		addItems.clear();
		addMobs.clear();
		addProjectiles.clear();
		addPlayers.clear();
		addParticles.clear();
		
		updateTiles();
	}
	
	// helper function -- updates a number of tiles each tick
	private void updateTiles() {
		HashSet<Pair<Integer, Integer>> set = new HashSet<Pair<Integer, Integer>>();
		for (int i = 0; i < 20; i++) {
			int x = (int)(Math.random()*tileTypes.length);
			int y = (int)(Math.random()*tileTypes[0].length);
			set.add(new Pair<Integer, Integer>(x, y));
		}
		
		for (Pair<Integer, Integer> p : set) {
			int t = getTileTypeAt(p.a, p.b);
			switch (t) {
			case Tiles.DIRT:
				double chance = 0;
				if (getTileTypeAt(p.a, p.b + 1) == Tiles.GRASS) {
					chance += 0.25;
				}
				if (getTileTypeAt(p.a, p.b - 1) == Tiles.GRASS) {
					chance += 0.25;
				}
				if (getTileTypeAt(p.a + 1, p.b) == Tiles.GRASS) {
					chance += 0.25;
				}
				if (getTileTypeAt(p.a - 1, p.b) == Tiles.GRASS) {
					chance += 0.25;
				}
				if (chance > Math.random()) {
					setTileAt(p.a, p.b, Tiles.GRASS);
				}
				break;
			case Tiles.GRASS:
				if (getTileTypeAt(p.a, p.b + 1) == Tiles.TREE ||
					getTileTypeAt(p.a, p.b - 1) == Tiles.TREE ||
					getTileTypeAt(p.a + 1, p.b) == Tiles.TREE ||
					getTileTypeAt(p.a - 1, p.b) == Tiles.TREE) {
					if (Math.random() < 0.1) {
						setTileAt(p.a, p.b, Tiles.TREE);
					}
				}
				break;
			case Tiles.TREE:
				if (Math.random() < 0.18) {
					setTileAt(p.a, p.b, Tiles.GRASS);
				}
				break;
			}
		}
	}
	
	/*
	 * Map generation methods
	 */
	
	private void generateSimplexNoiseMap() {
		int width = tileTypes.length;
		int height = tileTypes[0].length;
		
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
					setTileAt(x, y, Tiles.GRASS);
				} else if (terrainNoise[x][y] > .5f) {
					setTileAt(x, y, Tiles.SAND);
				} else {
					setTileAt(x, y, Tiles.WATER);
				}
			}
		}
		
		// generate boulders
		float[][] boulderNoise = SimplexNoise.generateSimplexNoise(width, height, 20);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if (boulderNoise[x][y]*2 - 1.6 > Math.random()) {
					if (getTileTypeAt(x, y) == Tiles.GRASS) {
						setTileAt(x, y, Tiles.BOULDER, Tiles.GRASS_BOULDER);
					} else if (getTileTypeAt(x, y) == Tiles.WATER) {
						setTileAt(x, y, Tiles.BOULDER, Tiles.WATER_BOULDER);
					} else if (getTileTypeAt(x, y) == Tiles.SAND) {
						setTileAt(x, y, Tiles.BOULDER, Tiles.SAND_BOULDER);
					}
				}
			}
		}
		
		// generate flowers
		float[][] flowerNoise = SimplexNoise.generateSimplexNoise(width, height, 20);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if (flowerNoise[x][y]*2 - 1.6 > Math.random()) {
					if (getTileTypeAt(x, y) == Tiles.GRASS) {
						setTileAt(x, y, Tiles.GRASS, Tiles.GRASS_FLOWERS);
					}
				}
			}
		}
		
		// generate trees (only on land tiles)
		float[][] treeNoise = SimplexNoise.generateSimplexNoise(width, height, 20);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if (treeNoise[x][y]*2 - 1 > Math.random() && getTileTypeAt(x, y) == Tiles.GRASS) {
					setTileAt(x, y, Tiles.TREE);
				}
			}
		}
	}
	
	@SuppressWarnings("unused")
	private void generateColdSimplexNoiseMap() {
		int width = tileTypes.length;
		int height = tileTypes[0].length;
		
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
					setTileAt(x, y, Tiles.GRASS, Tiles.GRASS_SNOW);
				} else if (terrainNoise[x][y] > .5f) {
					setTileAt(x, y, Tiles.SAND);
				} else {
					setTileAt(x, y, Tiles.WATER);
				}
			}
		}
		
		// generate boulders
		float[][] boulderNoise = SimplexNoise.generateSimplexNoise(width, height, 20);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if (boulderNoise[x][y]*2 - 1.6 > Math.random()) {
					if (getTileTypeAt(x, y) == Tiles.GRASS) {
						setTileAt(x, y, Tiles.BOULDER, Tiles.GRASS_BOULDER);
					} else if (getTileTypeAt(x, y) == Tiles.WATER) {
						setTileAt(x, y, Tiles.BOULDER, Tiles.WATER_BOULDER);
					} else if (getTileTypeAt(x, y) == Tiles.SAND) {
						setTileAt(x, y, Tiles.BOULDER, Tiles.SAND_BOULDER);
					}
				}
			}
		}
		
		// generate trees (only on land tiles)
		float[][] treeNoise = SimplexNoise.generateSimplexNoise(width, height, 20);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if (treeNoise[x][y]*2 - 1 > Math.random() && getTileTypeAt(x, y) == Tiles.GRASS) {
					setTileAt(x, y, Tiles.TREE, Tiles.TREE_SNOW);
				}
			}
		}
	}
	
	private void generateSavannahMap() {
		int width = tileTypes.length;
		int height = tileTypes[0].length;
		
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				setTileAt(x, y, Tiles.BEDROCK);
			}
		}
		for (int x = 1; x < width - 1; x++) {
			for (int y = 1; y < height - 1; y++) {
				if (Math.random() < .1) setTileAt(x, y, Tiles.DIRT);
			}
		}
		for (int x = 2; x < width - 2; x++) {
			for (int y = 2; y < height - 2; y++) {
				if (Math.random() < .2) setTileAt(x, y, Tiles.DIRT);
			}
		}
		for (int x = 3; x < width - 3; x++) {
			for (int y = 3; y < height - 3; y++) {
				if (Math.random() < .4) setTileAt(x, y, Tiles.DIRT);
			}
		}
		for (int x = 4; x < width - 4; x++) {
			for (int y = 4; y < height - 4; y++) {
				if (Math.random() < .9) setTileAt(x, y, Tiles.DIRT);
			}
		}
		for (int x = 5; x < width - 5; x++) {
			for (int y = 5; y < height - 5; y++) {
				if (Math.random() < .1) setTileAt(x, y, Tiles.DIRT_BOULDER);
				else setTileAt(x, y, Tiles.DIRT);
			}
		}
	}
	
	@SuppressWarnings("unused")
	private void generateOldCaveMap() {
		int width = tileTypes.length;
		int height = tileTypes[0].length;
		
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				setTileAt(x, y, Tiles.BEDROCK);
			}
		}
		for (int x = 1; x < width - 1; x++) {
			for (int y = 1; y < height - 1; y++) {
				if (Math.random() < .1) setTileAt(x, y, Tiles.DIRT);
			}
		}
		for (int x = 2; x < width - 2; x++) {
			for (int y = 2; y < height - 2; y++) {
				if (Math.random() < .2) setTileAt(x, y, Tiles.DIRT);
			}
		}
		for (int x = 3; x < width - 3; x++) {
			for (int y = 3; y < height - 3; y++) {
				if (Math.random() < .4) setTileAt(x, y, Tiles.DIRT);
			}
		}
		for (int x = 4; x < width - 4; x++) {
			for (int y = 4; y < height - 4; y++) {
				if (Math.random() < .9) setTileAt(x, y, Tiles.DIRT);
			}
		}
		for (int x = 5; x < width - 5; x++) {
			for (int y = 5; y < height - 5; y++) {
				if (Math.random() < .1) setTileAt(x, y, Tiles.DIRT_BOULDER);
				else setTileAt(x, y, Tiles.DIRT);
			}
		}
		
		this.homeCoords[0] = width/2;
		this.homeCoords[1] = height/2;
	}
	
	private void generateCaveMap() {
		int width = tileTypes.length;
		int height = tileTypes[0].length;
		
		// terrain distortion params
		boolean enableDistortion = true;
		double distortWeight = 0.25;
		int distortFreq = 20;
		boolean enableDistortionDistribution = false;
		int distortDistributionFreq = 20;
		
		// generate accessible areas
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
					setTileAt(x, y, Tiles.DIRT);
				} else if (terrainNoise[x][y] > .5f) {
					setTileAt(x, y, Tiles.BEDROCK);
				} else {
					setTileAt(x, y, Tiles.VOID);
				}
			}
		}
		
		// generate stone veins
		float[][] stoneNoise = SimplexNoise.generateSimplexNoise(width, height, 20);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if (stoneNoise[x][y]*2 - 1 > Math.random() && getTileTypeAt(x, y) == Tiles.DIRT) {
					setTileAt(x, y, Tiles.STONE);
				}
			}
		}
		
		// generate ore (only on stone)
		float[][] oreNoise = SimplexNoise.generateSimplexNoise(width, height, 20);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if (oreNoise[x][y] > 0.80f && getTileTypeAt(x, y) == Tiles.STONE) {
					setTileAt(x, y, Tiles.STONE, Tiles.IRONORE_STONE);
				}
			}
		}
	}
	
	private void generateTemplateMap() {
		int width = tileTypes.length;
		int height = tileTypes[0].length;
		
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				setTileAt(x, y, Tiles.BEDROCK);
			}
		}
		for (int x = 1; x < width - 1; x++) {
			for (int y = 1; y < height - 1; y++) {
				if (Math.random() < .1) setTileAt(x, y, Tiles.GRASS);
			}
		}
		for (int x = 2; x < width - 2; x++) {
			for (int y = 2; y < height - 2; y++) {
				if (Math.random() < .2) setTileAt(x, y, Tiles.GRASS);
			}
		}
		for (int x = 3; x < width - 3; x++) {
			for (int y = 3; y < height - 3; y++) {
				if (Math.random() < .4) setTileAt(x, y, Tiles.GRASS);
			}
		}
		for (int x = 4; x < width - 4; x++) {
			for (int y = 4; y < height - 4; y++) {
				if (Math.random() < .9) setTileAt(x, y, Tiles.GRASS);
			}
		}
		for (int x = 5; x < width - 5; x++) {
			for (int y = 5; y < height - 5; y++) {
				setTileAt(x, y, Tiles.GRASS);
			}
		}
	}
	
	/*
	 * Local map feature generation
	 */
	
	private void generateStartingArea() {
		int spawnCenterX = tileTypes.length/2;
		int spawnCenterY = tileTypes[0].length/2;
		
		while (!Tiles.isWalkable(getTileTypeAt(spawnCenterX, spawnCenterY))) {
			spawnCenterX = (int)(Math.random()*(tileTypes.length - 20)) + 10;
			spawnCenterY = (int)(Math.random()*(tileTypes.length - 20)) + 10;
		}
		
		for (int x = -3; x < 4; x++) {
			for (int y = -3; y < 4; y++) {
				setTileAt(spawnCenterX+x, spawnCenterY+y, Tiles.GRASS);
			}
		}

		setTileAt(spawnCenterX-2, spawnCenterY-2, Tiles.HEALTH_PAD);
		setTileAt(spawnCenterX-2, spawnCenterY+2, Tiles.HURT_PAD);
		setTileAt(spawnCenterX+2, spawnCenterY-2, Tiles.MANA_PAD);
		setTileAt(spawnCenterX+2, spawnCenterY+2, Tiles.SPEED_PAD);
		
		homeCoords[0] = spawnCenterX;
		homeCoords[1] = spawnCenterY;
	}
	
	private void findHomeCoords() {
		int spawnCenterX = (int)(Math.random()*(tileTypes.length - 20)) + 10;
		int spawnCenterY = (int)(Math.random()*(tileTypes.length - 20)) + 10;
		while (!Tiles.isWalkable(getTileTypeAt(spawnCenterX, spawnCenterY))) {
			spawnCenterX = (int)(Math.random()*(tileTypes.length - 20)) + 10;
			spawnCenterY = (int)(Math.random()*(tileTypes.length - 20)) + 10;
		}
		
		homeCoords[0] = spawnCenterX;
		homeCoords[1] = spawnCenterY;
	}
	
	private void generateBorderWall() {
		for (int i = 0; i < tileTypes.length; i++) {
			setTileAt(i, 0, Tiles.BEDROCK);
			setTileAt(i, tileTypes[0].length-1, Tiles.BEDROCK);
		}
		
		for (int i = 0; i < tileTypes[0].length; i++) {
			setTileAt(0, i, Tiles.BEDROCK);
			setTileAt(tileTypes.length-1, i, Tiles.BEDROCK);
		}
	}
	
	/*
	 * Minimap management
	 */
	
	public void generateMinimap() {
		minimap = new BufferedImage(tileTypes.length, tileTypes[0].length, BufferedImage.TYPE_4BYTE_ABGR);
		for (int x = 0; x < tileTypes.length; x++) {
			for (int y = 0; y < tileTypes[0].length; y++) {
				int color = Tiles.minimapColor(this.getTileTypeAt(x, y));
				minimap.setRGB(x, y, color);
			}
		}
	}
	
	public BufferedImage getMinimap() {
		return minimap;
	}
	
	public void setMinimapAt(int x, int y, int type) {
		minimap.setRGB(x, y, Tiles.minimapColor(type));
	}
	
	/*
	 * Entity generation
	 */
	
	public void generateChickens() {
		ArrayList<Mob> mobs = new ArrayList<Mob>();
		for (int x = 0; x < tileTypes.length; x++) {
			for (int y = 0; y < tileTypes[0].length; y++) {
				if (Tiles.isWalkable(tileTypes[x][y]) && Math.random() < 0.001) {
					mobs.add(new Chicken(x, y, this));
				}
			}
		}
		this.mobs.addAll(mobs);
	}
	
	public void generateCyclopses() {
		ArrayList<Mob> mobs = new ArrayList<Mob>();
		for (int x = 0; x < tileTypes.length; x++) {
			for (int y = 0; y < tileTypes[0].length; y++) {
				if (Tiles.isWalkable(tileTypes[x][y]) && Math.random() < 0.001) {
					mobs.add(new Cyclops(x, y, this));
				}
			}
		}
		this.mobs.addAll(mobs);
	}
	
	public void generateRogues() {
		ArrayList<Mob> mobs = new ArrayList<Mob>();
		for (int x = 0; x < tileTypes.length; x++) {
			for (int y = 0; y < tileTypes[0].length; y++) {
				if (Tiles.isWalkable(tileTypes[x][y]) && Math.random() < 0.0005) {
					mobs.add(new Rogue(x, y, this));
				}
			}
		}
		this.mobs.addAll(mobs);
	}
	
	public void generateItems() {
		ArrayList<Item> items = new ArrayList<Item>();
		for (int x = 0; x < tileTypes.length; x++) {
			for (int y = 0; y < tileTypes[0].length; y++) {
				if (tileTypes[x][y] == 2 && Math.random() < 0.005) {
					double rand = Math.random();
					if (rand > .9975) {
						items.add(new Item(x, y, Item.CHEESE, this));
					} else {
						items.add(new Item(x, y, Item.GEM, this));
					}
				}
			}
		}
		this.items = items;
	}
	
	/*
	 * Tile manipulation
	 */
	
	public int getTileTypeAt(int x, int y) {
		if (x < 0 || y < 0) return -1;
		if (x >= tileTypes.length || y >= tileTypes[0].length) return -1;
		return tileTypes[x][y];
	}
	
	public int getTileSubtypeAt(int x, int y) {
		if (x < 0 || y < 0) return 0;
		if (x >= tileTypes.length || y >= tileTypes[0].length) return 0;
		return tileSubtypes[x][y];
	}
	
	public int[] getTileAt(int x, int y) {
		return new int[] {getTileTypeAt(x, y), getTileSubtypeAt(x, y)};
	}
	
	public void setTileAt(int x, int y, int type) {
		tileTypes[x][y] = type;
		tileSubtypes[x][y] = 0;
		setMinimapAt(x, y, type);
	}
	
	public void setTileAt(int x, int y, int type, int subtype) {
		setTileAt(x, y, type);
		tileSubtypes[x][y] = subtype;
	}
	
	/*
	 * Misc map manipulation
	 */
	
	public boolean setHomeCoords(int x, int y) {
		if (Tiles.isWalkable(getTileTypeAt(x, y))) {
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
		tileTypes = new int[w][h];
	}
	
	public int getWidth() {
		return tileTypes.length;
	}
	
	public int getHeight() {
		return tileTypes[0].length;
	}
	
	/*
	 * ActiveTile management
	 */

	public void setActiveTile(int x, int y, ActiveTile at) {
		activeTiles[x][y] = at;
	}
	
	public ActiveTile getActiveTile(int x, int y) {
		return activeTiles[x][y];
	}
	
	public void useActiveTile(int x, int y, Mob m) {
		if (activeTiles[x][y] != null) {
			activeTiles[x][y].use(m);
		}
	}
	
	/*
	 * Entity management
	 */
	
	public void addItem(Item i) {
		addItems.add(i);
	}
	
	public void addMob(Mob m) {
		addMobs.add(m);
	}
	
	public void addProjectile(Projectile p) {
		addProjectiles.add(p);
	}
	
	public void addPlayer(Player p) {
		addPlayers.add(p);
	}
	
	public void addParticle(Particle p) {
		addParticles.add(p);
	}
	
	public void removeItem(Item i) {
		removeItems.add(i);
	}
	
	public void removeMob(Mob m) {
		removeMobs.add(m);
	}
	
	public void removeProjectile(Projectile p) {
		removeProjectiles.add(p);
	}
	
	public void removePlayer(Player p) {
		removePlayers.add(p);
	}
	
	public void removeParticle(Particle p) {
		removeParticles.add(p);
	}
	
	public ArrayList<Item> getItems() {
		return items;
	}
	
	public ArrayList<Mob> getMobs() {
		return mobs;
	}
	
	public ArrayList<Projectile> getProjectiles() {
		return projectiles;
	}
	
	public ArrayList<Player> getPlayers() {
		return players;
	}
	
	public ArrayList<Particle> getParticles() {
		return particles;
	}
	
	public int getNumItems() {
		return items.size();
	}
	
	public int getNumMobs() {
		return mobs.size();
	}
	
	public int getNumProjectiles() {
		return projectiles.size();
	}
	
	public int getNumPlayers() {
		return players.size();
	}
	
	public int getNumParticles() {
		return particles.size();
	}
	
	/*
	 * Special entity methods
	 */
	
	public Mob mobAt(double x, double y) {
		for (Mob m : mobs) {
			if (m.containsPoint(x, y)) {
				return m;
			}
		}
		
		return null;
	}
}