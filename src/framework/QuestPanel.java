package framework;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import framework.UrfQuest;
import guis.Overlay;
import guis.OverlayInit;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class QuestPanel extends JPanel {
	public int dispCenterX, dispCenterY; // the center of this JPanel relative to the window's top-left corner, in pixels
	public int dispTileWidth, dispTileHeight; // the number of tiles needed to fill the screen
	public static final int TILE_WIDTH = 30; // the width, in pixels, of each tile
	
	public Overlay currOverlay = OverlayInit.newMainMenu(), prevOverlay;
	
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
				
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					if (currOverlay == null && UrfQuest.time.isRunning()) {
						UrfQuest.time.stop();
						currOverlay = OverlayInit.newPauseMenu();
						UrfQuest.game.toggleGUIVisible();
						repaint();
					} else if (currOverlay.getName() == "pause" && !UrfQuest.time.isRunning()) {
						UrfQuest.time.start();
						currOverlay = null;
						UrfQuest.game.toggleGUIVisible();
						repaint();
					}
				}
				if (e.getKeyCode() == KeyEvent.VK_X) {
					UrfQuest.game.cycleMinimapSize();
				}
			}
			public void keyTyped(KeyEvent e) {}
		});
		
		this.addMouseMotionListener(new MouseMotionListener() {
			public void mouseDragged(MouseEvent e) {
				UrfQuest.mousePos[0] = e.getX();
				UrfQuest.mousePos[1] = e.getY();
				repaint();
			}
			public void mouseMoved(MouseEvent e) {
				UrfQuest.mousePos[0] = e.getX();
				UrfQuest.mousePos[1] = e.getY();
				repaint();
			}
		});
		
		this.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e) {
				if (currOverlay != null) {
					currOverlay.click();
					repaint();
				}
			}
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {
				UrfQuest.mousePressed = true;
				repaint();
			}
			public void mouseReleased(MouseEvent e) {
				UrfQuest.mousePressed = false;
				repaint();
			}
		});
	}
	
	// Paint methods
	public void paintComponent(Graphics g) {
		UrfQuest.game.drawGame(g);
		if (currOverlay != null) {
			currOverlay.draw(g);
		}
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