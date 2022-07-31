package xyz.urffer.urfquest.server.entities.mobs.ai.actions;

import xyz.urffer.urfquest.shared.Vector;

public class MoveAction extends MobAction {
	private Vector vector;
	
	public MoveAction(int duration, double direction, double velocity) {
		this.duration = duration;
		this.vector = new Vector(direction, velocity);
	}
	
	public boolean shouldEnd() {
		return (duration <= 0);
	}

	public Vector getSuggestedMovementVector() {
		return vector;
	}

	public void update() {
		duration--;
	}
}