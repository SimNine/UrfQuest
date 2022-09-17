package xyz.urffer.urfquest.server.entities.mobs;

import java.awt.geom.Rectangle2D;

import xyz.urffer.urfquest.server.Server;
import xyz.urffer.urfquest.server.entities.items.Item;
import xyz.urffer.urfquest.server.entities.mobs.ai.routines.IdleRoutine;
import xyz.urffer.urfquest.server.map.Map;
import xyz.urffer.urfquest.shared.Constants;
import xyz.urffer.urfquest.shared.protocol.messages.MessageEntityInit;
import xyz.urffer.urfquest.shared.protocol.types.EntityType;
import xyz.urffer.urfquest.shared.protocol.types.MobType;

public class Chicken extends Mob {
	private int thinkingDelay;
	private final int intelligence;
	
	public Chicken(Server srv, Map m, double[] pos) {
		super(srv, m, pos);
		bounds = new Rectangle2D.Double(pos[0], pos[1], 1, 1);
		movementVector.magnitude = Constants.DEFAULT_VELOCITY_CHICKEN;
		
		health = 10.0;
		maxHealth = 10.0;
		mana = 0.0;
		maxMana = 0.0;
		fullness = 0.0;
		maxFullness = 0.0;
		
		routine = new IdleRoutine(server, this);
		intelligence = 20;
		thinkingDelay = intelligence;
		
		MessageEntityInit msg = new MessageEntityInit();
		msg.entityType = EntityType.MOB;
		msg.entitySubtype = MobType.CHICKEN;
		msg.pos = this.getPos();
		msg.entityID = this.id;
		msg.mapID = m.id;
		server.sendMessageToAllClients(msg);
	}

	public void tick() {
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
		this.updateMovementVector(routine.getSuggestedMovementVector());
		super.attemptIncrementPos();
	}
	
	public double getBaseSpeed() {
		return Constants.DEFAULT_VELOCITY_CHICKEN;
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
			this.map.addItem(new Item(this.server, this.map, this.getCenter(), 4));
		}
	}
}