package xyz.urffer.urfquest.server.entities.mobs.ai.routines;

import xyz.urffer.urfquest.server.Server;
import xyz.urffer.urfquest.server.entities.mobs.Mob;
import xyz.urffer.urfquest.server.entities.mobs.ai.actions.IdleAction;
import xyz.urffer.urfquest.server.entities.mobs.ai.actions.MoveAction;

public class IdleRoutine extends MobRoutine {
	
	public IdleRoutine(Server s, Mob m) {
		super(s, m);
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
		double rand = server.randomDouble();
		if (rand > .60) {
			actions.add(new MoveAction(
							(50 + (int)(server.randomDouble()*50)), 
							(server.randomDouble()*Math.PI*2), 
							(mob.getBaseSpeed() + mob.getBaseSpeed()*((server.randomDouble() - 0.5)/10.0))
						)
			);
		} else {
			actions.add(new IdleAction(100 + (int)(server.randomDouble()*100)));
		}
	}
}
