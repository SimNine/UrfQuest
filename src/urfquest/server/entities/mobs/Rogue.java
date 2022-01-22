package urfquest.server.entities.mobs;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collection;

import urfquest.server.Server;
import urfquest.server.entities.items.Item;
import urfquest.server.entities.mobs.ai.routines.IdleRoutine;
import urfquest.server.map.Map;
import urfquest.server.state.Inventory;
import urfquest.server.state.State;

public class Rogue extends Mob {
	
	private String name = "ROGUE";
	private int statCounter = 200;
	
	private int thinkingDelay;
	private final int intelligence;
	
	private Inventory inventory;

	public Rogue(Server srv, State s, Map m, double x, double y) {
		super(srv, s, m, x, y);
		bounds = new Rectangle2D.Double(x, y, 1, 1);
		velocity = 0.013;
		defaultVelocity = 0.013;
		
		health = 100.0;
		maxHealth = 100.0;
		mana = 100.0;
		maxMana = 100.0;
		fullness = 100.0;
		maxFullness = 100.0;
		
		inventory = new Inventory(this, 10);
		inventory.addItem(new Item(srv, this.state, this.map, 0, 0, 16));
		inventory.addItem(new Item(srv, this.state, this.map, 0, 0, 15));
		inventory.addItem(new Item(srv, this.state, this.map, 0, 0, 13));
		
		intelligence = 50;
		routine = new IdleRoutine(this);
		thinkingDelay = intelligence;
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
		
		// if the rogue can think again
		thinkingDelay--;
		if (thinkingDelay <= 0) {
			think();
			thinkingDelay = intelligence;
		}
		
		// get new movement vector
		routine.update();
		direction = routine.suggestedDirection();
		if (routine.suggestedVelocity() == 0) {
			inventory.setSelectedEntry((int)(Math.random()*3));
		}
		velocity = routine.suggestedVelocity();
		while (direction < 0) {
			direction += 360;
		}
		attemptMove(direction, velocity);
		
		// try firing a weapon
		//if (this.distanceTo(UrfQuest.game.getPlayer()) < 10 && this.hasClearPathTo(UrfQuest.game.getPlayer())) {
		//	inventory.useSelectedItem();
		//}
		
		// just in case something weird happens
		if (direction > 360) {
			direction -= 360;
		}
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
		switch (this.map.getTileTypeAt((int)(bounds.getCenterX()),
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
		i.setPos(playerPos[0], playerPos[1]-1);
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
}
