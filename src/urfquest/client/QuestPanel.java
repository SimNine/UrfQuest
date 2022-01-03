package urfquest.client;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import urfquest.Logger;
import urfquest.Main;
import urfquest.client.entities.Entity;
import urfquest.client.entities.mobs.Player;
import urfquest.client.guis.GUIContainer;
import urfquest.client.guis.OverlayInit;
import urfquest.client.guis.game.ChatOverlay;
import urfquest.client.guis.game.CraftingOverlay;
import urfquest.client.guis.game.GameBoardOverlay;
import urfquest.client.guis.game.GameStatusOverlay;
import urfquest.client.guis.game.GameWeatherOverlay;
import urfquest.client.guis.game.MapViewOverlay;
import urfquest.client.guis.menus.KeybindingOverlay;
import urfquest.client.tiles.Tiles;
import urfquest.shared.message.Message;
import urfquest.shared.message.MessageType;

import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class QuestPanel extends JPanel {
	public int dispCenterX, dispCenterY; // the center of this JPanel relative to the window's top-left corner, in pixels
	public int dispTileWidth, dispTileHeight; // the number of tiles needed to fill the screen
	public static final int TILE_WIDTH = 30; // the width, in pixels, of each tile
	
	// user input fields
	public Set<Integer> keys = new HashSet<Integer>(0);
	public int[] mousePos = new int[2];
	public boolean mouseDown = false;
	
	private Deque<GUIContainer> overlays = new ArrayDeque<GUIContainer>();
	public GUIContainer mainMenu;
	public GUIContainer pauseMenu;
	public GUIContainer optionsMenu;
	
	public ChatOverlay chatOverlay;
	public GameStatusOverlay gameStatus;
	public GameBoardOverlay gameBoard;
	public GameWeatherOverlay gameWeather;
	public MapViewOverlay mapView;
	public CraftingOverlay craftingView;
	public KeybindingOverlay keybindingView;
	
	private boolean guiOpen = false;
	private boolean isUserIngame = false;
	
	private Keybindings keybindings = new Keybindings();

    public Timer renderTimer = new Timer(30, new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            Main.panel.repaint();
        }
    });
    
    public Timer inputScanTimer = new Timer(30, new ActionListener() {
    	public void actionPerformed(ActionEvent e) {
    		scanHeldKeys();
    	}
    });
	
	public QuestPanel() {
		this(640, 480);
	}
	
	public QuestPanel(int initwidth, int initheight) {
		super();
		setFocusable(true);
		requestFocusInWindow();
		setSize(initwidth, initheight);
		
		initAssets();
		
		addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
				keys.add(e.getKeyCode());
				
				// if currently on the keybinding page
				if (overlays.peek() == keybindingView) {
					keybindingView.keypress(e.getKeyCode());
				} else {
					if (e.getKeyCode() == keybindings.FULLSCREEN) {
						Main.resetFrame(!Main.isFullscreen);
					} else if (e.getKeyCode() == keybindings.CYCLE_DEBUG) {
						// TODO: incorporate a method to cycle through all modes
						if (Main.logger.getLogLevel() == Logger.LogLevel.LOG_DEBUG) {
							Main.logger.setLogLevel(Logger.LogLevel.LOG_WARNING);
						} else {
							Main.logger.setLogLevel(Logger.LogLevel.LOG_DEBUG);
						}
					}
				}
				
				if (isUserIngame) {
					if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
						pause();
					}
					
					if (guiOpen) {
						GUIContainer currentOverlay = overlays.peek();
						if (currentOverlay == mapView) {
							if (e.getKeyCode() == keybindings.TOGGLEMAPVIEW) {
								swap(gameStatus);
								guiOpen = false;
							}
						} else if (currentOverlay == craftingView) {
							if (e.getKeyCode() == keybindings.CRAFTING) {
								swap(gameStatus);
								guiOpen = false;
							}
						} else if (currentOverlay == chatOverlay) {
							if (e.getKeyCode() == KeyEvent.VK_ENTER) {
								swap(gameStatus);
								guiOpen = false;
							}
							chatOverlay.keypress(e);
						}
					} else {
						if (e.getKeyCode() == keybindings.CONSOLE) {
							//String command = JOptionPane.showInputDialog(UrfQuest.panel, "Command Prompt", null);
							//CommandProcessor.process(command);
						} else if (e.getKeyCode() == keybindings.CYCLE_MINIMAP) {
							gameStatus.cycleMinimapSize();
						} else if (e.getKeyCode() == keybindings.DROPITEM) {
							Main.client.getState().getPlayer().dropOneOfSelectedItem();
						} else if (e.getKeyCode() == keybindings.BUILDMODE) {
							//UrfQuestClient.client.getState().toggleBuildMode();
						} else if (e.getKeyCode() == KeyEvent.VK_1) {
							Main.client.getState().getPlayer().setSelectedEntry(0);
						} else if (e.getKeyCode() == KeyEvent.VK_2) {
							Main.client.getState().getPlayer().setSelectedEntry(1);
						} else if (e.getKeyCode() == KeyEvent.VK_3) {
							Main.client.getState().getPlayer().setSelectedEntry(2);
						} else if (e.getKeyCode() == KeyEvent.VK_4) {
							Main.client.getState().getPlayer().setSelectedEntry(3);
						} else if (e.getKeyCode() == KeyEvent.VK_5) {
							Main.client.getState().getPlayer().setSelectedEntry(4);
						} else if (e.getKeyCode() == KeyEvent.VK_6) {
							Main.client.getState().getPlayer().setSelectedEntry(5);
						} else if (e.getKeyCode() == KeyEvent.VK_7) {
							Main.client.getState().getPlayer().setSelectedEntry(6);
						} else if (e.getKeyCode() == KeyEvent.VK_8) {
							Main.client.getState().getPlayer().setSelectedEntry(7);
						} else if (e.getKeyCode() == KeyEvent.VK_9) {
							Main.client.getState().getPlayer().setSelectedEntry(8);
						} else if (e.getKeyCode() == KeyEvent.VK_0) {
							Main.client.getState().getPlayer().setSelectedEntry(9);
						} else if (e.getKeyCode() == keybindings.TOGGLEMAPVIEW) {
							swap(mapView);
							guiOpen = true;
						} else if (e.getKeyCode() == keybindings.CRAFTING) {
							swap(craftingView);
							guiOpen = true;
						} else if (e.getKeyCode() == keybindings.MAPLINK) {
							Main.client.getState().getPlayer().useTileUnderneath();
						} else if (e.getKeyCode() == keybindings.CHAT) {
							swap(chatOverlay);
							guiOpen = true;
						} else if (e.getKeyCode() == KeyEvent.VK_F4) {
							System.out.println(Main.client.getState().getPlayer().getCenter()[0] + "," +
											   Main.client.getState().getPlayer().getCenter()[1]);
							
							Message m = new Message();
							m.type = MessageType.DEBUG_PLAYER_INFO;
							Main.client.send(m);
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
			public void keyReleased(KeyEvent e) {
				keys.remove(e.getKeyCode());
				Main.logger.debug("key released: " + e.getKeyChar());
			}
			public void keyTyped(KeyEvent e) {}
		});
		
		addMouseMotionListener(new MouseMotionListener() {
			public void mouseDragged(MouseEvent e) {
				mousePos[0] = e.getX();
				mousePos[1] = e.getY();
				if (Main.client.getState().isBuildMode() && Main.client.getState().isGameRunning() && !guiOpen) {
					gameBoard.click();
				}
			}
			public void mouseMoved(MouseEvent e) {
				mousePos[0] = e.getX();
				mousePos[1] = e.getY();
			}
		});
		
		addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e) {
				Iterator<GUIContainer> it = overlays.iterator();
				while (it.hasNext()) {
					if (it.next().click()) {
						return;
					}
				}
			}
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {
				mouseDown = true;
			}
			public void mouseReleased(MouseEvent e) {
				mouseDown = false;
				Main.logger.debug(windowToGameX(mousePos[0]) + ", " + windowToGameY(mousePos[1]));
			}
		});
	}
	
	private void scanHeldKeys() {
		if (guiOpen) {
			// do not send a message that the user is trying to move, if a GUI is open
			return;
		}
		
		double xDiff = 0;
		double yDiff = 0;
		
		boolean keysHeld = false;
		
		if (keys.contains(KeyEvent.VK_W)) {
			yDiff += -1;
			keysHeld = true;
		} else if (keys.contains(KeyEvent.VK_S)) {
			yDiff += 1;
			keysHeld = true;
		}
		
		if (keys.contains(KeyEvent.VK_A)) {
			xDiff += -1;
			keysHeld = true;
		} else if (keys.contains(KeyEvent.VK_D)) {
			xDiff += 1;
			keysHeld = true;
		}
		
		if (keysHeld) {
			// TODO: *attempt* to move before actually moving
			// currently, if move is invalid, server has to correct client
			if (Main.client.getState().getPlayer() != null) {
				Main.client.getState().getPlayer().move(xDiff, yDiff);
			} else {
				Main.client.getState().getCamera().move(xDiff, yDiff);
			}
		}
	}
	
	public void setGUIOpen(boolean b) {
		this.guiOpen = b;
	}
	
	public boolean getGUIOpen() {
		return this.guiOpen;
	}
	
	public void setIsUserIngame(boolean b) {
		this.isUserIngame = b;
	}
	
	public boolean getIsUserIngame() {
		return this.isUserIngame;
	}
	
	public Keybindings getKeybindings() {
		return keybindings;
	}
	
	/*
	 * Overlay management
	 */
	
	public void pause() {
		// UrfQuestClient.gameRunning = false; TODO: send pause signal
		this.isUserIngame = false;
		pauseMenu.resetBounds();
		overlays.push(pauseMenu);
	}
	
	public void unpause() {
		// UrfQuestClient.gameRunning = true; TODO: send resume signal
		this.isUserIngame = true;
		overlays.pop();
	}
	
	public void swap(GUIContainer o) {
		overlays.pop();
		o.resetBounds();
		overlays.push(o);
	}
	
	public void initOverlays() {
		gameBoard = new GameBoardOverlay();
		gameStatus = new GameStatusOverlay();
		gameWeather = new GameWeatherOverlay();
		mapView = new MapViewOverlay();
		keybindingView = new KeybindingOverlay();
		craftingView = new CraftingOverlay();
		mainMenu = OverlayInit.newMainMenu();
		pauseMenu = OverlayInit.newPauseMenu();
		optionsMenu = OverlayInit.newOptionsOverlay();
		chatOverlay = new ChatOverlay();
		
		overlays.push(gameBoard);
		//overlays.push(gameWeather);
		overlays.push(gameStatus);
		overlays.push(mainMenu);
	}
	
	public Deque<GUIContainer> getOverlays() {
		return overlays;
	}
	
	public void setOverlays(ArrayDeque<GUIContainer> over) {
		overlays = over;
	}
	
	// paint methods
	public void paintComponent(Graphics g) {
		Iterator<GUIContainer> it = overlays.descendingIterator();
		while (it.hasNext()) {
			it.next().draw(g);
		}
		
		if (Main.logger.getLogLevel().compareTo(Logger.LogLevel.LOG_DEBUG) >= 0) {
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
		
		for (GUIContainer o : overlays) {
			o.resetBounds();
		}
		
		repaint();
	}
	
	/*
	 * Coordinate conversion
	 */
	
	public int gameToWindowX(double x) {
		int xRet = (int)(dispCenterX - (fetchCamera().getPos()[0] - x)*TILE_WIDTH);
		return xRet;
	}
	
	public int gameToWindowY(double y) {
		int yRet = (int)(dispCenterY - (fetchCamera().getPos()[1] - y)*TILE_WIDTH);
		return yRet;
	}
	
	// these two methods are broken for purposes of rendering the board, but they work for finding what tile the mouse is on.
	// i'm not sure why this is the case. don't trust these two methods.
	public double windowToGameX(int x) {
		return fetchCamera().getPos()[0] - (((double)dispCenterX - (double)x) / (double)TILE_WIDTH);
	}
	
	public double windowToGameY(int y) {
		return fetchCamera().getPos()[1] - (((double)dispCenterY - (double)y) / (double)TILE_WIDTH);
	}
	
	/*
	 * CameraMob management
	 */
	
	private Entity fetchCamera() {
		return Main.client.getState().getCamera();
	}
	
//	public void setCamera(Entity m) {
//		camera = m;
//	}
//	
//	public Entity getCamera() {
//		return camera;
//	}
	
	private void initAssets() {
		Player.initGraphics();
		Tiles.initGraphics();
	}
}