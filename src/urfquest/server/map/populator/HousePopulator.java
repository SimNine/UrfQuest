package urfquest.server.map.populator;

import urfquest.server.Server;
import urfquest.server.entities.mobs.NPCHuman;
import urfquest.server.map.Map;
import urfquest.server.map.structures.House;
import urfquest.shared.Constants;
import urfquest.shared.Tile;

public class HousePopulator extends TerrainPopulator {
	
	private static final int HOUSE_SIZE_MIN = 4;
	private static final int HOUSE_SIZE_MAX = 8;
	int sizeDiff = HOUSE_SIZE_MAX - HOUSE_SIZE_MIN;

	public HousePopulator(Server s) {
		super(s);
	}

	@Override
	public House populateChunk(Map map, int xChunk, int yChunk) {
		// get bottom-left corner coord offset
		int xRoot = xChunk * Constants.MAP_CHUNK_SIZE;
		int yRoot = yChunk * Constants.MAP_CHUNK_SIZE;
		
		// If a randomness check is passed, try to find a spot to put a house
		if (server.randomDouble() > 0.95) {
			int maxNumTries = 100;
			int numTries = 0;
			while (numTries < maxNumTries) {
				int x = xRoot + (int)(Constants.MAP_CHUNK_SIZE*server.randomDouble());
				int y = yRoot + (int)(Constants.MAP_CHUNK_SIZE*server.randomDouble());
				int width = HOUSE_SIZE_MIN + (int)(server.randomDouble()*sizeDiff);
				int height = HOUSE_SIZE_MIN + (int)(server.randomDouble()*sizeDiff);
				int[] pos = new int[] {x, y};
				int[] dims = new int[] {width, height};
				if (this.isHousePlaceableAt(map, pos, dims)) {
					return new House(new int[]{x, y}, new int[]{width, height});
				}
				numTries++;
			}
		}
		
		return null;
	}
	
	private boolean isHousePlaceableAt(Map map, int[] pos, int[] dims) {
		for (int x = pos[0]; x <= pos[0] + dims[0]; x++) {
			for (int y = pos[1]; y <= pos[1] + dims[1]; y++) {
				if (map.getTileAt(x, y)[0] != Tile.TILE_GRASS) {
					return false;
				}
			}
		}
		
		return true;
	}
	
	public void generateStructure(Map map, int[] pos, int[] dims) {
		// Floor
		for (int x = pos[0]; x < pos[0] + dims[0]; x++) {
			for (int y = pos[1]; y < pos[1] + dims[1]; y++) {
				map.setTileAt(x, y, Tile.TILE_FLOOR_WOOD, Tile.TILE_VOID);
			}
		}
		
		// Walls
		for (int x = pos[0]; x <= pos[0] + dims[0]; x++) {
			map.setTileAt(x, pos[1], Tile.TILE_FLOOR_WOOD, Tile.OBJECT_WALL_STONE);
			map.setTileAt(x, pos[1] + dims[1], Tile.TILE_FLOOR_WOOD, Tile.OBJECT_WALL_STONE);
		}
		for (int y = pos[1]; y <= pos[1] + dims[1]; y++) {
			map.setTileAt(pos[0], y, Tile.TILE_FLOOR_WOOD, Tile.OBJECT_WALL_STONE);
			map.setTileAt(pos[0] + dims[0], y, Tile.TILE_FLOOR_WOOD, Tile.OBJECT_WALL_STONE);
		}
		
		// Door
		map.setTileAt(pos[0] + dims[0]/2, pos[1] + dims[1], 
					  Tile.TILE_FLOOR_WOOD, Tile.TILE_VOID);
		
		// Resident
		// TODO: this line is causing client message parsing errors. look into it
		map.addMob(new NPCHuman(server, map, new double[] {pos[0] + dims[0]/2, pos[1] + dims[1]/2}));
	}

}
