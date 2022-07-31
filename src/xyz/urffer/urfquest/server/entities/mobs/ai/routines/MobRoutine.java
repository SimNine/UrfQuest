package xyz.urffer.urfquest.server.entities.mobs.ai.routines;

import java.util.ArrayDeque;

import xyz.urffer.urfquest.server.Server;
import xyz.urffer.urfquest.server.entities.mobs.Mob;
import xyz.urffer.urfquest.server.entities.mobs.ai.actions.MobAction;
import xyz.urffer.urfquest.shared.Vector;

public abstract class MobRoutine {
	protected ArrayDeque<MobAction> actions = new ArrayDeque<MobAction>();
	protected Mob mob;
	protected Server server;
	
	protected MobRoutine(Server s, Mob m) {
		this.server = s;
		this.mob = m;
	}
	
	public Vector getSuggestedMovementVector() {
		return actions.peek().getSuggestedMovementVector();
	}
	
	public MobAction getCurrentAction() {
		return actions.peek();
	}
	
	public abstract void update();
}