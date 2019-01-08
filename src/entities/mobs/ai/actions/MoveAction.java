package entities.mobs.ai.actions;

public class MoveAction extends MobAction {
	private int direction;
	private double velocity;
	
	public MoveAction(int duration, int direction, double velocity) {
		this.duration = duration;
		this.direction = direction;
		this.velocity = velocity;
	}
	
	public boolean shouldEnd() {
		return (duration <= 0);
	}

	public int suggestedDirection() {
		return direction;
	}

	public double suggestedVelocity() {
		return velocity;
	}

	public void update() {
		duration--;
	}
}