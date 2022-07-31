package xyz.urffer.urfquest.server.entities.mobs.ai.actions;

import xyz.urffer.urfquest.shared.Vector;

public abstract class MobAction {
	protected int duration;
	
	public abstract boolean shouldEnd();
	
	public abstract Vector getSuggestedMovementVector();
	
	public abstract void update();
	
	public int getDuration() {
		return duration;
	}
}