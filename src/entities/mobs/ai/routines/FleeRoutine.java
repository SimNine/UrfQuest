package entities.mobs.ai.routines;

import entities.mobs.Mob;
import entities.mobs.ai.actions.IdleAction;
import entities.mobs.ai.actions.MoveAction;

public class FleeRoutine extends MobRoutine {
	private Mob other;

	public FleeRoutine(Mob m, Mob other) {
		super(m);
		this.other = other;
		update();
	}

	public void update() {
		// if there are no actions in the queue
		if (actions.isEmpty()) {
			addAction();
		}
		
		// check to see if the current action is over; if it is, remove it
		if (actions.peekFirst().shouldEnd()) {
			actions.removeFirst();
			addAction();
		} else {
			// update the current action
			actions.peekFirst().update();
		}
	}
	
	private void addAction() {
		if (other != null) {
			int angle = other.angleTo(mob) + (int)(Math.random()*30);
			actions.add(new MoveAction(50, angle, mob.getVelocity()*1.5));
		} else {
			actions.add(new IdleAction(10));
		}
	}
}
