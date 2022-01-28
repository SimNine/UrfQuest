package urfquest.client.guis.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import urfquest.Logger;
import urfquest.client.Client;
import urfquest.client.entities.items.Item;
import urfquest.client.entities.mobs.Mob;
import urfquest.client.entities.mobs.Player;
import urfquest.client.guis.Clickable;
import urfquest.client.guis.GUIAnchor;
import urfquest.client.guis.GUIContainer;
import urfquest.client.guis.GUIObject;
import urfquest.client.map.Map;
import urfquest.shared.ArrayUtils;
import urfquest.shared.Constants;

public class Minimap extends GUIObject implements Clickable {
	// the coordinates of the pixel in the upper-left corner
	private int xRoot;
	private int yRoot;
	
	// the coordinates of the tile in the upper-left corner of the minimap
	// NOT the coordinates of pixel in the upper-left corner
	private int xCrop;
	private int yCrop;

	public Minimap(Client c, int xDisp, int yDisp, int width, int height, GUIAnchor anchorPoint, GUIContainer parent) {
		super(c, anchorPoint, xDisp, yDisp, width, height, parent);
	}

	public void draw(Graphics g) {
		// get references to important objects
		Map currentMap = this.client.getState().getCurrentMap();
		int[] mapOrigin = ArrayUtils.multiply(currentMap.getLocalChunkOrigin(), Constants.MAP_CHUNK_SIZE);
		Rectangle mapBounds = new Rectangle(
				mapOrigin[0],
				mapOrigin[1],
				currentMap.getMapDiameter() * Constants.MAP_CHUNK_SIZE,
				currentMap.getMapDiameter() * Constants.MAP_CHUNK_SIZE
		);
		int[] mapCenter = ArrayUtils.castToIntArr(new double[] {mapBounds.getCenterX(), mapBounds.getCenterY()});
		BufferedImage minimap = this.client.getState().getCurrentMap().getMinimap();
		Player player = this.client.getState().getPlayer();
		int[] playerPos = ArrayUtils.castToIntArr(player.getCenter());
		
		// draw background and border
		int borderWidth = 3;
		int gapWidth = 2;
		g.setColor(new Color(255, 255, 255, 128));
		g.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
		g.setColor(Color.WHITE);
		for (int i = 0; i < borderWidth; i++) {
			g.drawRect(bounds.x + i, bounds.y + i, bounds.width - i*2, bounds.height - i*2);
		}
		
		// compute the area that the map may be drawn in and give it a black background
		Rectangle minimapBounds = new Rectangle(
				bounds.x + borderWidth + gapWidth,
				bounds.y + borderWidth + gapWidth,
				bounds.width - 2*(borderWidth + gapWidth),
				bounds.height - 2*(borderWidth + gapWidth)
		);
		int[] minimapOrigin = {minimapBounds.x, minimapBounds.y};
		int[] minimapCenter = ArrayUtils.castToIntArr(
				new double[]{minimapBounds.getCenterX(),
							 minimapBounds.getCenterY()}
		);
		g.setColor(Color.BLACK);
		g.fillRect(minimapBounds.x, minimapBounds.y, 
				   minimapBounds.width, minimapBounds.height);
		
		// calculate minimapRoot: position to draw the minimap at in screen space
		int[] dispPlayerFromMapTopLeft = ArrayUtils.subtract(
				playerPos, 
				mapOrigin
		);
		int[] minimapRoot = ArrayUtils.subtract(minimapCenter, dispPlayerFromMapTopLeft);
		
		// crop minimap such that it does not overflow the minimap bounds
		if (minimapRoot[0] < minimapOrigin[0]) { // crop left side
			int leftSideOverflow = minimapOrigin[0] - minimapRoot[0];
			minimap = minimap.getSubimage(
					leftSideOverflow, 
					0, 
					minimap.getWidth() - leftSideOverflow, 
					minimap.getHeight()
			);
			minimapRoot[0] = minimapOrigin[0];
		}
		if (minimapRoot[1] < minimapOrigin[1]) { // crop top side
			int topSideOverflow = minimapOrigin[1] - minimapRoot[1];
			minimap = minimap.getSubimage( 
					0,
					topSideOverflow,
					minimap.getWidth(), 
					minimap.getHeight() - topSideOverflow
			);
			minimapRoot[1] = minimapOrigin[1];
		}
		// crop right and bottom sides
		int rightSideOverflow = (minimapRoot[0] + minimap.getWidth()) - (minimapBounds.x + minimapBounds.width);
		int bottomSideOverflow = (minimapRoot[1] + minimap.getHeight()) - (minimapBounds.y + minimapBounds.height);
		minimap = minimap.getSubimage(
				0, 
				0, 
				minimap.getWidth() - ((rightSideOverflow > 0) ? rightSideOverflow : 0), 
				minimap.getHeight() - ((bottomSideOverflow > 0) ? bottomSideOverflow : 0)
		);
		
		// draw minimap such that it is centered on the player
		g.drawImage(minimap, minimapRoot[0], minimapRoot[1], null);
		
		// draw a square for the player
		g.setColor(Color.BLACK);
		g.fillRect(minimapCenter[0]-2, minimapCenter[1]-2, 5, 5);

//		// draw a square for each item currently on the minimap
//		g.setColor(Color.RED);
//		for (Item i : this.client.getState().getCurrentMap().getItems().values()) {
//			if ((int)i.getPos()[0] > xCrop && (int)i.getPos()[0] < xCrop + width &&
//				(int)i.getPos()[1] > yCrop && (int)i.getPos()[1] < yCrop + height) {
//				g.fillRect(xRoot + ((int)i.getPos()[0]-xCrop) - 1, 
//						   yRoot + ((int)i.getPos()[1]-yCrop) - 1, 3, 3);
//			}
//		}
//		
//		// draw a square for each npc currently on the minimap
//		g.setColor(Color.YELLOW);
//		for (Mob m : this.client.getState().getCurrentMap().getMobs().values()) {
//			if ((int)m.getPos()[0] > xCrop && (int)m.getPos()[0] < xCrop + width &&
//				(int)m.getPos()[1] > yCrop && (int)m.getPos()[1] < yCrop + height) {
//				g.fillRect(xRoot + ((int)m.getPos()[0]-xCrop) - 1, 
//						   yRoot + ((int)m.getPos()[1]-yCrop) - 1, 3, 3);
//			}
//		}
//		
//		if (this.client.getLogger().getLogLevel().compareTo(Logger.LogLevel.LOG_DEBUG) >= 0) {
//			drawDebug(g);
//		}
	}
	
	public boolean click() {
//		int xPos = Main.panel.mousePos[0] - xRoot + xCrop;
//		int yPos = Main.panel.mousePos[1] - yRoot + yCrop;
//		Main.logger.debug(xPos + ", " + yPos);
//		
//		if (Main.logger.getLogLevel().compareTo(Logger.LogLevel.LOG_DEBUG) >= 0) {
//			Main.client.getState().getPlayer().setPos(xPos, yPos);
//		}
		return true;
	}

	public int getSize() {
		return bounds.width;
	}
}
