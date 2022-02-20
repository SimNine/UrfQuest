package urfquest.client;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Toolkit;
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

import urfquest.LogLevel;
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
import urfquest.shared.Constants;
import urfquest.shared.Vector;
import urfquest.shared.message.Message;
import urfquest.shared.message.MessageType;

import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class QuestPanel extends JPanel implements KeyListener, MouseListener, MouseMotionListener {
	private Client client;
	
	public int dispCenterX, dispCenterY; // the center of this JPanel relative to the window's top-left corner, in pixels
	public int dispTileWidth, dispTileHeight; // the number of tiles needed to fill the screen
	public static final int TILE_WIDTH = 30; // the width, in pixels, of each tile
	
	// user input fields
	public Set<Integer> keys = new HashSet<Integer>(0);
	public Set<Integer> prevMovementKeys = new HashSet<Integer>();
	public int[] mousePos = new int[2];
	public boolean mouseDown = false;
	
	public static final Set<Integer> MOVEMENT_KEYS = new HashSet<Integer>();
	static {
		MOVEMENT_KEYS.add(KeyEvent.VK_W);
		MOVEMENT_KEYS.add(KeyEvent.VK_A);
		MOVEMENT_KEYS.add(KeyEvent.VK_S);
		MOVEMENT_KEYS.add(KeyEvent.VK_D);
	}
	
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
            repaint();
        }
    });
    
    public Timer inputScanTimer = new Timer(30, new ActionListener() {
    	public void actionPerformed(ActionEvent e) {
    		// TODO: find a better way to scan for held keys.
    		// scanHeldKeys();
    	}
    });
	
	public QuestPanel(Client c) {
		this(c, 640, 480);
	}
	
	public QuestPanel(Client c, int initwidth, int initheight) {
		super();
		this.client = c;
		setFocusable(true);
		requestFocusInWindow();
		setSize(initwidth, initheight);
		
		this.addKeyListener(this);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		
		initAssets();
	}
	
	/*
	 * Listeners
	 */
	
	@Override
	public void keyPressed(KeyEvent e) {
		keys.add(e.getKeyCode());
		scanHeldKeys();
		
		// if currently on the keybinding page
		if (overlays.peek() == keybindingView) {
			keybindingView.keypress(e.getKeyCode());
		} else {
			if (e.getKeyCode() == keybindings.FULLSCREEN) {
				client.resetFrame(client.isFullscreen);
			} else if (e.getKeyCode() == keybindings.CYCLE_DEBUG) {
				// TODO: incorporate a method to cycle through all modes
				if (client.getLogger().getLogLevel() == LogLevel.DEBUG) {
					client.getLogger().setLogLevel(LogLevel.WARNING);
				} else {
					client.getLogger().setLogLevel(LogLevel.DEBUG);
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
						guiOpen = false;
						chatOverlay.setOpaqueChatbox(false);
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
					client.getState().getPlayer().dropOneOfSelectedItem();
				} else if (e.getKeyCode() == keybindings.BUILDMODE) {
					//UrfQuestClient.client.getState().toggleBuildMode();
				} else if (e.getKeyCode() == KeyEvent.VK_1) {
					client.getState().getPlayer().setSelectedEntry(0);
				} else if (e.getKeyCode() == KeyEvent.VK_2) {
					client.getState().getPlayer().setSelectedEntry(1);
				} else if (e.getKeyCode() == KeyEvent.VK_3) {
					client.getState().getPlayer().setSelectedEntry(2);
				} else if (e.getKeyCode() == KeyEvent.VK_4) {
					client.getState().getPlayer().setSelectedEntry(3);
				} else if (e.getKeyCode() == KeyEvent.VK_5) {
					client.getState().getPlayer().setSelectedEntry(4);
				} else if (e.getKeyCode() == KeyEvent.VK_6) {
					client.getState().getPlayer().setSelectedEntry(5);
				} else if (e.getKeyCode() == KeyEvent.VK_7) {
					client.getState().getPlayer().setSelectedEntry(6);
				} else if (e.getKeyCode() == KeyEvent.VK_8) {
					client.getState().getPlayer().setSelectedEntry(7);
				} else if (e.getKeyCode() == KeyEvent.VK_9) {
					client.getState().getPlayer().setSelectedEntry(8);
				} else if (e.getKeyCode() == KeyEvent.VK_0) {
					client.getState().getPlayer().setSelectedEntry(9);
				} else if (e.getKeyCode() == keybindings.TOGGLEMAPVIEW) {
					swap(mapView);
					guiOpen = true;
				} else if (e.getKeyCode() == keybindings.CRAFTING) {
					swap(craftingView);
					guiOpen = true;
				} else if (e.getKeyCode() == keybindings.MAPLINK) {
					client.getState().getPlayer().useTileUnderneath();
				} else if (e.getKeyCode() == keybindings.CHAT) {
					guiOpen = true;
					chatOverlay.setOpaqueChatbox(true);
				} else if (e.getKeyCode() == KeyEvent.VK_F4) {
					client.getLogger().debug("F4 pressed at: " + 
											 client.getState().getPlayer().getCenter()[0] + "," +
											 client.getState().getPlayer().getCenter()[1]);
					
					Message m = new Message();
					m.type = MessageType.DEBUG_PLAYER_INFO;
					client.send(m);
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

	@Override
	public void keyReleased(KeyEvent e) {
		keys.remove(e.getKeyCode());
		scanHeldKeys();
		client.getLogger().verbose("key released: " + e.getKeyChar());
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void mouseClicked(MouseEvent e) {
		Iterator<GUIContainer> it = overlays.iterator();
		while (it.hasNext()) {
			if (it.next().click()) {
				return;
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {
		mouseDown = true;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		mouseDown = false;
		client.getLogger().debug(windowToGameX(mousePos[0]) + ", " + windowToGameY(mousePos[1]));
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		mousePos[0] = e.getX();
		mousePos[1] = e.getY();
		if (client.getState().isBuildMode() && client.getState().isGameRunning() && !guiOpen) {
			gameBoard.click();
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mousePos[0] = e.getX();
		mousePos[1] = e.getY();
	}
	
	/*
	 * Class methods
	 */
	
	private void scanHeldKeys() {
		if (guiOpen) {
			// do not send a message that the user is trying to move, if a GUI is open
			return;
		}
		
		// get the set of player movement keys that are currently held
		Set<Integer> currentlyHeldKeys = new HashSet<>(this.keys);
		currentlyHeldKeys.retainAll(MOVEMENT_KEYS);
		
		// if the set of movement keys is the same as the last set, do nothing
		if (currentlyHeldKeys.equals(prevMovementKeys)) {
			return;
		}
		
		prevMovementKeys = currentlyHeldKeys;
		
		double x = 0;
		double y = 0;
		
		boolean keysHeld = false;
		
		if (keys.contains(KeyEvent.VK_W)) {
			y += -1;
			keysHeld = true;
		} else if (keys.contains(KeyEvent.VK_S)) {
			y += 1;
			keysHeld = true;
		}
		
		if (keys.contains(KeyEvent.VK_A)) {
			x += -1;
			keysHeld = true;
		} else if (keys.contains(KeyEvent.VK_D)) {
			x += 1;
			keysHeld = true;
		}
		
		double direction = Vector.EAST;
		if (x == 0) {
			if (y == 1) {
				direction = Vector.SOUTH;
			} else if (y == -1) {
				direction = Vector.NORTH;
			}
		} else if (x == 1) {
			if (y == 1) {
				direction = Vector.SOUTHEAST;
			} else if (y == -1) {
				direction = Vector.NORTHEAST;
			} else if (y == 0) {
				direction = Vector.EAST;
			}
		} else if (x == -1) {
			if (y == 1) {
				direction = Vector.SOUTHWEST;
			} else if (y == -1) {
				direction = Vector.NORTHWEST;
			} else if (y == 0) {
				direction = Vector.WEST;
			}
		}
		
		double velocity = (keysHeld ? Constants.DEFAULT_PLAYER_VELOCITY : 0);
		Player p = client.getState().getPlayer();
		if (p != null) {
			direction = (keysHeld ? direction : p.getDirection());
			p.setMovementVector(direction, velocity);
		} else {
			//client.getState().getCamera().move(xDiff, yDiff);
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
	
	public void setKeybindings(Keybindings k) {
		this.keybindings = k;
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
		overlays.pop();
		pauseMenu.resetBounds();
		overlays.push(pauseMenu);
	}
	
	public void unpause() {
		// UrfQuestClient.gameRunning = true; TODO: send resume signal
		this.isUserIngame = true;
		overlays.pop();
		overlays.push(chatOverlay);
	}
	
	public void swap(GUIContainer o) {
		overlays.pop();
		o.resetBounds();
		overlays.push(o);
	}
	
	public void initOverlays() {
		gameBoard = new GameBoardOverlay(this.client);
		gameStatus = new GameStatusOverlay(this.client);
		gameWeather = new GameWeatherOverlay(this.client);
		mapView = new MapViewOverlay(this.client);
		keybindingView = new KeybindingOverlay(this.client);
		craftingView = new CraftingOverlay(this.client);
		mainMenu = OverlayInit.newMainMenu(this.client);
		pauseMenu = OverlayInit.newPauseMenu(this.client);
		optionsMenu = OverlayInit.newOptionsOverlay(this.client);
		chatOverlay = new ChatOverlay(this.client);
		chatOverlay.resetBounds();
		
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
		
		if (client.getLogger().getLogLevel().compareTo(LogLevel.DEBUG) >= 0) {
			g.setColor(Color.WHITE);
			g.drawLine(0, dispCenterY, getWidth(), dispCenterY);
			g.drawLine(dispCenterX, 0, dispCenterX, getHeight());
		}
		
		Toolkit.getDefaultToolkit().sync();
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
		return client.getState().getCamera();
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