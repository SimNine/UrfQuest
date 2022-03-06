package urfquest.server.entities.mobs.ai.actions;

import urfquest.shared.Vector;

public class IdleAction extends MobAction {
	
	public Vector movementVector = new Vector(0, 0);
	
	public IdleAction(int duration) {
		this.duration = duration;
	}

	public boolean shouldEnd() {
		return (duration <= 0);
	}
	
	public Vector getSuggestedMovementVector() {
		return movementVector;
	}

	public void update() {
		duration--;
	}

}