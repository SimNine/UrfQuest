package tiles;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import framework.QuestPanel;
import framework.UrfQuest;

public class Tiles {
	private static String tileRoot = "/assets/tiles/";
	private static String errMsg = "Could not find image: ";
	
	private static BufferedImage[] tileImages = new BufferedImage[9];
	
	private static boolean[][] tileBooleanProperties = 
		   //walkable	//penetrable
		{ {true,		true},//0
		  {false,		false},
		  {true,		true},
		  {true,		true},
		  {true,		true},//4
		  {true,		true},
		  {true,		true},
		  {false,		false},
		  {false,		true} };
	
	private static int[][] tileIntProperties = 
		   //minimapColor
		{ {new Color(179, 136, 37).getRGB()},//0
		  {Color.LIGHT_GRAY.getRGB()},
		  {Color.GREEN.darker().getRGB()},
		  {Color.BLUE.getRGB()},
		  {Color.RED.getRGB()},//4
		  {Color.DARK_GRAY.getRGB()},
		  {Color.GREEN.getRGB()},
		  {Color.GREEN.darker().darker().getRGB()},
		  {Color.BLUE.getRGB()} };
	
	public static void initGraphics() {
		try {
			tileImages[0] = ImageIO.read(UrfQuest.quest.getClass().getResourceAsStream(tileRoot + "dirt.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(errMsg + tileRoot + "dirt.png");
		}
		
		try {
			tileImages[2] = ImageIO.read(UrfQuest.quest.getClass().getResourceAsStream(tileRoot + "grass.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(errMsg + tileRoot + "grass.png");
		}
		
		try {
			tileImages[1] = ImageIO.read(UrfQuest.quest.getClass().getResourceAsStream(tileRoot + "stone.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(errMsg + tileRoot + "stone.png");
		}
		
		try {
			tileImages[4] = ImageIO.read(UrfQuest.quest.getClass().getResourceAsStream(tileRoot + "healthPad.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(errMsg + tileRoot + "healthPad.png");
		}
		
		try {
			tileImages[3] = ImageIO.read(UrfQuest.quest.getClass().getResourceAsStream(tileRoot + "manaPad.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(errMsg + tileRoot + "manaPad.png");
		}
		
		try {
			tileImages[6] = ImageIO.read(UrfQuest.quest.getClass().getResourceAsStream(tileRoot + "speedPad.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(errMsg + tileRoot + "speedPad.png");
		}
		
		try {
			tileImages[5] = ImageIO.read(UrfQuest.quest.getClass().getResourceAsStream(tileRoot + "hurtPad.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(errMsg + tileRoot + "hurtPad.png");
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
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(errMsg + tileRoot + "water.png");
		}
	}
	
	// draws a tile with a specified ID
	public static void drawTile(Graphics g, int x, int y, int blockType) {
		if (blockType == -1) {
			g.setColor(Color.BLACK);
			g.fillRect(x, y, QuestPanel.TILE_WIDTH, QuestPanel.TILE_WIDTH);
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