package xyz.urffer.urfquest.client.map;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

import xyz.urffer.urfquest.client.Client;
import xyz.urffer.urfquest.client.entities.Entity;
import xyz.urffer.urfquest.client.entities.items.ItemStack;
import xyz.urffer.urfquest.client.entities.mobs.Mob;
import xyz.urffer.urfquest.client.entities.mobs.Player;
import xyz.urffer.urfquest.client.entities.particles.Particle;
import xyz.urffer.urfquest.client.entities.projectiles.Projectile;
import xyz.urffer.urfquest.client.tiles.ActiveTile;
import xyz.urffer.urfquest.shared.Constants;
import xyz.urffer.urfquest.shared.PairDouble;
import xyz.urffer.urfquest.shared.PairInt;
import xyz.urffer.urfquest.shared.Tile;
import xyz.urffer.urfquest.shared.protocol.messages.MessageRequestChunk;

public class Map {
	private Client client;
	
	public final int id;
	
	public static final int EMPTY_MAP = 5000;
	public static final int SIMPLEX_MAP = 5001;
	public static final int SAVANNAH_MAP = 5002;
	public static final int TEMPLATE_MAP = 5003;
	public static final int CAVE_MAP = 5004;
	
	private PairInt homeCoords = new PairInt(0,0);
	
	private BufferedImage minimap;
	
	private MapChunk[][] localChunks;
	private PairInt localChunkOrigin = new PairInt(0,0);

	private HashMap<Integer, Player> players = new HashMap<>();
	private HashMap<Integer, Mob> mobs = new HashMap<>();
	private HashMap<Integer, ItemStack> items = new HashMap<>();
	private HashMap<Integer, Projectile> projectiles = new HashMap<>();
	
	private ArrayList<Particle> particles = new ArrayList<Particle>();
	private ArrayList<Particle> addParticles = new ArrayList<Particle>();
	private ArrayList<Particle> removeParticles = new ArrayList<Particle>();
	
	public Map(Client c, int id, int loadedChunkSize) {
		this.client = c;
		this.id = id;
		
		localChunks = new MapChunk[loadedChunkSize][loadedChunkSize];
		localChunkOrigin.x = localChunkOrigin.y = 0 - (loadedChunkSize / 2);
		
		minimap = new BufferedImage(
			localChunks.length * Constants.MAP_CHUNK_SIZE, 
			localChunks[0].length * Constants.MAP_CHUNK_SIZE, 
			BufferedImage.TYPE_4BYTE_ABGR
		);
		this.generateMinimap();
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
	
	public Tile getTileAt(PairInt pos) {
		MapChunk chunk = getChunkAtPos(pos);
		if (chunk == null)
			return Tile.VOID;
		PairInt posInChunk = getPosInChunk(pos);
		
		return chunk.getTileAt(posInChunk);
	}
	
	public void setTileAt(PairInt pos, Tile tile) {
		MapChunk chunk = getChunkAtPos(pos);
		PairInt posInChunk = getPosInChunk(pos);
		
		chunk.setTileAt(posInChunk, tile);
	}
	
	
	/*
	 * Chunk manipulation
	 */
	
	private MapChunk getChunk(PairInt posChunk) {
		PairInt chunkLocal = posChunk.subtract(localChunkOrigin);
		
		try {
			return localChunks[chunkLocal.x][chunkLocal.y];
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
			return null;
		}
	}
	
	public MapChunk getChunkAtPos(PairInt pos) {
		if (pos.x < localChunkOrigin.x * Constants.MAP_CHUNK_SIZE || 
			pos.y < localChunkOrigin.y * Constants.MAP_CHUNK_SIZE)
			return null;
		if (pos.x >= (localChunkOrigin.x + localChunks.length) * Constants.MAP_CHUNK_SIZE || 
			pos.y >= (localChunkOrigin.y + localChunks[0].length) * Constants.MAP_CHUNK_SIZE)
			return null;

		int xChunk = Math.floorDiv(pos.x, Constants.MAP_CHUNK_SIZE);
		int yChunk = Math.floorDiv(pos.y, Constants.MAP_CHUNK_SIZE);
		return getChunk(new PairInt(xChunk, yChunk));
	}

	private PairInt getPosInChunk(PairInt pos) {
		PairInt posInChunk = pos.clone();
		
		posInChunk.x %= Constants.MAP_CHUNK_SIZE;
		if (posInChunk.x < 0)
			posInChunk.x += Constants.MAP_CHUNK_SIZE;

		posInChunk.y %= Constants.MAP_CHUNK_SIZE;
		if (posInChunk.y < 0)
			posInChunk.y += Constants.MAP_CHUNK_SIZE;
		
		return posInChunk;
	}

	public PairInt getLocalChunkOrigin() {
		return localChunkOrigin;
	}
	
	public int getMapDiameter() {
		return localChunks.length;
	}
	
	public void shiftMapChunks(PairInt chunkOriginNew) {
		this.client.getLogger().debug("Shifting local chunk origin to: " + 
									  "(" + chunkOriginNew.x + "," + chunkOriginNew.y + ")");
		
		PairInt chunkOriginDiff = chunkOriginNew.subtract(localChunkOrigin);
		
		// shift local chunk origin
		localChunkOrigin = chunkOriginNew.clone();
		
		// instantiate new local chunk matrix
		MapChunk[][] newLocalChunks = new MapChunk[localChunks.length][localChunks[0].length];
		
		// copy existing chunks over that are within bounds
		this.client.getLogger().debug("Copying existing chunks");
		for (int x = 0; x < localChunks.length; x++) {
			for (int y = 0; y < localChunks[0].length; y++) {
				PairInt chunkOld = new PairInt(x, y).add(chunkOriginDiff);
				
				// if new chunk position is within bounds, copy it over
				if (chunkOld.x >= 0 &&
					chunkOld.y >= 0 &&
					chunkOld.x < localChunks.length &&
					chunkOld.y < localChunks[0].length) {
					newLocalChunks[x][y] = localChunks[chunkOld.x][chunkOld.y];
				} else {
					newLocalChunks[x][y] = null;
				}
			}
		}
		
		// set new local chunk matrix as current
		localChunks = newLocalChunks;
		
		// request any chunks that are null (because they weren't copied above)
		//requestMissingChunks();
	}
	
	public void requestMissingChunks() {
		for (int x = 0; x < localChunks.length; x++) {
			for (int y = 0; y < localChunks[0].length; y++) {
				if (localChunks[x][y] == null) {
					MessageRequestChunk m = new MessageRequestChunk();
					m.mapID = this.id;
					m.xyChunk = new PairInt(
						x + localChunkOrigin.x,
						y + localChunkOrigin.y
					);
					this.client.send(m);
				}
			}
		}
	}
	
	private MapChunk createChunk(PairInt posChunk) {
		PairInt chunkLocal = posChunk.subtract(localChunkOrigin);
		
		MapChunk newChunk = new MapChunk();
		localChunks[chunkLocal.x][chunkLocal.y] = newChunk;
		return newChunk;
	}
	
	public void setChunk(PairInt chunkPos, Tile[][] tiles) {
		MapChunk chunk = this.getChunk(chunkPos);
		if (chunk == null) {
			chunk = this.createChunk(chunkPos);
		}
		
		chunk.setAllTiles(tiles);
		this.generateMinimap();
	}
	
	
	/*
	 * Minimap management
	 */
	
	// A minimap should be regenerated whenever this client recieves new chunks
	public void generateMinimap() {
		Graphics2D newMinimapGraphics = minimap.createGraphics();
		newMinimapGraphics.setColor(Color.BLACK);
		newMinimapGraphics.fillRect(0, 0, minimap.getWidth(), minimap.getHeight());
		
		for (int x = 0; x < localChunks.length; x++) {
			for (int y = 0; y < localChunks[0].length; y++) {
				MapChunk c = localChunks[x][y];
				if (c == null) {
					continue;
				}
				
				c.generateMinimap();
				newMinimapGraphics.drawImage(c.getMinimap(), null, 
											 x * Constants.MAP_CHUNK_SIZE, 
											 y * Constants.MAP_CHUNK_SIZE);
			}
		}
	}
	
	public BufferedImage getMinimap() {
		return minimap;
	}
	
	
	/*
	 * Misc map manipulation
	 */
	
	public boolean setHomeCoords(PairInt pos) {
		if (Tile.isWalkable(getTileAt(pos))) {
			homeCoords = pos;
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
		PairInt posInChunk = getPosInChunk(pos);
		
		chunk.setActiveTile(posInChunk, at);
	}
	
	public ActiveTile getActiveTile(PairInt pos) {
		MapChunk chunk = getChunkAtPos(pos);
		PairInt posInChunk = getPosInChunk(pos);
		
		return chunk.getActiveTile(posInChunk);
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
	
	public void removeItem(ItemStack i) {
		items.remove(i.id);
	}
	
	public HashMap<Integer, ItemStack> getItems() {
		return items;
	}
	
	public int getNumItems() {
		return items.size();
	}
	
	public ItemStack getItem(int entityID) {
		return items.get(entityID);
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
	
	public Mob getMob(int entityID) {
		return mobs.get(entityID);
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
	
	public Projectile getProjectile(int entityID) {
		return projectiles.get(entityID);
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
	
	public Player getPlayer(int entityID) {
		return players.get(entityID);
	}
	
	
	
	public ArrayList<Particle> getParticles() {
		return particles;
	}
	
	public int getNumParticles() {
		return particles.size();
	}
	
	
	
	public Entity getEntity(int entityID) {
		if (players.containsKey(entityID))
			return players.get(entityID);
		if (items.containsKey(entityID))
			return items.get(entityID);
		if (mobs.containsKey(entityID))
			return mobs.get(entityID);
		if (projectiles.containsKey(entityID))
			return projectiles.get(entityID);
		return null;
	}
	
	public void removeEntity(int entityID) {
		players.remove(entityID);
		items.remove(entityID);
		mobs.remove(entityID);
		projectiles.remove(entityID);
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