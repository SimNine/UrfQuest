package tiles;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import display.QuestPanel;

public class Tiles {
	private static BufferedImage grassTile;
	private static BufferedImage stoneTile;
	private static BufferedImage dirtTile;
	private static BufferedImage healthPadTile;
	private static BufferedImage manaPadTile;
	private static BufferedImage speedPadTile;
	private static BufferedImage hurtPadTile;
	private static BufferedImage treeTile;
	private static String tileRoot = "assets/tiles/";
	private static String errMsg = "Could not find image: ";
	
	private static boolean[][] tileProperties = 
		   //walkable
		{ {true},
		  {false},
		  {true},
		  {true},
		  {true},
		  {true},
		  {true},
		  {false} };
	
	public static void initGraphics() {
		try {
			grassTile = ImageIO.read(new File(tileRoot + "grass.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(errMsg + tileRoot + "grass.png");
		}
		
		try {
			stoneTile = ImageIO.read(new File(tileRoot + "stone.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(errMsg + tileRoot + "stone.png");
		}
		
		try {
			dirtTile = ImageIO.read(new File(tileRoot + "dirt.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(errMsg + tileRoot + "dirt.png");
		}
		
		try {
			healthPadTile = ImageIO.read(new File(tileRoot + "healthPad.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(errMsg + tileRoot + "healthPad.png");
		}
		
		try {
			manaPadTile = ImageIO.read(new File(tileRoot + "manaPad.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(errMsg + tileRoot + "manaPad.png");
		}
		
		try {
			speedPadTile = ImageIO.read(new File(tileRoot + "speedPad.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(errMsg + tileRoot + "speedPad.png");
		}
		
		try {
			hurtPadTile = ImageIO.read(new File(tileRoot + "hurtPad.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(errMsg + tileRoot + "hurtPad.png");
		}
		
		try {
			treeTile = ImageIO.read(new File(tileRoot + "tree_scaled.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(errMsg + tileRoot + "tree_scaled.png");
		}
	}
	
	public static void drawTile(Graphics g, int x, int y, int blockType) {
		switch (blockType) {
		case -1:
			g.setColor(Color.BLACK);
			g.fillRect(x, y, QuestPanel.TILE_WIDTH, QuestPanel.TILE_WIDTH);
			break;
		case 0:
			Tiles.drawDirtTile(g, x, y);
			break;
		case 1:
			Tiles.drawStoneTile(g, x, y);
			break;
		case 2:
			Tiles.drawGrassTile(g, x, y);
			break;
		case 3:
			Tiles.drawManaPad(g, x, y);
			break;
		case 4:
			Tiles.drawHealthPad(g, x, y);
			break;
		case 5:
			Tiles.drawHurtPad(g, x, y);
			break;
		case 6:
			Tiles.drawSpeedPad(g, x, y);
			break;
		case 7:
			Tiles.drawTreeTile(g, x, y);
			break;
		}
	}
	
	public static void drawGrassTile(Graphics g, int x, int y) {
		g.drawImage(grassTile, x, y, null);
	}
	
	public static void drawStoneTile(Graphics g, int x, int y) {
		g.drawImage(stoneTile, x, y, null);
	}
		
	public static void drawDirtTile(Graphics g, int x, int y) {
		g.drawImage(dirtTile, x, y, null);
	}
	
	public static void drawHealthPad(Graphics g, int x, int y) {
		g.drawImage(healthPadTile, x, y, null);
	}
	
	public static void drawManaPad(Graphics g, int x, int y) {
		g.drawImage(manaPadTile, x, y, null);
	}
	
	public static void drawSpeedPad(Graphics g, int x, int y) {
		g.drawImage(speedPadTile, x, y, null);
	}
	
	public static void drawHurtPad(Graphics g, int x, int y) {
		g.drawImage(hurtPadTile, x, y, null);
	}
	
	public static void drawTreeTile(Graphics g, int x, int y) {
		g.drawImage(grassTile, x, y, null);
		g.drawImage(treeTile, x+1, y+1, null);
	}
	
	// gets properties of tiles from boolean array
	public static boolean isWalkable(int t) {
		return tileProperties[t][0];
	}
}