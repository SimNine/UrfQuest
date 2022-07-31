package xyz.urffer.urfquest.client.tiles;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

import xyz.urffer.urfquest.Main;
import xyz.urffer.urfquest.client.QuestPanel;
import xyz.urffer.urfquest.shared.Tile;

public class TileImages {
	public static String tileRoot = QuestPanel.assetPath + "/tiles/";
	public static String errMsg = "Could not find image: ";
	
	private static BufferedImage[] TILE_IMAGES = new BufferedImage[Tile.NUM_TILE_TYPES];
	private static BufferedImage nullImage = new BufferedImage(
			QuestPanel.TILE_WIDTH, 
			QuestPanel.TILE_WIDTH, 
			BufferedImage.TYPE_3BYTE_BGR);
	
	private static HashMap<Integer, BufferedImage> cachedCompositeTileImages = new HashMap<>();
	
	public static void initGraphics() {
		TILE_IMAGES[Tile.TILE_VOID] = nullImage;
		TILE_IMAGES[Tile.TILE_DIRT] = loadImage("dirt.png");
		TILE_IMAGES[Tile.TILE_BEDROCK] = loadImage("bedrock.png");
		TILE_IMAGES[Tile.TILE_GRASS] = loadImage("grass.png");
		TILE_IMAGES[Tile.TILE_MANA_PAD] = loadImage("manaPad.png");
		TILE_IMAGES[Tile.TILE_HEALTH_PAD] = loadImage("healthPad.png");
		TILE_IMAGES[Tile.TILE_HURT_PAD] = loadImage("hurtPad.png");
		TILE_IMAGES[Tile.TILE_SPEED_PAD] = loadImage("speedPad.png");
		TILE_IMAGES[Tile.TILE_WATER] = loadImage("water.png");
		TILE_IMAGES[Tile.TILE_SAND] = loadImage("sand.png");
		TILE_IMAGES[Tile.TILE_FLOOR_WOOD] = loadImage("floor_wood.png");
		
		TILE_IMAGES[Tile.OBJECT_FLOWERS] = loadImage("grass_flowers.png");
		TILE_IMAGES[Tile.OBJECT_TREE] = loadImage("tree_scaled.png");
		TILE_IMAGES[Tile.OBJECT_BOULDER] = loadImage("boulder_scaled_30px.png");
		TILE_IMAGES[Tile.OBJECT_HOLE] = loadImage("hole_scaled_30px.png");
		TILE_IMAGES[Tile.OBJECT_STONE] = loadImage("stone.png");
		TILE_IMAGES[Tile.OBJECT_IRON_ORE] = loadImage("ironore_scaled_30px.png");
		TILE_IMAGES[Tile.OBJECT_COPPER_ORE] = loadImage("copperore_scaled_30px.png");
		TILE_IMAGES[Tile.OBJECT_CHEST] = loadImage("chest_scaled_30px.png");
		TILE_IMAGES[Tile.OBJECT_WALL_STONE] = loadImage("wall_stone.png");
	}
	
	
	/*
	 * initialization helpers
	 */
	
	private static BufferedImage loadImage(String s) {
		try {
			return ImageIO.read(Main.self.getClass().getResourceAsStream(tileRoot + s));
		} catch (IOException | IllegalArgumentException e) {
			Main.mainLogger.error("Tile image \"" + s + "\" was unable to be loaded");
			//e.printStackTrace();
		}
		return null;
	}
	
	private static BufferedImage generateOverlayedImage(BufferedImage bottom, BufferedImage top) {
		BufferedImage out = new BufferedImage(
				QuestPanel.TILE_WIDTH, 
				QuestPanel.TILE_WIDTH, 
				BufferedImage.TYPE_3BYTE_BGR);
		Graphics2D graphics = out.createGraphics();
		graphics.drawImage(bottom, 0, 0, null);
		graphics.drawImage(top, 0, 0, null);
		return out;
	}
	
	
	/*
	 * methods for getting info on a particular tile
	 */
	
	public static BufferedImage getTileImage(int tileType, int objectType, int animStage) {
		if (tileType < 0 || objectType < 0) {
			return nullImage;
		}
		
		if (objectType == Tile.TILE_VOID) {
			return TILE_IMAGES[tileType];
		} else {
			int compositeIndex = (tileType * 1000) + objectType;
			if (!cachedCompositeTileImages.containsKey(compositeIndex)) {
				BufferedImage compositeImage = generateOverlayedImage(TILE_IMAGES[tileType], TILE_IMAGES[objectType]);
				cachedCompositeTileImages.put(compositeIndex, compositeImage);
			}
			return cachedCompositeTileImages.get(compositeIndex);
		}
	}
	
}