package xyz.urffer.urfquest.server.entities.mobs;

import java.awt.Graphics;

import xyz.urffer.urfquest.server.Server;
import xyz.urffer.urfquest.server.entities.mobs.ai.routines.IdleRoutine;
import xyz.urffer.urfquest.server.state.State;
import xyz.urffer.urfquest.shared.Constants;
import xyz.urffer.urfquest.shared.protocol.Message;

public class CameraMob extends Mob {
	public static int STILL_MODE = 1301;
	public static int DEMO_MODE = 1302;
	
	private int mode;

	public CameraMob(Server srv, State s, int mode) {
		super(srv);
		
		health = 100;
		maxHealth = 100;
		routine = new IdleRoutine(server, this);
		
		this.mode = mode;
		
		this.server.sendMessageToAllClients(this.initMessage());
	}
	
	public Message initMessage() {
		return null;
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
