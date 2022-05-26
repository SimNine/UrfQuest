package urfquest.server.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import urfquest.Logger;
import urfquest.server.IDGenerator;
import urfquest.server.Server;
import urfquest.server.entities.items.Item;
import urfquest.server.entities.mobs.Mob;
import urfquest.server.entities.mobs.Player;
import urfquest.server.entities.projectiles.Projectile;
import urfquest.server.map.generator.TerrainGenerator;
import urfquest.server.map.generator.TerrainGeneratorCaves;
import urfquest.server.map.generator.TerrainGeneratorSavannah;
import urfquest.server.map.generator.TerrainGeneratorSimplex;
import urfquest.server.map.generator.TerrainGeneratorTemplate;
import urfquest.server.map.populator.HousePopulator;
import urfquest.server.map.structures.Structure;
import urfquest.server.tiles.ActiveTile;
import urfquest.shared.Constants;
import urfquest.shared.Tile;

public class Map {
	private Server server;
	
	public static final int EMPTY_MAP = 5000;
	public static final int SIMPLEX_MAP = 5001;
	public static final int SAVANNAH_MAP = 5002;
	public static final int TEMPLATE_MAP = 5003;
	public static final int CAVE_MAP = 5004;

	private HashMap<Integer, Player> players = new HashMap<Integer, Player>();
	private HashMap<Integer, Mob> mobs = new HashMap<Integer, Mob>();
	private HashMap<Integer, Item> items = new HashMap<Integer, Item>();
	private HashMap<Integer, Projectile> projectiles = new HashMap<Integer, Projectile>();
	
	private HashSet<Structure> structures = new HashSet<Structure>();
	
	private int[] homeCoords = new int[2];
	
	private HashMap<Integer, HashMap<Integer, MapChunk>> chunks;
	
	private TerrainGenerator generator;
	private HousePopulator populator;
	
	public int id;
	
	public Map(Server srv, int type) {
		this.server = srv;
		
		this.id = IDGenerator.newID();
		
		System.out.println("Generating map with ID :" + this.id);
		
		switch (type) {
		case EMPTY_MAP:
			break;
		case SIMPLEX_MAP:
			generator = new TerrainGeneratorSimplex(server.randomLong());
			break;
		case SAVANNAH_MAP:
			generator = new TerrainGeneratorSavannah(server.randomLong());
			break;
		case TEMPLATE_MAP:
			generator = new TerrainGeneratorTemplate(server.randomLong());
			break;
		case CAVE_MAP:
			generator = new TerrainGeneratorCaves(server.randomLong());
			// generateRogues();
			// generateCyclopses();
			break;
		}
		populator = new HousePopulator(server);
		
		// generate four center chunks in order to generate starting area
		chunks = new HashMap<Integer, HashMap<Integer, MapChunk>>();
		for (int xChunk = -1; xChunk < 1; xChunk++) {
			for (int yChunk = -1; yChunk < 1; yChunk++) {
				this.createChunk(xChunk, yChunk);
			}
		}
		
		if (type != EMPTY_MAP) {
			if (type == CAVE_MAP) {
				findHomeCoords();
			} else {
				generateStartingArea();
			}
		}
	}
	
	
	/*
	 * Tick updater
	 */
	
	public void tick() {
		ArrayList<Player> addPlayers = new ArrayList<Player>();
		ArrayList<Mob> addMobs = new ArrayList<Mob>();
		ArrayList<Item> addItems = new ArrayList<Item>();
		ArrayList<Projectile> addProjectiles = new ArrayList<Projectile>();
		
		ArrayList<Player> removePlayers = new ArrayList<Player>();
		ArrayList<Item> removeItems = new ArrayList<Item>();
		ArrayList<Mob> removeMobs = new ArrayList<Mob>();
		ArrayList<Projectile> removeProjectiles = new ArrayList<Projectile>();
		
		
		// update projectiles
		for (Projectile p : projectiles.values()) {
			p.tick();
		}
		
		// update mobs
		for (Mob m : mobs.values()) {
			m.tick();
		}
		
		// update items
		for (Item i : items.values()) {
			i.tick();
		}
		
		// update players
		for (Player p : players.values()) {
			p.tick();
		}
		
		// TODO: reimplement entity collision
//		// check for items near players
//		for (Player p : players.values()) {
//			for (Item i : items.values()) {
//				if (p.isWithinDistance(i, p.getPickupRange()) && i.isPickupable()) {
//					i.accelerateTowards(p);
//				}
//			}
//		}
//		
//		// check for players colliding with items
//		for (Player p : players.values()) {
//			HashSet<Item> removeNow = new HashSet<Item>();
//			for (Item i : items.values()) {
//				if (p.collides(i) && i.isPickupable()) {
//					this.server.getLogger().verbose(p.getName() + " collided with object: " + i.getClass().getName());
//					if (p.addItem(i)) {
//						removeNow.add(i);
//					} else {
//						continue;
//					}
//				}
//			}
//			for (Item i : removeNow) {
//				items.remove(i.id);
//			}
//		}
//		
//		// check for the player colliding with mobs
//		for (Mob m : mobs.values()) {
//			for (Player p : players.values()) {
//				if (p.collides(m)) {
//					this.server.getLogger().verbose(p.getName() + " collided with object: " + m.getClass().getName());
//				}
//			}
//		}
//		
//		// check for collisions between projectiles and mobs
//		for (Projectile p : projectiles.values()) {
//			for (Mob m : mobs.values()) {
//				if (p.getSource() == m) {
//					continue;
//				} else if (p.collides(m)) {
//					p.collideWith(m);
//				}
//			}
//		}
//		
//		// check for collisions between projectiles and players
//		for (Player p : players.values()) {
//			for (Projectile j : projectiles.values()) {
//				if (j.getSource() == p) {
//					continue;
//				} else if (j.collides(p)) {
//					j.collideWith(p);
//				}
//			}
//		}
		
		// clean up dead projectiles
		for (Projectile p : projectiles.values()) {
			if (p.isDead()) {
				removeProjectiles.add(p);
			}
		}
		
		// clean up dead mobs
		for (Mob m : mobs.values()) {
			if (m.isDead()) {
				m.onDeath();
				removeMobs.add(m);
			}
		}

		// remove entities
		for (Item i : removeItems) {
			items.remove(i.id);
		}
		for (Mob m : removeMobs) {
			mobs.remove(m.id);
		}
		for (Projectile p : removeProjectiles) {
			projectiles.remove(p.id);
		}
		for (Player p : removePlayers) {
			players.remove(p.id);
		}
		removeItems.clear();
		removeMobs.clear();
		removeProjectiles.clear();
		removePlayers.clear();

		// add entities
		for (Item i : addItems) {
			items.put(i.id, i);
		}
		for (Mob m : addMobs) {
			mobs.put(m.id, m);
		}
		for (Projectile p : addProjectiles) {
			projectiles.put(p.id, p);
		}
		for (Player p : addPlayers) {
			players.put(p.id, p);
		}
		addItems.clear();
		addMobs.clear();
		addProjectiles.clear();
		addPlayers.clear();
		
		// updateTiles();
	}
	
	
	
	/*
	 * Chunk manipulation
	 */
	
	public MapChunk getChunk(int xChunk, int yChunk) {
		HashMap<Integer, MapChunk> column = chunks.get(xChunk);
		if (column == null)
			return null;
		return column.get(yChunk);
	}
	
	public MapChunk getChunkAtPos(int x, int y) {
		int xChunk = Math.floorDiv(x, Constants.MAP_CHUNK_SIZE);
		int yChunk = Math.floorDiv(y, Constants.MAP_CHUNK_SIZE);
		
		return getChunk(xChunk, yChunk);
	}
	
	public HashMap<Integer, HashMap<Integer, MapChunk>> getAllChunks() {
		return chunks;
	}
	
	public MapChunk createChunk(int xChunk, int yChunk) {
		HashMap<Integer, MapChunk> column = chunks.get(xChunk);
		if (column == null) {
			column = new HashMap<Integer, MapChunk>();
			chunks.put(xChunk, column);
		}
		
		MapChunk chunk = generator.generateChunk(xChunk, yChunk);
		column.put(yChunk, chunk);
		
		Structure struct = populator.populateChunk(this, xChunk, yChunk);
		if (struct != null) {
			structures.add(struct);
			populator.generateStructure(this, struct.getPosition(), struct.getDimensions());
		}
		
		return chunk;
	}
	
	private MapChunk createChunkAtPos(int x, int y) {
		int xChunk = x / Constants.MAP_CHUNK_SIZE;
		int yChunk = y / Constants.MAP_CHUNK_SIZE;
		
		return createChunk(xChunk, yChunk);
	}
	
	// update a number of chunks each tick
//	private void updateTiles() {
//		HashSet<Pair<Integer, Integer>> set = new HashSet<Pair<Integer, Integer>>();
//		for (int i = 0; i < 20; i++) {
//			int x = (int)(server.random()*chunks.length);
//			int y = (int)(server.random()*chunks[0].length);
//			set.add(new Pair<Integer, Integer>(x, y));
//		}
//		
//		for (Pair<Integer, Integer> p : set) {
//			int t = getTileTypeAt(p.a, p.b);
//			switch (t) {
//			case Tiles.DIRT:
//				double chance = 0;
//				if (getTileTypeAt(p.a, p.b + 1) == Tiles.GRASS) {
//					chance += 0.25;
//				}
//				if (getTileTypeAt(p.a, p.b - 1) == Tiles.GRASS) {
//					chance += 0.25;
//				}
//				if (getTileTypeAt(p.a + 1, p.b) == Tiles.GRASS) {
//					chance += 0.25;
//				}
//				if (getTileTypeAt(p.a - 1, p.b) == Tiles.GRASS) {
//					chance += 0.25;
//				}
//				if (chance > server.random()) {
//					setTileAt(p.a, p.b, Tiles.GRASS);
//				}
//				break;
//			case Tiles.GRASS:
//				if (getTileTypeAt(p.a, p.b + 1) == Tiles.TREE ||
//					getTileTypeAt(p.a, p.b - 1) == Tiles.TREE ||
//					getTileTypeAt(p.a + 1, p.b) == Tiles.TREE ||
//					getTileTypeAt(p.a - 1, p.b) == Tiles.TREE) {
//					if (server.random() < 0.1) {
//						setTileAt(p.a, p.b, Tiles.TREE);
//					}
//				}
//				break;
//			case Tiles.TREE:
//				if (server.random() < 0.18) {
//					setTileAt(p.a, p.b, Tiles.GRASS);
//				}
//				break;
//			}
//		}
//	}
	
	
	
	/*
	 * Map generation methods
	 */
	
//	@SuppressWarnings("unused")
//	private void generateOldCaveMap() {
//		int width = tileTypes.length;
//		int height = tileTypes[0].length;
//		
//		for (int x = 0; x < width; x++) {
//			for (int y = 0; y < height; y++) {
//				setTileAt(x, y, Tiles.BEDROCK);
//			}
//		}
//		for (int x = 1; x < width - 1; x++) {
//			for (int y = 1; y < height - 1; y++) {
//				if (server.random() < .1) setTileAt(x, y, Tiles.DIRT);
//			}
//		}
//		for (int x = 2; x < width - 2; x++) {
//			for (int y = 2; y < height - 2; y++) {
//				if (server.random() < .2) setTileAt(x, y, Tiles.DIRT);
//			}
//		}
//		for (int x = 3; x < width - 3; x++) {
//			for (int y = 3; y < height - 3; y++) {
//				if (server.random() < .4) setTileAt(x, y, Tiles.DIRT);
//			}
//		}
//		for (int x = 4; x < width - 4; x++) {
//			for (int y = 4; y < height - 4; y++) {
//				if (server.random() < .9) setTileAt(x, y, Tiles.DIRT);
//			}
//		}
//		for (int x = 5; x < width - 5; x++) {
//			for (int y = 5; y < height - 5; y++) {
//				if (server.random() < .1) setTileAt(x, y, Tiles.DIRT_BOULDER);
//				else setTileAt(x, y, Tiles.DIRT);
//			}
//		}
//		
//		this.homeCoords[0] = width/2;
//		this.homeCoords[1] = height/2;
//	}

	
	
	/*
	 * Local map feature generation
	 */
	
	private void generateStartingArea() {
		//findHomeCoords();
		homeCoords = new int[] {0, 0};
		
		for (int x = -3; x < 4; x++) {
			for (int y = -3; y < 4; y++) {
				setTileAt(homeCoords[0]+x, homeCoords[1]+y, Tile.TILE_GRASS);
			}
		}

		setTileAt(homeCoords[0]-2, homeCoords[1]-2, Tile.TILE_HEALTH_PAD);
		setTileAt(homeCoords[0]-2, homeCoords[1]+2, Tile.TILE_HURT_PAD);
		setTileAt(homeCoords[0]+2, homeCoords[1]-2, Tile.TILE_MANA_PAD);
		setTileAt(homeCoords[0]+2, homeCoords[1]+2, Tile.TILE_SPEED_PAD);
	}
	
	// TODO: this is broken and prone to not letting the server initialize. fix
	private void findHomeCoords() {
		int spawnCenterX = (int)((server.randomDouble()-0.5)*10);
		int spawnCenterY = (int)((server.randomDouble()-0.5)*10);
		while (!Tile.isWalkable(getTileAt(spawnCenterX, spawnCenterY))) {
			spawnCenterX = (int)((server.randomDouble()-0.5)*10);
			spawnCenterY = (int)((server.randomDouble()-0.5)*10);
		}
		
		homeCoords[0] = spawnCenterX;
		homeCoords[1] = spawnCenterY;
	}
//	
//	private void generateBorderWall() {
//		for (int i = 0; i < tileTypes.length; i++) {
//			setTileAt(i, 0, Tiles.BEDROCK);
//			setTileAt(i, tileTypes[0].length-1, Tiles.BEDROCK);
//		}
//		
//		for (int i = 0; i < tileTypes[0].length; i++) {
//			setTileAt(0, i, Tiles.BEDROCK);
//			setTileAt(tileTypes.length-1, i, Tiles.BEDROCK);
//		}
//	}

	
	
	/*
	 * Entity generation
	 */
//	
//	public void generateChickens() {
//		ArrayList<Mob> mobs = new ArrayList<Mob>();
//		for (int x = 0; x < tileTypes.length; x++) {
//			for (int y = 0; y < tileTypes[0].length; y++) {
//				if (Tiles.isWalkable(tileTypes[x][y]) && server.random() < 0.001) {
//					mobs.add(new Chicken(x, y, this));
//				}
//			}
//		}
//		this.mobs.addAll(mobs);
//	}
//	
//	public void generateCyclopses() {
//		ArrayList<Mob> mobs = new ArrayList<Mob>();
//		for (int x = 0; x < tileTypes.length; x++) {
//			for (int y = 0; y < tileTypes[0].length; y++) {
//				if (Tiles.isWalkable(tileTypes[x][y]) && server.random() < 0.001) {
//					mobs.add(new Cyclops(x, y, this));
//				}
//			}
//		}
//		this.mobs.addAll(mobs);
//	}
//	
//	public void generateRogues() {
//		ArrayList<Mob> mobs = new ArrayList<Mob>();
//		for (int x = 0; x < tileTypes.length; x++) {
//			for (int y = 0; y < tileTypes[0].length; y++) {
//				if (Tiles.isWalkable(tileTypes[x][y]) && server.random() < 0.0005) {
//					mobs.add(new Rogue(x, y, this));
//				}
//			}
//		}
//		this.mobs.addAll(mobs);
//	}
//	
//	public void generateItems() {
//		ArrayList<Item> items = new ArrayList<Item>();
//		for (int x = 0; x < tileTypes.length; x++) {
//			for (int y = 0; y < tileTypes[0].length; y++) {
//				if (tileTypes[x][y] == 2 && server.random() < 0.005) {
//					double rand = server.random();
//					if (rand > .9975) {
//						items.add(new Item(x, y, Item.CHEESE, this));
//					} else {
//						items.add(new Item(x, y, Item.GEM, this));
//					}
//				}
//			}
//		}
//		this.items = items;
//	}
	
	
	
	/*
	 * Tile manipulation
	 */
	
	public int getTileTypeAt(int x, int y) {
		MapChunk chunk = getChunkAtPos(x, y);
		if (chunk == null) {
			return Tile.TILE_VOID;
		}
		
		int[] posInChunk = getCoordsInChunk(x, y);
		return chunk.getTileTypeAt(posInChunk[0], posInChunk[1]);
	}
	
	public int getTileSubtypeAt(int x, int y) {
		MapChunk chunk = getChunkAtPos(x, y);
		if (chunk == null)
			return Tile.TILE_VOID;

		int[] posInChunk = getCoordsInChunk(x, y);
		return chunk.getObjectTypeAt(posInChunk[0], posInChunk[1]);
	}
	
	public int[] getTileAt(int x, int y) {
		return new int[] {getTileTypeAt(x, y), getTileSubtypeAt(x, y)};
	}
	
	public void setTileAt(int x, int y, int type) {
		setTileAt(x, y, type, 0);
	}
	
	public void setTileAt(int x, int y, int type, int objectType) {
		MapChunk chunk = getChunkAtPos(x, y);
		if (chunk == null) {
			chunk = createChunkAtPos(x, y);
		}

		int[] posInChunk = getCoordsInChunk(x, y);
		chunk.setTileAt(posInChunk[0], posInChunk[1], type, objectType);
	}
	
	public int[] getCoordsInChunk(int x, int y) {
		int[] returns = new int[2];
		
		returns[0] = Math.floorMod(x, Constants.MAP_CHUNK_SIZE);
		returns[1] = Math.floorMod(y, Constants.MAP_CHUNK_SIZE);
		
		return returns;
	}
	
	
	/*
	 * Misc map manipulation
	 */
	
	public boolean setHomeCoords(int x, int y) {
		if (Tile.isWalkable(getTileAt(x, y))) {
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
	
	
	/*
	 * ActiveTile management
	 */

	public void setActiveTile(int x, int y, ActiveTile at) {
		MapChunk chunk = getChunkAtPos(x, y);
		if (chunk == null) {
			chunk = createChunkAtPos(x, y);
		}
		
		chunk.setActiveTile(x, y, at);
	}
	
	public ActiveTile getActiveTile(int x, int y) {
		MapChunk chunk = getChunkAtPos(x, y);
		if (chunk == null)
			return null;
		return chunk.getActiveTile(x, y);
	}
	
	public void useActiveTile(int x, int y, Mob m) {
		ActiveTile tile = getActiveTile(x, y);
		if (tile != null) {
			tile.use(m);
		}
	}
	
	
	/*
	 * Entity management
	 */
	
	public void addItem(Item i) {
		items.put(i.id, i);
	}
	
	public void addMob(Mob m) {
		mobs.put(m.id, m);
	}
	
	public void addProjectile(Projectile p) {
		projectiles.put(p.id, p);
	}
	
	public void addPlayer(Player p) {
		players.put(p.id, p);
	}
	
	public void removeItem(int entityID) {
		items.remove(entityID);
	}
	
	public void removeItem(Item i) {
		items.remove(i.id);
	}
	
	public void removeMob(int entityID) {
		mobs.remove(entityID);
	}
	
	public void removeMob(Mob m) {
		mobs.remove(m.id);
	}
	
	public void removeProjectile(int entityID) {
		projectiles.remove(entityID);
	}
	
	public void removeProjectile(Projectile p) {
		projectiles.remove(p.id);
	}
	
	public void removePlayer(int entityID) {
		players.remove(entityID);
	}
	
	public void removePlayer(Player p) {
		players.remove(p.id);
	}
	
	public HashMap<Integer, Item> getItems() {
		return items;
	}
	
	public HashMap<Integer, Mob> getMobs() {
		return mobs;
	}
	
	public HashMap<Integer, Projectile> getProjectiles() {
		return projectiles;
	}
	
	public HashMap<Integer, Player> getPlayers() {
		return players;
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
	
	
	/*
	 * Special entity methods
	 */
	
	public Mob mobAt(double x, double y) {
		for (Mob m : mobs.values()) {
			if (m.containsPoint(x, y)) {
				return m;
			}
		}
		
		return null;
	}
}