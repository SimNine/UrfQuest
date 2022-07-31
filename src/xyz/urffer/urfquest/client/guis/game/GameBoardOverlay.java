package xyz.urffer.urfquest.client.guis.game;

import java.awt.Color;
import java.awt.Graphics;

import xyz.urffer.urfquest.LogLevel;
import xyz.urffer.urfquest.client.Client;
import xyz.urffer.urfquest.client.QuestPanel;
import xyz.urffer.urfquest.client.entities.Entity;
import xyz.urffer.urfquest.client.entities.items.Item;
import xyz.urffer.urfquest.client.entities.mobs.Mob;
import xyz.urffer.urfquest.client.entities.mobs.Player;
import xyz.urffer.urfquest.client.entities.particles.Particle;
import xyz.urffer.urfquest.client.entities.projectiles.Projectile;
import xyz.urffer.urfquest.client.guis.GUIAnchor;
import xyz.urffer.urfquest.client.guis.GUIContainer;
import xyz.urffer.urfquest.client.map.Map;
import xyz.urffer.urfquest.client.tiles.TileImages;
import xyz.urffer.urfquest.shared.Tile;

public class GameBoardOverlay extends GUIContainer {
	private int selectedTileTransparency = 255;
	private boolean transparencyIncreasing = false;
	private int tileAnimStage = 0;

	public GameBoardOverlay(Client c) {
		super(c, 
			  GUIAnchor.TOP_LEFT, 
			  0, 
			  0, 
			  0, 
			  0, 
			  "board", 
			  null, null, null, 0);
	}

	public void draw(Graphics g) {
		drawBoard(g);
		drawEntities(g);
		if (this.client.getLogger().getLogLevel().compareTo(LogLevel.DEBUG) >= 0) {
			drawDebugText(g);
			drawCrosshair(g);
		}
		
		tileAnimStage++;
		if (tileAnimStage == 360) {
			tileAnimStage = 0;
		}
	}
	
	private void drawBoard(Graphics g) {
		int dispTileWidth = client.getPanel().dispTileWidth;
		int dispTileHeight = client.getPanel().dispTileHeight;
		int dispCenterX = client.getPanel().dispCenterX;
		int dispCenterY = client.getPanel().dispCenterY;
		int TILE_WIDTH = QuestPanel.TILE_WIDTH;
		Map currMap = this.client.getState().getCurrentMap();
		Entity camera = this.client.getState().getPlayer();

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
				g.drawImage(TileImages.getTileImage(tile[0], tile[1], animStage), xRoot, yRoot, null);
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
		double mouseCoordX = client.getPanel().windowToGameX(client.getPanel().mousePos[0]);
		double mouseCoordY = client.getPanel().windowToGameY(client.getPanel().mousePos[1]);
		
		// find which tile the mouse is over
		int mouseX = (int) mouseCoordX;
		int mouseY = (int) mouseCoordY;
		
		// convenience
		Player p = this.client.getState().getPlayer();
		
		// draw the highlight of the selected tile
		if (this.client.getState().isGameRunning() && !client.getPanel().getGUIOpen()) {
			if (this.client.getState().isBuildMode()) {
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
		Mob m = this.client.getState().getCurrentMap().mobAt(mouseCoordX, mouseCoordY);
		if (m != null) {
			int tileWidth = QuestPanel.TILE_WIDTH;
			int xTemp = client.getPanel().gameToWindowX(m.getPos()[0]);
			int yTemp = client.getPanel().gameToWindowY(m.getPos()[1]);
			g.setColor(Color.RED);
			g.drawRect(xTemp, yTemp, (int)(m.getWidth()*tileWidth), (int)(m.getHeight()*tileWidth));
		}
		
		// when debugging, draw the grid itself
		if (this.client.getLogger().getLogLevel().compareTo(LogLevel.DEBUG) >= 0) {
			g.setColor(Color.BLACK);
			for (int x = 0; x < dispTileWidth + 2; x++) {
				g.drawLine(-rootX + x*TILE_WIDTH, 0, -rootX + x*TILE_WIDTH, client.getPanel().getHeight());
			}

			for (int y = 0; y < dispTileHeight + 2; y++) {
				g.drawLine(0, -rootY + y * TILE_WIDTH, client.getPanel().getWidth(), -rootY + y * TILE_WIDTH);
			}
		}
	}
	
	public boolean click() {
		int mouseX = (int) client.getPanel().windowToGameX(client.getPanel().mousePos[0]);
		int mouseY = (int) client.getPanel().windowToGameY(client.getPanel().mousePos[1]);
		
		Player p = this.client.getState().getPlayer();
		if (mouseX < p.getPos()[0] + 3 &&
				   mouseX > p.getPos()[0] - 3 &&
				   mouseY < p.getPos()[1] + 3 &&
				   mouseY > p.getPos()[1] - 3) {
			p.getMap().useActiveTile(mouseX, mouseY, p);
		}
		client.getLogger().debug("clicked: " + p.getMap().getTileAt(mouseX, mouseY)[0]);
		
		if (this.client.getState().isBuildMode() && this.client.getState().isGameRunning() && !client.getPanel().getGUIOpen()) {
			this.client.getState().getCurrentMap().setTileAt(mouseX, mouseY, 15);
			return true;
		} else {
			return false;
		}
	}

	private void drawDebugText(Graphics g) {
		Player player = this.client.getState().getPlayer();
		Map currMap = this.client.getState().getCurrentMap();
		
		g.setColor(new Color(128, 128, 128, 128));
		g.fillRect(0, 0, 600, 150);

		g.setColor(Color.WHITE);
		g.drawString(client.getPanel().keys.toString(), 10, 10);
		g.drawString("PlayerUpperLeftCoords: " + player.getPos()[0] + ", " + player.getPos()[1], 10, 20);
		g.drawString("PlayerCenterCoords: " + player.getCenter()[0] + ", " + player.getCenter()[1], 10, 30);
		g.drawString("DisplayCenter: " + client.getPanel().dispCenterX + ", " + client.getPanel().dispCenterY, 10, 40);
		g.drawString("DisplayDimensionsInTiles: " + client.getPanel().dispTileWidth + ", " + client.getPanel().dispTileHeight, 10,
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
		Map currMap = this.client.getState().getCurrentMap();
		Entity camera = this.client.getState().getPlayer();
		
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
		int dispCenterX = client.getPanel().dispCenterX;
		int dispCenterY = client.getPanel().dispCenterY;

		g.setColor(Color.WHITE);
		g.drawLine(dispCenterX + 5, dispCenterY + 15, dispCenterX + 25, dispCenterY + 15);
		g.drawLine(dispCenterX + 15, dispCenterY + 5, dispCenterX + 15, dispCenterY + 25);
	}
	
	// gets the appropriate animstage for Tiles.getTileImage(type, subtype, animstage)
	private int getAnimStage(int type, int subtype) {
		if (type == Tile.TILE_WATER) {
			return (tileAnimStage / 30) % 3;
		} else {
			return 0;
		}
	}
}