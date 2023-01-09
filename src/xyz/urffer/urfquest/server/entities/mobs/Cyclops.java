package xyz.urffer.urfquest.server.entities.mobs;

import java.awt.geom.Rectangle2D;

import xyz.urffer.urfutils.math.PairDouble;

import xyz.urffer.urfquest.server.Server;
import xyz.urffer.urfquest.server.entities.items.ItemStack;
import xyz.urffer.urfquest.server.entities.mobs.ai.routines.AttackRoutine;
import xyz.urffer.urfquest.server.entities.mobs.ai.routines.IdleRoutine;
import xyz.urffer.urfquest.server.map.Map;
import xyz.urffer.urfquest.shared.Constants;
import xyz.urffer.urfquest.shared.protocol.messages.MessageInitMob;
import xyz.urffer.urfquest.shared.protocol.types.ItemType;
import xyz.urffer.urfquest.shared.protocol.types.MobType;

public class Cyclops extends Mob {
	private int thinkingDelay;
	private final int intelligence;
	
	private ItemStack shotgun;

	public Cyclops(Server srv, int mapID, PairDouble pos) {
		super(srv);
		
		// figure out what scaling this should be
		bounds = new Rectangle2D.Double(pos.x, pos.y, 10, 10);
		//								pic.getWidth()/(double)QuestPanel.TILE_WIDTH,
		//								pic.getHeight()/(double)QuestPanel.TILE_WIDTH);
		
		health = 50.0;
		maxHealth = 50.0;
		mana = 0.0;
		maxMana = 0.0;
		fullness = 0.0;
		maxFullness = 0.0;
		
		shotgun = new ItemStack(srv, this.mapID, new PairDouble(0, 0), ItemType.SHOTGUN);
		intelligence = 50;
		routine = new IdleRoutine(server, this);
		thinkingDelay = intelligence;
		
		MessageInitMob msg = new MessageInitMob();
		msg.entityID = this.id;
		msg.mobType = MobType.CYCLOPS;
		server.sendMessageToAllClients(msg);
		
		this.setPos(pos, mapID);
	}

	public void tick() {
		if (healthbarVisibility > 0) {
			healthbarVisibility--;
		}
		
		// if the cyclops can think again
		thinkingDelay--;
		if (thinkingDelay <= 0) {
			think();
			thinkingDelay = intelligence;
		}

		routine.update();
		this.updateMovementVector(routine.getSuggestedMovementVector());
		super.attemptIncrementPos();
		
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
		Map map = this.server.getState().getMapByID(this.mapID);
		map.addItem(new ItemStack(this.server, this.mapID, this.getCenter(), ItemType.BONE));
	}

	@Override
	public double getBaseSpeed() {
		return Constants.DEFAULT_VELOCITY_CYCLOPS;
	}
}