package urfquest.client.entities.mobs;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.imageio.ImageIO;

import urfquest.Main;
import urfquest.client.Client;
import urfquest.client.entities.items.Item;
import urfquest.client.map.Map;
import urfquest.client.map.MapChunk;
import urfquest.client.state.Inventory;
import urfquest.shared.Constants;
import urfquest.shared.message.Message;
import urfquest.shared.message.MessageType;

public class Player extends Mob {

	private final static String assetPath = "/assets/player/";

	// img[0] is east, img[1] is SE, img[2] is S, etc
	private static BufferedImage[][] img = new BufferedImage[8][8];
	
	public static void initGraphics() {
		// Load E-W
		try {
			img[0][0] = img[0][4] = img[4][0] = img[4][4] = ImageIO.read(Main.mainLogger.getClass().getResourceAsStream(assetPath + "E1.png"));
		} catch (IOException e) {
			e.printStackTrace();
			Main.mainLogger.error("Image could not be read at: " + assetPath + "E1.png");
		}
		try {
			img[0][1] = img[0][3] = img[4][1] = img[4][3] = ImageIO.read(Main.mainLogger.getClass().getResourceAsStream(assetPath + "E2.png"));
		} catch (IOException e) {
			e.printStackTrace();
			Main.mainLogger.error("Image could not be read at: " + assetPath + "E2.png");
		}
		try {
			img[0][2] = img[4][2] = ImageIO.read(Main.mainLogger.getClass().getResourceAsStream(assetPath + "E3.png"));
		} catch (IOException e) {
			e.printStackTrace();
			Main.mainLogger.error("Image could not be read at: " + assetPath + "E3.png");
		}
		try {
			img[0][5] = img[0][7] = img[4][5] = img[4][7] = ImageIO.read(Main.mainLogger.getClass().getResourceAsStream(assetPath + "E6.png"));
		} catch (IOException e) {
			e.printStackTrace();
			Main.mainLogger.error("Image could not be read at: " + assetPath + "E6.png");
		}
		try {
			img[0][6] = img[4][6] = ImageIO.read(Main.mainLogger.getClass().getResourceAsStream(assetPath + "E7.png"));
		} catch (IOException e) {
			e.printStackTrace();
			Main.mainLogger.error("Image could not be read at: " + assetPath + "E7.png");
		}
		
		// Load NE-SW
		try {
			img[7][0] = img[7][4] = img[3][0] = img[3][4] = ImageIO.read(Main.mainLogger.getClass().getResourceAsStream(assetPath + "NE1.png"));
		} catch (IOException e) {
			e.printStackTrace();
			Main.mainLogger.error("Image could not be read at: " + assetPath + "NE1.png");
		}
		try {
			img[7][1] = img[7][3] = img[3][1] = img[3][3] = ImageIO.read(Main.mainLogger.getClass().getResourceAsStream(assetPath + "NE2.png"));
		} catch (IOException e) {
			e.printStackTrace();
			Main.mainLogger.error("Image could not be read at: " + assetPath + "NE2.png");
		}
		try {
			img[7][2] = img[3][2] = ImageIO.read(Main.mainLogger.getClass().getResourceAsStream(assetPath + "NE3.png"));
		} catch (IOException e) {
			e.printStackTrace();
			Main.mainLogger.error("Image could not be read at: " + assetPath + "NE3.png");
		}
		try {
			img[7][5] = img[7][7] = img[3][5] = img[3][7] = ImageIO.read(Main.mainLogger.getClass().getResourceAsStream(assetPath + "NE6.png"));
		} catch (IOException e) {
			e.printStackTrace();
			Main.mainLogger.error("Image could not be read at: " + assetPath + "NE6.png");
		}
		try {
			img[7][6] = img[3][6] = ImageIO.read(Main.mainLogger.getClass().getResourceAsStream(assetPath + "NE7.png"));
		} catch (IOException e) {
			e.printStackTrace();
			Main.mainLogger.error("Image could not be read at: " + assetPath + "NE7.png");
		}
		
		// Load NW-SE
		try {
			img[5][0] = img[5][4] = img[1][0] = img[1][4] = ImageIO.read(Main.mainLogger.getClass().getResourceAsStream(assetPath + "NW1.png"));
		} catch (IOException e) {
			e.printStackTrace();
			Main.mainLogger.error("Image could not be read at: " + assetPath + "NW1.png");
		}
		try {
			img[5][1] = img[5][3] = img[1][1] = img[1][3] = ImageIO.read(Main.mainLogger.getClass().getResourceAsStream(assetPath + "NW2.png"));
		} catch (IOException e) {
			e.printStackTrace();
			Main.mainLogger.error("Image could not be read at: " + assetPath + "NW2.png");
		}
		try {
			img[5][2] = img[1][2] = ImageIO.read(Main.mainLogger.getClass().getResourceAsStream(assetPath + "NW3.png"));
		} catch (IOException e) {
			e.printStackTrace();
			Main.mainLogger.error("Image could not be read at: " + assetPath + "NW3.png");
		}
		try {
			img[5][5] = img[5][7] = img[1][5] = img[1][7] = ImageIO.read(Main.mainLogger.getClass().getResourceAsStream(assetPath + "NW6.png"));
		} catch (IOException e) {
			e.printStackTrace();
			Main.mainLogger.error("Image could not be read at: " + assetPath + "NW6.png");
		}
		try {
			img[5][6] = img[1][6] = ImageIO.read(Main.mainLogger.getClass().getResourceAsStream(assetPath + "NW7.png"));
		} catch (IOException e) {
			e.printStackTrace();
			Main.mainLogger.error("Image could not be read at: " + assetPath + "NW7.png");
		}
		
		// Load north-south
		try {
			img[6][0] = img[6][4] = img[2][0] = img[2][4] = ImageIO.read(Main.mainLogger.getClass().getResourceAsStream(assetPath + "N1.png"));
		} catch (IOException e) {
			e.printStackTrace();
			Main.mainLogger.error("Image could not be read at: " + assetPath + "N1.png");
		}
		try {
			img[6][1] = img[6][3] = img[2][1] = img[2][3] = ImageIO.read(Main.mainLogger.getClass().getResourceAsStream(assetPath + "N2.png"));
		} catch (IOException e) {
			e.printStackTrace();
			Main.mainLogger.error("Image could not be read at: " + assetPath + "N2.png");
		}
		try {
			img[6][2] = img[2][2] = ImageIO.read(Main.mainLogger.getClass().getResourceAsStream(assetPath + "N3.png"));
		} catch (IOException e) {
			e.printStackTrace();
			Main.mainLogger.error("Image could not be read at: " + assetPath + "N3.png");
		}
		try {
			img[6][5] = img[6][7] = img[2][5] = img[2][7] = ImageIO.read(Main.mainLogger.getClass().getResourceAsStream(assetPath + "N6.png"));
		} catch (IOException e) {
			e.printStackTrace();
			Main.mainLogger.error("Image could not be read at: " + assetPath + "N6.png");
		}
		try {
			img[6][6] = img[2][6] = ImageIO.read(Main.mainLogger.getClass().getResourceAsStream(assetPath + "N7.png"));
		} catch (IOException e) {
			e.printStackTrace();
			Main.mainLogger.error("Image could not be read at: " + assetPath + "N7.png");
		}
	}
	
	private String name;
	private int statCounter = 200;
	private Inventory inventory;
	private Item heldItem;
	
	private double pickupRange = 3.0;
	
	private int animStage = 0;

	public Player(Client c, int id, Map currMap, double x, double y, String name) {
		super(c, id, currMap, x, y);
		bounds = new Rectangle2D.Double(x, y, 1, 1);
		velocity = Constants.DEFAULT_PLAYER_VELOCITY;
		
		health = 100.0;
		maxHealth = 100.0;
		mana = 100.0;
		maxMana = 100.0;
		fullness = 100.0;
		maxFullness = 100.0;
		
		inventory = new Inventory(this, 10);
		
		this.name = name;
	}
	
	public void move(double x, double y) {
		if (x == 0) {
			if (y == 1) {
				direction = 90;
			} else if (y == -1) {
				direction = 270;
			}
		} else if (x == 1) {
			if (y == 1) {
				direction = 45;
			} else if (y == -1) {
				direction = 315;
			} else if (y == 0) {
				direction = 0;
			}
		} else if (x == -1) {
			if (y == 1) {
				direction = 135;
			} else if (y == -1) {
				direction = 225;
			} else if (y == 0) {
				direction = 180;
			}
		}
		
		double dirRadians = Math.toRadians(direction);
		double xComp = velocity*Math.cos(dirRadians);
		double yComp = velocity*Math.sin(dirRadians);
		
		super.move(xComp, yComp);
		
		Message m = new Message();
		m.type = MessageType.PLAYER_MOVE;
		m.pos = new double[]{xComp, yComp};
		this.client.send(m);
	}
	
	public void setPos(double x, double y) {
		super.setPos(x, y);

		// if this new position would put the player within one chunk of the world edge,
		// shift the map and load more chunks
		int mapWidth = map.getMapDiameter();
		int xChunk = Math.floorDiv((int) x, Constants.MAP_CHUNK_SIZE);
		int yChunk = Math.floorDiv((int) y, Constants.MAP_CHUNK_SIZE);
		
		int[] localChunkOrigin = map.getLocalChunkOrigin();
		if (xChunk <= localChunkOrigin[0] + 1) {
			map.shiftMapChunks(localChunkOrigin[0] - 1, localChunkOrigin[1]);
			map.requestMissingChunks();
		} else if (xChunk >= localChunkOrigin[0] + mapWidth - 1) {
			map.shiftMapChunks(localChunkOrigin[0] + 1, localChunkOrigin[1]);
			map.requestMissingChunks();
		}

		// it's necessary to refresh the local chunk origin in case it was shifted above
		localChunkOrigin = map.getLocalChunkOrigin();
		if (yChunk <= localChunkOrigin[1] + 1) {
			map.shiftMapChunks(localChunkOrigin[0], localChunkOrigin[1] - 1);
			map.requestMissingChunks();
		} else if (yChunk >= localChunkOrigin[1] + mapWidth - 1) {
			map.shiftMapChunks(localChunkOrigin[0], localChunkOrigin[1] + 1);
			map.requestMissingChunks();
		}
	}

	/*
	 * per-tick updater
	 */
	 
	public void update() {
		if (healthbarVisibility > 0) {
			healthbarVisibility--;
		}
		
		// update hunger and health mechanics
		if (statCounter > 0) {
			statCounter--;
		} else {
			if (fullness > maxFullness/2) {
				if (health < maxHealth) {
					health += 0.2;
				}
			}
			
			if (fullness > 0) {
				fullness -= 0.2;
			} else {
				health -= 0.2;
			}
			
			if (mana < maxMana) {
				mana += 0.1;
			}
			
			statCounter = 200;
		}
		
		// update each entry (cooldown) in the inventory
		for (Item i : inventory.getItems()) {
			if (i != null) {
				i.update();
			}
		}
	}
	
	/*
	 * Getters, setters, incrementers
	 */
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setMap(Map m) {
		map = m;
	}
	
	/*
	 * Inventory management
	 */
	
	public Inventory getInventory() {
		return inventory;
	}
	
	public ArrayList<Item> getInventoryItems() {
		return inventory.getItems();
	}
	
	public boolean addItem(Item i) {
		return inventory.addItem(i);
	}
	
	public Item getSelectedItem() {
		return inventory.getSelectedItem();
	}
	
	public void dropOneOfSelectedItem() {
		Item i = inventory.removeOneOfSelectedItem();
		
		if (i == null) {
			return;
		}
		
		double[] playerPos = getPos();
		i.setPos(playerPos[0], playerPos[1]);
		i.resetDropTimeout();
		map.addItem(i);
	}
	
	public void setSelectedEntry(int i) {
		inventory.setSelectedEntry(i);
	}
	
	public void useSelectedItem() {
		inventory.useSelectedItem();
	}
	
	public void tryCrafting(Collection<Item> input, Collection<Item> output) {
		inventory.tryCrafting(input, output);
	}
	
	public void setHeldItem(Item i) {
		heldItem = i;
	}
	
	public Item getHeldItem() {
		return heldItem;
	}
	
	/*
	 * Misc
	 */
	
	public void useTileUnderneath() {
		map.useActiveTile((int)getCenter()[0], (int)getCenter()[1], this);
	}
	
	public double getPickupRange() {
		return pickupRange;
	}
	
	public void setPickupRange(double d) {
		pickupRange = d;
	}
	
	/*
	 * Drawing methods
	 */
	
	protected void drawEntity(Graphics g) {
		final int STEP_SIZE = 15;
		
		if (animStage/STEP_SIZE == 8) {
			animStage = -1;
		}
		
		g.drawImage(img[(int)direction/45][animStage/STEP_SIZE], 
					client.getPanel().gameToWindowX(bounds.getX()), 
					client.getPanel().gameToWindowY(bounds.getY()), 
					null);
		
		g.setColor(Color.BLACK);
		g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 15));
		g.drawString(name, 
				client.getPanel().gameToWindowX(bounds.getX()) - 5*(name.length()/2), 
				client.getPanel().gameToWindowY(bounds.getY()));
		
		drawHealthBar(g);
	}
	
	public void drawDebug(Graphics g) {
		g.setColor(Color.BLACK);
		g.drawString("direction: " + this.direction, 
				client.getPanel().gameToWindowX(bounds.getX()), 
				client.getPanel().gameToWindowY(bounds.getY()));
		g.drawString("moveStage: " + this.animStage, 
				client.getPanel().gameToWindowX(bounds.getX()), 
				client.getPanel().gameToWindowY(bounds.getY()) + 10);
	}
}
