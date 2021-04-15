package server.entities.mobs.ai.actions;

public abstract class MobAction {
	protected int duration;
	
	public abstract boolean shouldEnd();
	
	public abstract int suggestedDirection();
	
	public abstract double suggestedVelocity();
	
	public abstract void update();
	
	public int getDuration() {
		return duration;
	}
}