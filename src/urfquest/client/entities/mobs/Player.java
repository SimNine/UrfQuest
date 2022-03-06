package urfquest.client.entities.mobs;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
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
import urfquest.client.state.Inventory;
import urfquest.shared.Constants;
import urfquest.shared.Vector;
import urfquest.shared.message.Message;
import urfquest.shared.message.MessageType;

public class Player extends Mob {

	private final static String assetPath = "/assets/entities/player/";

	// img[dir][frame]
	// dir = right/left
	// frame = idle/step1/step2/step3
	// walk: 1 -> 2 -> 3 -> 2 -> 1 -> 2 etc...
	private static BufferedImage[][] img = new BufferedImage[2][4];
	
	public static void initGraphics() {
		try {
			BufferedImage idle = ImageIO.read(Main.self.getClass().getResourceAsStream(assetPath + "new_0.png"));
			BufferedImage walk1 = ImageIO.read(Main.self.getClass().getResourceAsStream(assetPath + "new_1.png"));
			BufferedImage walk2 = ImageIO.read(Main.self.getClass().getResourceAsStream(assetPath + "new_2.png"));
			
			img[0][0] = idle;
			img[0][1] = walk1;
			img[0][2] = walk2;
			img[0][3] = walk1;
			img[1][0] = flipImage(idle, true, false);
			img[1][1] = flipImage(walk1, true, false);
			img[1][2] = flipImage(walk2, true, false);
			img[1][3] = flipImage(walk1, true, false);
		} catch (IOException e) {
			e.printStackTrace();
			Main.mainLogger.error("Image could not be read at: " + "new_0.png");
		}
	}    
	
	public static BufferedImage flipImage(final BufferedImage image, boolean horizontal, boolean vertical) {
        int x = 0;
        int y = 0;
        int w = image.getWidth();
        int h = image.getHeight();

        final BufferedImage out = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        final Graphics2D g2d = out.createGraphics();

        if (horizontal) {
            x = w;
            w *= -1;
        }

        if (vertical) {
            y = h;
            h *= -1;
        }

        g2d.drawImage(image, x, y, w, h, null);
        g2d.dispose();

        return out;
    }
	
	private String name;
	private int statCounter = 200;
	private Inventory inventory;
	private Item heldItem;
	
	private double pickupRange = 3.0;

	public Player(Client c, int id, Map currMap, double[] pos, String name) {
		super(c, id, currMap, pos);
		this.bounds = new Rectangle2D.Double(pos[0], pos[1], 1, 1);
		
		health = 100.0;
		maxHealth = 100.0;
		mana = 100.0;
		maxMana = 100.0;
		fullness = 100.0;
		maxFullness = 100.0;
		
		inventory = new Inventory(this, 10);
		
		this.name = name;
	}
	
	public void setMovementVector(double dirRadians, double velocity, boolean byClient) {
		if (byClient) {
			Message m = new Message();
			m.type = MessageType.PLAYER_SET_MOVE_VECTOR;
			m.vector = new Vector(dirRadians, velocity);
			this.client.send(m);
		} else {
			this.movementVector = new Vector(dirRadians, velocity);
		}
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
		final int WALK_CYCLE_NUM_PHASES = 4;
		final int WALK_CYCLE_STEP_LENGTH_MS = 220;
		final int WALK_CYCLE_TOTAL_LENGTH = WALK_CYCLE_NUM_PHASES * WALK_CYCLE_STEP_LENGTH_MS;
		
		int dirIndex;
		if (this.movementVector.dirRadians < (Math.PI / 2) || this.movementVector.dirRadians > (Math.PI * 3.0 / 2.0)) {
			dirIndex = 0;
		} else {
			dirIndex = 1;
		}
		
		int stepIndex;
		if (this.movementVector.magnitude == 0) {
			stepIndex = 0;
		} else {
			stepIndex = (int)(System.currentTimeMillis() % WALK_CYCLE_TOTAL_LENGTH) / WALK_CYCLE_STEP_LENGTH_MS;
		}
		
		g.drawImage(img[dirIndex][stepIndex], 
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
		g.drawString("direction: " + this.movementVector.dirRadians, 
				client.getPanel().gameToWindowX(bounds.getX()), 
				client.getPanel().gameToWindowY(bounds.getY()));
	}
}
