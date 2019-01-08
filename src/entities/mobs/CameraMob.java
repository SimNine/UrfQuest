package entities.mobs;

import java.awt.Graphics;

import entities.mobs.ai.routines.IdleRoutine;
import framework.UrfQuest;
import game.QuestMap;

public class CameraMob extends Mob {
	public static int STILL_MODE = 1301;
	public static int DEMO_MODE = 1302;
	
	private int mode;

	public CameraMob(double x, double y, int mode, QuestMap m) {
		super(x, y, m);
		velocity = 0.01;
		direction = (int)(Math.random()*360.0);
		health = 100.0;
		maxHealth = 100.0;
		routine = new IdleRoutine(this);
		
		this.mode = mode;
	}

	public void update() {
		if (mode == DEMO_MODE) {
			if (UrfQuest.game.getCurrMap().getTileTypeAt((int)(bounds.x + velocity), (int)bounds.y) == -1) {
				if (0 <= direction && direction <= 180) {
					direction = (90 - direction) + 90;
				} else { // if (
					direction = (270 - direction) + 270;
				}
			}
			if (UrfQuest.game.getCurrMap().getTileTypeAt((int)bounds.x, (int)(bounds.y + velocity)) == -1) {
				if (0 <= direction && direction <= 90) {
					direction = 360 - direction;
				} else if (90 < direction && direction < 270) {
					direction = (180 - direction) + 180;
				} else { // if (270 <= direction && direction <= 360)
					direction = 360 - direction;
				}
			}
			
			this.move(direction, velocity);
		} else { // if (mode == STILL_MODE)
			// do nothing
		}
	}

	protected void drawEntity(Graphics g) {
		// do nothing
	}
}
