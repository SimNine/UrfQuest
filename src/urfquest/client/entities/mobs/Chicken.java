package urfquest.client.entities.mobs;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

public class Chicken extends Mob {
	
	public Chicken(double x, double y, urfquest.client.map.Map m) {
		super(x, y, m);
		bounds = new Rectangle2D.Double(x, y, 1, 1);
		
		velocity = 0.02;
		defaultVelocity = 0.02;
		
		health = 10.0;
		maxHealth = 10.0;
		mana = 0.0;
		maxMana = 0.0;
		fullness = 0.0;
		maxFullness = 0.0;
	}

	public void update() {
		if (healthbarVisibility > 0) {
			healthbarVisibility--;
		}
		
		// execute the current action
		// routine.update();
		// attemptMove(routine.suggestedDirection(), routine.suggestedVelocity());
	}

	@Override
	protected void drawEntity(Graphics g) {
		// TODO Auto-generated method stub
		
	}
}