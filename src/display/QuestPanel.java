package display;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JPanel;

import game.QuestMap;

import pixelart.Sprites;

@SuppressWarnings("serial")
public class QuestPanel extends JPanel {
	
	private int scale = 3; // the scale is 1/10th the width of a block, i.e., the width of one "pixel" of a block
	private int dispCenterX; // the center of this JPanel relative to the top-left corner, in pixels
	private int dispCenterY;
	private int dispTileWidth; // the number of blocks needed to fill the screen
	private int dispTileHeight;
	
	private Set<Integer> keys = new HashSet<Integer>(0);
	private StatusBar healthBar = new StatusBar(100, 100, scale, Color.RED);
	private StatusBar manaBar = new StatusBar(100, 100, scale, Color.BLUE);

	private double gameCenterX; // these are updated every tick so that the map can be rendered properly
	private double gameCenterY;
	private int gameCenterTileX;
	private int gameCenterTileY;
	private int charDir;
	private int health;
	private int mana;
	private QuestMap qMap;
	
	public QuestPanel(int initwidth, int initheight) {
		super();
		setFocusable(true);
		requestFocusInWindow();
		setSize(initwidth, initheight);
		addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
				keys.add(e.getKeyCode());
				repaint();
			}
			public void keyReleased(KeyEvent e) {
				keys.remove(e.getKeyCode());
				repaint();
			}
			public void keyTyped(KeyEvent e) {}
		});
	}
	
	public Set<Integer> getKeys() {
		return keys;
	}
	
	public void setQuestMap(QuestMap q) {
		qMap = q;
	}
	
	public void setPlayerLocation(double gcx, double gcy, int gctx, int gcty) {
		gameCenterX = gcx;
		gameCenterY = gcy;
		gameCenterTileX = gctx;
		gameCenterTileY = gcty;
	}
	
	public void setPlayerStatus(int m, int h) {
		manaBar.setVal(m);
		healthBar.setVal(h);
	}
	
	public void setCharDir(int dir) {
		charDir = dir;
	}
	
	// Paint methods
	public void paintComponent(Graphics g) {
		drawBoard(g);
		//drawDebug(g);
		drawSprites(g);
		drawStatusBars(g);
		//drawCursor(g);
	}
	
	private void drawBoard(Graphics g) {
		int tileWidth = scale*10;
		int tempX = (int)((gameCenterX % 1)*tileWidth);
		int tempY = (int)((gameCenterY % 1)*tileWidth);
		for (int x = -(int)Math.floor(dispTileWidth/2.0); x < Math.ceil(dispTileWidth/2.0); x++) {
			for (int y = -(int)Math.floor(dispTileHeight/2.0); y < Math.ceil(dispTileHeight/2.0); y++) {
				switch (qMap.getTileAt(gameCenterTileX + x, gameCenterTileY + y)) {
				case -1:
					g.setColor(Color.BLACK);
					g.fillRect(dispCenterX - tempX + x*tileWidth, dispCenterY - tempY + y*tileWidth, tileWidth, tileWidth);
					break;
				case 0:
					pixelart.Tiles.drawDirtBlock(g, dispCenterX - tempX + x*tileWidth, dispCenterY - tempY + y*tileWidth, scale);
					break;
				case 1:
					pixelart.Tiles.drawStoneBlock(g, dispCenterX - tempX + x*tileWidth, dispCenterY - tempY + y*tileWidth, scale);
					break;
				case 2:
					pixelart.Tiles.drawGrassBlock(g, dispCenterX - tempX + x*tileWidth, dispCenterY - tempY + y*tileWidth, scale);
					break;
				case 3:
					pixelart.Tiles.drawManaPad(g, dispCenterX - tempX + x*tileWidth, dispCenterY - tempY + y*tileWidth, scale);
					break;
				case 4:
					pixelart.Tiles.drawHealthPad(g, dispCenterX - tempX + x*tileWidth, dispCenterY - tempY + y*tileWidth, scale);
					break;
				case 5:
					pixelart.Tiles.drawHurtPad(g, dispCenterX - tempX + x*tileWidth, dispCenterY - tempY + y*tileWidth, scale);
					break;
				case 6:
					pixelart.Tiles.drawSpeedPad(g, dispCenterX - tempX + x*tileWidth, dispCenterY - tempY + y*tileWidth, scale);
					break;
				}
			}
		}
	}
	
	private void drawDebug (Graphics g) {
		g.setColor(Color.WHITE);
		g.drawString(keys.toString(), 10, 10);
		g.drawString("GameCenter: " + gameCenterX + ", " + gameCenterY, 10, 20);
		g.drawString("GameCenterBlock: " + gameCenterTileX + ", " + gameCenterTileY, 10, 30);
		g.drawString("DisplayCenter: " + dispCenterX + ", " + dispCenterY, 10, 40);
		g.drawString("DisplayDimensions: " + dispTileWidth + ", " + dispTileHeight, 10, 50);
		g.drawString("CharacterDirection: " + charDir, 10, 60);
		g.drawString("CharacterHealth: " + healthBar.getVal(), 10, 70);
		g.drawString("CharacterMana: " + manaBar.getVal(), 10, 80);
	}
	
	private void drawSprites(Graphics g) {
		Sprites.drawCharacter(g, dispCenterX - 5*scale, dispCenterY - 5*scale, scale, charDir);
	}

	private void drawCursor(Graphics g) {
		g.setColor(Color.WHITE);
		g.drawLine(dispCenterX - 10, dispCenterY, dispCenterX + 10, dispCenterY);
		g.drawLine(dispCenterX, dispCenterY - 10, dispCenterX, dispCenterY + 10);
	}
	
	private void drawStatusBars(Graphics g) {
		healthBar.drawStatusBar(g, scale*10, getHeight() - scale*15);
		manaBar.drawStatusBar(g, scale*10, getHeight() - scale*25);
	}
	
	// Panel methods
	public void setSize(int w, int h) {
		super.setSize(w, h);
		dispCenterX = w/2;
		dispCenterY = h/2;
		dispTileWidth = (int)Math.ceil(this.getWidth()/(scale*10))+2;
		dispTileHeight = (int)Math.ceil(this.getHeight()/(scale*10))+2;
		System.out.println("DisplayCenter: " + dispCenterX + ", " + dispCenterY);
		System.out.println("DisplayTileDims: " + dispTileWidth + ", " + dispTileHeight);
		repaint();
	}
}