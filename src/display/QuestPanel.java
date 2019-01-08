package display;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import tiles.Tiles;

import framework.MapLoader;
import framework.V;

import javax.swing.JPanel;

import entities.Entity;

@SuppressWarnings("serial")
public class QuestPanel extends JPanel {
	
	public QuestPanel(int initwidth, int initheight) {
		super();
		setFocusable(true);
		requestFocusInWindow();
		setSize(initwidth, initheight);
		
		V.qPanel = this;
		
		addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
				V.keys.add(e.getKeyCode());
				repaint();
			}
			public void keyReleased(KeyEvent e) {
				V.keys.remove(e.getKeyCode());
				repaint();

				if (e.getKeyChar() == 's') {
					MapLoader.saveLevel();
				} else if (e.getKeyChar() == 'l') {
					MapLoader.loadLevel();
				}
			}
			public void keyTyped(KeyEvent e) {}
		});
	}
	
	// Paint methods
	public void paintComponent(Graphics g) {
		drawBoard(g);
		drawSprites(g);
		drawStatusBars(g);
		
		if (V.debug) drawCrosshair(g);
		if (V.debug) drawDebug(g);
	}
	
	private void drawBoard(Graphics g) {
		int tempX = (int)((V.player.getPosition()[0] % 1)*V.tileWidth);
		int tempY = (int)((V.player.getPosition()[1] % 1)*V.tileWidth);
		for (int x = -(int)Math.floor(V.dispTileWidth/2.0); x < Math.ceil(V.dispTileWidth/2.0); x++) {
			for (int y = -(int)Math.floor(V.dispTileHeight/2.0); y < Math.ceil(V.dispTileHeight/2.0); y++) {
				switch (V.qMap.getTileAt((int)V.player.getPosition()[0] + x, (int)V.player.getPosition()[1] + y)) {
				case -1:
					g.setColor(Color.BLACK);
					g.fillRect(V.dispCenterX - tempX + x*V.tileWidth, V.dispCenterY - tempY + y*V.tileWidth, V.tileWidth, V.tileWidth);
					break;
				case 0:
					Tiles.drawDirtBlock(g, V.dispCenterX - tempX + x*V.tileWidth, V.dispCenterY - tempY + y*V.tileWidth);
					break;
				case 1:
					Tiles.drawStoneBlock(g, V.dispCenterX - tempX + x*V.tileWidth, V.dispCenterY - tempY + y*V.tileWidth);
					break;
				case 2:
					Tiles.drawGrassBlock(g, V.dispCenterX - tempX + x*V.tileWidth, V.dispCenterY - tempY + y*V.tileWidth);
					break;
				case 3:
					Tiles.drawManaPad(g, V.dispCenterX - tempX + x*V.tileWidth, V.dispCenterY - tempY + y*V.tileWidth, V.tileWidth);
					break;
				case 4:
					Tiles.drawHealthPad(g, V.dispCenterX - tempX + x*V.tileWidth, V.dispCenterY - tempY + y*V.tileWidth, V.tileWidth);
					break;
				case 5:
					Tiles.drawHurtPad(g, V.dispCenterX - tempX + x*V.tileWidth, V.dispCenterY - tempY + y*V.tileWidth, V.tileWidth);
					break;
				case 6:
					Tiles.drawSpeedPad(g, V.dispCenterX - tempX + x*V.tileWidth, V.dispCenterY - tempY + y*V.tileWidth, V.tileWidth);
					break;
				}
			}
		}
	}
	
	private void drawDebug (Graphics g) {
		g.setColor(Color.WHITE);
		g.drawString(V.keys.toString(), 10, 10);
		g.drawString("GameCenter: " + V.player.getPosition()[0] + ", " + V.player.getPosition()[1], 10, 20);
		g.drawString("GameCenterBlock: " + (int)V.player.getPosition()[0] + ", " + (int)V.player.getPosition()[1], 10, 30);
		g.drawString("DisplayCenter: " + V.dispCenterX + ", " + V.dispCenterY, 10, 40);
		g.drawString("DisplayDimensions: " + V.dispTileWidth + ", " + V.dispTileHeight, 10, 50);
		g.drawString("CharacterDirection: " + V.player.getOrientation(), 10, 60);
		g.drawString("CharacterHealth: " + V.player.getHealth(), 10, 70);
		g.drawString("CharacterMana: " + V.player.getMana(), 10, 80);
	}
	
	private void drawSprites(Graphics g) {
		for (Entity e : V.entities) {
			e.draw(g);
		}
		V.player.draw(g);
	}

	private void drawCrosshair(Graphics g) {
		g.setColor(Color.WHITE);
		g.drawLine(V.dispCenterX - 10, V.dispCenterY, V.dispCenterX + 10, V.dispCenterY);
		g.drawLine(V.dispCenterX, V.dispCenterY - 10, V.dispCenterX, V.dispCenterY + 10);
	}
	
	private void drawStatusBars(Graphics g) {
		QuestGUI.drawStatusBar(g, Color.RED, V.player.getHealth(), 100, (int)(V.tileWidth*0.1), V.tileWidth, getHeight() - (int)(V.tileWidth*1.5));
		QuestGUI.drawStatusBar(g, Color.BLUE, V.player.getMana(), 100, (int)(V.tileWidth*0.1), V.tileWidth, getHeight() - (int)(V.tileWidth*2.5));
	}
	
	// sets the dimensions of the panel, in pixels
	public void setSize(int w, int h) {
		super.setSize(w, h);
		V.dispCenterX = w/2;
		V.dispCenterY = h/2;
		V.dispTileWidth = (int)Math.ceil(w/(V.tileWidth))+2;
		V.dispTileHeight = (int)Math.ceil(h/(V.tileWidth))+2;
		System.out.println("DisplayCenter: " + V.dispCenterX + ", " + V.dispCenterY);
		System.out.println("DisplayTileDims: " + V.dispTileWidth + ", " + V.dispTileHeight);
		repaint();
	}
}