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
import xyz.urffer.urfquest.shared.PairDouble;
import xyz.urffer.urfquest.shared.PairInt;
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
		PairInt dispTileDims = client.getPanel().dispTileDims;
		PairInt dispCenter = client.getPanel().dispCenter;
		int TILE_WIDTH = QuestPanel.TILE_WIDTH;
		Map currMap = this.client.getState().getCurrentMap();
		Entity camera = this.client.getState().getPlayer();

		// get the rendering offset
		PairInt root = dispCenter.mod(TILE_WIDTH);
		root = PairInt.subtract(TILE_WIDTH, root);
		root = root.add(camera.getPos().mod(1).multiply(TILE_WIDTH).toInt());
		
		// get the block coordinate of the upper-left corner
		PairInt ul = camera.getPos().toInt();
		ul = ul.subtract(dispTileDims.divide(2));
		ul = ul.subtract(1);
		
		// draw the tiles
		for (int x = 0; x < dispTileDims.x + 2; x++) {
			for (int y = 0; y < dispTileDims.y + 2; y++) {
				PairInt tempRoot = new PairInt(-root.x, -root.y);
				tempRoot = tempRoot.add(new PairInt(x, y).multiply(TILE_WIDTH));
				
				int[] tile = currMap.getTileAt(new PairInt(ul.x + x, ul.y + y));
				int animStage = getAnimStage(tile[0], tile[1]);
				g.drawImage(TileImages.getTileImage(tile[0], tile[1], animStage), tempRoot.x, tempRoot.y, null);
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
		PairDouble mouseCoords = client.getPanel().windowToGame(client.getPanel().mousePos);
		
		// find which tile the mouse is over
		PairInt mousePos = mouseCoords.toInt();
		int mouseX = mousePos.x;
		int mouseY = mousePos.y;
		
		// convenience
		Player p = this.client.getState().getPlayer();
		
		// draw the highlight of the selected tile
		if (this.client.getState().isGameRunning()) { //&& !client.getPanel().getGUIOpen()) {
			if (this.client.getState().isBuildMode()) {
				int xRoot = - root.x + (mouseX - ul.x)*TILE_WIDTH;
				int yRoot = - root.y + (mouseY - ul.y)*TILE_WIDTH;
				g.setColor(new Color(255, 255, 255, selectedTileTransparency));
				g.fillRect(xRoot, yRoot, TILE_WIDTH, TILE_WIDTH);
				g.setColor(Color.WHITE);
				for (int i = 0; i < 3; i++) {
					g.drawRect(xRoot + i, yRoot + i, TILE_WIDTH - i*2 - 1, TILE_WIDTH - i*2 - 1);
				}
			} else if (mouseX < p.getPos().x + 3 &&
					   mouseX > p.getPos().x - 3 &&
					   mouseY < p.getPos().y + 3 &&
					   mouseY > p.getPos().y - 3) {
				int xRoot = - root.x + (mouseX - ul.x)*TILE_WIDTH;
				int yRoot = - root.y + (mouseY - ul.y)*TILE_WIDTH;
				g.setColor(new Color(255, 0, 0, selectedTileTransparency));
				g.fillRect(xRoot, yRoot, TILE_WIDTH, TILE_WIDTH);
				g.setColor(Color.RED);
				for (int i = 0; i < 3; i++) {
					g.drawRect(xRoot + i, yRoot + i, TILE_WIDTH - i*2 - 1, TILE_WIDTH - i*2 - 1);
				}
			}
		}
		
		// get any mob underneath the mouse cursor, highlight it
		Mob m = this.client.getState().getCurrentMap().mobAt(mouseCoords);
		if (m != null) {
			int tileWidth = QuestPanel.TILE_WIDTH;
			int xTemp = client.getPanel().gameToWindowX(m.getPos().x);
			int yTemp = client.getPanel().gameToWindowY(m.getPos().y);
			g.setColor(Color.RED);
			g.drawRect(xTemp, yTemp, (int)(m.getDims().x*tileWidth), (int)(m.getDims().y*tileWidth));
		}
		
		// when debugging, draw the grid itself
		if (this.client.getLogger().getLogLevel().compareTo(LogLevel.DEBUG) >= 0) {
			g.setColor(Color.BLACK);
			for (int x = 0; x < dispTileDims.x + 2; x++) {
				g.drawLine(-root.x + x*TILE_WIDTH, 0, -root.x + x*TILE_WIDTH, client.getPanel().getHeight());
			}

			for (int y = 0; y < dispTileDims.y + 2; y++) {
				g.drawLine(0, -root.y + y * TILE_WIDTH, client.getPanel().getWidth(), -root.y + y * TILE_WIDTH);
			}
		}
	}
	
	public boolean click() {
		PairInt mousePos = client.getPanel().windowToGame(client.getPanel().mousePos).toInt();
		int mouseX = mousePos.x;
		int mouseY = mousePos.y;
		
		Player p = this.client.getState().getPlayer();
		if (
			mouseX < p.getPos().x + 3 &&
			mouseX > p.getPos().x - 3 &&
			mouseY < p.getPos().y + 3 &&
			mouseY > p.getPos().y - 3) {
			p.getMap().useActiveTile(mousePos, p);
		}
		int[] tile = p.getMap().getTileAt(mousePos);
		client.getLogger().debug("clicked: " + tile[0] + "/" + tile[1]);
		
		if (this.client.getState().isBuildMode() && this.client.getState().isGameRunning() && !client.getPanel().getGUIOpen()) {
			this.client.getState().getCurrentMap().setTileAt(mousePos, 15);
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
		g.drawString("PlayerUpperLeftCoords: " + player.getPos().x + ", " + player.getPos().y, 10, 20);
		g.drawString("PlayerCenterCoords: " + player.getCenter().x + ", " + player.getCenter().y, 10, 30);
		g.drawString("DisplayCenter: " + client.getPanel().dispCenter.x + ", " + client.getPanel().dispCenter.y, 10, 40);
		g.drawString("DisplayDimensionsInTiles: " + client.getPanel().dispTileDims.x + ", " + client.getPanel().dispTileDims.y, 10,
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
			if (m.getCenter().x > camera.getPos().x - 30 &&
				m.getCenter().x < camera.getPos().x + 30 &&
				m.getCenter().y > camera.getPos().y - 30 &&
				m.getCenter().y < camera.getPos().y + 30)
			m.draw(g);
		}

		for (Item i : currMap.getItems().values()) {
			if (i.getCenter().x > camera.getPos().x - 30 &&
				i.getCenter().x < camera.getPos().x + 30 &&
				i.getCenter().y > camera.getPos().y - 30 &&
				i.getCenter().y < camera.getPos().y + 30)
			i.draw(g);
		}
		
		for (Projectile p : currMap.getProjectiles().values()) {
			p.draw(g);
		}
		
		for (Particle p : currMap.getParticles()) {
			p.draw(g);
		}

		for (Player player : currMap.getPlayers().values()) {
			if (player.getCenter().x > camera.getPos().x - 30 &&
				player.getCenter().x < camera.getPos().x + 30 &&
				player.getCenter().y > camera.getPos().y - 30 &&
				player.getCenter().y < camera.getPos().y + 30) {
				player.draw(g);
			}
		}
	}

	private void drawCrosshair(Graphics g) {
		int dispCenterX = client.getPanel().dispCenter.x;
		int dispCenterY = client.getPanel().dispCenter.y;

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