package urfquest.server.map;

import java.awt.image.BufferedImage;

import urfquest.client.tiles.Tiles;
import urfquest.server.entities.mobs.Mob;
import urfquest.server.tiles.ActiveTile;
import urfquest.shared.Constants;

public class MapChunk {

	private int[][] tileTypes;
	private int[][] tileSubtypes;
	
	private ActiveTile[][] activeTiles;
	
	private BufferedImage minimap;
	
	public MapChunk() {
		this(Constants.MAP_CHUNK_SIZE, Constants.MAP_CHUNK_SIZE);
	}
	
	public MapChunk(int width, int height) {
		tileTypes = new int[width][height];
		tileSubtypes = new int[width][height];
		activeTiles = new ActiveTile[width][height];
		
		minimap = new BufferedImage(tileTypes.length, tileTypes[0].length, BufferedImage.TYPE_4BYTE_ABGR);
	}
	
	/*
	 * Tile manipulation
	 */
	
	public int getTileTypeAt(int x, int y) {
		if (x < 0 || y < 0) return -1;
		if (x >= tileTypes.length || y >= tileTypes[0].length) return -1;
		return tileTypes[x][y];
	}
	
	// TODO: potentially change defaut subtype to be -1 (null)
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
		this.setMinimapAt(x, y, type);
	}
	
	public void setTileAt(int x, int y, int type, int subtype) {
		setTileAt(x, y, type);
		tileSubtypes[x][y] = subtype;
	}
	
	public int[][] getAllTileTypes() {
		return tileTypes;
	}
	
	public int[][] getAllTileSubtypes() {
		return tileSubtypes;
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
				int color = Tiles.minimapColor(this.getTileTypeAt(x, y));
				minimap.setRGB(x, y, color);
			}
		}
	}
	
	public BufferedImage getMinimap() {
		return minimap;
	}
	
	private void setMinimapAt(int x, int y, int type) {
		minimap.setRGB(x, y, Tiles.minimapColor(type));
	}

}
