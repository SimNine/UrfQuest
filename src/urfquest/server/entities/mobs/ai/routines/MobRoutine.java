package urfquest.server.entities.mobs.ai.routines;

import java.util.ArrayDeque;

import urfquest.server.entities.mobs.Mob;
import urfquest.server.entities.mobs.ai.actions.MobAction;

public abstract class MobRoutine {
	protected ArrayDeque<MobAction> actions = new ArrayDeque<MobAction>();
	protected Mob mob;
	
	protected MobRoutine(Mob m) {
		this.mob = m;
	}
	
	public int suggestedDirection() {
		return actions.peek().suggestedDirection();
	}
	
	public double suggestedVelocity() {
		return actions.peek().suggestedVelocity();
	}
	
	public MobAction getCurrentAction() {
		return actions.peek();
	}
	
	public abstract void update();
}