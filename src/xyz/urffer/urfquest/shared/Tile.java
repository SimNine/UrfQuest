package xyz.urffer.urfquest.shared;

import java.awt.Color;

public class Tile {
	
	
	/*
	 * Tile keywords
	 */
	
	// Floor tiles
	public static final int TILE_VOID;
	public static final int TILE_BEDROCK;
	public static final int TILE_GRASS;
	public static final int TILE_MANA_PAD;
	public static final int TILE_HEALTH_PAD;
	public static final int TILE_HURT_PAD;
	public static final int TILE_SPEED_PAD;
	public static final int TILE_WATER;
	public static final int TILE_SAND;
	public static final int TILE_DIRT;
	public static final int TILE_FLOOR_WOOD;
	
	// Object tiles
	public static final int OBJECT_FLOWERS;
	public static final int OBJECT_TREE;
	public static final int OBJECT_BOULDER;
	public static final int OBJECT_HOLE;
	public static final int OBJECT_STONE;
	public static final int OBJECT_IRON_ORE;
	public static final int OBJECT_COPPER_ORE;
	public static final int OBJECT_CHEST;
	public static final int OBJECT_WALL_STONE;

	public static final int NUM_TILE_TYPES;
	
	
	/*
	 * Tile number IDs
	 */
	
	static {
		int i = 0;

		// Floor tiles
		TILE_VOID = i++;
		TILE_BEDROCK = i++;
		TILE_GRASS = i++;
		TILE_MANA_PAD = i++;
		TILE_HEALTH_PAD = i++;
		TILE_HURT_PAD = i++;
		TILE_SPEED_PAD = i++;
		TILE_WATER = i++;
		TILE_SAND = i++;
		TILE_DIRT = i++;
		TILE_FLOOR_WOOD = i++;
		
		// Object tiles
		OBJECT_FLOWERS = i++;
		OBJECT_TREE = i++;
		OBJECT_BOULDER = i++;
		OBJECT_HOLE = i++;
		OBJECT_STONE = i++;
		OBJECT_IRON_ORE = i++;
		OBJECT_COPPER_ORE = i++;
		OBJECT_CHEST = i++;
		OBJECT_WALL_STONE = i++;
		
		NUM_TILE_TYPES = i;
	}
	
	
	/*
	 * Tile properties
	 */
	
	public static final int PROPERTY_WALKABLE = 0;
	public static final int PROPERTY_PENETRABLE = 1;
	public static final int NUM_PROPERTIES = 2;
	
	private static final boolean[][] BOOLEAN_PROPERTIES = new boolean[NUM_TILE_TYPES][NUM_PROPERTIES];
	
	static {
		BOOLEAN_PROPERTIES[TILE_VOID][PROPERTY_WALKABLE] = false;
		BOOLEAN_PROPERTIES[TILE_VOID][PROPERTY_PENETRABLE] = false;
		BOOLEAN_PROPERTIES[TILE_BEDROCK][PROPERTY_WALKABLE] = false;
		BOOLEAN_PROPERTIES[TILE_BEDROCK][PROPERTY_PENETRABLE] = false;
		BOOLEAN_PROPERTIES[TILE_GRASS][PROPERTY_WALKABLE] = true;
		BOOLEAN_PROPERTIES[TILE_GRASS][PROPERTY_PENETRABLE] = true;
		BOOLEAN_PROPERTIES[TILE_MANA_PAD][PROPERTY_WALKABLE] = true;
		BOOLEAN_PROPERTIES[TILE_MANA_PAD][PROPERTY_PENETRABLE] = true;
		BOOLEAN_PROPERTIES[TILE_HEALTH_PAD][PROPERTY_WALKABLE] = true;
		BOOLEAN_PROPERTIES[TILE_HEALTH_PAD][PROPERTY_PENETRABLE] = true;
		BOOLEAN_PROPERTIES[TILE_HURT_PAD][PROPERTY_WALKABLE] = true;
		BOOLEAN_PROPERTIES[TILE_HURT_PAD][PROPERTY_PENETRABLE] = true;
		BOOLEAN_PROPERTIES[TILE_SPEED_PAD][PROPERTY_WALKABLE] = true;
		BOOLEAN_PROPERTIES[TILE_SPEED_PAD][PROPERTY_PENETRABLE] = true;
		BOOLEAN_PROPERTIES[TILE_WATER][PROPERTY_WALKABLE] = false;
		BOOLEAN_PROPERTIES[TILE_WATER][PROPERTY_PENETRABLE] = true;
		BOOLEAN_PROPERTIES[TILE_SAND][PROPERTY_WALKABLE] = true;
		BOOLEAN_PROPERTIES[TILE_SAND][PROPERTY_PENETRABLE] = true;
		BOOLEAN_PROPERTIES[TILE_DIRT][PROPERTY_WALKABLE] = true;
		BOOLEAN_PROPERTIES[TILE_DIRT][PROPERTY_PENETRABLE] = true;
		BOOLEAN_PROPERTIES[TILE_FLOOR_WOOD][PROPERTY_WALKABLE] = true;
		BOOLEAN_PROPERTIES[TILE_FLOOR_WOOD][PROPERTY_PENETRABLE] = true;

		BOOLEAN_PROPERTIES[OBJECT_FLOWERS][PROPERTY_WALKABLE] = true;
		BOOLEAN_PROPERTIES[OBJECT_FLOWERS][PROPERTY_PENETRABLE] = true;
		BOOLEAN_PROPERTIES[OBJECT_TREE][PROPERTY_WALKABLE] = false;
		BOOLEAN_PROPERTIES[OBJECT_TREE][PROPERTY_PENETRABLE] = false;
		BOOLEAN_PROPERTIES[OBJECT_BOULDER][PROPERTY_WALKABLE] = false;
		BOOLEAN_PROPERTIES[OBJECT_BOULDER][PROPERTY_PENETRABLE] = false;
		BOOLEAN_PROPERTIES[OBJECT_HOLE][PROPERTY_WALKABLE] = true;
		BOOLEAN_PROPERTIES[OBJECT_HOLE][PROPERTY_PENETRABLE] = true;
		BOOLEAN_PROPERTIES[OBJECT_STONE][PROPERTY_WALKABLE] = false;
		BOOLEAN_PROPERTIES[OBJECT_STONE][PROPERTY_PENETRABLE] = false;
		BOOLEAN_PROPERTIES[OBJECT_IRON_ORE][PROPERTY_WALKABLE] = false;
		BOOLEAN_PROPERTIES[OBJECT_IRON_ORE][PROPERTY_PENETRABLE] = false;
		BOOLEAN_PROPERTIES[OBJECT_COPPER_ORE][PROPERTY_WALKABLE] = false;
		BOOLEAN_PROPERTIES[OBJECT_COPPER_ORE][PROPERTY_PENETRABLE] = false;
		BOOLEAN_PROPERTIES[OBJECT_CHEST][PROPERTY_WALKABLE] = false;
		BOOLEAN_PROPERTIES[OBJECT_CHEST][PROPERTY_PENETRABLE] = false;
		BOOLEAN_PROPERTIES[OBJECT_WALL_STONE][PROPERTY_WALKABLE] = false;
		BOOLEAN_PROPERTIES[OBJECT_WALL_STONE][PROPERTY_PENETRABLE] = false;
	}

	private static final int[][] INT_PROPERTIES = new int[NUM_TILE_TYPES][1];
	
	static {
		INT_PROPERTIES[TILE_VOID][0] = Color.BLACK.getRGB();
		INT_PROPERTIES[TILE_BEDROCK][0] = Color.DARK_GRAY.getRGB();
		INT_PROPERTIES[TILE_GRASS][0] = Color.GREEN.getRGB();
		INT_PROPERTIES[TILE_MANA_PAD][0] = Color.BLUE.getRGB();
		INT_PROPERTIES[TILE_HEALTH_PAD][0] = Color.RED.getRGB();
		INT_PROPERTIES[TILE_HURT_PAD][0] = Color.DARK_GRAY.getRGB();
		INT_PROPERTIES[TILE_SPEED_PAD][0] = Color.GREEN.brighter().getRGB();
		INT_PROPERTIES[TILE_WATER][0] = Color.BLUE.getRGB();
		INT_PROPERTIES[TILE_SAND][0] = Color.YELLOW.brighter().getRGB();
		INT_PROPERTIES[TILE_DIRT][0] = new Color(179, 136, 37).getRGB();
		INT_PROPERTIES[TILE_FLOOR_WOOD][0] = new Color(179, 136, 37).getRGB();
		
		INT_PROPERTIES[OBJECT_FLOWERS][0] = Color.CYAN.getRGB();
		INT_PROPERTIES[OBJECT_TREE][0] = Color.GREEN.darker().getRGB();
		INT_PROPERTIES[OBJECT_BOULDER][0] = Color.DARK_GRAY.getRGB();
		INT_PROPERTIES[OBJECT_HOLE][0] = Color.BLACK.getRGB();
		INT_PROPERTIES[OBJECT_STONE][0] = Color.DARK_GRAY.getRGB();
		INT_PROPERTIES[OBJECT_IRON_ORE][0] = Color.GRAY.darker().getRGB();
		INT_PROPERTIES[OBJECT_COPPER_ORE][0] = Color.ORANGE.getRGB();
		INT_PROPERTIES[OBJECT_CHEST][0] = Color.ORANGE.darker().getRGB();
		INT_PROPERTIES[OBJECT_WALL_STONE][0] = Color.DARK_GRAY.getRGB();
	}
	
	/*
	 * Methods for getting tile props
	 */
	
	public static boolean isWalkable(int[] types) {
		return isWalkable(types[0], types[1]);
	}
	
	public static boolean isWalkable(int tileType, int objectType) {
		if (objectType != Tile.TILE_VOID) {
			return BOOLEAN_PROPERTIES[objectType][PROPERTY_WALKABLE];
		} else {
			return BOOLEAN_PROPERTIES[tileType][PROPERTY_WALKABLE];
		}
	}
	
	public static boolean isPenetrable(int[] types) {
		return isPenetrable(types[0], types[1]);
	}
	
	public static boolean isPenetrable(int tileType, int objectType) {
		if (objectType != Tile.TILE_VOID) {
			return BOOLEAN_PROPERTIES[objectType][PROPERTY_PENETRABLE];
		} else {
			return BOOLEAN_PROPERTIES[tileType][PROPERTY_PENETRABLE];
		}
	}
	
	public static int minimapColor(int[] types) {
		return minimapColor(types[0], types[1]);
	}
	
	public static int minimapColor(int tileType, int objectType) {
		if (objectType != Tile.TILE_VOID) {
			return INT_PROPERTIES[objectType][0];
		} else {
			return INT_PROPERTIES[tileType][0];
		}
	}
	
}