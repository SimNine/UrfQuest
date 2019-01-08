package game;

import java.awt.event.KeyEvent;

import entities.Entity;
import framework.V;

public class QuestGame {
	
	public QuestGame() {
		V.qGame = this;
		
		V.playerPositionX = V.qMap.getWidth()/2.0;
		V.playerPositionY = V.qMap.getHeight()/2.0;
	}
	
	public void tick() {
		String dir = "";
		
		processCurrentTile();
		
		for (Entity e : V.entities) {
			e.update();
		}
		
		if (V.keys.contains(KeyEvent.VK_UP)) {
			attemptMove(1);
			dir += "N";
		}
		if (V.keys.contains(KeyEvent.VK_DOWN)) {
			attemptMove(2);
			dir += "S";
		}
		if (V.keys.contains(KeyEvent.VK_LEFT)) {
			attemptMove(3);
			dir += "W";
		}
		if (V.keys.contains(KeyEvent.VK_RIGHT)) {
			attemptMove(4);
			dir += "E";
		}
		if (dir.isEmpty()) {
			dir += "X";
		}
		
		V.playerOrientation = dir;
	}
	
	private void attemptMove(int dir) { // 1 = up, 2 = down, 3 = left, 4 = right
		switch (dir) {
		case 1:
			switch (V.qMap.getTileAt((int)V.playerPositionX, (int)(V.playerPositionY - V.speed))) {
			case -1:
				V.playerPositionY = (int)(V.playerPositionY) + 0.0000001;
				break;
			case 0:
				V.playerPositionY -= V.speed;
				break;
			case 1:
				V.playerPositionY = (int)Math.floor(V.playerPositionY) + 0.0000001;
				break;
			case 2:
				V.playerPositionY -= V.speed;
				break;
			default:
				V.playerPositionY -= V.speed;
				break;
			}
			break;
		case 2:
			switch (V.qMap.getTileAt((int)V.playerPositionX, (int)(V.playerPositionY + V.speed))) {
			case -1:
				V.playerPositionY = (int)Math.ceil(V.playerPositionY) - 0.0000001;
				break;
			case 0:
				V.playerPositionY += V.speed;
				break;
			case 1:
				V.playerPositionY = (int)Math.ceil(V.playerPositionY) - 0.0000001;
				break;
			case 2:
				V.playerPositionY += V.speed;
				break;
			default:
				V.playerPositionY += V.speed;
				break;
			}
			break;
		case 3:
			switch (V.qMap.getTileAt((int)(V.playerPositionX - V.speed), (int)V.playerPositionY)) {
			case -1:
				V.playerPositionX = (int)Math.floor(V.playerPositionX) + 0.0000001;
				break;
			case 0:
				V.playerPositionX -= V.speed;
				break;
			case 1:
				V.playerPositionX = (int)Math.floor(V.playerPositionX) + 0.0000001;
				break;
			case 2:
				V.playerPositionX -= V.speed;
				break;
			default:
				V.playerPositionX -= V.speed;
				break;
			}
			break;
		case 4:
			switch (V.qMap.getTileAt((int)(V.playerPositionX + V.speed), (int)V.playerPositionY)) {
			case -1:
				V.playerPositionX = (int)Math.ceil(V.playerPositionX) - 0.0000001;
				break;
			case 0:
				V.playerPositionX += V.speed;
				break;
			case 1:
				V.playerPositionX = (int)Math.ceil(V.playerPositionX) - 0.0000001;
				break;
			case 2:
				V.playerPositionX += V.speed;
				break;
			default:
				V.playerPositionX += V.speed;
				break;
			}
			break;
		default:
			break;
		}
	}
	
	private void processCurrentTile() {
		switch (V.qMap.getTileAt((int)V.playerPositionX, (int)V.playerPositionY)) {
		case 0:
			//zilch
			break;
		case 1:
			//impossible
			break;
		case 2:
			V.qMap.setTileAt((int)V.playerPositionX, (int)V.playerPositionY, 0);
			break;
		case 3:
			if (V.mana < 100) V.mana += 0.1;
			break;
		case 4:
			if (V.health < 100) V.health += 0.1;
			break;
		case 5:
			if (V.health > 0) V.health -= 0.1;
			if (V.mana > 0) V.mana -= 0.1;
			if (V.speed > 0.01) V.speed -= .001;
			break;
		case 6:
			if (V.speed < 1) V.speed += .001;
			break;
		default:
			//zilch
			break;
		}
	}
}