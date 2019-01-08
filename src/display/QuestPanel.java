package display;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import framework.MapLoader;
import framework.V;

import javax.swing.JPanel;

import entities.Entity;
import pixelart.Sprites;

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
		
		drawCrosshair(g);
		drawDebug(g);
	}
	
	private void drawBoard(Graphics g) {
		int tileWidth = V.scale*10;
		int tempX = (int)((V.playerPositionX % 1)*tileWidth);
		int tempY = (int)((V.playerPositionY % 1)*tileWidth);
		for (int x = -(int)Math.floor(V.dispTileWidth/2.0); x < Math.ceil(V.dispTileWidth/2.0); x++) {
			for (int y = -(int)Math.floor(V.dispTileHeight/2.0); y < Math.ceil(V.dispTileHeight/2.0); y++) {
				switch (V.qMap.getTileAt((int)V.playerPositionX + x, (int)V.playerPositionY + y)) {
				case -1:
					g.setColor(Color.BLACK);
					g.fillRect(V.dispCenterX - tempX + x*tileWidth, V.dispCenterY - tempY + y*tileWidth, tileWidth, tileWidth);
					break;
				case 0:
					pixelart.Tiles.drawDirtBlock(g, V.dispCenterX - tempX + x*tileWidth, V.dispCenterY - tempY + y*tileWidth, V.scale);
					break;
				case 1:
					pixelart.Tiles.drawStoneBlock(g, V.dispCenterX - tempX + x*tileWidth, V.dispCenterY - tempY + y*tileWidth, V.scale);
					break;
				case 2:
					pixelart.Tiles.drawGrassBlock(g, V.dispCenterX - tempX + x*tileWidth, V.dispCenterY - tempY + y*tileWidth, V.scale);
					break;
				case 3:
					pixelart.Tiles.drawManaPad(g, V.dispCenterX - tempX + x*tileWidth, V.dispCenterY - tempY + y*tileWidth, V.scale);
					break;
				case 4:
					pixelart.Tiles.drawHealthPad(g, V.dispCenterX - tempX + x*tileWidth, V.dispCenterY - tempY + y*tileWidth, V.scale);
					break;
				case 5:
					pixelart.Tiles.drawHurtPad(g, V.dispCenterX - tempX + x*tileWidth, V.dispCenterY - tempY + y*tileWidth, V.scale);
					break;
				case 6:
					pixelart.Tiles.drawSpeedPad(g, V.dispCenterX - tempX + x*tileWidth, V.dispCenterY - tempY + y*tileWidth, V.scale);
					break;
				}
			}
		}
	}
	
	private void drawDebug (Graphics g) {
		g.setColor(Color.WHITE);
		g.drawString(V.keys.toString(), 10, 10);
		g.drawString("GameCenter: " + V.playerPositionX + ", " + V.playerPositionY, 10, 20);
		g.drawString("GameCenterBlock: " + (int)V.playerPositionX + ", " + (int)V.playerPositionY, 10, 30);
		g.drawString("DisplayCenter: " + V.dispCenterX + ", " + V.dispCenterY, 10, 40);
		g.drawString("DisplayDimensions: " + V.dispTileWidth + ", " + V.dispTileHeight, 10, 50);
		g.drawString("CharacterDirection: " + V.playerOrientation, 10, 60);
		g.drawString("CharacterHealth: " + V.health, 10, 70);
		g.drawString("CharacterMana: " + V.mana, 10, 80);
	}
	
	private void drawSprites(Graphics g) {
		Sprites.drawCharacterPlaceholder(g, V.dispCenterX - 5*V.scale, V.dispCenterY - 5*V.scale, V.scale, V.playerOrientation);
		for (Entity e : V.entities) {
			e.draw(g);
		}
	}

	private void drawCrosshair(Graphics g) {
		g.setColor(Color.WHITE);
		g.drawLine(V.dispCenterX - 10, V.dispCenterY, V.dispCenterX + 10, V.dispCenterY);
		g.drawLine(V.dispCenterX, V.dispCenterY - 10, V.dispCenterX, V.dispCenterY + 10);
	}
	
	private void drawStatusBars(Graphics g) {
		QuestGUI.drawStatusBar(g, Color.RED, V.health, 100, V.scale, V.scale*10, getHeight() - V.scale*15);
		QuestGUI.drawStatusBar(g, Color.BLUE, V.mana, 100, V.scale, V.scale*10, getHeight() - V.scale*25);
	}
	
	// Panel methods
	public void setSize(int w, int h) {
		super.setSize(w, h);
		V.dispCenterX = w/2;
		V.dispCenterY = h/2;
		V.dispTileWidth = (int)Math.ceil(w/(V.scale*10))+2;
		V.dispTileHeight = (int)Math.ceil(h/(V.scale*10))+2;
		System.out.println("DisplayCenter: " + V.dispCenterX + ", " + V.dispCenterY);
		System.out.println("DisplayTileDims: " + V.dispTileWidth + ", " + V.dispTileHeight);
		repaint();
	}
}