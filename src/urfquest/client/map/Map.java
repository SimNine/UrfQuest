package urfquest.client.map;

import java.util.ArrayList;

import urfquest.Main;
import urfquest.client.entities.items.Item;
import urfquest.client.entities.mobs.Mob;
import urfquest.client.entities.mobs.Player;
import urfquest.client.entities.particles.Particle;
import urfquest.client.entities.projectiles.Projectile;
import urfquest.client.tiles.ActiveTile;
import urfquest.client.tiles.Tiles;
import urfquest.shared.message.Message;
import urfquest.shared.message.MessageType;

public class Map {
	public static final int EMPTY_MAP = 5000;
	public static final int SIMPLEX_MAP = 5001;
	public static final int SAVANNAH_MAP = 5002;
	public static final int TEMPLATE_MAP = 5003;
	public static final int CAVE_MAP = 5004;
	
	private int[] homeCoords = new int[2];
	
	private MapChunk[][] localChunks;
	private int[] localChunkOrigin = new int[2];

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
	
	public Map(int loadedChunkSize) {
		localChunks = new MapChunk[loadedChunkSize][loadedChunkSize];
		localChunkOrigin[0] = localChunkOrigin[1] = 0 - (loadedChunkSize / 2);
		
		for (int x = 0; x < loadedChunkSize; x++) {
			for (int y = 0; y < loadedChunkSize; y++) {
				localChunks[x][y] = null;
			}
		}
	}
	
	/*
	 * Tick updater
	 */
	
//	public void update() {
//		// update projectiles
//		for (Projectile p : projectiles) {
//			p.update();
//		}
//		
//		// update mobs
//		for (Mob m : mobs) {
//			m.update();
//		}
//		
//		// update items
//		for (Item i : items) {
//			i.update();
//		}
//		
//		// check for items near players
//		for (Player p : players) {
//			for (Item i : items) {
//				if (p.isWithinDistance(i, p.getPickupRange()) && i.isPickupable()) {
//					i.accelerateTowards(p);
//				}
//			}
//		}
//		
//		// check for players colliding with items
//		for (Player p : players) {
//			HashSet<Item> removeNow = new HashSet<Item>();
//			for (Item i : items) {
//				if (p.collides(i) && i.isPickupable()) {
//					if (UrfQuestServer.debug) {
//						System.out.println(p.getName() + " collided with object: " + i.getClass().getName());
//					}
//					if (p.addItem(i)) {
//						removeNow.add(i);
//					} else {
//						continue;
//					}
//				}
//			}
//			items.removeAll(removeNow);
//		}
//		
//		// check for the player colliding with mobs
//		for (Mob m : mobs) {
//			for (Player p : players) {
//				if (p.collides(m)) {
//					if (UrfQuestServer.debug) {
//						System.out.println(p.getName() + " collided with object: " + m.getClass().getName());
//					}
//				}
//			}
//		}
//		
//		// check for collisions between projectiles and mobs
//		for (Projectile p : projectiles) {
//			for (Mob m : mobs) {
//				if (p.getSource() == m) {
//					continue;
//				} else if (p.collides(m)) {
//					p.collideWith(m);
//				}
//			}
//		}
//		
//		// check for collisions between projectiles and players
//		for (Player p : players) {
//			for (Projectile j : projectiles) {
//				if (j.getSource() == p) {
//					continue;
//				} else if (j.collides(p)) {
//					j.collideWith(p);
//				}
//			}
//		}
//		
//		// clean up dead projectiles
//		for (Projectile p : projectiles) {
//			if (p.isDead()) {
//				removeProjectiles.add(p);
//			}
//		}
//		
//		// clean up dead mobs
//		for (Mob m : mobs) {
//			if (m.isDead()) {
//				m.onDeath();
//				removeMobs.add(m);
//			}
//		}
//
//		items.removeAll(removeItems);
//		mobs.removeAll(removeMobs);
//		projectiles.removeAll(removeProjectiles);
//		players.removeAll(removePlayers);
//		
//		removeItems.clear();
//		removeMobs.clear();
//		removeProjectiles.clear();
//		removePlayers.clear();
//		
//		items.addAll(addItems);
//		mobs.addAll(addMobs);
//		projectiles.addAll(addProjectiles);
//		players.addAll(addPlayers);
//		
//		addItems.clear();
//		addMobs.clear();
//		addProjectiles.clear();
//		addPlayers.clear();
//		
//		updateTiles();
//	}
	
	/*
	 * Tile manipulation
	 */
	
	public int getTileTypeAt(int x, int y) {
		MapChunk chunk = getChunkAtPos(x, y);
		if (chunk == null)
			return -1;
		int[] pos = getPosInChunk(x, y);
		
		return chunk.getTileTypeAt(pos[0], pos[1]);
	}
	
	public int getTileSubtypeAt(int x, int y) {
		MapChunk chunk = getChunkAtPos(x, y);
		if (chunk == null)
			return -1;
		int[] pos = getPosInChunk(x, y);
		
		return chunk.getTileSubtypeAt(pos[0], pos[1]);
	}
	
	public int[] getTileAt(int x, int y) {
		return new int[] {getTileTypeAt(x, y), getTileSubtypeAt(x, y)};
	}
	
	public void setTileAt(int x, int y, int type) {
		setTileAt(x, y, type, 0);
	}
	
	public void setTileAt(int x, int y, int type, int subtype) {
		MapChunk chunk = getChunkAtPos(x, y);
		int[] pos = getPosInChunk(x, y);
		
		chunk.setTileAt(pos[0], pos[1], type, subtype);
	}
	
	/*
	 * Chunk manipulation
	 */
	
	public MapChunk getChunk(int xChunk, int yChunk) {
		int xChunkLocal = xChunk - localChunkOrigin[0];
		int yChunkLocal = yChunk - localChunkOrigin[1];
		
		return localChunks[xChunkLocal][yChunkLocal];
	}
	
	public MapChunk getChunkAtPos(int x, int y) {
		if (x < localChunkOrigin[0] * MapChunk.CHUNK_SIZE || 
			y < localChunkOrigin[1] * MapChunk.CHUNK_SIZE)
			return null;
		if (x >= (localChunkOrigin[0] + localChunks.length) * MapChunk.CHUNK_SIZE || 
			y >= (localChunkOrigin[1] + localChunks[0].length) * MapChunk.CHUNK_SIZE)
			return null;

		int xChunk = Math.floorDiv(x, MapChunk.CHUNK_SIZE);
		int yChunk = Math.floorDiv(y, MapChunk.CHUNK_SIZE);
		return getChunk(xChunk, yChunk);
	}

	private int[] getPosInChunk(int x, int y) {
		x %= MapChunk.CHUNK_SIZE;
		if (x < 0)
			x += MapChunk.CHUNK_SIZE;

		y %= MapChunk.CHUNK_SIZE;
		if (y < 0)
			y += MapChunk.CHUNK_SIZE;
		
		return new int[]{x, y};
	}

	public int[] getLocalChunkOrigin() {
		return localChunkOrigin;
	}
	
	public int getMapDiameter() {
		return localChunks.length;
	}
	
	public void shiftMapChunks(int xChunkOriginNew, int yChunkOriginNew) {
		int xChunkOriginDiff = xChunkOriginNew - localChunkOrigin[0];
		int yChunkOriginDiff = yChunkOriginNew - localChunkOrigin[1];
		
		// shift local chunk origin
		localChunkOrigin[0] = xChunkOriginNew;
		localChunkOrigin[1] = yChunkOriginNew;
		
		// instantiate new local chunk matrix
		MapChunk[][] newLocalChunks = new MapChunk[localChunks.length][localChunks[0].length];
		
		// copy existing chunks over that are within bounds
		System.out.println("copying existing chunks");
		for (int x = 0; x < localChunks.length; x++) {
			for (int y = 0; y < localChunks[0].length; y++) {
				int xChunkOld = x + xChunkOriginDiff;
				int yChunkOld = y + yChunkOriginDiff;
				
				// if new chunk position is within bounds, copy it over
				if (xChunkOld >= 0 &&
					yChunkOld >= 0 &&
					xChunkOld < localChunks.length &&
					yChunkOld < localChunks[0].length) {
					newLocalChunks[x][y] = localChunks[xChunkOld][yChunkOld];
				} else {
					newLocalChunks[x][y] = null;
				}
			}
		}
		
		// set new local chunk matrix as current
		localChunks = newLocalChunks;
		
		// request any chunks that are null (because they weren't copied above)
		requestMissingChunks();
	}
	
	public void requestMissingChunks() {
		for (int x = 0; x < localChunks.length; x++) {
			for (int y = 0; y < localChunks[0].length; y++) {
				if (localChunks[x][y] == null) {
					Message m = new Message();
					m.type = MessageType.CHUNK_LOAD;
					m.xyChunk = new int[] {
						x + localChunkOrigin[0],
						y + localChunkOrigin[1]
					};
					Main.client.send(m);
				}
			}
		}
	}
	
	public MapChunk createChunk(int xChunk, int yChunk) {
		int xChunkLocal = xChunk - localChunkOrigin[0];
		int yChunkLocal = yChunk - localChunkOrigin[1];
		
		MapChunk newChunk = new MapChunk();
		localChunks[xChunkLocal][yChunkLocal] = newChunk;
		return newChunk;
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
	
	/*
	 * ActiveTile management
	 */

	public void setActiveTile(int x, int y, ActiveTile at) {
		MapChunk chunk = getChunkAtPos(x, y);
		int[] pos = getPosInChunk(x, y);
		
		chunk.setActiveTile(pos[0], pos[1], at);
	}
	
	public ActiveTile getActiveTile(int x, int y) {
		MapChunk chunk = getChunkAtPos(x, y);
		int[] pos = getPosInChunk(x, y);
		
		return chunk.getActiveTile(pos[0], pos[1]);
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