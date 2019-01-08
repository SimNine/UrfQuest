package tiles;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Tiles {
	private static BufferedImage grassTile;
	private static BufferedImage stoneTile;
	private static BufferedImage dirtTile;
	private static BufferedImage healthPadTile;
	private static BufferedImage manaPadTile;
	private static BufferedImage speedPadTile;
	private static BufferedImage hurtPadTile;
	private static String tileRoot = "src/tiles/";
	
	public static void initGraphics() {
		try {
			grassTile = ImageIO.read(new File(tileRoot + "grass.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Image could not be read at: " + tileRoot + "grass.png");
		}
		
		try {
			stoneTile = ImageIO.read(new File(tileRoot + "stone.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Image could not be read at: " + tileRoot + "stone.png");
		}
		
		try {
			dirtTile = ImageIO.read(new File(tileRoot + "dirt.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Image could not be read at: " + tileRoot + "dirt.png");
		}
		
		try {
			healthPadTile = ImageIO.read(new File(tileRoot + "healthPad.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Image could not be read at: " + tileRoot + "healthPad.png");
		}
		
		try {
			manaPadTile = ImageIO.read(new File(tileRoot + "manaPad.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Image could not be read at: " + tileRoot + "manaPad.png");
		}
		
		try {
			speedPadTile = ImageIO.read(new File(tileRoot + "speedPad.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Image could not be read at: " + tileRoot + "speedPad.png");
		}
		
		try {
			hurtPadTile = ImageIO.read(new File(tileRoot + "hurtPad.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Image could not be read at: " + tileRoot + "hurtPad.png");
		}
	}
	
	public static void drawGrassBlock(Graphics g, int x, int y) {
		g.drawImage(grassTile, x, y, null);
	}
	
	public static void drawStoneBlock(Graphics g, int x, int y) {
		g.drawImage(stoneTile, x, y, null);
	}
		
	public static void drawDirtBlock(Graphics g, int x, int y) {
		g.drawImage(dirtTile, x, y, null);
	}
	
	public static void drawHealthPad(Graphics g, int x, int y, int scale) {
		g.drawImage(healthPadTile, x, y, null);
	}
	
	public static void drawManaPad(Graphics g, int x, int y, int scale) {
		g.drawImage(manaPadTile, x, y, null);
	}
	
	public static void drawSpeedPad(Graphics g, int x, int y, int scale) {
		g.drawImage(speedPadTile, x, y, null);
	}
	
	public static void drawHurtPad(Graphics g, int x, int y, int scale) {
		g.drawImage(hurtPadTile, x, y, null);
	}
}