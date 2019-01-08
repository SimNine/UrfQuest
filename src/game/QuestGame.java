package game;

import java.awt.event.KeyEvent;

import framework.V;

public class QuestGame {
	
	public QuestGame() {
		V.qGame = this;
		
		V.gameCenterX = V.qMap.getWidth()/2.0;
		V.gameCenterY = V.qMap.getHeight()/2.0;
		V.gameCenterTileX = (int)V.gameCenterX;
		V.gameCenterTileY = (int)V.gameCenterY;
	}
	
	public void tick() {
		int dir = 0;
		
		processCurrentTile();
		
		for (Entity e : V.entities) {
			e.update();
		}
		
		if (V.keys.contains(KeyEvent.VK_UP)) {
			attemptMove(1);
			dir -= 1;
		}
		if (V.keys.contains(KeyEvent.VK_DOWN)) {
			attemptMove(2);
			dir += 1;
		}
		if (V.keys.contains(KeyEvent.VK_LEFT)) {
			attemptMove(3);
			dir -= 3;
		}
		if (V.keys.contains(KeyEvent.VK_RIGHT)) {
			attemptMove(4);
			dir += 3;
		}
		
		V.gameCenterTileX = (int)(V.gameCenterX);
		V.gameCenterTileY = (int)(V.gameCenterY);
		V.facing = dir;
	}
	
	private void attemptMove(int dir) { // 1 = up, 2 = down, 3 = left, 4 = right
		switch (dir) {
		case 1:
			switch (V.qMap.getTileAt(V.gameCenterTileX, (int)(V.gameCenterY - V.speed))) {
			case -1:
				V.gameCenterY = (int)(V.gameCenterY) + 0.0000001;
				break;
			case 0:
				V.gameCenterY -= V.speed;
				break;
			case 1:
				V.gameCenterY = (int)Math.floor(V.gameCenterY) + 0.0000001;
				break;
			case 2:
				V.gameCenterY -= V.speed;
				break;
			default:
				V.gameCenterY -= V.speed;
				break;
			}
			break;
		case 2:
			switch (V.qMap.getTileAt(V.gameCenterTileX, (int)(V.gameCenterY + V.speed))) {
			case -1:
				V.gameCenterY = (int)Math.ceil(V.gameCenterY) - 0.0000001;
				break;
			case 0:
				V.gameCenterY += V.speed;
				break;
			case 1:
				V.gameCenterY = (int)Math.ceil(V.gameCenterY) - 0.0000001;
				break;
			case 2:
				V.gameCenterY += V.speed;
				break;
			default:
				V.gameCenterY += V.speed;
				break;
			}
			break;
		case 3:
			switch (V.qMap.getTileAt((int)(V.gameCenterX - V.speed), V.gameCenterTileY)) {
			case -1:
				V.gameCenterX = (int)Math.floor(V.gameCenterX) + 0.0000001;
				break;
			case 0:
				V.gameCenterX -= V.speed;
				break;
			case 1:
				V.gameCenterX = (int)Math.floor(V.gameCenterX) + 0.0000001;
				break;
			case 2:
				V.gameCenterX -= V.speed;
				break;
			default:
				V.gameCenterX -= V.speed;
				break;
			}
			break;
		case 4:
			switch (V.qMap.getTileAt((int)(V.gameCenterX + V.speed), V.gameCenterTileY)) {
			case -1:
				V.gameCenterX = (int)Math.ceil(V.gameCenterX) - 0.0000001;
				break;
			case 0:
				V.gameCenterX += V.speed;
				break;
			case 1:
				V.gameCenterX = (int)Math.ceil(V.gameCenterX) - 0.0000001;
				break;
			case 2:
				V.gameCenterX += V.speed;
				break;
			default:
				V.gameCenterX += V.speed;
				break;
			}
			break;
		default:
			break;
		}
	}
	
	private void processCurrentTile() {
		switch (V.qMap.getTileAt(V.gameCenterTileX, V.gameCenterTileY)) {
		case 0:
			//zilch
			break;
		case 1:
			//impossible
			break;
		case 2:
			V.qMap.setTileAt(V.gameCenterTileX, V.gameCenterTileY, 0);
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