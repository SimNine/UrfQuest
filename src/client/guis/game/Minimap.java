package client.guis.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import entities.items.Item;
import entities.mobs.Mob;
import entities.mobs.Player;
import framework.UrfQuest;
import guis.Clickable;
import guis.GUIContainer;
import guis.GUIObject;

public class Minimap extends GUIObject implements Clickable {
	// the coordinates of the pixel in the upper-left corner
	private int xRoot;
	private int yRoot;
	
	// the coordinates of the block in the upper-left corner of the minimap
	// NOT the coordinates of pixel in the upper-left corner
	private int xCrop;
	private int yCrop;

	public Minimap(int xDisp, int yDisp, int width, int height, int anchorPoint, GUIContainer parent) {
		super(anchorPoint, xDisp, yDisp, width, height, parent);
	}

	public void draw(Graphics g) {
		BufferedImage minimap = UrfQuest.game.getCurrMap().getMinimap();
		Player player = UrfQuest.game.getPlayer();
		
		int borderWidth = 3;
		int gapWidth = 2;
		
		// draw background and border
		g.setColor(new Color(255, 255, 255, 128));
		g.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
		g.setColor(Color.WHITE);
		for (int i = 0; i < borderWidth; i++) {
			g.drawRect(bounds.x + i, bounds.y + i, bounds.width - i*2, bounds.height - i*2);
		}
		
		xRoot = bounds.x + borderWidth + gapWidth;
		yRoot = bounds.y + borderWidth + gapWidth;
		int width = bounds.width - 2*(borderWidth + gapWidth);
		int height = bounds.height - 2*(borderWidth + gapWidth);
		
		if (minimap.getWidth() <= width) {
			xRoot += (width - minimap.getWidth())/2;
			xCrop = 0;
			width = minimap.getWidth();
		} else {
			xCrop = (int)player.getPos()[0] - width/2;
			if (xCrop < 0) {
				xCrop = 0;
			}
			if (xCrop + width > minimap.getWidth()) {
				xCrop = minimap.getWidth() - width;
			}
		}
		
		if (minimap.getHeight() <= height) {
			yRoot += (height - minimap.getHeight())/2;
			yCrop = 0;
			height = minimap.getHeight();
		} else {
			yCrop = (int)player.getPos()[1] - height/2;
			if (yCrop < 0) {
				yCrop = 0;
			}
			if (yCrop + height > minimap.getHeight()) {
				yCrop = minimap.getHeight() - height;
			}
		}
		
		// crop the map's minimap to fit the current size
		minimap = minimap.getSubimage(xCrop, yCrop, width, height);
		g.drawImage(minimap, xRoot, yRoot, null);
		
		// draw a square for each item currently on the minimap
		g.setColor(Color.RED);
		for (Item i : UrfQuest.game.getCurrMap().getItems()) {
			if ((int)i.getPos()[0] > xCrop && (int)i.getPos()[0] < xCrop + width &&
				(int)i.getPos()[1] > yCrop && (int)i.getPos()[1] < yCrop + height) {
				g.fillRect(xRoot + ((int)i.getPos()[0]-xCrop) - 1, 
						   yRoot + ((int)i.getPos()[1]-yCrop) - 1, 3, 3);
			}
		}
		
		// draw a square for each npc currently on the minimap
		g.setColor(Color.YELLOW);
		for (Mob m : UrfQuest.game.getCurrMap().getMobs()) {
			if ((int)m.getPos()[0] > xCrop && (int)m.getPos()[0] < xCrop + width &&
				(int)m.getPos()[1] > yCrop && (int)m.getPos()[1] < yCrop + height) {
				g.fillRect(xRoot + ((int)m.getPos()[0]-xCrop) - 1, 
						   yRoot + ((int)m.getPos()[1]-yCrop) - 1, 3, 3);
			}
		}
		
		// draw a square for the player
		g.setColor(Color.BLACK);
		int playerIndX = xRoot + ((int)player.getPos()[0]-xCrop);
		int playerIndY = yRoot + ((int)player.getPos()[1]-yCrop);
		g.fillRect(playerIndX-2, playerIndY-2, 5, 5);
		
		if (UrfQuest.debug) {
			drawDebug(g);
		}
	}
	
	public boolean click() {
		if (UrfQuest.debug) {
			int xPos = UrfQuest.mousePos[0] - xRoot + xCrop;
			int yPos = UrfQuest.mousePos[1] - yRoot + yCrop;
			
			System.out.println(xPos + ", " + yPos);
			UrfQuest.game.getPlayer().setPos(xPos, yPos);
		}
		return true;
	}

	public int getSize() {
		return bounds.width;
	}
}