package game;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import entities.Entity;
import entities.characters.Player;
import framework.UrfQuest;
import guis.QuestGUI;
import framework.QuestPanel;
import tiles.Tiles;

public class QuestGame {
	private QuestMap currMap;
	private ArrayList<QuestMap> maps;
	private Player player;
	private boolean guiVisible;
	
	public QuestGame() {
		currMap = new QuestMap(500, 500);
		maps = new ArrayList<QuestMap>();
		maps.add(currMap);
		player = new Player(currMap.getWidth()/2.0, currMap.getHeight()/2.0);
		guiVisible = false;
	}
	
	public void tick() {
		for (Entity e : currMap.entities) {
			e.update();
		}
		
		player.update();
	}
	
	public QuestMap getCurrMap() {
		return currMap;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public void toggleGUIVisible() {
		if (guiVisible) {
			guiVisible = false;
		} else {
			guiVisible = true;
		}
	}
	
	public boolean isGUIVisible() {
		return guiVisible;
	}
	
	public void drawGame(Graphics g) {
		drawBoard(g);
		drawSprites(g);
		if (guiVisible) {
			drawStatusBars(g);
		}
		
		if (UrfQuest.debug) drawCrosshair(g);
		if (UrfQuest.debug) drawDebug(g);
	}
	
	private void drawBoard(Graphics g) {
		int dispTileWidth = UrfQuest.panel.dispTileWidth;
		int dispTileHeight = UrfQuest.panel.dispTileHeight;
		int TILE_WIDTH = QuestPanel.TILE_WIDTH;
		int dispCenterX = UrfQuest.panel.dispCenterX;
		int dispCenterY = UrfQuest.panel.dispCenterY;
		
		int tempX = (int)((player.getPosition()[0] % 1)*TILE_WIDTH);
		int tempY = (int)((player.getPosition()[1] % 1)*TILE_WIDTH);
		for (int x = -(int)Math.floor(dispTileWidth/2.0); x < Math.ceil(dispTileWidth/2.0); x++) {
			for (int y = -(int)Math.floor(dispTileHeight/2.0); y < Math.ceil(dispTileHeight/2.0); y++) {
				int currTile = (currMap.getTileAt((int)player.getPosition()[0] + x, (int)player.getPosition()[1] + y));
				Tiles.drawTile(g, dispCenterX - tempX + x*TILE_WIDTH, dispCenterY - tempY + y*TILE_WIDTH, currTile);
			}
		}
	}
	
	private void drawDebug (Graphics g) {
		g.setColor(new Color(128, 128, 128, 128));
		g.fillRect(0, 0, 200, 100);
		
		g.setColor(Color.WHITE);
		g.drawString(UrfQuest.keys.toString(), 10, 10);
		g.drawString("GameCenter: " + Math.round(player.getPosition()[0]) + ", " + Math.round(player.getPosition()[1]), 10, 20);
		g.drawString("GameCenterBlock: " + (int)player.getPosition()[0] + ", " + (int)player.getPosition()[1], 10, 30);
		g.drawString("DisplayCenter: " + UrfQuest.panel.dispCenterX + ", " + UrfQuest.panel.dispCenterY, 10, 40);
		g.drawString("DisplayDimensions: " + UrfQuest.panel.dispTileWidth + ", " + UrfQuest.panel.dispTileHeight, 10, 50);
		g.drawString("CharacterDirection: " + player.getOrientation(), 10, 60);
		g.drawString("CharacterHealth: " + player.getHealth(), 10, 70);
		g.drawString("CharacterMana: " + player.getMana(), 10, 80);
	}
	
	private void drawSprites(Graphics g) {
		for (Entity e : currMap.entities) {
			e.draw(g);
		}
		player.draw(g);
	}

	private void drawCrosshair(Graphics g) {
		int dispCenterX = UrfQuest.panel.dispCenterX;
		int dispCenterY = UrfQuest.panel.dispCenterY;
		
		g.setColor(Color.WHITE);
		g.drawLine(dispCenterX - 10, dispCenterY, dispCenterX + 10, dispCenterY);
		g.drawLine(dispCenterX, dispCenterY - 10, dispCenterX, dispCenterY + 10);
	}
	
	private void drawStatusBars(Graphics g) {
		int TILE_WIDTH = QuestPanel.TILE_WIDTH;
		QuestGUI.drawStatusBar(g, Color.RED, player.getHealth(), 100, (int)(TILE_WIDTH*0.1), TILE_WIDTH, UrfQuest.panel.getHeight() - (int)(TILE_WIDTH*1.5));
		QuestGUI.drawStatusBar(g, Color.BLUE, player.getMana(), 100, (int)(TILE_WIDTH*0.1), TILE_WIDTH, UrfQuest.panel.getHeight() - (int)(TILE_WIDTH*2.5));
	}
}