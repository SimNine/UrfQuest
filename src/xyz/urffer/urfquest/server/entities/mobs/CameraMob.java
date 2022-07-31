package xyz.urffer.urfquest.server.entities.mobs;

import java.awt.Graphics;

import xyz.urffer.urfquest.server.Server;
import xyz.urffer.urfquest.server.entities.mobs.ai.routines.IdleRoutine;
import xyz.urffer.urfquest.server.map.Map;
import xyz.urffer.urfquest.server.state.State;
import xyz.urffer.urfquest.shared.Constants;

public class CameraMob extends Mob {
	public static int STILL_MODE = 1301;
	public static int DEMO_MODE = 1302;
	
	private int mode;

	public CameraMob(Server srv, State s, Map m, double[] pos, int mode) {
		super(srv, m, pos);
		
		movementVector.magnitude = 0.01;
		movementVector.dirRadians = server.randomDouble()*2.0*Math.PI;
		
		health = 100.0;
		maxHealth = 100.0;
		routine = new IdleRoutine(server, this);
		
		this.mode = mode;
	}

	public void tick() {
		// TODO: update with vector
//		if (mode == DEMO_MODE) {
//			if (this.map.getTileTypeAt((int)(bounds.x + velocity), (int)bounds.y) == -1) {
//				if (0 <= direction && direction <= 180) {
//					direction = (90 - direction) + 90;
//				} else { // if (
//					direction = (270 - direction) + 270;
//				}
//			}
//			if (this.map.getTileTypeAt((int)bounds.x, (int)(bounds.y + velocity)) == -1) {
//				if (0 <= direction && direction <= 90) {
//					direction = 360 - direction;
//				} else if (90 < direction && direction < 270) {
//					direction = (180 - direction) + 180;
//				} else { // if (270 <= direction && direction <= 360)
//					direction = 360 - direction;
//				}
//			}
//			
//			this.move(direction, velocity);
//		} else { // if (mode == STILL_MODE)
//			// do nothing
//		}
	}

	protected void drawEntity(Graphics g) {
		// do nothing
	}

	@Override
	public double getBaseSpeed() {
		return Constants.DEFAULT_VELOCITY_CAMERA;
	}
}