package xyz.urffer.urfquest.server.entities.mobs;

import java.awt.geom.Rectangle2D;

import xyz.urffer.urfquest.server.Server;
import xyz.urffer.urfquest.server.entities.mobs.ai.routines.IdleRoutine;
import xyz.urffer.urfquest.shared.Constants;
import xyz.urffer.urfquest.shared.protocol.Message;
import xyz.urffer.urfquest.shared.protocol.messages.MessageInitMob;
import xyz.urffer.urfquest.shared.protocol.types.MobType;

public class NPCHuman extends Mob {
	private int thinkingDelay;
	private final int intelligence;
	
	public NPCHuman(Server srv) {
		super(srv);
		bounds = new Rectangle2D.Double(0, 0, 1, 1);
		
		health = Constants.DEFAULT_HEALTH_MAX_PLAYER;
		maxHealth = Constants.DEFAULT_HEALTH_MAX_PLAYER;
		mana = Constants.DEFAULT_MANA_MAX_PLAYER;
		maxMana = Constants.DEFAULT_MANA_MAX_PLAYER;
		fullness = Constants.DEFAULT_FULLNESS_MAX_PLAYER;
		maxFullness = Constants.DEFAULT_FULLNESS_MAX_PLAYER;
		
		routine = new IdleRoutine(server, this);
		intelligence = 20;
		thinkingDelay = intelligence;
		
		this.server.sendMessageToAllClients(this.initMessage());
	}
	
	public Message initMessage() {
		MessageInitMob msg = new MessageInitMob();
		msg.entityID = this.id;
		msg.mobType = MobType.NPC_HUMAN;
		msg.mobName = "NewNPC";
		return msg;
	}

	public void tick() {
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
		return Constants.DEFAULT_VELOCITY_PLAYER;
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
		// Do nothing
	}
}