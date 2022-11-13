package xyz.urffer.urfquest.client.guis.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import xyz.urffer.urfquest.Logger;
import xyz.urffer.urfquest.client.Client;
import xyz.urffer.urfquest.client.entities.items.ItemStack;
import xyz.urffer.urfquest.client.entities.mobs.Mob;
import xyz.urffer.urfquest.client.entities.mobs.Player;
import xyz.urffer.urfquest.client.guis.Clickable;
import xyz.urffer.urfquest.client.guis.GUIAnchor;
import xyz.urffer.urfquest.client.guis.GUIContainer;
import xyz.urffer.urfquest.client.guis.GUIObject;
import xyz.urffer.urfquest.client.map.Map;
import xyz.urffer.urfquest.shared.ArrayUtils;
import xyz.urffer.urfquest.shared.Constants;
import xyz.urffer.urfquest.shared.PairDouble;
import xyz.urffer.urfquest.shared.PairInt;

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
		PairInt mapOrigin = currentMap.getLocalChunkOrigin().multiply(Constants.MAP_CHUNK_SIZE);
		Rectangle mapBounds = new Rectangle(
				mapOrigin.x,
				mapOrigin.y,
				currentMap.getMapDiameter() * Constants.MAP_CHUNK_SIZE,
				currentMap.getMapDiameter() * Constants.MAP_CHUNK_SIZE
		);
		int[] mapCenter = ArrayUtils.castToIntArr(new double[] {mapBounds.getCenterX(), mapBounds.getCenterY()});
		BufferedImage minimap = this.client.getState().getCurrentMap().getMinimap();
		Player player = this.client.getState().getPlayer();
		PairInt playerPos = player.getCenter().toInt();
		
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
		PairInt minimapOrigin = new PairInt(minimapBounds.x, minimapBounds.y);
		PairInt minimapCenter = new PairDouble(
			minimapBounds.getCenterX(),
			minimapBounds.getCenterY()
		).toInt();
		g.setColor(Color.BLACK);
		g.fillRect(minimapBounds.x, minimapBounds.y, 
				   minimapBounds.width, minimapBounds.height);
		
		// calculate minimapRoot: position to draw the minimap at in screen space
		PairInt dispPlayerFromMapTopLeft = playerPos.subtract(mapOrigin);
		PairInt minimapRoot = minimapCenter.subtract(dispPlayerFromMapTopLeft);
		
		// crop minimap such that it does not overflow the minimap bounds
		if (minimapRoot.x < minimapOrigin.x) { // crop left side
			int leftSideOverflow = minimapOrigin.x - minimapRoot.x;
			minimap = minimap.getSubimage(
					leftSideOverflow, 
					0, 
					minimap.getWidth() - leftSideOverflow, 
					minimap.getHeight()
			);
			minimapRoot.x = minimapOrigin.x;
		}
		if (minimapRoot.y < minimapOrigin.y) { // crop top side
			int topSideOverflow = minimapOrigin.y - minimapRoot.y;
			minimap = minimap.getSubimage( 
					0,
					topSideOverflow,
					minimap.getWidth(), 
					minimap.getHeight() - topSideOverflow
			);
			minimapRoot.y = minimapOrigin.y;
		}
		// crop right and bottom sides
		int rightSideOverflow = (minimapRoot.x + minimap.getWidth()) - (minimapBounds.x + minimapBounds.width);
		int bottomSideOverflow = (minimapRoot.y + minimap.getHeight()) - (minimapBounds.y + minimapBounds.height);
		minimap = minimap.getSubimage(
				0, 
				0, 
				minimap.getWidth() - ((rightSideOverflow > 0) ? rightSideOverflow : 0), 
				minimap.getHeight() - ((bottomSideOverflow > 0) ? bottomSideOverflow : 0)
		);
		
		// draw minimap such that it is centered on the player
		g.drawImage(minimap, minimapRoot.x, minimapRoot.y, null);
		
		// draw a square for the player
		g.setColor(Color.BLACK);
		g.fillRect(minimapCenter.x-2, minimapCenter.y-2, 5, 5);

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
//		if (this.client.getLogger().getLogLevel().compareTo(LogLevel.LOG_DEBUG) >= 0) {
//			drawDebug(g);
//		}
	}
	
	public boolean click() {
//		int xPos = Main.panel.mousePos[0] - xRoot + xCrop;
//		int yPos = Main.panel.mousePos[1] - yRoot + yCrop;
//		Main.logger.debug(xPos + ", " + yPos);
//		
//		if (Main.logger.getLogLevel().compareTo(LogLevel.LOG_DEBUG) >= 0) {
//			Main.client.getState().getPlayer().setPos(xPos, yPos);
//		}
		return true;
	}

	public int getSize() {
		return bounds.width;
	}
}
