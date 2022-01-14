package urfquest.client.map;

import urfquest.client.entities.mobs.Mob;
import urfquest.client.tiles.ActiveTile;

public class MapChunk {

	private int[][] tileTypes;
	private int[][] tileSubtypes;
	
	private ActiveTile[][] activeTiles;
	
	public static final int CHUNK_SIZE = 32;
	
	public MapChunk() {
		this(CHUNK_SIZE, CHUNK_SIZE);
	}
	
	public MapChunk(int width, int height) {
		tileTypes = new int[width][height];
		tileSubtypes = new int[width][height];
		activeTiles = new ActiveTile[width][height];
	}
	
	/*
	 * Tile manipulation
	 */
	
	public int getTileTypeAt(int x, int y) {
		if (x < 0 || y < 0)
			return -1;
		if (x >= tileTypes.length || y >= tileTypes[0].length)
			return -1;
		return tileTypes[x][y];
	}

	// TODO: tiles with no subtype should probably return -1
	public int getTileSubtypeAt(int x, int y) {
		if (x < 0 || y < 0)
			return 0;
		if (x >= tileTypes.length || y >= tileTypes[0].length)
			return 0;
		return tileSubtypes[x][y];
	}
	
	public int[] getTileAt(int x, int y) {
		return new int[] {getTileTypeAt(x, y), getTileSubtypeAt(x, y)};
	}
	
	public void setTileAt(int x, int y, int type) {
		tileTypes[x][y] = type;
		tileSubtypes[x][y] = 0;
	}
	
	public void setTileAt(int x, int y, int type, int subtype) {
		setTileAt(x, y, type);
		tileSubtypes[x][y] = subtype;
	}
	
	public void setAllTileTypes(int[][] types) {
		this.tileTypes = types;
	}
	
	public void setAllTileSubtypes(int[][] subtypes) {
		this.tileSubtypes = subtypes;
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

}
