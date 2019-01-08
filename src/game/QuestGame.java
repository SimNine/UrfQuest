package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

import entities.Entity;
import entities.characters.Player;
import entities.items.Item;
import entities.items.Key;
import framework.UrfQuest;
import guis.QuestGUI;
import framework.QuestPanel;
import tiles.Tiles;

public class QuestGame {
	
	private QuestMap currMap;
	private ArrayList<QuestMap> maps;
	private boolean guiVisible;
	public Player player;
	
	private int keyCount = 0;
	
	public QuestGame() {
		currMap = new QuestMap(500, 500);
		maps = new ArrayList<QuestMap>();
		maps.add(currMap);
		player = new Player(currMap.getWidth()/2.0, currMap.getHeight()/2.0);
		guiVisible = false;
	}
	
	public void tick() {
		currMap.update();
		player.update();
	}
	
	public QuestMap getCurrMap() {
		return currMap;
	}
	
	public void setCurrMap(QuestMap map) {
		currMap = map;
	}
	
	public ArrayList<QuestMap> getAllMaps() {
		return maps;
	}
	
	public void setAllMaps(ArrayList<QuestMap> maps) {
		this.maps = maps;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public void setPlayer(Player p) {
		this.player = p;
	}
	
	public int getKeyCount() {
		return keyCount;
	}
	
	public void incKeyCount(int i) {
		keyCount += i;
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
			//drawMinimap(g);
			drawKeyCounter(g);
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
		for (int x = -(int)Math.floor(dispTileWidth/2.0); x < Math.ceil(dispTileWidth/2.0) + 1; x++) {
			for (int y = -(int)Math.floor(dispTileHeight/2.0); y < Math.ceil(dispTileHeight/2.0) + 1; y++) {
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
		g.drawString("PlayerBlockCoords: " + player.getPosition()[0]+15/QuestPanel.TILE_WIDTH + ", " + player.getPosition()[1]+15/QuestPanel.TILE_WIDTH, 10, 20);
		g.drawString("PlayerActualCoords: " + player.getPosition()[0] + ", " + player.getPosition()[1], 10, 30);
		g.drawString("DisplayCenter: " + UrfQuest.panel.dispCenterX + ", " + UrfQuest.panel.dispCenterY, 10, 40);
		g.drawString("DisplayDimensions: " + UrfQuest.panel.dispTileWidth + ", " + UrfQuest.panel.dispTileHeight, 10, 50);
		g.drawString("CharacterDirection: " + player.getOrientation(), 10, 60);
		g.drawString("CharacterHealth: " + player.getHealth(), 10, 70);
		g.drawString("CharacterMana: " + player.getMana(), 10, 80);
		g.drawString("CharacterSpeed: " + player.getSpeed(), 10, 90);
	}
	
	private void drawSprites(Graphics g) {
		for (Entity e : currMap.entities) {
			e.draw(g);
		}
		
		for (Item i : currMap.items) {
			i.draw(g);
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
	
	private void drawMinimap(Graphics g) {
		g.setColor(Color.BLACK);
		g.drawOval(UrfQuest.panel.getWidth() - 220, 20, 200, 200);
	}
	
	private void drawKeyCounter(Graphics g) {
		g.setColor(new Color(128, 128, 128, 180));
		g.fillRect(UrfQuest.panel.getWidth()-120, UrfQuest.panel.getHeight()-80, 120, 80);
		
		g.drawImage(Key.getPic(), UrfQuest.panel.getWidth()-110, UrfQuest.panel.getHeight()-70, 60, 60, null);
		
		g.setColor(Color.YELLOW);
		g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 50));
		g.drawString("" + keyCount, UrfQuest.panel.getWidth()-60, UrfQuest.panel.getHeight()-30);
		g.setColor(Color.WHITE);
		g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 10));
		
	}
	
	private void drawStatusBars(Graphics g) {
		g.setColor(new Color(128, 128, 128, 180));
		g.fillRect(0, UrfQuest.panel.getHeight()-110, 380, 110);
		
		int TILE_WIDTH = QuestPanel.TILE_WIDTH;
		QuestGUI.drawStatusBar(g, Color.RED, player.getHealth(), 100, (int)(TILE_WIDTH*0.1), TILE_WIDTH, UrfQuest.panel.getHeight() - (int)(TILE_WIDTH*1.5));
		QuestGUI.drawStatusBar(g, Color.BLUE, player.getMana(), 100, (int)(TILE_WIDTH*0.1), TILE_WIDTH, UrfQuest.panel.getHeight() - (int)(TILE_WIDTH*2.5));
	}
}