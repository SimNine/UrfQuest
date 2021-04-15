package server.entities.mobs.ai.routines;

import server.entities.mobs.Mob;
import server.entities.mobs.ai.actions.IdleAction;
import server.entities.mobs.ai.actions.MoveAction;

public class IdleRoutine extends MobRoutine {
	
	public IdleRoutine(Mob m) {
		super(m);
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
		// randomly add either an idle action or a move action
		double rand = Math.random();
		if (rand > .60) {
			actions.add(new MoveAction((50 + (int)(Math.random()*50)), 
									   (int)(Math.random()*360), 
									   (mob.getDefaultVelocity() + mob.getDefaultVelocity()*((Math.random() - 0.5)/10.0))));
		} else {
			actions.add(new IdleAction(100 + (int)(Math.random()*100)));
		}
	}
}