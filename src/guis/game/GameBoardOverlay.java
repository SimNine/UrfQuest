package guis.game;

import java.awt.Color;
import java.awt.Graphics;

import entities.items.Item;
import entities.mobs.Mob;
import entities.mobs.Player;
import entities.particles.Particle;
import framework.QuestPanel;
import framework.UrfQuest;
import game.QuestMap;
import guis.Overlay;
import tiles.Tiles;

public class GameBoardOverlay extends Overlay {
	private int selectedTileTransparency = 255;
	private boolean transparencyIncreasing = false;

	public GameBoardOverlay() {
		super("board");
	}

	public void draw(Graphics g) {
		drawBoard(g);
		drawEntities(g);
		if (UrfQuest.debug) {
			drawDebugText(g);
			drawCrosshair(g);
		}
	}
	
	private void drawBoard(Graphics g) {
		int dispTileWidth = UrfQuest.panel.dispTileWidth;
		int dispTileHeight = UrfQuest.panel.dispTileHeight;
		int dispCenterX = UrfQuest.panel.dispCenterX;
		int dispCenterY = UrfQuest.panel.dispCenterY;
		int TILE_WIDTH = QuestPanel.TILE_WIDTH;
		Player player = UrfQuest.game.getPlayer();
		QuestMap currMap = UrfQuest.game.getCurrMap();

		// get the rendering offset
		int rootX = (int)(TILE_WIDTH - (dispCenterX % TILE_WIDTH));
		int rootY = (int)(TILE_WIDTH - (dispCenterY % TILE_WIDTH));
		rootX += (player.getPos()[0] % 1)*TILE_WIDTH;
		rootY += (player.getPos()[1] % 1)*TILE_WIDTH;
		
		// get the block coordinate of the upper-left corner
		int ulX = ((int)player.getPos()[0] - dispTileWidth/2) - 1;
		int ulY = ((int)player.getPos()[1] - dispTileHeight/2) - 1;
		
		// find which tile the mouse is over
		int mouseX = (int) UrfQuest.panel.windowToGameX(UrfQuest.mousePos[0]);
		int mouseY = (int) UrfQuest.panel.windowToGameY(UrfQuest.mousePos[1]);
		
		// draw grid tiles
		for (int x = 0; x < dispTileWidth + 2; x++) {
			int xRoot = - rootX + x * TILE_WIDTH;
			for (int y = 0; y < dispTileHeight + 2; y++) {
				int yRoot = - rootY + y * TILE_WIDTH;
				int currTile = (currMap.getTileAt(ulX + x, ulY + y));
				Tiles.drawTile(g, xRoot, yRoot, currTile);
			}
		}

		// change the transparency of the selected tile
		if (transparencyIncreasing) {
			if (selectedTileTransparency < 255) {
				selectedTileTransparency += 2;
			}
			if (selectedTileTransparency > 255) {
				selectedTileTransparency = 255;
				transparencyIncreasing = false;
			}
		} else {
			if (selectedTileTransparency > 0) {
				selectedTileTransparency -= 2;
			}
			if (selectedTileTransparency < 0) {
				selectedTileTransparency = 0;
				transparencyIncreasing = true;
			}
		}
		
		// draw the highlight of the selected tile
		if (UrfQuest.game.isBuildMode() && !UrfQuest.panel.isGUIOpen()) {
			int xRoot = - rootX + (mouseX - ulX)*TILE_WIDTH;
			int yRoot = - rootY + (mouseY - ulY)*TILE_WIDTH;
			g.setColor(new Color(255, 255, 255, selectedTileTransparency));
			g.fillRect(xRoot, yRoot, TILE_WIDTH, TILE_WIDTH);
			g.setColor(Color.WHITE);
			for (int i = 0; i < 3; i++) {
				g.drawRect(xRoot + i, yRoot + i, TILE_WIDTH - i*2 - 1, TILE_WIDTH - i*2 - 1);
			}
		}
		
		// when debugging, draw the grid itself
		if (UrfQuest.debug) {
			g.setColor(Color.BLACK);
			for (int x = 0; x < dispTileWidth + 2; x++) {
				g.drawLine(-rootX + x*TILE_WIDTH, 0, -rootX + x*TILE_WIDTH, UrfQuest.panel.getHeight());
			}

			for (int y = 0; y < dispTileHeight + 2; y++) {
				g.drawLine(0, -rootY + y * TILE_WIDTH, UrfQuest.panel.getWidth(), -rootY + y * TILE_WIDTH);
			}
		}
	}
	
	public boolean click() {
		if (UrfQuest.game.isBuildMode()) {
			int mouseX = (int) UrfQuest.panel.windowToGameX(UrfQuest.mousePos[0]);
			int mouseY = (int) UrfQuest.panel.windowToGameY(UrfQuest.mousePos[1]);
			
			UrfQuest.game.getCurrMap().setTileAt(mouseX, mouseY, 2);
			return true;
		} else {
			return false;
		}
	}

	private void drawDebugText(Graphics g) {
		Player player = UrfQuest.game.getPlayer();
		QuestMap currMap = UrfQuest.game.getCurrMap();
		
		g.setColor(new Color(128, 128, 128, 128));
		g.fillRect(0, 0, 600, 150);

		g.setColor(Color.WHITE);
		g.drawString(UrfQuest.keys.toString(), 10, 10);
		g.drawString("PlayerUpperLeftCoords: " + player.getPos()[0] + ", " + player.getPos()[1], 10, 20);
		g.drawString("PlayerCenterCoords: " + player.getCenter()[0] + ", " + player.getCenter()[1], 10, 30);
		g.drawString("DisplayCenter: " + UrfQuest.panel.dispCenterX + ", " + UrfQuest.panel.dispCenterY, 10, 40);
		g.drawString("DisplayDimensionsInTiles: " + UrfQuest.panel.dispTileWidth + ", " + UrfQuest.panel.dispTileHeight, 10,
				50);
		g.drawString("CharacterDirection: " + player.getDirection(), 10, 60);
		g.drawString("CharacterHealth: " + player.getHealth(), 10, 70);
		g.drawString("CharacterMana: " + player.getMana(), 10, 80);
		g.drawString("CharacterSpeed: " + player.getVelocity(), 10, 90);
		g.drawString("NumMobs: " + currMap.mobs.size(), 10, 100);
		g.drawString("NumItems: " + currMap.items.size(), 10, 110);
		g.drawString("NumParticles: " + currMap.particles.size(), 10, 120);
	}

	private void drawEntities(Graphics g) {
		Player player = UrfQuest.game.getPlayer();
		QuestMap currMap = UrfQuest.game.getCurrMap();
		
		for (Mob m : currMap.mobs) {
			if (m.getPos()[0] > player.getPos()[0] - 30 &&
				m.getPos()[0] < player.getPos()[0] + 30 &&
				m.getPos()[1] > player.getPos()[1] - 30 &&
				m.getPos()[1] < player.getPos()[1] + 30)
			m.draw(g);
		}

		for (Item i : currMap.items) {
			if (i.getPos()[0] > player.getPos()[0] - 30 &&
				i.getPos()[0] < player.getPos()[0] + 30 &&
				i.getPos()[1] > player.getPos()[1] - 30 &&
				i.getPos()[1] < player.getPos()[1] + 30)
			i.draw(g);
		}
		
		for (Particle p : currMap.particles) {
			p.draw(g);
		}

		player.draw(g);
	}

	private void drawCrosshair(Graphics g) {
		int dispCenterX = UrfQuest.panel.dispCenterX;
		int dispCenterY = UrfQuest.panel.dispCenterY;

		g.setColor(Color.WHITE);
		g.drawLine(dispCenterX + 5, dispCenterY + 15, dispCenterX + 25, dispCenterY + 15);
		g.drawLine(dispCenterX + 15, dispCenterY + 5, dispCenterX + 15, dispCenterY + 25);
	}
}