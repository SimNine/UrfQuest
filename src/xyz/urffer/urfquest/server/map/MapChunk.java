package xyz.urffer.urfquest.server.map;

import java.awt.image.BufferedImage;

import xyz.urffer.urfutils.math.PairInt;

import xyz.urffer.urfquest.server.entities.mobs.Mob;
import xyz.urffer.urfquest.server.tiles.ActiveTile;
import xyz.urffer.urfquest.shared.Constants;
import xyz.urffer.urfquest.shared.Tile;

public class MapChunk {

	private Tile[][] tiles;
	
	private ActiveTile[][] activeTiles;
	
	private BufferedImage minimap;
	
	public MapChunk() {
		this(new PairInt(Constants.MAP_CHUNK_SIZE, Constants.MAP_CHUNK_SIZE));
	}
	
	public MapChunk(PairInt dims) {
		tiles = new Tile[dims.x][dims.y];
		for (int x = 0; x < dims.x; x++) {
			for (int y = 0; y < dims.y; y++) {
				tiles[x][y] = new Tile();
			}
		}
		activeTiles = new ActiveTile[dims.x][dims.y];
		
		minimap = new BufferedImage(
			tiles.length,
			tiles[0].length,
			BufferedImage.TYPE_4BYTE_ABGR
		);
	}
	
	
	/*
	 * Tile manipulation
	 */
	
	public Tile getTileAt(PairInt pos) {
		if (pos.x < 0 || pos.y < 0) {
			return Tile.VOID;
		}
		if (pos.x >= tiles.length || pos.y >= tiles[0].length) {
			return Tile.VOID;
		}
		return tiles[pos.x][pos.y];
	}
	
	public void setTileAt(PairInt pos, Tile tile) {
		tiles[pos.x][pos.y] = tile;
		this.setMinimapAt(pos, tile);
	}
	
	public Tile[][] getAllTiles() {
		return tiles;
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
		for (int x = 0; x < tiles.length; x++) {
			for (int y = 0; y < tiles[0].length; y++) {
				int color = Tile.minimapColor(this.getTileAt(new PairInt(x, y)));
				minimap.setRGB(x, y, color);
			}
		}
	}
	
	public BufferedImage getMinimap() {
		return minimap;
	}
	
	private void setMinimapAt(PairInt pos, Tile tile) {
		minimap.setRGB(pos.x, pos.y, Tile.minimapColor(tile));
	}

}
