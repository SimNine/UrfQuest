package urfquest.server.entities.mobs.ai.actions;

import urfquest.shared.Vector;

public class IdleAction extends MobAction {
	
	public IdleAction(int duration) {
		this.duration = duration;
	}

	public boolean shouldEnd() {
		return (duration <= 0);
	}
	
	public Vector getSuggestedMovementVector() {
		return new Vector(0, 0);
	}

	public void update() {
		duration--;
	}

}