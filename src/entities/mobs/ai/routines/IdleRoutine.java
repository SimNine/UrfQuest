package entities.mobs.ai.routines;

import entities.mobs.Mob;
import entities.mobs.ai.actions.IdleAction;
import entities.mobs.ai.actions.MoveAction;

public class IdleRoutine extends MobRoutine {
	
	public IdleRoutine(Mob m) {
		super(m);
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
		// randomly add either an idle action or a move action
		double rand = Math.random();
		if (rand > .60) {
			actions.add(new MoveAction((50 + (int)(Math.random()*50)), 
									   (int)(Math.random()*360), 
									   (mob.getVelocity() + mob.getVelocity()*((Math.random() - 0.5)/10.0))));
		} else {
			actions.add(new IdleAction(250 + (int)(Math.random()*250)));
		}
	}
}