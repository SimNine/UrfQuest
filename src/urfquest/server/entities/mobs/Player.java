package urfquest.server.entities.mobs;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collection;

import urfquest.Main;
import urfquest.server.entities.items.Item;
import urfquest.server.map.Map;
import urfquest.server.state.Inventory;
import urfquest.shared.message.Constants;
import urfquest.shared.message.Message;
import urfquest.shared.message.MessageType;

public class Player extends Mob {
	
	private String name;
	private int statCounter = 200;
	private Inventory inventory;
	private Item heldItem;
	private int id;
	
	private double pickupRange = 3.0;

	public Player(double x, double y, Map currMap, String name, int id) {
		super(x, y, currMap);
		bounds = new Rectangle2D.Double(x, y, 1, 1);
		velocity = Constants.playerVelocity;
		
		health = 100.0;
		maxHealth = 100.0;
		mana = 100.0;
		maxMana = 100.0;
		fullness = 100.0;
		maxFullness = 100.0;
		
		inventory = new Inventory(this, 10);
		inventory.addItem(new Item(0, 0, 19, currMap));
		inventory.addItem(new Item(0, 0, 17, currMap));
		inventory.addItem(new Item(0, 0, 18, currMap));
		
		this.name = name;
		this.id = id;
	}
	
	public void move(double x, double y) {
		super.move(x, y);
		
		Message m = new Message();
		m.type = MessageType.PLAYER_SET_POS;
		m.pos[0] = bounds.getX();
		m.pos[1] = bounds.getY();
		Main.server.sendMessageToSingleClient(m, id);
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
		
		// process the tile the player is currently on
		processCurrentTile();
	}
	
	// helpers
	private void processCurrentTile() {
		switch (map.getTileTypeAt((int)(bounds.getCenterX()),
							  	  (int)(bounds.getCenterY()))) {
		case 0:
			//nothing
			break;
		case 1:
			//impossible
			break;
		case 2:
			break;
		case 3:
			if (mana < 100) incrementMana(0.1);
			break;
		case 4:
			if (health < 100) incrementHealth(0.1);
			break;
		case 5:
			if (health > 0) incrementHealth(-0.1);
			if (mana > 0) incrementMana(-0.1);
			if (velocity > 0.01) incrementVelocity(-0.001);
			break;
		case 6:
			if (velocity < 1) incrementVelocity(0.001);
			break;
		case 7:
			//impossible
			break;
		case 8:
			//impossible
			break;
		case 9:
			//nothing
			break;
		default:
			//nothing
			break;
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
		map.removePlayer(this);
		m.addPlayer(this);
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
}
