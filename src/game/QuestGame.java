package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import entities.mobs.Mob;
import entities.mobs.Player;
import entities.items.Item;
import entities.particles.Particle;
import framework.UrfQuest;
import guis.QuestGUI;
import framework.QuestPanel;
import tiles.Tiles;

public class QuestGame {

	private int minimapSize = 100; // either 1 (100px across), 2 (200px across), or 3 (300px across)
	private QuestMap currMap;
	private ArrayList<QuestMap> maps;
	private boolean guiVisible;
	public Player player;

	public QuestGame() {
		currMap = new QuestMap(500, 500, QuestMap.SIMPLEX_MAP);
		currMap.generateItems();
		currMap.generateMobs();

		maps = new ArrayList<QuestMap>();
		maps.add(currMap);
		player = new Player(currMap.getWidth() / 2.0, currMap.getHeight() / 2.0);
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

	public boolean isGUIVisible() {
		return guiVisible;
	}
	
	public void hideGUI() {
		guiVisible = false;
	}
	
	public void showGUI() {
		guiVisible = true;
	}
	
	public void cycleMinimapSize() {
		if (minimapSize == 100) {
			minimapSize = 200;
		} else if (minimapSize == 200) {
			minimapSize = 300;
		} else {
			minimapSize = 100;
		}
	}
	
	public void useSelectedItemConstantly() {
		Item i = player.getSelectedItem();
		
		if (i == null) {
			return;
		}

		switch (i.getType()) {
		case "smg":
			currMap.addParticle(new Particle(player.getPosition()[0], player.getPosition()[1]));
			break;
		default:
			break;
		}
	}
	
	public void useSelectedItem() {
		Item i = player.getSelectedItem();
		
		if (i == null) {
			return;
		}
		
		switch (i.getType()) {
		case "pistol":
			currMap.addParticle(new Particle(player.getPosition()[0], player.getPosition()[1]));
			if (UrfQuest.debug) {
				System.out.println("used item: " + i.getType());
			}
			break;
		}
	}
	
	public void dropOneOfSelectedItem() {
		Item i = player.dropOneOfSelectedItem();
		
		if (i == null) {
			return;
		}
		
		double[] playerPos = player.getPosition();
		i.setPosition(playerPos[0], playerPos[1]-1);
		currMap.addItem(i);
	}
	
	public void setSelectedEntry(int i) {
		player.setSelectedEntry(i);
	}

	public void drawGame(Graphics g) {
		drawBoard(g);
		drawEntities(g);

		if (guiVisible) {
			drawMinimap(g);
			drawStatusBars(g);
			drawInventoryBar(g);
		}

		if (UrfQuest.debug) {
			drawCrosshair(g);
			drawDebug(g);
		}
	}

	private void drawBoard(Graphics g) {
		int dispTileWidth = UrfQuest.panel.dispTileWidth;
		int dispTileHeight = UrfQuest.panel.dispTileHeight;
		int TILE_WIDTH = QuestPanel.TILE_WIDTH;
		int dispCenterX = UrfQuest.panel.dispCenterX;
		int dispCenterY = UrfQuest.panel.dispCenterY;

		int tempX = (int) ((player.getPosition()[0] % 1) * TILE_WIDTH);
		int tempY = (int) ((player.getPosition()[1] % 1) * TILE_WIDTH);
		for (int x = -(int) Math.floor(dispTileWidth / 2.0); x < Math.ceil(dispTileWidth / 2.0) + 1; x++) {
			for (int y = -(int) Math.floor(dispTileHeight / 2.0); y < Math.ceil(dispTileHeight / 2.0) + 1; y++) {
				int currTile = (currMap.getTileAt((int) player.getPosition()[0] + x,
						(int) player.getPosition()[1] + y));
				Tiles.drawTile(g, dispCenterX - tempX + x * TILE_WIDTH, dispCenterY - tempY + y * TILE_WIDTH, currTile);
			}
		}
	}

	private void drawDebug(Graphics g) {
		g.setColor(new Color(128, 128, 128, 128));
		g.fillRect(0, 0, 600, 150);

		g.setColor(Color.WHITE);
		g.drawString(UrfQuest.keys.toString(), 10, 10);
		g.drawString("PlayerBlockCoords: " + player.getPosition()[0] + 15 / QuestPanel.TILE_WIDTH + ", "
				+ player.getPosition()[1] + 15 / QuestPanel.TILE_WIDTH, 10, 20);
		g.drawString("PlayerActualCoords: " + player.getPosition()[0] + ", " + player.getPosition()[1], 10, 30);
		g.drawString("DisplayCenter: " + UrfQuest.panel.dispCenterX + ", " + UrfQuest.panel.dispCenterY, 10, 40);
		g.drawString("DisplayDimensions: " + UrfQuest.panel.dispTileWidth + ", " + UrfQuest.panel.dispTileHeight, 10,
				50);
		g.drawString("CharacterDirection: " + player.getOrientation(), 10, 60);
		g.drawString("CharacterHealth: " + player.getHealth(), 10, 70);
		g.drawString("CharacterMana: " + player.getMana(), 10, 80);
		g.drawString("CharacterSpeed: " + player.getSpeed(), 10, 90);
		g.drawString("Number of mobs: " + currMap.mobs.size(), 10, 100);
		g.drawString("Number of items: " + currMap.items.size(), 10, 110);
		g.drawString("Number of particles: " + currMap.particles.size(), 10, 120);
	}

	private void drawEntities(Graphics g) {
		for (Mob m : currMap.mobs) {
			if (m.getPosition()[0] > player.getPosition()[0] - 30 &&
				m.getPosition()[0] < player.getPosition()[0] + 30 &&
				m.getPosition()[1] > player.getPosition()[1] - 30 &&
				m.getPosition()[1] < player.getPosition()[1] + 30)
			m.draw(g);
		}

		for (Item i : currMap.items) {
			if (i.getPosition()[0] > player.getPosition()[0] - 30 &&
				i.getPosition()[0] < player.getPosition()[0] + 30 &&
				i.getPosition()[1] > player.getPosition()[1] - 30 &&
				i.getPosition()[1] < player.getPosition()[1] + 30)
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

	private void drawMinimap(Graphics g) {
		BufferedImage minimap = this.currMap.getMinimap();
		
		int rootX = UrfQuest.panel.getWidth() - minimapSize - 20;
		int rootY = 20;
		g.setColor(Color.BLACK);
		g.fillRect(rootX - 5, rootY - 5, minimapSize + 10, minimapSize + 10);
		
		int cropX = (int)player.getPosition()[0] - minimapSize/2;
		int cropY = (int)player.getPosition()[1] - minimapSize/2;
		if (cropX < 0) {
			cropX = 0;
		}
		if (cropY < 0) {
			cropY = 0;
		}
		if (cropX + minimapSize > minimap.getWidth()) {
			cropX = minimap.getWidth() - minimapSize;
		}
		if (cropY + minimapSize > minimap.getHeight()) {
			cropY = minimap.getHeight() - minimapSize;
		}
		
		// crop the map's minimap to fit the current size
		minimap = minimap.getSubimage(cropX, cropY, minimapSize, minimapSize);
		g.drawImage(minimap, rootX, rootY, null);
		
		// draw a square for each item currently on the minimap
		g.setColor(Color.RED);
		for (Item i : currMap.items) {
			if ((int)i.getPosition()[0] > cropX && (int)i.getPosition()[0] < cropX + minimapSize &&
				(int)i.getPosition()[1] > cropY && (int)i.getPosition()[1] < cropY + minimapSize) {
				g.fillRect(rootX + ((int)i.getPosition()[0]-cropX) - 1, 
						   rootY + ((int)i.getPosition()[1]-cropY) - 1, 3, 3);
			}
		}
		
		// draw a square for each npc currently on the minimap
		g.setColor(Color.YELLOW);
		for (Mob m : currMap.mobs) {
			if ((int)m.getPosition()[0] > cropX && (int)m.getPosition()[0] < cropX + minimapSize &&
				(int)m.getPosition()[1] > cropY && (int)m.getPosition()[1] < cropY + minimapSize) {
				g.fillRect(rootX + ((int)m.getPosition()[0]-cropX) - 1, 
						   rootY + ((int)m.getPosition()[1]-cropY) - 1, 3, 3);
			}
		}
		
		// draw a square for the player
		g.setColor(Color.BLACK);
		int playerIndX = rootX + ((int)player.getPosition()[0]-cropX);
		int playerIndY = rootY + ((int)player.getPosition()[1]-cropY);
		g.fillRect(playerIndX-2, playerIndY-2, 5, 5);
	}
	
	private void drawInventoryBar(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, UrfQuest.panel.getHeight()-50, 500, 50);
		
		int tempx = 0;
		g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 15));
		for (InventoryEntry e : player.getInventory()) {
			if (e.isSelected()) {
				g.setColor(new Color(192, 192, 192));
			} else {
				g.setColor(new Color(128, 128, 128));
			}
			g.fillRect(tempx + 5, UrfQuest.panel.getHeight()-45, 40, 40);
			if (!e.isEmpty()) {
				g.drawImage(e.getPic(), tempx + 5, UrfQuest.panel.getHeight()-45, 40, 40, null);
				if (e.isStack()) {
					g.setColor(Color.BLACK);
					g.drawString("" + e.getNumItems(), tempx + 5, UrfQuest.panel.getHeight()-35);
				}
			}
			tempx += 50;
		}
	}

	private void drawStatusBars(Graphics g) {
		QuestGUI.drawStatusBar(g, Color.RED, player.getHealth(), 100, 3, 0,	UrfQuest.panel.getHeight() - 80);
		QuestGUI.drawStatusBar(g, Color.BLUE, player.getMana(), 100, 3, 0, UrfQuest.panel.getHeight() - 65);
		//QuestGUI.drawStatusBar(g, Color.GREEN, player.getSpeed(), 1, 3, 0, UrfQuest.panel.getHeight() - 95);
	}
}