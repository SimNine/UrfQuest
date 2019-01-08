package framework;

import java.awt.Color;
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
import guis.menus.KeybindingOverlay;

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
	public KeybindingOverlay keybindingOverlay;
	public Overlay mainMenu;
	public Overlay pauseMenu;
	public Overlay optionsMenu;
	
	private boolean guiOpen = false;
	
	private Keybindings keybindings = new Keybindings();
	
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
				
				if (overlays.getLast() instanceof KeybindingOverlay) {
					keybindingOverlay.keypress(e.getKeyCode());
				}
			}
			public void keyReleased(KeyEvent e) {
				UrfQuest.keys.remove(e.getKeyCode());
				
				if (UrfQuest.debug) {
					System.out.println("key released: " + e.getKeyChar());
				}
				
				if (!(overlays.getLast() instanceof KeybindingOverlay) && e.getKeyCode() == keybindings.FULLSCREEN) {
					UrfQuest.resetFrame(!UrfQuest.isFullscreen);
				}
				
				if (UrfQuest.gameRunning) {
					if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
						pause();
					}
					if (guiOpen) {
						if (e.getKeyCode() == keybindings.TOGGLEMAPVIEW) {
							if (overlays.getLast() instanceof MapViewOverlay) {
								swap(gameStatus);
								guiOpen = false;
							}
						}
					} else {
						if (e.getKeyCode() == keybindings.CONSOLE) {
							String command = JOptionPane.showInputDialog(UrfQuest.panel, "Command Prompt", null);
							CommandProcessor.process(command);
						} else if (e.getKeyCode() == keybindings.CYCLEMINIMAP) {
							gameStatus.cycleMinimapSize();
						} else if (e.getKeyCode() == keybindings.DROPITEM) {
							UrfQuest.game.dropOneOfSelectedItem();
						} else if (e.getKeyCode() == keybindings.BUILDMODE) {
							UrfQuest.game.toggleBuildMode();
						} else if (e.getKeyCode() == KeyEvent.VK_1) {
							UrfQuest.game.setSelectedEntry(0);
						} else if (e.getKeyCode() == KeyEvent.VK_2) {
							UrfQuest.game.setSelectedEntry(1);
						} else if (e.getKeyCode() == KeyEvent.VK_3) {
							UrfQuest.game.setSelectedEntry(2);
						} else if (e.getKeyCode() == KeyEvent.VK_4) {
							UrfQuest.game.setSelectedEntry(3);
						} else if (e.getKeyCode() == KeyEvent.VK_5) {
							UrfQuest.game.setSelectedEntry(4);
						} else if (e.getKeyCode() == KeyEvent.VK_6) {
							UrfQuest.game.setSelectedEntry(5);
						} else if (e.getKeyCode() == KeyEvent.VK_7) {
							UrfQuest.game.setSelectedEntry(6);
						} else if (e.getKeyCode() == KeyEvent.VK_8) {
							UrfQuest.game.setSelectedEntry(7);
						} else if (e.getKeyCode() == KeyEvent.VK_9) {
							UrfQuest.game.setSelectedEntry(8);
						} else if (e.getKeyCode() == KeyEvent.VK_0) {
							UrfQuest.game.setSelectedEntry(9);
						} else if (e.getKeyCode() == keybindings.TOGGLEMAPVIEW) {
							swap(mapView);
							guiOpen = true;
						}
					}
				} else {
					if (!(overlays.getLast() instanceof KeybindingOverlay)) {
						if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
							unpause();
						}
					}
				}
			}
			public void keyTyped(KeyEvent e) {}
		});
		
		addMouseMotionListener(new MouseMotionListener() {
			public void mouseDragged(MouseEvent e) {
				UrfQuest.mousePos[0] = e.getX();
				UrfQuest.mousePos[1] = e.getY();
				if (UrfQuest.game.isBuildMode() && UrfQuest.gameRunning && !guiOpen) {
					gameBoard.click();
				}
			}
			public void mouseMoved(MouseEvent e) {
				UrfQuest.mousePos[0] = e.getX();
				UrfQuest.mousePos[1] = e.getY();
			}
		});
		
		addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e) {
				Iterator<Overlay> it = overlays.descendingIterator();
				while (it.hasNext()) {
					if (it.next().click()) {
						return;
					}
				}
			}
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {
				UrfQuest.mouseDown = true;
			}
			public void mouseReleased(MouseEvent e) {
				UrfQuest.mouseDown = false;
				if (UrfQuest.debug) {
					System.out.println(windowToGameX(UrfQuest.mousePos[0]) + ", " + windowToGameY(UrfQuest.mousePos[1]));
				}
			}
		});
	}
	
	public void setGUIOpen(boolean b) {
		guiOpen = b;
	}
	
	public boolean isGUIOpen() {
		return guiOpen;
	}
	
	public Keybindings getKeybindings() {
		return keybindings;
	}
	
	// overlay management
	public void pause() {
		UrfQuest.gameRunning = false;
		addLast(pauseMenu);
	}
	
	public void unpause() {
		UrfQuest.gameRunning = true;
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
		keybindingOverlay = new KeybindingOverlay();
		mainMenu = OverlayInit.newMainMenu();
		pauseMenu = OverlayInit.newPauseMenu();
		optionsMenu = OverlayInit.newOptionsOverlay();
		
		overlays.add(gameBoard);
		overlays.add(gameStatus);
		overlays.add(mainMenu);
	}
	
	public ArrayDeque<Overlay> getOverlays() {
		return overlays;
	}
	
	public void setOverlays(ArrayDeque<Overlay> over) {
		overlays = over;
	}
	
	// paint methods
	public void paintComponent(Graphics g) {
		Iterator<Overlay> it = overlays.iterator();
		while (it.hasNext()) {
			it.next().draw(g);
		}
		
		if (UrfQuest.debug) {
			g.setColor(Color.WHITE);
			g.drawLine(0, dispCenterY, getWidth(), dispCenterY);
			g.drawLine(dispCenterX, 0, dispCenterX, getHeight());
		}
	}
	
	// sets the dimensions of the panel, in pixels
	public void setSize(int w, int h) {
		super.setSize(w, h);
		dispCenterX = w/2;
		dispCenterY = h/2;
		dispTileWidth = w/TILE_WIDTH;
		dispTileHeight = h/TILE_WIDTH;
		
		for (Overlay o : overlays) {
			o.resetObjectBounds();
		}
		
		repaint();
	}
	
	// coordinate conversion
	public int gameToWindowX(double x) {
		int xRet = (int)(dispCenterX - (UrfQuest.game.getPlayer().getPos()[0] - x)*TILE_WIDTH);
		return xRet;
	}
	
	public int gameToWindowY(double y) {
		int yRet = (int)(dispCenterY - (UrfQuest.game.getPlayer().getPos()[1] - y)*TILE_WIDTH);
		return yRet;
	}
	
	// these two methods are broken for purposes of rendering the board, but they work for finding what tile the mouse is on
	// (don't trust these two methods)
	public double windowToGameX(int x) {
		return UrfQuest.game.getPlayer().getPos()[0] - (((double)dispCenterX - (double)x) / (double)TILE_WIDTH);
	}
	
	public double windowToGameY(int y) {
		return UrfQuest.game.getPlayer().getPos()[1] - (((double)dispCenterY - (double)y) / (double)TILE_WIDTH);
	}
}