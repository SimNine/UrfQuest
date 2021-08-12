package urfquest.server.tiles;

public class Tiles {
	public static String tileRoot = "/assets/tiles/";
	public static String errMsg = "Could not find image: ";
	
	public static final int VOID = -1;
	public static final int DIRT = 0;
	public static final int BEDROCK = 1;
	public static final int GRASS = 2;
		public static final int GRASS_DEF = 0;
		public static final int GRASS_FLOWERS = 1;
		public static final int GRASS_SNOW = 2;
	public static final int MANA_PAD = 3;
	public static final int HEALTH_PAD = 4;
	public static final int HURT_PAD = 5;
	public static final int SPEED_PAD = 6;
	public static final int TREE = 7;
		public static final int TREE_DEF = 0;
		public static final int TREE_SNOW = 1;
	public static final int WATER = 8;
	public static final int SAND = 9;
	public static final int BOULDER = 10;
		public static final int GRASS_BOULDER = 0;
		public static final int WATER_BOULDER = 1;
		public static final int SAND_BOULDER = 2;
		public static final int DIRT_BOULDER = 3;
	public static final int DIRT_HOLE = 11;
	public static final int STONE = 12;
		public static final int STONE_DEF = 0;
		public static final int IRONORE_STONE = 1;
		public static final int COPPERORE_STONE = 2;
	public static final int CHEST = 13;
	
	/*
	 * given indices [x][y][z],
	 * x = tile type
	 * y = tile subtype
	 * z = animation stage
	 * 
	 * if a tile type doesn't have any subtypes or animstages, they are index 0
	 */
	
	private static boolean[][] tileBooleanProperties = 
		   //walkable	//penetrable
		{ {true,		true},//0
		  {false,		false},
		  {true,		true},//2
		  {true,		true},
		  {true,		true},//4
		  {true,		true},
		  {true,		true},//6
		  {false,		false},
		  {false,		true},//8
		  {true,		true},
		  {false,		true},//10
		  {true,		true},
		  {false,		false},//12
		  {false,		true}};
	
	/*
	 * methods for getting info on a particular tile
	 */
	
	// returns whether mobs can walk on this tile
	public static boolean isWalkable(int t) {
		if (t == -1) return false; // if this tile is blank
		else return tileBooleanProperties[t][0];
	}
	
	// returns whether particles can pass through this tile
	public static boolean isPenetrable(int t) {
		if (t == -1) return false; // if this tile is blank
		else return tileBooleanProperties[t][1];
	}
	
}