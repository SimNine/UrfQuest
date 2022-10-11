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
		objectTypes = new int[width][height];
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				tileTypes[x][y] = Constants.DEFAULT_CHUNK_TILE;
				objectTypes[x][y] = Constants.DEFAULT_CHUNK_OBJECT;
			}
		}
		activeTiles = new ActiveTile[width][height];

		minimap = new BufferedImage(tileTypes.length, tileTypes[0].length, BufferedImage.TYPE_4BYTE_ABGR);
		this.generateMinimap();
	}
	
	
	/*
	 * Tile manipulation
	 */
	
	public int getTileTypeAt(int[] pos) {
		if (pos[0] < 0 || pos[1] < 0)
			return Tile.TILE_VOID;
		if (pos[0] >= tileTypes.length || pos[1] >= tileTypes[0].length)
			return Tile.TILE_VOID;
		return tileTypes[pos[0]][pos[1]];
	}

	public int getObjectTypeAt(int[] pos) {
		if (pos[0] < 0 || pos[1] < 0)
			return Tile.TILE_VOID;
		if (pos[0] >= tileTypes.length || pos[1] >= tileTypes[0].length)
			return Tile.TILE_VOID;
		return objectTypes[pos[0]][pos[1]];
	}
	
	public int[] getTileAt(int[] pos) {
		return new int[] {getTileTypeAt(pos), getObjectTypeAt(pos)};
	}
	
	public void setTileAt(int[] pos, int tileType, int objectType) {
		tileTypes[pos[0]][pos[1]] = tileType;
		objectTypes[pos[0]][pos[1]] = objectType;
		this.setMinimapAt(pos, tileType, objectType);
	}
	
	public void setTileTypeAt(int[] pos, int tileType) {
		int objectType = objectTypes[pos[0]][pos[1]];
		setTileAt(pos, tileType, objectType);
	}
	
	public void setObjectTypeAt(int[] pos, int objectType) {
		int tileType = tileTypes[pos[0]][pos[1]];
		setTileAt(pos, tileType, objectType);
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

	public void setActiveTile(int[] pos, ActiveTile at) {
		activeTiles[pos[0]][pos[1]] = at;
	}
	
	public ActiveTile getActiveTile(int[] pos) {
		return activeTiles[pos[0]][pos[1]];
	}
	
	public void useActiveTile(int[] pos, Mob m) {
		if (activeTiles[pos[0]][pos[1]] != null) {
			activeTiles[pos[0]][pos[1]].use(m);
		}
	}
	
	
	/*
	 * Minimap management
	 */
	
	// A minimap should be regenerated whenever this client recieves new chunks
	public void generateMinimap() {
		for (int x = 0; x < tileTypes.length; x++) {
			for (int y = 0; y < tileTypes[0].length; y++) {
				int color = Tile.minimapColor(this.getTileAt(new int[] {x,y}));
				minimap.setRGB(x, y, color);
			}
		}
	}
	
	public BufferedImage getMinimap() {
		return minimap;
	}
	
	private void setMinimapAt(int[] pos, int tileType, int objectType) {
		if (objectType != Tile.TILE_VOID) {
			minimap.setRGB(pos[0], pos[1], Tile.minimapColor(tileType, objectType));
		} else {
			minimap.setRGB(pos[0], pos[1], Tile.minimapColor(tileType, objectType));
		}
	}

}
