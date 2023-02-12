package xyz.urffer.urfquest.client.entities.mobs;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collection;

import xyz.urffer.urfutils.math.PairDouble;
import xyz.urffer.urfutils.math.PairInt;

import xyz.urffer.urfquest.client.Client;
import xyz.urffer.urfquest.client.QuestPanel;
import xyz.urffer.urfquest.client.entities.items.ItemStack;
import xyz.urffer.urfquest.client.map.Map;
import xyz.urffer.urfquest.client.state.Inventory;
import xyz.urffer.urfquest.shared.Constants;
import xyz.urffer.urfquest.shared.ImageUtils;
import xyz.urffer.urfquest.shared.Vector;
import xyz.urffer.urfquest.shared.protocol.messages.MessageMobSetHeldItem;
import xyz.urffer.urfquest.shared.protocol.messages.MessageRequestPlayerSetMoveVector;
import xyz.urffer.urfquest.shared.protocol.messages.MessageRequestPlayerUseHeldItem;

public class Player extends Mob {

	private final static String assetPath = QuestPanel.assetPath + "/entities/player/";

	// img[dir][frame]
	// dir = right/left
	// frame = idle/step1/step2/step3
	// walk: 1 -> 2 -> 3 -> 2 -> 1 -> 2 etc...
	private static BufferedImage[][] img = new BufferedImage[2][4];
	
	static {
		BufferedImage idle = ImageUtils.loadImage(assetPath + "new_0.png");
		BufferedImage walk1 = ImageUtils.loadImage(assetPath + "new_1.png");
		BufferedImage walk2 = ImageUtils.loadImage(assetPath + "new_2.png");
		
		img[0][0] = idle;
		img[0][1] = walk1;
		img[0][2] = walk2;
		img[0][3] = walk1;
		img[1][0] = ImageUtils.flipImage(idle, true, false);
		img[1][1] = ImageUtils.flipImage(walk1, true, false);
		img[1][2] = ImageUtils.flipImage(walk2, true, false);
		img[1][3] = ImageUtils.flipImage(walk1, true, false);
	}
	
	private String name;
	private int statCounter = 200;
	private Inventory inventory = new Inventory(this, Constants.DEFAULT_PLAYER_INVENTORY_SIZE);
	
	private double pickupRange = 3.0;

	public Player(Client c, int id, String name) {
		super(c, id);
		this.bounds = new Rectangle2D.Double(0, 0, 1, 1);
		
		health = Constants.DEFAULT_HEALTH_MAX_PLAYER;
		maxHealth = Constants.DEFAULT_HEALTH_MAX_PLAYER;
		mana = Constants.DEFAULT_MANA_MAX_PLAYER;
		maxMana = Constants.DEFAULT_MANA_MAX_PLAYER;
		fullness = Constants.DEFAULT_FULLNESS_MAX_PLAYER;
		maxFullness = Constants.DEFAULT_FULLNESS_MAX_PLAYER;
		
		this.name = name;
	}
	
	public void setMovementVector(double dirRadians, double velocity, boolean byClient) {
		if (byClient) {
			MessageRequestPlayerSetMoveVector m = new MessageRequestPlayerSetMoveVector();
			m.vector = new Vector(dirRadians, velocity);
			m.entityID = this.id;
			this.client.send(m);
		} else {
			this.movementVector = new Vector(dirRadians, velocity);
		}
	}
	
	public void setPos(PairDouble pos) {
		super.setPos(pos);
		
		// If this player isn't the one owned by this client, do nothing else
		if (this != this.client.getState().getPlayer()) {
			return;
		}
		
		Map map = this.getMap();
		if (map == null) {
			return;
		}
		
		// if this new position would put the player within one chunk of the world edge,
		// shift the map and load more chunks
		int mapWidth = map.getMapDiameter();
		int xChunk = Math.floorDiv((int) pos.x, Constants.MAP_CHUNK_SIZE);
		int yChunk = Math.floorDiv((int) pos.y, Constants.MAP_CHUNK_SIZE);
		
		PairInt localChunkOrigin = map.getLocalChunkOrigin();
		if (xChunk <= localChunkOrigin.x + 1) {
			map.shiftMapChunks(localChunkOrigin.add(new PairInt(-1,0)));
			map.requestMissingChunks();
		} else if (xChunk >= localChunkOrigin.x + mapWidth - 1) {
			map.shiftMapChunks(localChunkOrigin.add(new PairInt(1,0)));
			map.requestMissingChunks();
		}

		// it's necessary to refresh the local chunk origin in case it was shifted above
		localChunkOrigin = map.getLocalChunkOrigin();
		if (yChunk <= localChunkOrigin.y + 1) {
			map.shiftMapChunks(localChunkOrigin.add(new PairInt(0,-1)));
			map.requestMissingChunks();
		} else if (yChunk >= localChunkOrigin.y + mapWidth - 1) {
			map.shiftMapChunks(localChunkOrigin.add(new PairInt(0,1)));
			map.requestMissingChunks();
		}

		map.requestMissingChunks();
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
		for (ItemStack i : inventory.getItems()) {
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
	
	/*
	 * Inventory management
	 */
	
	public Inventory getInventory() {
		return inventory;
	}
	
	public ArrayList<ItemStack> getInventoryItems() {
		return inventory.getItems();
	}
	
	public boolean addItem(ItemStack i) {
		return inventory.addItem(i);
	}
	
	public void dropOneOfSelectedItem() {
		ItemStack i = inventory.removeOneOfSelectedItem();
		
		if (i == null) {
			return;
		}
		
		PairDouble playerPos = getPos();
		i.setPos(playerPos);
		i.resetDropTimeout();
		
		// TODO: figure out how to handle this
//		map.addItem(i);
	}
	
	public int getSelectedInventoryIndex() {
		return inventory.getSelectedIndex();
	}
	
	public void setSelectedInventoryIndex(int i, boolean byClient) {
		if (byClient) {
			MessageMobSetHeldItem m = new MessageMobSetHeldItem();
			m.entityID = this.id;
			m.setHeldSlot = i;
			this.client.send(m);
		} else {
			inventory.setSelectedEntry(i);
		}
	}
	
	public ItemStack getSelectedInventoryItem() {
		return inventory.getSelectedItem();
	}
	
	public void useSelectedItem() {
		MessageRequestPlayerUseHeldItem m = new MessageRequestPlayerUseHeldItem();
		this.client.send(m);
//		inventory.useSelectedItem();
	}
	
	public void tryCrafting(Collection<ItemStack> input, Collection<ItemStack> output) {
		inventory.tryCrafting(input, output);
	}
	
	/*
	 * Misc
	 */
	
	public void useTileUnderneath() {
		Map map = this.getMap();
		if (map == null) {
			return;
		}
		map.useActiveTile(getCenter().toInt(), this);
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
