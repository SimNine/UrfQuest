package urfquest.client.guis.game;

import java.awt.Color;
import java.awt.Graphics;

import urfquest.Logger;
import urfquest.Main;
import urfquest.client.QuestPanel;
import urfquest.client.entities.Entity;
import urfquest.client.entities.items.Item;
import urfquest.client.entities.mobs.Mob;
import urfquest.client.entities.mobs.Player;
import urfquest.client.entities.particles.Particle;
import urfquest.client.entities.projectiles.Projectile;
import urfquest.client.guis.GUIContainer;
import urfquest.client.guis.GUIObject;
import urfquest.client.map.Map;
import urfquest.client.tiles.Tiles;

public class GameBoardOverlay extends GUIContainer {
	private int selectedTileTransparency = 255;
	private boolean transparencyIncreasing = false;
	private int tileAnimStage = 0;

	public GameBoardOverlay() {
		super(GUIObject.TOP_LEFT, 
			  0, 
			  0, 
			  Main.panel.getWidth(), 
			  Main.panel.getHeight(), 
			  "board", 
			  null, 
			  null, null, 0);
	}

	public void draw(Graphics g) {
		drawBoard(g);
		drawEntities(g);
		if (Main.logger.getLogLevel().compareTo(Logger.LogLevel.LOG_DEBUG) >= 0) {
			drawDebugText(g);
			drawCrosshair(g);
		}
		
		tileAnimStage++;
		if (tileAnimStage == 360) {
			tileAnimStage = 0;
		}
	}
	
	private void drawBoard(Graphics g) {
		int dispTileWidth = Main.panel.dispTileWidth;
		int dispTileHeight = Main.panel.dispTileHeight;
		int dispCenterX = Main.panel.dispCenterX;
		int dispCenterY = Main.panel.dispCenterY;
		int TILE_WIDTH = QuestPanel.TILE_WIDTH;
		Map currMap = Main.client.getState().getCurrentMap();
		Entity camera = Main.client.getState().getPlayer();

		// get the rendering offset
		int rootX = (int)(TILE_WIDTH - (dispCenterX % TILE_WIDTH));
		int rootY = (int)(TILE_WIDTH - (dispCenterY % TILE_WIDTH));
		rootX += (camera.getPos()[0] % 1)*TILE_WIDTH;
		rootY += (camera.getPos()[1] % 1)*TILE_WIDTH;
		
		// get the block coordinate of the upper-left corner
		int ulX = ((int)camera.getPos()[0] - dispTileWidth/2) - 1;
		int ulY = ((int)camera.getPos()[1] - dispTileHeight/2) - 1;
		
		// draw the tiles
		for (int x = 0; x < dispTileWidth + 2; x++) {
			int xRoot = - rootX + x * TILE_WIDTH;
			for (int y = 0; y < dispTileHeight + 2; y++) {
				int yRoot = - rootY + y * TILE_WIDTH;
				int[] tile = currMap.getTileAt(ulX + x, ulY + y);
				int animStage = getAnimStage(tile[0], tile[1]);
				g.drawImage(Tiles.getTileImage(tile[0], tile[1], animStage), xRoot, yRoot, null);
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
		double mouseCoordX = Main.panel.windowToGameX(Main.panel.mousePos[0]);
		double mouseCoordY = Main.panel.windowToGameY(Main.panel.mousePos[1]);
		
		// find which tile the mouse is over
		int mouseX = (int) mouseCoordX;
		int mouseY = (int) mouseCoordY;
		
		// convenience
		Player p = Main.client.getState().getPlayer();
		
		// draw the highlight of the selected tile
		if (Main.client.getState().isGameRunning() && !Main.panel.getGUIOpen()) {
			if (Main.client.getState().isBuildMode()) {
				int xRoot = - rootX + (mouseX - ulX)*TILE_WIDTH;
				int yRoot = - rootY + (mouseY - ulY)*TILE_WIDTH;
				g.setColor(new Color(255, 255, 255, selectedTileTransparency));
				g.fillRect(xRoot, yRoot, TILE_WIDTH, TILE_WIDTH);
				g.setColor(Color.WHITE);
				for (int i = 0; i < 3; i++) {
					g.drawRect(xRoot + i, yRoot + i, TILE_WIDTH - i*2 - 1, TILE_WIDTH - i*2 - 1);
				}
			} else if (mouseX < p.getPos()[0] + 3 &&
					   mouseX > p.getPos()[0] - 3 &&
					   mouseY < p.getPos()[1] + 3 &&
					   mouseY > p.getPos()[1] - 3) {
				int xRoot = - rootX + (mouseX - ulX)*TILE_WIDTH;
				int yRoot = - rootY + (mouseY - ulY)*TILE_WIDTH;
				g.setColor(new Color(255, 0, 0, selectedTileTransparency));
				g.fillRect(xRoot, yRoot, TILE_WIDTH, TILE_WIDTH);
				g.setColor(Color.RED);
				for (int i = 0; i < 3; i++) {
					g.drawRect(xRoot + i, yRoot + i, TILE_WIDTH - i*2 - 1, TILE_WIDTH - i*2 - 1);
				}
			}
		}
		
		// get any mob underneath the mouse cursor, highlight it
		Mob m = Main.client.getState().getCurrentMap().mobAt(mouseCoordX, mouseCoordY);
		if (m != null) {
			int tileWidth = QuestPanel.TILE_WIDTH;
			int xTemp = Main.panel.gameToWindowX(m.getPos()[0]);
			int yTemp = Main.panel.gameToWindowY(m.getPos()[1]);
			g.setColor(Color.RED);
			g.drawRect(xTemp, yTemp, (int)(m.getWidth()*tileWidth), (int)(m.getHeight()*tileWidth));
		}
		
		// when debugging, draw the grid itself
		if (Main.logger.getLogLevel().compareTo(Logger.LogLevel.LOG_DEBUG) >= 0) {
			g.setColor(Color.BLACK);
			for (int x = 0; x < dispTileWidth + 2; x++) {
				g.drawLine(-rootX + x*TILE_WIDTH, 0, -rootX + x*TILE_WIDTH, Main.panel.getHeight());
			}

			for (int y = 0; y < dispTileHeight + 2; y++) {
				g.drawLine(0, -rootY + y * TILE_WIDTH, Main.panel.getWidth(), -rootY + y * TILE_WIDTH);
			}
		}
	}
	
	public boolean click() {
		int mouseX = (int) Main.panel.windowToGameX(Main.panel.mousePos[0]);
		int mouseY = (int) Main.panel.windowToGameY(Main.panel.mousePos[1]);
		
		Player p = Main.client.getState().getPlayer();
		if (mouseX < p.getPos()[0] + 3 &&
				   mouseX > p.getPos()[0] - 3 &&
				   mouseY < p.getPos()[1] + 3 &&
				   mouseY > p.getPos()[1] - 3) {
			p.getMap().useActiveTile(mouseX, mouseY, p);
		}
		System.out.println(p.getMap().getTileAt(mouseX, mouseY)[0]);
		
		if (Main.client.getState().isBuildMode() && Main.client.getState().isGameRunning() && !Main.panel.getGUIOpen()) {
			Main.client.getState().getCurrentMap().setTileAt(mouseX, mouseY, 15);
			return true;
		} else {
			return false;
		}
	}

	private void drawDebugText(Graphics g) {
		Player player = Main.client.getState().getPlayer();
		Map currMap = Main.client.getState().getCurrentMap();
		
		g.setColor(new Color(128, 128, 128, 128));
		g.fillRect(0, 0, 600, 150);

		g.setColor(Color.WHITE);
		g.drawString(Main.panel.keys.toString(), 10, 10);
		g.drawString("PlayerUpperLeftCoords: " + player.getPos()[0] + ", " + player.getPos()[1], 10, 20);
		g.drawString("PlayerCenterCoords: " + player.getCenter()[0] + ", " + player.getCenter()[1], 10, 30);
		g.drawString("DisplayCenter: " + Main.panel.dispCenterX + ", " + Main.panel.dispCenterY, 10, 40);
		g.drawString("DisplayDimensionsInTiles: " + Main.panel.dispTileWidth + ", " + Main.panel.dispTileHeight, 10,
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
		Map currMap = Main.client.getState().getCurrentMap();
		Entity camera = Main.client.getState().getPlayer();
		
		for (Mob m : currMap.getMobs().values()) {
			if (m.getCenter()[0] > camera.getPos()[0] - 30 &&
				m.getCenter()[0] < camera.getPos()[0] + 30 &&
				m.getCenter()[1] > camera.getPos()[1] - 30 &&
				m.getCenter()[1] < camera.getPos()[1] + 30)
			m.draw(g);
		}

		for (Item i : currMap.getItems().values()) {
			if (i.getCenter()[0] > camera.getPos()[0] - 30 &&
				i.getCenter()[0] < camera.getPos()[0] + 30 &&
				i.getCenter()[1] > camera.getPos()[1] - 30 &&
				i.getCenter()[1] < camera.getPos()[1] + 30)
			i.draw(g);
		}
		
		for (Projectile p : currMap.getProjectiles().values()) {
			p.draw(g);
		}
		
		for (Particle p : currMap.getParticles()) {
			p.draw(g);
		}

		for (Player player : currMap.getPlayers().values()) {
			if (player.getCenter()[0] > camera.getPos()[0] - 30 &&
				player.getCenter()[0] < camera.getPos()[0] + 30 &&
				player.getCenter()[1] > camera.getPos()[1] - 30 &&
				player.getCenter()[1] < camera.getPos()[1] + 30) {
				player.draw(g);
			}
		}
	}

	private void drawCrosshair(Graphics g) {
		int dispCenterX = Main.panel.dispCenterX;
		int dispCenterY = Main.panel.dispCenterY;

		g.setColor(Color.WHITE);
		g.drawLine(dispCenterX + 5, dispCenterY + 15, dispCenterX + 25, dispCenterY + 15);
		g.drawLine(dispCenterX + 15, dispCenterY + 5, dispCenterX + 15, dispCenterY + 25);
	}
	
	// gets the appropriate animstage for Tiles.getTileImage(type, subtype, animstage)
	private int getAnimStage(int type, int subtype) {
		if (type == Tiles.WATER) {
			return (tileAnimStage / 30) % 3;
		} else {
			return 0;
		}
	}
}