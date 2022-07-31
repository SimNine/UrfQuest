package xyz.urffer.urfquest.client.map;

import java.awt.image.BufferedImage;

import xyz.urffer.urfquest.client.entities.mobs.Mob;
import xyz.urffer.urfquest.client.tiles.ActiveTile;
import xyz.urffer.urfquest.shared.Constants;
import xyz.urffer.urfquest.shared.Tile;

public class MapChunk {

	private int[][] tileTypes;
	private int[][] objectTypes;
	
	private ActiveTile[][] activeTiles;
	
	private BufferedImage minimap;
	
	public MapChunk() {
		this(Constants.MAP_CHUNK_SIZE, Constants.MAP_CHUNK_SIZE);
	}
	
	public MapChunk(int width, int height) {
		tileTypes = new int[width][height];
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				tileTypes[x][y] = Constants.DEFAULT_CHUNK_TILE;
			}
		}
		objectTypes = new int[width][height];
		activeTiles = new ActiveTile[width][height];

		minimap = new BufferedImage(tileTypes.length, tileTypes[0].length, BufferedImage.TYPE_4BYTE_ABGR);
	}
	
	
	/*
	 * Tile manipulation
	 */
	
	public int getTileTypeAt(int x, int y) {
		if (x < 0 || y < 0)
			return Tile.TILE_VOID;
		if (x >= tileTypes.length || y >= tileTypes[0].length)
			return Tile.TILE_VOID;
		return tileTypes[x][y];
	}

	public int getObjectTypeAt(int x, int y) {
		if (x < 0 || y < 0)
			return Tile.TILE_VOID;
		if (x >= tileTypes.length || y >= tileTypes[0].length)
			return Tile.TILE_VOID;
		return objectTypes[x][y];
	}
	
	public int[] getTileAt(int x, int y) {
		return new int[] {getTileTypeAt(x, y), getObjectTypeAt(x, y)};
	}
	
	public void setTileAt(int x, int y, int tileType, int objectType) {
		tileTypes[x][y] = tileType;
		objectTypes[x][y] = objectType;
		this.setMinimapAt(x, y, tileType, objectType);
	}
	
	public void setTileTypeAt(int x, int y, int tileType) {
		int objectType = objectTypes[x][y];
		setTileAt(x, y, tileType, objectType);
	}
	
	public void setObjectTypeAt(int x, int y, int objectType) {
		int tileType = tileTypes[x][y];
		setTileAt(x, y, tileType, objectType);
	}
	
	public void setAllTileTypes(int[][] types) {
		this.tileTypes = types;
		this.generateMinimap();
	}
	
	public void setAllObjectTypes(int[][] subtypes) {
		this.objectTypes = subtypes;
		this.generateMinimap();
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
	 * Minimap management
	 */
	
	// A minimap should be regenerated whenever this client recieves new chunks
	public void generateMinimap() {
		for (int x = 0; x < tileTypes.length; x++) {
			for (int y = 0; y < tileTypes[0].length; y++) {
				int color = Tile.minimapColor(this.getTileAt(x, y));
				minimap.setRGB(x, y, color);
			}
		}
	}
	
	public BufferedImage getMinimap() {
		return minimap;
	}
	
	private void setMinimapAt(int x, int y, int tileType, int objectType) {
		if (objectType != Tile.TILE_VOID) {
			minimap.setRGB(x, y, Tile.minimapColor(tileType, objectType));
		} else {
			minimap.setRGB(x, y, Tile.minimapColor(tileType, objectType));
		}
	}

}
