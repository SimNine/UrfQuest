package tiles;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import framework.QuestPanel;
import framework.UrfQuest;

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
	private static BufferedImage[][][] tileImage = new BufferedImage[14][4][3];
	private static BufferedImage nullImage = new BufferedImage(
			QuestPanel.TILE_WIDTH, 
			QuestPanel.TILE_WIDTH, 
			BufferedImage.TYPE_3BYTE_BGR);
	
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
	
	private static int[][] tileIntProperties = 
		   //minimapColor
		{ {new Color(179, 136, 37).getRGB()},//0
		  {Color.LIGHT_GRAY.getRGB()},
		  {Color.GREEN.darker().getRGB()},//2
		  {Color.BLUE.getRGB()},
		  {Color.RED.getRGB()},//4
		  {Color.DARK_GRAY.getRGB()},
		  {Color.GREEN.getRGB()},//6
		  {Color.GREEN.darker().darker().getRGB()},
		  {Color.BLUE.getRGB()},//8
		  {Color.YELLOW.brighter().getRGB()},
		  {Color.GRAY.getRGB()},//10
		  {Color.BLACK.getRGB()},
		  {Color.GRAY.getRGB()},//12
		  {Color.ORANGE.getRGB()} };
	
	public static void initGraphics() {
		tileImage[0][0][0] = loadImage("dirt.png");
		tileImage[1][0][0] = loadImage("bedrock.png");
		tileImage[2][0][0] = loadImage("grass.png");
			tileImage[2][1][0] = loadImage("grass_flowers.png");
			tileImage[2][2][0] = loadImage("grass_snow.png");
		tileImage[3][0][0] = loadImage("manaPad.png");
		tileImage[4][0][0] = loadImage("healthPad.png");
		tileImage[5][0][0] = loadImage("hurtPad.png");
		tileImage[6][0][0] = loadImage("speedPad.png");
		tileImage[7][0][0] = loadOverlayedImage("tree_scaled.png", "grass.png");
			tileImage[7][1][0] = loadOverlayedImage("tree_scaled_snow.png", "grass_snow.png");
		tileImage[8][0][0] = loadImage("water.png");
			tileImage[8][0][1] = loadImage("water_2.png");
			tileImage[8][0][2] = loadImage("water_3.png");
		tileImage[9][0][0] = loadImage("sand.png");
		tileImage[10][0][0] = loadOverlayedImage("boulder_scaled_30px.png", "grass.png");
			tileImage[10][1][0] = loadOverlayedImage("boulder_scaled_30px.png", "water.png");
			tileImage[10][2][0] = loadOverlayedImage("boulder_scaled_30px.png", "sand.png");
			tileImage[10][3][0] = loadOverlayedImage("boulder_scaled_30px.png", "dirt.png");
		tileImage[11][0][0] = loadOverlayedImage("hole_scaled_30px.png", "dirt.png");
		tileImage[12][0][0] = loadImage("stone.png");
			tileImage[12][1][0] = loadOverlayedImage("ironore_scaled_30px.png", "stone.png");
			tileImage[12][2][0] = loadOverlayedImage("copperore_scaled_30px.png", "stone.png");
		tileImage[13][0][0] = loadImage("chest_scaled_30px.png");
	}
	
	/*
	 * initialization helpers
	 */
	
	private static BufferedImage loadImage(String s) {
		try {
			return ImageIO.read(UrfQuest.quest.getClass().getResourceAsStream(tileRoot + s));
		} catch (IOException e) {
			System.out.println("this was unable to be loaded: " + s);
			e.printStackTrace();
		}
		return null;
	}
	
	private static BufferedImage loadOverlayedImage(String top, String bottom) {
		BufferedImage topImg = loadImage(top);
		BufferedImage bottomImg = loadImage(bottom);
		bottomImg.createGraphics().drawImage(topImg, 0, 0, null);
		return bottomImg;
	}
	
	/*
	 * methods for getting info on a particular tile
	 */
	
	public static BufferedImage getTileImage(int type, int subtype, int animStage) {
		if (type == -1) {
			return nullImage;
		}
		return tileImage[type][subtype][animStage];
	}
	
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
	
	// gets the color of the pixel representing this tile to be displayed on the minimap
	public static int minimapColor(int t) {
		if (t == -1) return Color.BLACK.getRGB();
		else return tileIntProperties[t][0];
	}
}