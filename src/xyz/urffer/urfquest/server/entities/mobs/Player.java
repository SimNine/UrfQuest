package xyz.urffer.urfquest.server.entities.mobs;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collection;

import xyz.urffer.urfquest.server.ClientThread;
import xyz.urffer.urfquest.server.Server;
import xyz.urffer.urfquest.server.entities.items.ItemStack;
import xyz.urffer.urfquest.server.map.Map;
import xyz.urffer.urfquest.server.state.Inventory;
import xyz.urffer.urfquest.shared.Constants;
import xyz.urffer.urfquest.shared.protocol.messages.MessageInitPlayer;
import xyz.urffer.urfquest.shared.protocol.messages.MessageItemSetOwner;
import xyz.urffer.urfquest.shared.protocol.messages.MessageMobSetHeldItem;

public class Player extends Mob {
	
	private int statCounter = 200;
	private Inventory inventory;
	
	private double pickupRange = 3.0;
	
	private String name;
	private ClientThread client;
	
	public Player(Server srv, String name, ClientThread c) {
		super(srv);
		bounds = new Rectangle2D.Double(0, 0, 1, 1);
		
		health = 100.0;
		maxHealth = 100.0;
		mana = 100.0;
		maxMana = 100.0;
		fullness = 100.0;
		maxFullness = 100.0;
		
		inventory = new Inventory(srv, this, Constants.DEFAULT_PLAYER_INVENTORY_SIZE);
		
		this.name = name;
		this.client = c;
		
		MessageInitPlayer msg = new MessageInitPlayer();
		msg.entityID = this.id;
		msg.clientOwnerID = c.id;
		msg.entityName = this.name;
		server.sendMessageToAllClients(msg);
	}

	/*
	 * per-tick updater
	 */
	
	public void tick() {
		this.attemptIncrementPos();
		
		// TODO: reimplement all this
//		if (healthbarVisibility > 0) {
//			healthbarVisibility--;
//		}
//		
//		// update hunger and health mechanics
//		if (statCounter > 0) {
//			statCounter--;
//		} else {
//			if (fullness > maxFullness/2) {
//				if (health < maxHealth) {
//					health += 0.2;
//				}
//			}
//			
//			if (fullness > 0) {
//				fullness -= 0.2;
//			} else {
//				health -= 0.2;
//			}
//			
//			if (mana < maxMana) {
//				mana += 0.1;
//			}
//			
//			statCounter = 200;
//		}
//		
//		// update each entry (cooldown) in the inventory
//		for (Item i : inventory.getItems()) {
//			if (i != null) {
//				i.tick();
//			}
//		}
//		
//		// process the tile the player is currently on
//		processCurrentTile();
	}
	
	// helpers
	private void processCurrentTile() {
		Map map = this.server.getState().getMapByID(this.mapID);
		switch (map.getTileAt(this.getCenter().toInt()).tileType) {
		case VOID:
			//nothing
			break;
		case BEDROCK:
			//impossible
			break;
		case GRASS:
			break;
		case MANA_PAD:
			if (mana < 100) incrementMana(0.1);
			break;
		case HEALTH_PAD:
			if (health < 100) incrementHealth(0.1);
			break;
		case HURT_PAD:
			if (health > 0) incrementHealth(-0.1);
			if (mana > 0) incrementMana(-0.1);
			if (movementVector.magnitude > 0.01) incrementVelocity(-0.001);
			break;
		case SPEED_PAD:
			if (movementVector.magnitude < 1) incrementVelocity(0.001);
			break;
		case WATER:
			//impossible
			break;
		case SAND:
			//impossible
			break;
		case DIRT:
			//nothing
			break;
		case FLOOR_WOOD:
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
	
	/*
	 * Inventory management
	 */
	
	public ArrayList<ItemStack> getInventoryItems() {
		return inventory.getItems();
	}
	
	public boolean addItem(ItemStack i) {
		MessageItemSetOwner misp = new MessageItemSetOwner();
		misp.entityID = i.id;
		misp.entityOwnerID = this.id;
		server.sendMessageToAllClients(misp);
		
		return inventory.addItem(i);
	}
	
	public void dropOneOfSelectedItem() {
		// TODO: multiplayerize
//		ItemStack i = inventory.removeOneOfSelectedItem();
//		
//		if (i == null) {
//			return;
//		}
//		
//		i.setPos(this.getPos().clone());
//		i.resetDropTimeout();
//		map.addItem(i);
	}
	
	public void useSelectedItem() {
		// TODO: multiplayerize
		inventory.useSelectedItem();
	}
	
	public void tryCrafting(Collection<ItemStack> input, Collection<ItemStack> output) {
		// TODO: multiplayerize
//		inventory.tryCrafting(input, output);
	}
	
	public void setSelectedInventoryIndex(int itemIndex) {
		inventory.setSelectedEntry(itemIndex);
		
		MessageMobSetHeldItem m = new MessageMobSetHeldItem();
		m.entityID = this.id;
		m.setHeldSlot = itemIndex;
		server.sendMessageToAllClients(m);
	}
	
	public int getSelectedInventoryIndex() {
		return inventory.getSelectedIndex();
	}
	
	public ItemStack getSelectedInventoryItem() {
		return inventory.getSelectedItem();
	}
	
	/*
	 * Misc
	 */
	
	public void useTileUnderneath() {
		Map map = this.server.getState().getMapByID(this.mapID);
		map.useActiveTile(getCenter().toInt(), this);
	}
	
	public double getPickupRange() {
		return pickupRange;
	}
	
	public void setPickupRange(double d) {
		pickupRange = d;
	}

	@Override
	public double getBaseSpeed() {
		return Constants.DEFAULT_VELOCITY_PLAYER;
	}
}
