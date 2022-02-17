package urfquest.server.entities.mobs.ai.routines;

import urfquest.server.Server;
import urfquest.server.entities.mobs.Mob;
import urfquest.server.entities.mobs.ai.actions.MoveAction;

public class AttackRoutine extends MobRoutine {
	private Mob other;

	public AttackRoutine(Server server, Mob m, Mob other) {
		super(server, m);
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