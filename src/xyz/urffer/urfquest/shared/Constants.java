package xyz.urffer.urfquest.shared;

import xyz.urffer.urfquest.shared.protocol.types.ObjectType;
import xyz.urffer.urfquest.shared.protocol.types.TileType;

public class Constants {
	
	public static final int MAP_CHUNK_SIZE = 32;
	public static final TileType DEFAULT_CHUNK_TILE = TileType.DIRT;
	public static final ObjectType DEFAULT_CHUNK_OBJECT = ObjectType.VOID;
	
	public static final String FILE_STARTUP_PREFS = "startup_prefs.json";
	
	public static final int DEFAULT_PLAYER_INVENTORY_SIZE = 10;
	
	
	/*
	 * ENTITY CONSTANTS
	 */
	public static final int DEFAULT_HEALTH_MAX_PLAYER = 1000;
	public static final int DEFAULT_MANA_MAX_PLAYER = 1000;
	public static final int DEFAULT_FULLNESS_MAX_PLAYER = 1000;
	public static final double DEFAULT_VELOCITY_PLAYER = 0.15;
	
	public static final int DEFAULT_HEALTH_MAX_CHICKEN = 100;
	public static final int DEFAULT_MANA_MAX_CHICKEN = 0;
	public static final int DEFAULT_FULLNESS_MAX_CHICKEN = 0;
	public static final double DEFAULT_VELOCITY_CHICKEN = 0.02;

	public static final int DEFAULT_HEALTH_MAX_CYCLOPS = 500;
	public static final int DEFAULT_MANA_MAX_CYCLOPS = 0;
	public static final int DEFAULT_FULLNESS_MAX_CYCLOPS = 0;
	public static final double DEFAULT_VELOCITY_CYCLOPS = 0.01;
	
	public static final double DEFAULT_VELOCITY_CAMERA = 0.01;
	
	
	/*
	 * CLIENT ONLY
	 */
	
	public static final int CLIENT_CACHED_MAP_DIAMETER = 8;
	
	public static final int DEFAULT_TEXT_SIZE = 20;
	
	
	/*
	 * SERVER ONLY
	 */
	
	public static final int MILLISECONDS_PER_TICK = 20;
	
	public static final double TESTING_POSITION_TOLERANCE = 0.01;
	public static final String FILE_OPS_LIST = "ops.config";
	
	public static final long DEFAULT_SERVER_SEED = 10;

}
