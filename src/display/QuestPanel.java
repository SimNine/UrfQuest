package display;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import framework.MapLoader;
import framework.UrfQuest;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class QuestPanel extends JPanel {
	public int dispCenterX, dispCenterY; // the center of this JPanel relative to the window's top-left corner, in pixels
	public int dispTileWidth, dispTileHeight; // the number of tiles needed to fill the screen
	public static final int TILE_WIDTH = 30; // the width, in pixels, of each tile
	
	public QuestPanel() {
		this(640, 480);
	}
	
	public QuestPanel(int initwidth, int initheight) {
		super();
		setFocusable(true);
		requestFocusInWindow();
		setSize(initwidth, initheight);
		
		addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
				UrfQuest.keys.add(e.getKeyCode());
				//repaint();
			}
			public void keyReleased(KeyEvent e) {
				UrfQuest.keys.remove(e.getKeyCode());
				//repaint();

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
		UrfQuest.game.drawGame(g);
	}
	
	// sets the dimensions of the panel, in pixels
	public void setSize(int w, int h) {
		super.setSize(w, h);
		dispCenterX = w/2;
		dispCenterY = h/2;
		dispTileWidth = (int)Math.ceil(w/(TILE_WIDTH))+2;
		dispTileHeight = (int)Math.ceil(h/(TILE_WIDTH))+2;
		repaint();
	}
}