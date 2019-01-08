package guis.game;

import java.awt.Color;
import java.awt.Graphics;

import entities.Entity;
import entities.items.Item;
import entities.mobs.Mob;
import entities.mobs.Player;
import entities.particles.Particle;
import entities.projectiles.Projectile;
import framework.QuestPanel;
import framework.UrfQuest;
import game.QuestMap;
import guis.GUIContainer;
import guis.GUIObject;
import tiles.Tiles;

public class GameBoardOverlay extends GUIContainer {
	private int selectedTileTransparency = 255;
	private boolean transparencyIncreasing = false;
	private int tileAnimStage = 0;

	public GameBoardOverlay() {
		super(GUIObject.TOP_LEFT, 
			  0, 
			  0, 
			  UrfQuest.panel.getWidth(), 
			  UrfQuest.panel.getHeight(), 
			  "board", 
			  null, 
			  null, null, 0);
	}

	public void draw(Graphics g) {
		drawBoard(g);
		drawEntities(g);
		if (UrfQuest.debug) {
			drawDebugText(g);
			drawCrosshair(g);
		}
		
		tileAnimStage++;
		if (tileAnimStage == 360) {
			tileAnimStage = 0;
		}
	}
	
	private void drawBoard(Graphics g) {
		int dispTileWidth = UrfQuest.panel.dispTileWidth;
		int dispTileHeight = UrfQuest.panel.dispTileHeight;
		int dispCenterX = UrfQuest.panel.dispCenterX;
		int dispCenterY = UrfQuest.panel.dispCenterY;
		int TILE_WIDTH = QuestPanel.TILE_WIDTH;
		QuestMap currMap = UrfQuest.game.getCurrMap();
		Entity camera = UrfQuest.panel.getCamera();

		// get the rendering offset
		int rootX = (int)(TILE_WIDTH - (dispCenterX % TILE_WIDTH));
		int rootY = (int)(TILE_WIDTH - (dispCenterY % TILE_WIDTH));
		rootX += (camera.getPos()[0] % 1)*TILE_WIDTH;
		rootY += (camera.getPos()[1] % 1)*TILE_WIDTH;
		
		// get the block coordinate of the upper-left corner
		int ulX = ((int)camera.getPos()[0] - dispTileWidth/2) - 1;
		int ulY = ((int)camera.getPos()[1] - dispTileHeight/2) - 1;
		
		// draw grid tiles
		for (int x = 0; x < dispTileWidth + 2; x++) {
			int xRoot = - rootX + x * TILE_WIDTH;
			for (int y = 0; y < dispTileHeight + 2; y++) {
				int yRoot = - rootY + y * TILE_WIDTH;
				int currTile = (currMap.getTileAt(ulX + x, ulY + y));
				Tiles.drawTile(g, xRoot, yRoot, currTile, tileAnimStage);
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
		
		// find what coordinates the mouse is at
		double mouseCoordX = UrfQuest.panel.windowToGameX(UrfQuest.mousePos[0]);
		double mouseCoordY = UrfQuest.panel.windowToGameY(UrfQuest.mousePos[1]);
		
		// find which tile the mouse is over
		int mouseX = (int) mouseCoordX;
		int mouseY = (int) mouseCoordY;
		
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
		
		// get any mob underneath the mouse cursor, highlight it
		Mob m = UrfQuest.game.getCurrMap().mobAt(mouseCoordX, mouseCoordY);
		if (m != null) {
			int tileWidth = QuestPanel.TILE_WIDTH;
			int xTemp = UrfQuest.panel.gameToWindowX(m.getPos()[0]);
			int yTemp = UrfQuest.panel.gameToWindowY(m.getPos()[1]);
			g.setColor(Color.RED);
			g.drawRect(xTemp, yTemp, (int)(m.getWidth()*tileWidth), (int)(m.getHeight()*tileWidth));
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
		int mouseX = (int) UrfQuest.panel.windowToGameX(UrfQuest.mousePos[0]);
		int mouseY = (int) UrfQuest.panel.windowToGameY(UrfQuest.mousePos[1]);
		
		if (UrfQuest.game.isBuildMode() && !UrfQuest.panel.isGUIOpen()) {
			UrfQuest.game.getCurrMap().setTileAt(mouseX, mouseY, 15);
			return true;
		} else if (UrfQuest.game.getCurrMap().getTileAt(mouseX, mouseY) == 7) {
			UrfQuest.game.getPlayer().incrementHealth(-5.0);
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
		g.drawString("NumMobs: " + currMap.getNumMobs(), 10, 100);
		g.drawString("NumItems: " + currMap.getNumItems(), 10, 110);
		g.drawString("NumParticles: " + currMap.getNumProjectiles(), 10, 120);
	}

	private void drawEntities(Graphics g) {
		QuestMap currMap = UrfQuest.game.getCurrMap();
		Entity camera = UrfQuest.panel.getCamera();
		
		for (Mob m : currMap.getMobs()) {
			if (m.getCenter()[0] > camera.getPos()[0] - 30 &&
				m.getCenter()[0] < camera.getPos()[0] + 30 &&
				m.getCenter()[1] > camera.getPos()[1] - 30 &&
				m.getCenter()[1] < camera.getPos()[1] + 30)
			m.draw(g);
		}

		for (Item i : currMap.getItems()) {
			if (i.getCenter()[0] > camera.getPos()[0] - 30 &&
				i.getCenter()[0] < camera.getPos()[0] + 30 &&
				i.getCenter()[1] > camera.getPos()[1] - 30 &&
				i.getCenter()[1] < camera.getPos()[1] + 30)
			i.draw(g);
		}
		
		for (Projectile p : currMap.getProjectiles()) {
			p.draw(g);
		}
		
		for (Particle p : currMap.getParticles()) {
			p.draw(g);
		}

		for (Player player : currMap.getPlayers()) {
			if (player.getCenter()[0] > camera.getPos()[0] - 30 &&
				player.getCenter()[0] < camera.getPos()[0] + 30 &&
				player.getCenter()[1] > camera.getPos()[1] - 30 &&
				player.getCenter()[1] < camera.getPos()[1] + 30) {
				player.draw(g);
			}
		}
	}

	private void drawCrosshair(Graphics g) {
		int dispCenterX = UrfQuest.panel.dispCenterX;
		int dispCenterY = UrfQuest.panel.dispCenterY;

		g.setColor(Color.WHITE);
		g.drawLine(dispCenterX + 5, dispCenterY + 15, dispCenterX + 25, dispCenterY + 15);
		g.drawLine(dispCenterX + 15, dispCenterY + 5, dispCenterX + 15, dispCenterY + 25);
	}
}