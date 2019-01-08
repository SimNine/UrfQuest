package framework;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayDeque;
import java.util.Iterator;

import framework.UrfQuest;
import guis.Overlay;
import guis.OverlayInit;
import guis.game.GameBoardOverlay;
import guis.game.GameStatusOverlay;
import guis.game.MapViewOverlay;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class QuestPanel extends JPanel {
	public int dispCenterX, dispCenterY; // the center of this JPanel relative to the window's top-left corner, in pixels
	public int dispTileWidth, dispTileHeight; // the number of tiles needed to fill the screen
	public static final int TILE_WIDTH = 30; // the width, in pixels, of each tile
	
	private ArrayDeque<Overlay> overlays = new ArrayDeque<Overlay>();
	public GameStatusOverlay gameStatus;
	public GameBoardOverlay gameBoard;
	public MapViewOverlay mapView;
	public Overlay mainMenu;
	public Overlay pauseMenu;
	public Overlay optionsMenu;
	
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
				
				if (e.getKeyCode() == KeyEvent.VK_P && 
					UrfQuest.keys.contains(KeyEvent.VK_CONTROL) &&
					UrfQuest.keys.contains(KeyEvent.VK_F)) {
					String command = JOptionPane.showInputDialog(UrfQuest.panel, "Command Prompt", null);
					CommandProcessor.process(command);
				}
			}
			public void keyReleased(KeyEvent e) {
				UrfQuest.keys.remove(e.getKeyCode());
				
				if (UrfQuest.debug) {
					System.out.println("keypress: " + e.getKeyChar());
				}
				
				if (UrfQuest.time.isRunning()) {
					switch (e.getKeyCode()) {
					case KeyEvent.VK_ESCAPE:
						pause();
						break;
					case KeyEvent.VK_X:
						gameStatus.cycleMinimapSize();
						break;
					case KeyEvent.VK_Q:
						UrfQuest.game.dropOneOfSelectedItem();
						break;
					case KeyEvent.VK_SPACE:
						//UrfQuest.game.useSelectedItem();
						break;
					case KeyEvent.VK_M:
						if (overlays.getLast() instanceof MapViewOverlay) {
							swap(gameStatus);
						} else {
							swap(mapView);
						}
						break;
					case KeyEvent.VK_1:
						UrfQuest.game.setSelectedEntry(0);
						break;
					case KeyEvent.VK_2:
						UrfQuest.game.setSelectedEntry(1);
						break;
					case KeyEvent.VK_3:
						UrfQuest.game.setSelectedEntry(2);
						break;
					case KeyEvent.VK_4:
						UrfQuest.game.setSelectedEntry(3);
						break;
					case KeyEvent.VK_5:
						UrfQuest.game.setSelectedEntry(4);
						break;
					case KeyEvent.VK_6:
						UrfQuest.game.setSelectedEntry(5);
						break;
					case KeyEvent.VK_7:
						UrfQuest.game.setSelectedEntry(6);
						break;
					case KeyEvent.VK_8:
						UrfQuest.game.setSelectedEntry(7);
						break;
					case KeyEvent.VK_9:
						UrfQuest.game.setSelectedEntry(8);
						break;
					case KeyEvent.VK_0:
						UrfQuest.game.setSelectedEntry(9);
						break;
					}
					repaint();
				} else if (!UrfQuest.time.isRunning()) {
					if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
						unpause();
					}
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
				Iterator<Overlay> it = overlays.descendingIterator();
				while (it.hasNext()) {
					if (it.next().click()) {
						return;
					}
				}
				repaint();
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
	
	// overlay management
	public void pause() {
		UrfQuest.time.stop();
		addLast(pauseMenu);
	}
	
	public void unpause() {
		UrfQuest.time.start();
		overlays.removeLast();
	}
	
	public void swap(Overlay o) {
		overlays.removeLast();
		addLast(o);
	}
	
	public void addFirst(Overlay o) {
		o.resetObjectBounds();
		overlays.addFirst(o);
	}
	
	public void addLast(Overlay o) {
		o.resetObjectBounds();
		overlays.addLast(o);
	}
	
	public void initOverlays() {
		gameBoard = new GameBoardOverlay();
		gameStatus = new GameStatusOverlay();
		mapView = new MapViewOverlay();
		mainMenu = OverlayInit.newMainMenu();
		pauseMenu = OverlayInit.newPauseMenu();
		optionsMenu = OverlayInit.newOptionsOverlay();
		
		overlays.add(gameBoard);
		overlays.add(gameStatus);
		overlays.add(mainMenu);
	}
	
	// paint methods
	public void paintComponent(Graphics g) {
		Iterator<Overlay> it = overlays.iterator();
		while (it.hasNext()) {
			it.next().draw(g);
		}
	}
	
	// sets the dimensions of the panel, in pixels
	public void setSize(int w, int h) {
		super.setSize(w, h);
		dispCenterX = w/2;
		dispCenterY = h/2;
		dispTileWidth = (int)Math.ceil(w/(TILE_WIDTH))+2;
		dispTileHeight = (int)Math.ceil(h/(TILE_WIDTH))+2;
		
		for (Overlay o : overlays) {
			o.resetObjectBounds();
		}
		
		repaint();
	}
}