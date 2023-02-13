package xyz.urffer.urfquest.server.entities.mobs;

import java.awt.geom.Rectangle2D;

import xyz.urffer.urfquest.server.Server;
import xyz.urffer.urfquest.server.entities.items.ItemStack;
import xyz.urffer.urfquest.server.map.Map;
import xyz.urffer.urfquest.server.state.Inventory;
import xyz.urffer.urfquest.shared.Constants;
import xyz.urffer.urfquest.shared.protocol.Message;
import xyz.urffer.urfquest.shared.protocol.messages.MessageInitPlayer;
import xyz.urffer.urfquest.shared.protocol.messages.MessageItemSetOwner;
import xyz.urffer.urfquest.shared.protocol.messages.MessageMobSetHeldItem;
import xyz.urffer.urfutils.math.PairDouble;

public class Player extends Mob {
	
	private Inventory inventory;
	
	private double pickupRange = 3.0;
	
	private String name;
	private int clientID;
	
	public Player(Server srv, String name, int clientID) {
		super(srv);
		bounds = new Rectangle2D.Double(0, 0, 1, 1);
		
		health = Constants.DEFAULT_HEALTH_MAX_PLAYER;
		maxHealth = Constants.DEFAULT_HEALTH_MAX_PLAYER;
		mana = Constants.DEFAULT_MANA_MAX_PLAYER;
		maxMana = Constants.DEFAULT_MANA_MAX_PLAYER;
		fullness = Constants.DEFAULT_FULLNESS_MAX_PLAYER;
		maxFullness = Constants.DEFAULT_FULLNESS_MAX_PLAYER;
		
		inventory = new Inventory(srv, this.id, Constants.DEFAULT_PLAYER_INVENTORY_SIZE);
		
		this.name = name;
		this.clientID = clientID;
		
		this.server.sendMessageToAllClients(this.initMessage());
	}
	
	public Message initMessage() {
		MessageInitPlayer msg = new MessageInitPlayer();
		msg.entityID = this.id;
		msg.clientOwnerID = this.clientID;
		msg.entityName = this.name;
		return msg;
	}

	/*
	 * per-tick updater
	 */
	
	public void tick() {
		this.attemptIncrementPos();
		
//		this.inventory.tick();
		
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
			if (mana < 100) incrementMana(1);
			break;
		case HEALTH_PAD:
			if (health < 100) incrementHealth(1);
			break;
		case HURT_PAD:
			if (health > 0) incrementHealth(-1);
			if (mana > 0) incrementMana(-1);
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
	
	public void addItem(int itemID) {
		inventory.addItem(itemID);
		
		ItemStack i = (ItemStack)this.server.getState().getEntity(itemID);
		i.setPos(new PairDouble(0, 0), 0);
		
		MessageItemSetOwner misp = new MessageItemSetOwner();
		misp.entityID = itemID;
		misp.entityOwnerID = this.id;
		server.sendMessageToAllClients(misp);
	}
	
	public void useSelectedItem() {
		inventory.useSelectedItem();
	}
	
	public void setSelectedInventoryIndex(int itemIndex) {
		inventory.setSelectedEntry(itemIndex);
		
		MessageMobSetHeldItem m = new MessageMobSetHeldItem();
		m.entityID = this.id;
		m.setHeldSlot = itemIndex;
		server.sendMessageToAllClients(m);
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
