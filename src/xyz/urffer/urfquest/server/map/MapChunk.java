package xyz.urffer.urfquest.server.map;

import java.awt.image.BufferedImage;

import xyz.urffer.urfquest.server.entities.mobs.Mob;
import xyz.urffer.urfquest.server.tiles.ActiveTile;
import xyz.urffer.urfquest.shared.Constants;
import xyz.urffer.urfquest.shared.PairInt;
import xyz.urffer.urfquest.shared.Tile;

public class MapChunk {

	private int[][] tileTypes;
	private int[][] objectTypes;
	
	private ActiveTile[][] activeTiles;
	
	private BufferedImage minimap;
	
	public MapChunk() {
		this(new PairInt(Constants.MAP_CHUNK_SIZE, Constants.MAP_CHUNK_SIZE));
	}
	
	public MapChunk(PairInt dims) {
		tileTypes = new int[dims.x][dims.y];
		for (int x = 0; x < dims.x; x++) {
			for (int y = 0; y < dims.y; y++) {
				tileTypes[x][y] = Constants.DEFAULT_CHUNK_TILE;
			}
		}
		objectTypes = new int[dims.x][dims.y];
		activeTiles = new ActiveTile[dims.x][dims.y];
		
		minimap = new BufferedImage(
			tileTypes.length,
			tileTypes[0].length,
			BufferedImage.TYPE_4BYTE_ABGR
		);
	}
	
	
	/*
	 * Tile manipulation
	 */
	
	public int getTileTypeAt(PairInt pos) {
		if (pos.x < 0 || pos.y < 0) {
			return Tile.TILE_VOID;
		}
		if (pos.x >= tileTypes.length || pos.y >= tileTypes[0].length) {
			return Tile.TILE_VOID;
		}
		return tileTypes[pos.x][pos.y];
	}
	
	public int getObjectTypeAt(PairInt pos) {
		if (pos.x < 0 || pos.y < 0) {
			return Tile.TILE_VOID;
		}
		if (pos.x >= tileTypes.length || pos.y >= tileTypes[0].length) {
			return Tile.TILE_VOID;
		}
		return objectTypes[pos.x][pos.y];
	}
	
	public int[] getTileAt(PairInt pos) {
		return new int[] {getTileTypeAt(pos), getObjectTypeAt(pos)};
	}
	
	public void setTileAt(PairInt pos, int tileType, int objectType) {
		tileTypes[pos.x][pos.y] = tileType;
		objectTypes[pos.x][pos.y] = objectType;
		this.setMinimapAt(pos, tileType, objectType);
	}
	
	public void setTileTypeAt(PairInt pos, int tileType) {
		int objectType = objectTypes[pos.x][pos.y];
		setTileAt(pos, tileType, objectType);
	}
	
	public void setObjectTypeAt(PairInt pos, int objectType) {
		int tileType = tileTypes[pos.x][pos.y];
		setTileAt(pos, tileType, objectType);
	}
	
	public int[][] getAllTileTypes() {
		return tileTypes;
	}
	
	public int[][] getAllObjectTypes() {
		return objectTypes;
	}
	
	
	/*
	 * ActiveTile management
	 */

	public void setActiveTile(PairInt pos, ActiveTile at) {
		activeTiles[pos.x][pos.y] = at;
	}
	
	public ActiveTile getActiveTile(PairInt pos) {
		return activeTiles[pos.x][pos.y];
	}
	
	public void useActiveTile(PairInt pos, Mob m) {
		if (activeTiles[pos.x][pos.y] != null) {
			activeTiles[pos.x][pos.y].use(m);
		}
	}
	
	
	/*
	 * Minimap management
	 */
	
	// A minimap should be regenerated whenever this client recieves new chunks
	public void generateMinimap() {
		for (int x = 0; x < tileTypes.length; x++) {
			for (int y = 0; y < tileTypes[0].length; y++) {
				int color = Tile.minimapColor(this.getTileAt(new PairInt(x, y)));
				minimap.setRGB(x, y, color);
			}
		}
	}
	
	public BufferedImage getMinimap() {
		return minimap;
	}
	
	private void setMinimapAt(PairInt pos, int tileType, int objectType) {
		if (objectType != Tile.TILE_VOID) {
			minimap.setRGB(pos.x, pos.y, Tile.minimapColor(tileType, objectType));
		} else {
			minimap.setRGB(pos.x, pos.y, Tile.minimapColor(tileType, objectType));
		}
	}

}
