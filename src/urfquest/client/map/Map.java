package urfquest.client.map;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

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
	
	private BufferedImage minimap;
	
	private MapChunk[][] localChunks;
	private int[] localChunkOrigin = new int[2];

	private HashMap<Integer, Player> players = new HashMap<>();
	private HashMap<Integer, Mob> mobs = new HashMap<>();
	private HashMap<Integer, Item> items = new HashMap<>();
	private HashMap<Integer, Projectile> projectiles = new HashMap<>();
	
	private ArrayList<Particle> particles = new ArrayList<Particle>();
	private ArrayList<Particle> addParticles = new ArrayList<Particle>();
	private ArrayList<Particle> removeParticles = new ArrayList<Particle>();
	
	public Map(int loadedChunkSize) {
		localChunks = new MapChunk[loadedChunkSize][loadedChunkSize];
		localChunkOrigin[0] = localChunkOrigin[1] = 0 - (loadedChunkSize / 2);
		
		for (int x = 0; x < loadedChunkSize; x++) {
			for (int y = 0; y < loadedChunkSize; y++) {
				localChunks[x][y] = null;
			}
		}
		
		minimap = new BufferedImage(localChunks.length * MapChunk.CHUNK_SIZE, 
									localChunks[0].length * MapChunk.CHUNK_SIZE, 
									BufferedImage.TYPE_4BYTE_ABGR);
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
		
		try {
			return localChunks[xChunkLocal][yChunkLocal];
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
			return null;
		}
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
		Main.logger.debug("Copying existing chunks");
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
	 * Minimap management
	 */
	
	// A minimap should be regenerated whenever this client recieves new chunks
	public void generateMinimap() {
		for (int x = 0; x < localChunks.length; x++) {
			for (int y = 0; y < localChunks[0].length; y++) {
				MapChunk c = localChunks[x][y];
				if (c == null) {
					continue;
				}
				
				for (int xc = 0; xc < MapChunk.CHUNK_SIZE; xc++) {
					for (int yc = 0; yc < MapChunk.CHUNK_SIZE; yc++) {
						int color = Tiles.minimapColor(c.getTileTypeAt(xc, yc));
						minimap.setRGB(x * MapChunk.CHUNK_SIZE + xc, 
									   y * MapChunk.CHUNK_SIZE + yc, 
									   color);
					}
				}
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
		items.put(i.id, i);
	}
	
	public void removeItem(Item i) {
		items.remove(i.id);
	}
	
	public HashMap<Integer, Item> getItems() {
		return items;
	}
	
	public int getNumItems() {
		return items.size();
	}
	
	public Item getItem(int id) {
		return items.get(id);
	}
	
	
	
	public void addMob(Mob m) {
		mobs.put(m.id, m);
	}
	
	public void removeMob(Mob m) {
		mobs.remove(m.id);
	}
	
	public HashMap<Integer, Mob> getMobs() {
		return mobs;
	}
	
	public int getNumMobs() {
		return mobs.size();
	}
	
	public Mob getMob(int id) {
		return mobs.get(id);
	}
	
	
	
	public void addProjectile(Projectile p) {
		projectiles.put(p.id, p);
	}
	
	public void removeProjectile(Projectile p) {
		projectiles.remove(p.id);
	}
	
	public HashMap<Integer, Projectile> getProjectiles() {
		return projectiles;
	}
	
	public int getNumProjectiles() {
		return projectiles.size();
	}
	
	public Projectile getProjectile(int id) {
		return projectiles.get(id);
	}
	
	
	
	public void addPlayer(Player p) {
		players.put(p.id, p);
	}
	
	public void removePlayer(Player p) {
		players.remove(p.id);
	}
	
	public HashMap<Integer, Player> getPlayers() {
		return players;
	}
	
	public int getNumPlayers() {
		return players.size();
	}
	
	public Player getPlayer(int id) {
		return players.get(id);
	}
	
	
	
	public ArrayList<Particle> getParticles() {
		return particles;
	}
	
	public int getNumParticles() {
		return particles.size();
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