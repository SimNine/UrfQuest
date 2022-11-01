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
import xyz.urffer.urfquest.shared.protocol.types.TileType;

public class GameBoardOverlay extends GUIContainer {
	private int selectedTileTransparency = 255;
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
		// get the position in the top-left corner
		PairDouble ul = client.getPanel().windowToGame(new PairInt(0, 0));
		
		// get the rendering offset
		PairDouble tileOffset = ul.mod(1);
		PairInt pixelOffset = tileOffset.multiply(QuestPanel.TILE_WIDTH).floor();
		
		// render every tile
		Map currMap = this.client.getState().getCurrentMap();
		PairInt dispTileDims = client.getPanel().dispTileDims.floor();
		for (int x = -1; x < dispTileDims.x - 1; x++) {
			for (int y = -1; y < dispTileDims.y - 1; y++) {
				PairInt currTilePos = ul.toInt().add(new PairInt(x, y));
				Tile tile = currMap.getTileAt(currTilePos);
				
				int animStage = getAnimStage(tile);
				g.drawImage(
					TileImages.getTileImage(tile, animStage),
					x * QuestPanel.TILE_WIDTH - pixelOffset.x,
					y * QuestPanel.TILE_WIDTH - pixelOffset.y,
					null
				);
			}
		}

		// change the transparency of the selected tile
		if (selectedTileTransparency < 250) {
			selectedTileTransparency += 5;
		} else {
			selectedTileTransparency = 0;
		}
		
		// find what coordinates the mouse is at
		PairDouble mouseCoords = client.getPanel().windowToGame(client.getPanel().mousePos);
		
		// find which tile the mouse is over
		PairInt mouseTile = mouseCoords.floor();
		
		// convenience
		Player p = this.client.getState().getPlayer();
		
		// draw the highlight of the selected tile
		if (this.client.getState().isGameRunning()) { //&& !client.getPanel().getGUIOpen()) {
			PairInt mouseTilePixels = client.getPanel().gameToWindow(mouseTile);
			if (this.client.getState().isBuildMode()) {
				g.setColor(new Color(255, 255, 255, selectedTileTransparency));
				g.fillRect(
					mouseTilePixels.x,
					mouseTilePixels.y,
					QuestPanel.TILE_WIDTH,
					QuestPanel.TILE_WIDTH
				);
				
				g.setColor(Color.WHITE);
				for (int i = 0; i < 3; i++) {
					g.drawRect(
						mouseTilePixels.x + i,
						mouseTilePixels.y + i,
						QuestPanel.TILE_WIDTH - i*2 - 1,
						QuestPanel.TILE_WIDTH - i*2 - 1
					);
				}
			} else if (
				mouseTile.x < p.getPos().x + 3 &&
				mouseTile.x > p.getPos().x - 3 &&
				mouseTile.y < p.getPos().y + 3 &&
				mouseTile.y > p.getPos().y - 3
			) {
				g.setColor(new Color(255, 0, 0, selectedTileTransparency));
				g.fillRect(
					mouseTilePixels.x,
					mouseTilePixels.y,
					QuestPanel.TILE_WIDTH,
					QuestPanel.TILE_WIDTH
				);
				g.setColor(Color.RED);
				for (int i = 0; i < 3; i++) {
					g.drawRect(
						mouseTilePixels.x + i,
						mouseTilePixels.y + i,
						QuestPanel.TILE_WIDTH - i*2 - 1,
						QuestPanel.TILE_WIDTH - i*2 - 1
					);
				}
			}
		}
//		
//		// get any mob underneath the mouse cursor, highlight it
//		Mob m = this.client.getState().getCurrentMap().mobAt(mouseCoords);
//		if (m != null) {
//			int tileWidth = QuestPanel.TILE_WIDTH;
//			int xTemp = client.getPanel().gameToWindowX(m.getPos().x);
//			int yTemp = client.getPanel().gameToWindowY(m.getPos().y);
//			g.setColor(Color.RED);
//			g.drawRect(xTemp, yTemp, (int)(m.getDims().x*tileWidth), (int)(m.getDims().y*tileWidth));
//		}
//		
//		// when debugging, draw the grid itself
//		if (this.client.getLogger().getLogLevel().compareTo(LogLevel.DEBUG) >= 0) {
//			g.setColor(Color.BLACK);
//			for (int x = 0; x < dispTileDims.x + 2; x++) {
//				g.drawLine(
//					(int)(-root.x + x*TILE_WIDTH),
//					0,
//					(int)(-root.x + x*TILE_WIDTH),
//					client.getPanel().getHeight()
//				);
//			}
//
//			for (int y = 0; y < dispTileDims.y + 2; y++) {
//				g.drawLine(
//					0,
//					(int)(-root.y + y * TILE_WIDTH),
//					client.getPanel().getWidth(),
//					(int)(-root.y + y * TILE_WIDTH)
//				);
//			}
//		}
	}
	
	public boolean click() {
		PairInt mouseTile = client.getPanel().windowToGame(client.getPanel().mousePos).floor();
		
		Player p = this.client.getState().getPlayer();
		if (
			mouseTile.x < p.getPos().x + 3 &&
			mouseTile.x > p.getPos().x - 3 &&
			mouseTile.y < p.getPos().y + 3 &&
			mouseTile.y > p.getPos().y - 3) {
			p.getMap().useActiveTile(mouseTile, p);
		}
		
		if (this.client.getState().isBuildMode() && this.client.getState().isGameRunning() && !client.getPanel().getGUIOpen()) {
			this.client.getState().getCurrentMap().setTileAt(mouseTile, new Tile(TileType.BEDROCK));
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
		PairInt dispCenter = client.getPanel().dispCenter;

		g.setColor(Color.CYAN);
		g.drawLine(dispCenter.x - 5, dispCenter.y - 5, dispCenter.x + 5, dispCenter.y + 5);
		g.drawLine(dispCenter.x - 5, dispCenter.y + 5, dispCenter.x + 5, dispCenter.y - 5);
	}
	
	// gets the appropriate animstage for Tiles.getTileImage(type, subtype, animstage)
	private int getAnimStage(Tile tile) {
		if (tile.tileType == TileType.WATER) {
			return (tileAnimStage / 30) % 3;
		} else {
			return 0;
		}
	}
}