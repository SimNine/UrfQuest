package server.entities.mobs.ai.routines;

import server.entities.mobs.Mob;
import server.entities.mobs.ai.actions.MoveAction;

public class AttackRoutine extends MobRoutine {
	private Mob other;

	public AttackRoutine(Mob m, Mob other) {
		super(m);
		this.other = other;
		update();
	}

	public void update() {
		if (actions.isEmpty()) {
			actions.add(new MoveAction(50, mob.angleTo(other), mob.getDefaultVelocity()*3.0));
		}
		
		if (actions.peek().shouldEnd()) {
			actions.pop();
			update();
		} else {
			actions.peek().update();
		}
	}
}