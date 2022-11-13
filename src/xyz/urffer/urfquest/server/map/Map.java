package xyz.urffer.urfquest.server.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import xyz.urffer.urfquest.server.IDGenerator;
import xyz.urffer.urfquest.server.Server;
import xyz.urffer.urfquest.server.entities.items.ItemStack;
import xyz.urffer.urfquest.server.entities.mobs.Mob;
import xyz.urffer.urfquest.server.entities.mobs.Player;
import xyz.urffer.urfquest.server.entities.projectiles.Projectile;
import xyz.urffer.urfquest.server.map.generator.TerrainGenerator;
import xyz.urffer.urfquest.server.map.generator.TerrainGeneratorCaves;
import xyz.urffer.urfquest.server.map.generator.TerrainGeneratorSavannah;
import xyz.urffer.urfquest.server.map.generator.TerrainGeneratorSimplex;
import xyz.urffer.urfquest.server.map.generator.TerrainGeneratorTemplate;
import xyz.urffer.urfquest.server.map.populator.HousePopulator;
import xyz.urffer.urfquest.server.map.structures.Structure;
import xyz.urffer.urfquest.server.tiles.ActiveTile;
import xyz.urffer.urfquest.shared.Constants;
import xyz.urffer.urfquest.shared.PairDouble;
import xyz.urffer.urfquest.shared.PairInt;
import xyz.urffer.urfquest.shared.Tile;
import xyz.urffer.urfquest.shared.protocol.types.TileType;

public class Map {
	private Server server;
	
	public static final int EMPTY_MAP = 5000;
	public static final int SIMPLEX_MAP = 5001;
	public static final int SAVANNAH_MAP = 5002;
	public static final int TEMPLATE_MAP = 5003;
	public static final int CAVE_MAP = 5004;

	private HashMap<Integer, Player> players = new HashMap<Integer, Player>();
	private HashMap<Integer, Mob> mobs = new HashMap<Integer, Mob>();
	private HashMap<Integer, ItemStack> items = new HashMap<Integer, ItemStack>();
	private HashMap<Integer, Projectile> projectiles = new HashMap<Integer, Projectile>();
	
	private HashSet<Structure> structures = new HashSet<Structure>();
	
	private PairInt homeCoords = new PairInt(0, 0);
	
	private HashMap<Integer, HashMap<Integer, MapChunk>> chunks;
	
	private TerrainGenerator generator;
	private HousePopulator populator;
	
	public int id;
	
	public Map(Server srv, int type) {
		this.server = srv;
		
		this.id = IDGenerator.newID();
		
		server.getLogger().info("Generating map with ID: " + this.id);
		
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
				this.createChunk(new PairInt(xChunk, yChunk));
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
		ArrayList<ItemStack> addItems = new ArrayList<ItemStack>();
		ArrayList<Projectile> addProjectiles = new ArrayList<Projectile>();
		
		ArrayList<Player> removePlayers = new ArrayList<Player>();
		ArrayList<ItemStack> removeItems = new ArrayList<ItemStack>();
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
		for (ItemStack i : items.values()) {
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
		for (ItemStack i : removeItems) {
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
		for (ItemStack i : addItems) {
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
	
	public MapChunk getChunk(PairInt posChunk) {
		HashMap<Integer, MapChunk> column = chunks.get(posChunk.x);
		if (column == null)
			return null;
		return column.get(posChunk.y);
	}
	
	public MapChunk getChunkAtPos(PairInt pos) {
		int xChunk = Math.floorDiv(pos.x, Constants.MAP_CHUNK_SIZE);
		int yChunk = Math.floorDiv(pos.y, Constants.MAP_CHUNK_SIZE);
		
		return getChunk(new PairInt(xChunk, yChunk));
	}
	
	public HashMap<Integer, HashMap<Integer, MapChunk>> getAllChunks() {
		return chunks;
	}
	
	public MapChunk createChunk(PairInt posChunk) {
		HashMap<Integer, MapChunk> column = chunks.get(posChunk.x);
		if (column == null) {
			column = new HashMap<Integer, MapChunk>();
			chunks.put(posChunk.x, column);
		}
		
		MapChunk chunk = generator.generateChunk(posChunk);
		column.put(posChunk.y, chunk);
		
		Structure struct = populator.populateChunk(this, posChunk);
		if (struct != null) {
			structures.add(struct);
			populator.generateStructure(this, struct.getPosition(), struct.getDimensions());
		}
		
		return chunk;
	}
	
	private MapChunk createChunkAtPos(PairInt pos) {
		PairInt posChunk = pos.divide(Constants.MAP_CHUNK_SIZE);

		return createChunk(posChunk);
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
		homeCoords = new PairInt(0, 0);
		
		for (int x = -3; x < 4; x++) {
			for (int y = -3; y < 4; y++) {
				setTileAt(homeCoords.add(new PairInt(x,y)), new Tile(TileType.GRASS));
			}
		}

		setTileAt(homeCoords.add(new PairInt(-2,-2)), new Tile(TileType.HEALTH_PAD));
		setTileAt(homeCoords.add(new PairInt(-2,2)), new Tile(TileType.HURT_PAD));
		setTileAt(homeCoords.add(new PairInt(2,-2)), new Tile(TileType.MANA_PAD));
		setTileAt(homeCoords.add(new PairInt(2,2)), new Tile(TileType.SPEED_PAD));
	}
	
	// TODO: this is broken and prone to not letting the server initialize. fix
	private void findHomeCoords() {
		PairInt spawnCenter = new PairInt(0, 0);
		spawnCenter.x = (int)((server.randomDouble()-0.5)*10);
		spawnCenter.y = (int)((server.randomDouble()-0.5)*10);
		while (!Tile.isWalkable(getTileAt(spawnCenter))) {
			spawnCenter.x = (int)((server.randomDouble()-0.5)*10);
			spawnCenter.y = (int)((server.randomDouble()-0.5)*10);
		}
		
		homeCoords = spawnCenter;
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
	
	public Tile getTileAt(PairInt pos) {
		MapChunk chunk = getChunkAtPos(pos);
		if (chunk == null) {
			return Tile.VOID;
		}
		
		PairInt posInChunk = getCoordsInChunk(pos);
		return chunk.getTileAt(posInChunk);
	}
	
	public void setTileAt(PairInt pos, Tile tile) {
		MapChunk chunk = getChunkAtPos(pos);
		if (chunk == null) {
			chunk = createChunkAtPos(pos);
		}

		PairInt posInChunk = getCoordsInChunk(pos);
		chunk.setTileAt(posInChunk, tile);
	}
	
	public PairInt getCoordsInChunk(PairInt pos) {
		return pos.floorMod(Constants.MAP_CHUNK_SIZE);
	}
	
	
	/*
	 * Misc map manipulation
	 */
	
	public boolean setHomeCoords(PairInt pos) {
		if (Tile.isWalkable(getTileAt(pos))) {
			homeCoords = pos.clone();
			return true;
		} else {
			return false;
		}
	}
	
	public PairInt getHomeCoords() {
		return homeCoords;
	}
	
	
	/*
	 * ActiveTile management
	 */

	public void setActiveTile(PairInt pos, ActiveTile at) {
		MapChunk chunk = getChunkAtPos(pos);
		if (chunk == null) {
			chunk = createChunkAtPos(pos);
		}
		
		chunk.setActiveTile(pos, at);
	}
	
	public ActiveTile getActiveTile(PairInt pos) {
		MapChunk chunk = getChunkAtPos(pos);
		if (chunk == null)
			return null;
		return chunk.getActiveTile(pos);
	}
	
	public void useActiveTile(PairInt pos, Mob m) {
		ActiveTile tile = getActiveTile(pos);
		if (tile != null) {
			tile.use(m);
		}
	}
	
	
	/*
	 * Entity management
	 */
	
	public void addItem(ItemStack i) {
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
	
	public void removeItem(ItemStack i) {
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
	
	public HashMap<Integer, ItemStack> getItems() {
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
	
	public Mob mobAt(PairDouble pos) {
		for (Mob m : mobs.values()) {
			if (m.containsPoint(pos)) {
				return m;
			}
		}
		
		return null;
	}
}