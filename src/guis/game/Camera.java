package guis.game;

import entities.mobs.Mob;

public class Camera {
	private Mob followMob;
	
	public Camera(Mob m) {
		followMob = m;
	}

	public void setFollowMob(Mob m) {
		followMob = m;
	}
	
	public double[] getPos() {
		return followMob.getPos();
	}
}