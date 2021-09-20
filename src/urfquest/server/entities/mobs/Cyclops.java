package urfquest.server.entities.mobs;

import java.awt.geom.Rectangle2D;

import urfquest.server.entities.items.Item;
import urfquest.server.entities.mobs.ai.routines.AttackRoutine;
import urfquest.server.entities.mobs.ai.routines.IdleRoutine;
import urfquest.server.map.Map;
import urfquest.server.state.State;

public class Cyclops extends Mob {
	private int thinkingDelay;
	private final int intelligence;
	
	private Item shotgun;

	public Cyclops(State s, Map m, double x, double y) {
		super(s, m, x, y);
		
		// figure out what scaling this should be
		bounds = new Rectangle2D.Double(x, y, 10, 10);
		//								pic.getWidth()/(double)QuestPanel.TILE_WIDTH,
		//								pic.getHeight()/(double)QuestPanel.TILE_WIDTH);
		
		velocity = 0.01;
		defaultVelocity = 0.01;
		
		health = 50.0;
		maxHealth = 50.0;
		mana = 0.0;
		maxMana = 0.0;
		fullness = 0.0;
		maxFullness = 0.0;
		
		shotgun = new Item(this.state, this.map, 0, 0, 15);
		intelligence = 50;
		routine = new IdleRoutine(this);
		thinkingDelay = intelligence;
	}

	public void update() {
		if (healthbarVisibility > 0) {
			healthbarVisibility--;
		}
		
		// if the cyclops can think again
		thinkingDelay--;
		if (thinkingDelay <= 0) {
			think();
			thinkingDelay = intelligence;
		}
	
		// get new movement vector
		routine.update();
		direction = routine.suggestedDirection();
		velocity = routine.suggestedVelocity();
		attemptMove(direction, velocity);
		
		// try firing shotgun
//		if (this.distanceTo(Main.server.getGame().getPlayer()) < 10 && 
//			this.hasClearPathTo(Main.server.getGame().getPlayer())) {
//			shotgun.use(this);
//		}
//		shotgun.update();
	}
	
	private void think() {
//		// if the cyclops is within 20 blocks of the player, and it isn't attacking already, attack
//		if (Math.abs(getPos()[0] - Main.server.getGame().getPlayer().getPos()[0]) < 20 &&
//			Math.abs(getPos()[1] - Main.server.getGame().getPlayer().getPos()[1]) < 20 &&
//			this.hasClearPathTo(Main.server.getGame().getPlayer())) {
//			if (!(routine instanceof AttackRoutine)) {
//				routine = new AttackRoutine(this, Main.server.getGame().getPlayer());
//			}
//		} else {
//			if (!(routine instanceof IdleRoutine)){
//				routine = new IdleRoutine(this);
//			}
//		}
	}
	
	public void onDeath() {
		this.map.addItem(new Item(this.state, this.map, bounds.getCenterX(), bounds.getCenterY(), 6));
	}
}