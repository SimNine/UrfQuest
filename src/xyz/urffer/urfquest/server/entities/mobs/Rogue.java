package xyz.urffer.urfquest.server.entities.mobs;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collection;

import xyz.urffer.urfutils.math.PairDouble;

import xyz.urffer.urfquest.server.Server;
import xyz.urffer.urfquest.server.entities.items.ItemStack;
import xyz.urffer.urfquest.server.entities.mobs.ai.routines.IdleRoutine;
import xyz.urffer.urfquest.server.map.Map;
import xyz.urffer.urfquest.server.state.Inventory;
import xyz.urffer.urfquest.server.state.State;
import xyz.urffer.urfquest.shared.Constants;
import xyz.urffer.urfquest.shared.protocol.types.ItemType;

public class Rogue extends Mob {
	
	private String name = "ROGUE";
	private int statCounter = 200;
	
	private int thinkingDelay;
	private final int intelligence;
	
	private Inventory inventory;

	public Rogue(Server srv, State s, Map m, PairDouble pos) {
		super(srv, m, pos);
		
		bounds = new Rectangle2D.Double(pos.x, pos.y, 1, 1);
		movementVector.magnitude = Constants.DEFAULT_VELOCITY_ROGUE;
		
		health = 100.0;
		maxHealth = 100.0;
		mana = 100.0;
		maxMana = 100.0;
		fullness = 100.0;
		maxFullness = 100.0;
		
		inventory = new Inventory(this, 10);
		inventory.addItem(new ItemStack(srv, this.map, new PairDouble(0, 0), ItemType.PISTOL));
		inventory.addItem(new ItemStack(srv, this.map, new PairDouble(0, 0), ItemType.SHOTGUN));
		inventory.addItem(new ItemStack(srv, this.map, new PairDouble(0, 0), ItemType.SMG));
		
		intelligence = 50;
		routine = new IdleRoutine(server, this);
		thinkingDelay = intelligence;
	}

	/*
	 * per-tick updater
	 */
	
	public void tick() {
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
				i.tick();
			}
		}
		
		// process the tile the player is currently on
		processCurrentTile();
		
		// if the rogue can think again
		thinkingDelay--;
		if (thinkingDelay <= 0) {
			think();
			thinkingDelay = intelligence;
		}
		
		// TODO: update all this stuff with Vectors
//		// get new movement vector
//		routine.update();
//		movementVector.dirRadians = routine.suggestedDirection();
//		if (routine.suggestedVelocity() == 0) {
//			inventory.setSelectedEntry((int)(server.randomDouble()*3));
//		}
//		velocity = routine.suggestedVelocity();
//		while (direction < 0) {
//			direction += 360;
//		}
//		attemptMove(direction, velocity);
//		
//		// try firing a weapon
//		//if (this.distanceTo(UrfQuest.game.getPlayer()) < 10 && this.hasClearPathTo(UrfQuest.game.getPlayer())) {
//		//	inventory.useSelectedItem();
//		//}
//		
//		// just in case something weird happens
//		if (direction > 360) {
//			direction -= 360;
//		}
	}
	
	private void think() {
		/*
		if (Math.abs(getPos()[0] - UrfQuest.game.getPlayer().getPos()[0]) < 20 &&
			Math.abs(getPos()[1] - UrfQuest.game.getPlayer().getPos()[1]) < 20 &&
			this.hasClearPathTo(UrfQuest.game.getPlayer())) {
			if (!(routine instanceof AttackRoutine)) {
				routine = new AttackRoutine(this, UrfQuest.game.getPlayer());
			}
		} else {
			if (!(routine instanceof IdleRoutine)){
				routine = new IdleRoutine(this);
			}
		}
		*/
	}
	
	// helpers
	private void processCurrentTile() {
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
	
	public Inventory getInventory() {
		return inventory;
	}
	
	public ArrayList<ItemStack> getInventoryItems() {
		return inventory.getItems();
	}
	
	public boolean addItem(ItemStack i) {
		return inventory.addItem(i);
	}
	
	public ItemStack getSelectedItem() {
		return inventory.getSelectedItem();
	}
	
	public void dropOneOfSelectedItem() {
		ItemStack i = inventory.removeOneOfSelectedItem();
		
		if (i == null) {
			return;
		}
		
		PairDouble playerPos = getPos();
		playerPos.y -= 1;
		i.setPos(playerPos);
	}
	
	public void setSelectedEntry(int i) {
		inventory.setSelectedEntry(i);
	}
	
	public void useSelectedItem() {
		inventory.useSelectedItem();
	}
	
	public void tryCrafting(Collection<ItemStack> input, Collection<ItemStack> output) {
		inventory.tryCrafting(input, output);
	}

	@Override
	public double getBaseSpeed() {
		return Constants.DEFAULT_VELOCITY_ROGUE;
	}
}
