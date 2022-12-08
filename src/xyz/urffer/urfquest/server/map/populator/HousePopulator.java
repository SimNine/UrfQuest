package xyz.urffer.urfquest.server.map.populator;

import xyz.urffer.urfutils.math.PairDouble;
import xyz.urffer.urfutils.math.PairInt;

import xyz.urffer.urfquest.server.Server;
import xyz.urffer.urfquest.server.entities.mobs.NPCHuman;
import xyz.urffer.urfquest.server.map.Map;
import xyz.urffer.urfquest.server.map.structures.House;
import xyz.urffer.urfquest.shared.Constants;
import xyz.urffer.urfquest.shared.Tile;
import xyz.urffer.urfquest.shared.protocol.types.ObjectType;
import xyz.urffer.urfquest.shared.protocol.types.TileType;

public class HousePopulator extends TerrainPopulator {
	
	private static final int HOUSE_SIZE_MIN = 4;
	private static final int HOUSE_SIZE_MAX = 8;
	int sizeDiff = HOUSE_SIZE_MAX - HOUSE_SIZE_MIN;

	public HousePopulator(Server s) {
		super(s);
	}

	@Override
	public House populateChunk(Map map, PairInt chunkPos) {
		// get bottom-left corner coord offset
		PairInt root = chunkPos.multiply(Constants.MAP_CHUNK_SIZE);
		
		// If a randomness check is passed, try to find a spot to put a house
		if (server.randomDouble() > 0.95) {
			int maxNumTries = 100;
			int numTries = 0;
			while (numTries < maxNumTries) {
				int x = root.x + (int)(Constants.MAP_CHUNK_SIZE*server.randomDouble());
				int y = root.y + (int)(Constants.MAP_CHUNK_SIZE*server.randomDouble());
				int width = HOUSE_SIZE_MIN + (int)(server.randomDouble()*sizeDiff);
				int height = HOUSE_SIZE_MIN + (int)(server.randomDouble()*sizeDiff);
				PairInt pos = new PairInt(x, y);
				PairInt dims = new PairInt(width, height);
				if (this.isHousePlaceableAt(map, pos, dims)) {
					return new House(pos, dims);
				}
				numTries++;
			}
		}
		
		return null;
	}
	
	private boolean isHousePlaceableAt(Map map, PairInt pos, PairInt dims) {
		for (int x = pos.x; x <= pos.x + dims.x; x++) {
			for (int y = pos.y; y <= pos.y + dims.y; y++) {
				PairInt testPos = new PairInt(x,y);
				if (map.getTileAt(testPos).tileType != TileType.GRASS) {
					return false;
				}
			}
		}
		
		return true;
	}
	
	public void generateStructure(Map map, PairInt pos, PairInt dims) {
		// Floor
		for (int x = pos.x; x < pos.x + dims.x; x++) {
			for (int y = pos.y; y < pos.y + dims.y; y++) {
				PairInt testPos = new PairInt(x,y);
				map.setTileAt(testPos, new Tile(TileType.FLOOR_WOOD, ObjectType.VOID));
			}
		}
		
		// Walls
		for (int x = pos.x; x <= pos.x + dims.x; x++) {
			map.setTileAt(new PairInt(x, pos.y), new Tile(TileType.FLOOR_WOOD, ObjectType.WALL_STONE));
			map.setTileAt(new PairInt(x, pos.y + dims.y), new Tile(TileType.FLOOR_WOOD, ObjectType.WALL_STONE));
		}
		for (int y = pos.y; y <= pos.y + dims.y; y++) {
			map.setTileAt(new PairInt(pos.x, y), new Tile(TileType.FLOOR_WOOD, ObjectType.WALL_STONE));
			map.setTileAt(new PairInt(pos.x + dims.x, y), new Tile(TileType.FLOOR_WOOD, ObjectType.WALL_STONE));
		}
		
		// Door
		map.setTileAt(new PairInt(pos.x + dims.x/2, pos.y + dims.y), 
				new Tile(TileType.FLOOR_WOOD, ObjectType.VOID));
		
		// Resident
		// TODO: this line is causing client message parsing errors. look into it
		map.addMob(new NPCHuman(server, map, new PairDouble(pos.x + dims.x/2, pos.y + dims.y/2)));
	}

}
