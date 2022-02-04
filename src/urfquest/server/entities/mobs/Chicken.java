package urfquest.server.entities.mobs;

import java.awt.geom.Rectangle2D;

import urfquest.server.Server;
import urfquest.server.entities.items.Item;
import urfquest.server.entities.mobs.ai.routines.IdleRoutine;
import urfquest.server.map.Map;
import urfquest.server.state.State;

public class Chicken extends Mob {
	private int thinkingDelay;
	private final int intelligence;
	
	public Chicken(Server srv, State s, Map m, double x, double y) {
		super(srv, s, m, x, y);
		bounds = new Rectangle2D.Double(x, y, 1, 1);
		
		velocity = 0.02;
		defaultVelocity = 0.02;
		
		health = 10.0;
		maxHealth = 10.0;
		mana = 0.0;
		maxMana = 0.0;
		fullness = 0.0;
		maxFullness = 0.0;
		
		routine = new IdleRoutine(this);
		intelligence = 50;
		thinkingDelay = intelligence;
	}

	public void update() {
		if (healthbarVisibility > 0) {
			healthbarVisibility--;
		}
		
		// if the chicken can think again
		thinkingDelay--;
		if (thinkingDelay <= 0) {
			think();
			thinkingDelay = intelligence;
		}
		
		// execute the current action
		routine.update();
		attemptMove(routine.suggestedDirection(), routine.suggestedVelocity());
	}
	
	private void think() {
		/*
		// if the chicken is within 10 blocks of the player, and it isn't fleeing already, flee
		if (Math.abs(getPos()[0] - UrfQuest.game.getPlayer().getPos()[0]) < 5 &&
			Math.abs(getPos()[1] - UrfQuest.game.getPlayer().getPos()[1]) < 5) {
			if (!(routine instanceof FleeRoutine)) {
				routine = new FleeRoutine(this, UrfQuest.game.getPlayer());
			}
		} else {
			if (!(routine instanceof IdleRoutine)){
				routine = new IdleRoutine(this);
			}
		}
		*/
	}
	
	public void onDeath() {
		if (server.randomDouble() > 0.5) {
			this.map.addItem(new Item(this.server, this.state, this.map, bounds.getCenterX(), bounds.getCenterY(), 4));
		}
	}
}