package tiles;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import framework.QuestPanel;
import framework.UrfQuest;

public class Tiles {
	public static String tileRoot = "/asset/tiles/";
	public static String errMsg = "Could not find image: ";
	
	public static final int VOID = -1;
	public static final int DIRT = 0;
	public static final int BEDROCK = 1;
	public static final int GRASS = 2;
	public static final int MANA_PAD = 3;
	public static final int HEALTH_PAD = 4;
	public static final int HURT_PAD = 5;
	public static final int SPEED_PAD = 6;
	public static final int TREE = 7;
	public static final int WATER = 8;
	public static final int SAND = 9;
	public static final int GRASS_BOULDER = 10;
	public static final int WATER_BOULDER = 11;
	public static final int SAND_BOULDER = 12;
	public static final int DIRT_HOLE = 13;
	public static final int DIRT_BOULDER = 14;
	public static final int STONE = 15;
	public static final int IRONORE_STONE = 16;
	public static final int COPPERORE_STONE = 17;
	
	private static BufferedImage[] tileImages = new BufferedImage[18];
	private static BufferedImage[] waterImages = new BufferedImage[3];
	
	private static boolean[][] tileBooleanProperties = 
		   //walkable	//penetrable	//animated
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
		  {false,		false},//10
		  {false,		false},
		  {false,		false},//12
		  {true,		true},
		  {false,		false},//14
		  {false,		false},
		  {false,		false},//16
		  {false,		false}};
	
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
		  {Color.GRAY.getRGB()},
		  {Color.GRAY.getRGB()},//12
		  {Color.BLACK.getRGB()},
		  {new Color(107, 40, 7).getRGB()},//14
		  {Color.LIGHT_GRAY.getRGB()},
		  {Color.LIGHT_GRAY.darker().getRGB()},//16
		  {Color.LIGHT_GRAY.darker().getRGB()} };
	
	public static void initGraphics() {
		try {
			tileImages[0] = ImageIO.read(UrfQuest.quest.getClass().getResourceAsStream(tileRoot + "dirt.png"));
			tileImages[1] = ImageIO.read(UrfQuest.quest.getClass().getResourceAsStream(tileRoot + "bedrock.png"));
			tileImages[2] = ImageIO.read(UrfQuest.quest.getClass().getResourceAsStream(tileRoot + "grass.png"));
			tileImages[3] = ImageIO.read(UrfQuest.quest.getClass().getResourceAsStream(tileRoot + "manaPad.png"));
			tileImages[4] = ImageIO.read(UrfQuest.quest.getClass().getResourceAsStream(tileRoot + "healthPad.png"));
			tileImages[5] = ImageIO.read(UrfQuest.quest.getClass().getResourceAsStream(tileRoot + "hurtPad.png"));
			tileImages[6] = ImageIO.read(UrfQuest.quest.getClass().getResourceAsStream(tileRoot + "speedPad.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			// here, the tree sprite is drawn onto the grass sprite, and that becomes the new tile
			BufferedImage top = ImageIO.read(UrfQuest.quest.getClass().getResourceAsStream(tileRoot + "tree_scaled.png"));
			BufferedImage bottom = ImageIO.read(UrfQuest.quest.getClass().getResourceAsStream(tileRoot + "grass.png"));
			bottom.createGraphics().drawImage(top, 0, 0, null);
			tileImages[7] = bottom;
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(errMsg + tileRoot + "tree_scaled.png");
		}
		
		try {
			tileImages[8] = ImageIO.read(UrfQuest.quest.getClass().getResourceAsStream(tileRoot + "water.png"));
			waterImages[0] = tileImages[8];
			waterImages[1] = ImageIO.read(UrfQuest.quest.getClass().getResourceAsStream(tileRoot + "water_2.png"));
			waterImages[2] = ImageIO.read(UrfQuest.quest.getClass().getResourceAsStream(tileRoot + "water_3.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(errMsg + tileRoot + "water.png");
		}
		
		try {
			tileImages[9] = ImageIO.read(UrfQuest.quest.getClass().getResourceAsStream(tileRoot + "sand.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(errMsg + tileRoot + "sand.png");
		}
		
		try {
			// here, the boulder sprite is drawn onto the grass sprite, and that becomes the new tile
			BufferedImage top = ImageIO.read(UrfQuest.quest.getClass().getResourceAsStream(tileRoot + "boulder_scaled_30px.png"));
			BufferedImage bottom = ImageIO.read(UrfQuest.quest.getClass().getResourceAsStream(tileRoot + "grass.png"));
			bottom.createGraphics().drawImage(top, 0, 0, null);
			tileImages[10] = bottom;
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(errMsg + tileRoot + "boulder_scaled_30px.png");
			System.out.println(errMsg + tileRoot + "grass.png");
		}
		
		try {
			// here, the boulder sprite is drawn onto the sand sprite, and that becomes the new tile
			BufferedImage top = ImageIO.read(UrfQuest.quest.getClass().getResourceAsStream(tileRoot + "boulder_scaled_30px.png"));
			BufferedImage bottom = ImageIO.read(UrfQuest.quest.getClass().getResourceAsStream(tileRoot + "sand.png"));
			bottom.createGraphics().drawImage(top, 0, 0, null);
			tileImages[12] = bottom;
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(errMsg + tileRoot + "boulder_scaled_30px.png");
			System.out.println(errMsg + tileRoot + "sand.png");
		}
		
		try {
			// here, the boulder sprite is drawn onto the water sprite, and that becomes the new tile
			BufferedImage top = ImageIO.read(UrfQuest.quest.getClass().getResourceAsStream(tileRoot + "boulder_scaled_30px.png"));
			BufferedImage bottom = ImageIO.read(UrfQuest.quest.getClass().getResourceAsStream(tileRoot + "water.png"));
			bottom.createGraphics().drawImage(top, 0, 0, null);
			tileImages[11] = bottom;
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(errMsg + tileRoot + "boulder_scaled_30px.png");
			System.out.println(errMsg + tileRoot + "water.png");
		}
		
		try {
			// here, the hole sprite is drawn onto the dirt sprite, and that becomes the new tile
			BufferedImage top = ImageIO.read(UrfQuest.quest.getClass().getResourceAsStream(tileRoot + "hole_scaled_30px.png"));
			BufferedImage bottom = ImageIO.read(UrfQuest.quest.getClass().getResourceAsStream(tileRoot + "dirt.png"));
			bottom.createGraphics().drawImage(top, 0, 0, null);
			tileImages[13] = bottom;
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(errMsg + tileRoot + "hole_scaled_30px.png");
			System.out.println(errMsg + tileRoot + "dirt.png");
		}
		
		try {
			// here, the boulder sprite is drawn onto the dirt sprite, and that becomes the new tile
			BufferedImage top = ImageIO.read(UrfQuest.quest.getClass().getResourceAsStream(tileRoot + "boulder_scaled_30px.png"));
			BufferedImage bottom = ImageIO.read(UrfQuest.quest.getClass().getResourceAsStream(tileRoot + "dirt.png"));
			bottom.createGraphics().drawImage(top, 0, 0, null);
			tileImages[14] = bottom;
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(errMsg + tileRoot + "boulder_scaled_30px.png");
			System.out.println(errMsg + tileRoot + "dirt.png");
		}
		
		try {
			tileImages[15] = ImageIO.read(UrfQuest.quest.getClass().getResourceAsStream(tileRoot + "stone.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(errMsg + tileRoot + "stone.png");
		}
		
		try {
			// here, the ore sprite is drawn onto the stone sprite, and that becomes the new tile
			BufferedImage top = ImageIO.read(UrfQuest.quest.getClass().getResourceAsStream(tileRoot + "ironore_scaled_30px.png"));
			BufferedImage bottom = ImageIO.read(UrfQuest.quest.getClass().getResourceAsStream(tileRoot + "stone.png"));
			bottom.createGraphics().drawImage(top, 0, 0, null);
			tileImages[16] = bottom;
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(errMsg + tileRoot + "ironore_scaled_30px.png");
			System.out.println(errMsg + tileRoot + "stone.png");
		}
		
		try {
			// here, the ore sprite is drawn onto the stone sprite, and that becomes the new tile
			BufferedImage top = ImageIO.read(UrfQuest.quest.getClass().getResourceAsStream(tileRoot + "copperore_scaled_30px.png"));
			BufferedImage bottom = ImageIO.read(UrfQuest.quest.getClass().getResourceAsStream(tileRoot + "stone.png"));
			bottom.createGraphics().drawImage(top, 0, 0, null);
			tileImages[17] = bottom;
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(errMsg + tileRoot + "copperore_scaled_30px.png");
			System.out.println(errMsg + tileRoot + "stone.png");
		}
	}
	
	// draws a tile with a specified ID
	public static void drawTile(Graphics g, int x, int y, int blockType, int stage) {
		if (blockType == -1) {
			g.setColor(Color.BLACK);
			g.fillRect(x, y, QuestPanel.TILE_WIDTH, QuestPanel.TILE_WIDTH);
		} else if (blockType == 8) {
			g.drawImage(waterImages[(stage/30) % 3], x, y, null);
		} else {
			g.drawImage(tileImages[blockType], x, y, null);
		}
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