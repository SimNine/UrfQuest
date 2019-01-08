package entities;

import java.awt.Graphics;
import java.awt.event.KeyEvent;

import framework.V;
import pixelart.Sprites;

public class Player extends Entity{
	private double health = 100.0;
	private double mana = 100.0;
	private double speed = 0.05;

	public Player(double x, double y) {
		super(x, y);
	}

	@Override
	protected void drawEntity(Graphics g) {
		Sprites.drawCharacterPlaceholder(g, V.dispCenterX - 5*V.scale, V.dispCenterY - 5*V.scale, V.scale, V.player.getOrientation());
	}

	@Override
	public void update() {
		processCurrentTile();
		
		orientation = "";
		
		if (V.keys.contains(KeyEvent.VK_UP)) {
			attemptMove(1);
			orientation += "N";
		}
		if (V.keys.contains(KeyEvent.VK_DOWN)) {
			attemptMove(2);
			orientation += "S";
		}
		if (V.keys.contains(KeyEvent.VK_LEFT)) {
			attemptMove(3);
			orientation += "W";
		}
		if (V.keys.contains(KeyEvent.VK_RIGHT)) {
			attemptMove(4);
			orientation += "E";
		}
		if (orientation.isEmpty()) {
			orientation = "NONE";
		}
	}
	
	// helper
	private void processCurrentTile() {
		switch (V.qMap.getTileAt((int)Xpos, (int)Ypos)) {
		case 0:
			//zilch
			break;
		case 1:
			//impossible
			break;
		case 2:
			V.qMap.setTileAt((int)Xpos, (int)Ypos, 0);
			break;
		case 3:
			if (mana < 100) incrementMana(0.1);
			break;
		case 4:
			if (health < 100) incrementHealth(0.1);
			break;
		case 5:
			if (health > 0) incrementHealth(-0.1);
			if (mana > 0) incrementMana(-0.1);
			if (speed > 0.01) incrementSpeed(-.001);
			break;
		case 6:
			if (speed < 1) incrementSpeed(.001);
			break;
		default:
			//zilch
			break;
		}
	}
	
	// helper
	private void attemptMove(int dir) { // 1 = up, 2 = down, 3 = left, 4 = right
		switch (dir) {
		case 1:
			switch (V.qMap.getTileAt((int)Xpos, (int)(Ypos - speed))) {
			case -1:
				Ypos = (int)(Ypos) + 0.0000001;
				break;
			case 0:
				Ypos -= speed;
				break;
			case 1:
				Ypos = (int)Math.floor(Ypos) + 0.0000001;
				break;
			case 2:
				Ypos -= speed;
				break;
			default:
				Ypos -= speed;
				break;
			}
			break;
		case 2:
			switch (V.qMap.getTileAt((int)Xpos, (int)(Ypos + speed))) {
			case -1:
				Ypos = (int)Math.ceil(Ypos) - 0.0000001;
				break;
			case 0:
				Ypos += speed;
				break;
			case 1:
				Ypos = (int)Math.ceil(Ypos) - 0.0000001;
				break;
			case 2:
				Ypos += speed;
				break;
			default:
				Ypos += speed;
				break;
			}
			break;
		case 3:
			switch (V.qMap.getTileAt((int)(Xpos - speed), (int)Ypos)) {
			case -1:
				Xpos = (int)Math.floor(Xpos) + 0.0000001;
				break;
			case 0:
				Xpos -= speed;
				break;
			case 1:
				Xpos = (int)Math.floor(Xpos) + 0.0000001;
				break;
			case 2:
				Xpos -= speed;
				break;
			default:
				Xpos -= speed;
				break;
			}
			break;
		case 4:
			switch (V.qMap.getTileAt((int)(Xpos + speed), (int)Ypos)) {
			case -1:
				Xpos = (int)Math.ceil(Xpos) - 0.0000001;
				break;
			case 0:
				Xpos += speed;
				break;
			case 1:
				Xpos = (int)Math.ceil(Xpos) - 0.0000001;
				break;
			case 2:
				Xpos += speed;
				break;
			default:
				Xpos += speed;
				break;
			}
			break;
		default:
			break;
		}
	}
	
	// incrementers and decrementers
	public void incrementHealth(double amt) {
		health += amt;
	}
	
	public void incrementMana(double amt) {
		mana += amt;
	}
	
	public void incrementSpeed(double amt) {
		speed += amt;
	}
	
	// getters and setters
	public void setOrientation(String o) {
		orientation = o;
	}
	
	public void setHealth(double h) {
		health = h;
	}
	
	public void setMana(double m) {
		mana = m;
	}
	
	public void setSpeed(double s) {
		speed = s;
	}
	
	public String getOrientation() {
		return orientation;
	}
	
	public double getHealth() {
		return health;
	}
	
	public double getMana() {
		return mana;
	}
	
	public double getSpeed() {
		return speed;
	}

}
