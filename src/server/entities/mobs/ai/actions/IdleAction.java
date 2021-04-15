package server.entities.mobs.ai.actions;

public class IdleAction extends MobAction {
	public IdleAction(int duration) {
		this.duration = duration;
	}

	public boolean shouldEnd() {
		return (duration <= 0);
	}

	public int suggestedDirection() {
		return 0;
	}

	public double suggestedVelocity() {
		return 0;
	}

	public void update() {
		duration--;
	}

}