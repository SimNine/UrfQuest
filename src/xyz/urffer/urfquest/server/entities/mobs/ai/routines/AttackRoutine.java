package xyz.urffer.urfquest.server.entities.mobs.ai.routines;

import xyz.urffer.urfquest.server.Server;
import xyz.urffer.urfquest.server.entities.mobs.Mob;
import xyz.urffer.urfquest.server.entities.mobs.ai.actions.MoveAction;

public class AttackRoutine extends MobRoutine {
	private Mob other;

	public AttackRoutine(Server server, Mob m, Mob other) {
		super(server, m);
		this.other = other;
		update();
	}

	public void update() {
		if (actions.isEmpty()) {
			actions.add(new MoveAction(50, mob.angleTo(other), mob.getBaseSpeed()*3.0));
		}
		
		if (actions.peek().shouldEnd()) {
			actions.pop();
			update();
		} else {
			actions.peek().update();
		}
	}
}