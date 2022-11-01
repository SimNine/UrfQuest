package xyz.urffer.urfquest.client.tiles;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import xyz.urffer.urfquest.client.QuestPanel;
import xyz.urffer.urfquest.shared.Tile;
import xyz.urffer.urfquest.shared.protocol.types.ObjectType;
import xyz.urffer.urfquest.shared.protocol.types.TileType;

public class TileImages {
	
	private static HashMap<Integer, BufferedImage> cachedCompositeTileImages = new HashMap<>();
	
	
	/*
	 * initialization helpers
	 */
	
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
	
	public static BufferedImage getTileImage(Tile tile, int animStage) {
		if (tile.objectType == ObjectType.VOID) {
			return tile.tileType.getImage();
		} else {
			int compositeIndex = (tile.tileType.value * TileType.values().length) + tile.objectType.value;
			if (!cachedCompositeTileImages.containsKey(compositeIndex)) {
				BufferedImage compositeImage = generateOverlayedImage(tile.tileType.getImage(), tile.objectType.getImage());
				cachedCompositeTileImages.put(compositeIndex, compositeImage);
			}
			return cachedCompositeTileImages.get(compositeIndex);
		}
	}
	
}