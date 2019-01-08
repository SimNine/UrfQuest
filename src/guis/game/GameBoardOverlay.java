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
		int TILE_WIDTH = QuestPanel.TILE_WIDTH;
		int dispCenterX = UrfQuest.panel.dispCenterX;
		int dispCenterY = UrfQuest.panel.dispCenterY;
		Player player = UrfQuest.game.getPlayer();
		QuestMap currMap = UrfQuest.game.getCurrMap();

		// get the rendering offset
		int tempX = (int) ((player.getPos()[0] % 1) * TILE_WIDTH);
		int tempY = (int) ((player.getPos()[1] % 1) * TILE_WIDTH);
		
		// draw grid tiles
		for (int x = -(int) Math.floor(dispTileWidth / 2.0); x < Math.ceil(dispTileWidth / 2.0) + 1; x++) {
			int xRoot = dispCenterX - tempX + x * TILE_WIDTH;
			for (int y = -(int) Math.floor(dispTileHeight / 2.0); y < Math.ceil(dispTileHeight / 2.0) + 1; y++) {
				int yRoot = dispCenterY - tempY + y * TILE_WIDTH;
				int currTile = (currMap.getTileAt((int) player.getPos()[0] + x,
						(int) player.getPos()[1] + y));
				Tiles.drawTile(g, xRoot, yRoot, currTile);
			}
		}
		
		// when debugging, draw the grid itself
		if (UrfQuest.debug) {
			for (int x = -(int) Math.floor(dispTileWidth / 2.0); x < Math.ceil(dispTileWidth / 2.0) + 1; x++) {
				g.drawLine(dispCenterX - tempX + x*TILE_WIDTH, 0, dispCenterX - tempX + x*TILE_WIDTH, UrfQuest.panel.getHeight());
			}

			for (int y = -(int) Math.floor(dispTileHeight / 2.0); y < Math.ceil(dispTileHeight / 2.0) + 1; y++) {
				g.drawLine(0, dispCenterY - tempY + y * TILE_WIDTH, UrfQuest.panel.getWidth(), dispCenterY - tempY + y * TILE_WIDTH);
			}
		}
		
		// highlight the tile with the mouse currently over it
	}

	private void drawDebugText(Graphics g) {
		Player player = UrfQuest.game.getPlayer();
		QuestMap currMap = UrfQuest.game.getCurrMap();
		
		g.setColor(new Color(128, 128, 128, 128));
		g.fillRect(0, 0, 600, 150);

		g.setColor(Color.WHITE);
		g.drawString(UrfQuest.keys.toString(), 10, 10);
		g.drawString("PlayerBlockCoords: " + player.getPos()[0] + 15 / QuestPanel.TILE_WIDTH + ", "
				+ player.getPos()[1] + 15 / QuestPanel.TILE_WIDTH, 10, 20);
		g.drawString("PlayerActualCoords: " + player.getPos()[0] + ", " + player.getPos()[1], 10, 30);
		g.drawString("DisplayCenter: " + UrfQuest.panel.dispCenterX + ", " + UrfQuest.panel.dispCenterY, 10, 40);
		g.drawString("DisplayDimensions: " + UrfQuest.panel.dispTileWidth + ", " + UrfQuest.panel.dispTileHeight, 10,
				50);
		g.drawString("CharacterDirection: " + player.getDirection(), 10, 60);
		g.drawString("CharacterHealth: " + player.getHealth(), 10, 70);
		g.drawString("CharacterMana: " + player.getMana(), 10, 80);
		g.drawString("CharacterSpeed: " + player.getVelocity(), 10, 90);
		g.drawString("Number of mobs: " + currMap.mobs.size(), 10, 100);
		g.drawString("Number of items: " + currMap.items.size(), 10, 110);
		g.drawString("Number of particles: " + currMap.particles.size(), 10, 120);
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