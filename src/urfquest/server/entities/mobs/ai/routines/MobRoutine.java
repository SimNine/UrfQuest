package urfquest.server.entities.mobs.ai.routines;

import java.util.ArrayDeque;

import urfquest.server.Server;
import urfquest.server.entities.mobs.Mob;
import urfquest.server.entities.mobs.ai.actions.MobAction;
import urfquest.shared.Vector;

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