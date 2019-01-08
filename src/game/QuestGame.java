package game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Set;

import javax.swing.Timer;

import display.QuestPanel;

public class QuestGame {

	private double gameCenterX; // the position of the in-game cursor (the player)
	private double gameCenterY;
	private int gameCenterBlockX; // the block that the in-game cursor is currently on
	private int gameCenterBlockY;
	
	private double speed = 0.05;
	private int health = 100;
	private int mana = 100;
	
	private QuestMap qMap;
	private QuestPanel qPanel;
	private Timer time;
	
	public QuestGame(QuestPanel qPanel, QuestMap qMap) {
		this.qMap = qMap;
		this.qPanel = qPanel;
		qPanel.setQuestMap(qMap);
		
		gameCenterX = qMap.getWidth()/2.0;
		gameCenterY = qMap.getHeight()/2.0;
		gameCenterBlockX = (int)gameCenterX;
		gameCenterBlockY = (int)gameCenterY;
		
	    time = new Timer(5, new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            tick();
	        }
	    });

		time.start();
	}
	
	private void tick() {
		Set<Integer> keys = qPanel.getKeys();
		int dir = 0;
		
		processCurrentTile();
		
		if (keys.contains(KeyEvent.VK_UP)) {
			attemptMove(1);
			dir -= 1;
		}
		if (keys.contains(KeyEvent.VK_DOWN)) {
			attemptMove(2);
			dir += 1;
		}
		if (keys.contains(KeyEvent.VK_LEFT)) {
			attemptMove(3);
			dir -= 3;
		}
		if (keys.contains(KeyEvent.VK_RIGHT)) {
			attemptMove(4);
			dir += 3;
		}
		
		gameCenterBlockX = (int)(gameCenterX);
		gameCenterBlockY = (int)(gameCenterY);
		
		qPanel.setPlayerLocation(gameCenterX, gameCenterY, gameCenterBlockX, gameCenterBlockY);
		qPanel.setPlayerStatus(mana, health);
		qPanel.setCharDir(dir);
		qPanel.repaint();
	}
	
	private void attemptMove(int dir) { // 1 = up, 2 = down, 3 = left, 4 = right
		switch (dir) {
		case 1:
			switch (qMap.getTileAt(gameCenterBlockX, (int)(gameCenterY - speed))) {
			case -1:
				gameCenterY = (int)(gameCenterY) + 0.0000001;
				break;
			case 0:
				gameCenterY -= speed;
				break;
			case 1:
				gameCenterY = (int)Math.floor(gameCenterY) + 0.0000001;
				break;
			case 2:
				gameCenterY -= speed;
				break;
			default:
				gameCenterY -= speed;
				break;
			}
			break;
		case 2:
			switch (qMap.getTileAt(gameCenterBlockX, (int)(gameCenterY + speed))) {
			case -1:
				gameCenterY = (int)Math.ceil(gameCenterY) - 0.0000001;
				break;
			case 0:
				gameCenterY += speed;
				break;
			case 1:
				gameCenterY = (int)Math.ceil(gameCenterY) - 0.0000001;
				break;
			case 2:
				gameCenterY += speed;
				break;
			default:
				gameCenterY += speed;
				break;
			}
			break;
		case 3:
			switch (qMap.getTileAt((int)(gameCenterX - speed), gameCenterBlockY)) {
			case -1:
				gameCenterX = (int)Math.floor(gameCenterX) + 0.0000001;
				break;
			case 0:
				gameCenterX -= speed;
				break;
			case 1:
				gameCenterX = (int)Math.floor(gameCenterX) + 0.0000001;
				break;
			case 2:
				gameCenterX -= speed;
				break;
			default:
				gameCenterX -= speed;
				break;
			}
			break;
		case 4:
			switch (qMap.getTileAt((int)(gameCenterX + speed), gameCenterBlockY)) {
			case -1:
				gameCenterX = (int)Math.ceil(gameCenterX) - 0.0000001;
				break;
			case 0:
				gameCenterX += speed;
				break;
			case 1:
				gameCenterX = (int)Math.ceil(gameCenterX) - 0.0000001;
				break;
			case 2:
				gameCenterX += speed;
				break;
			default:
				gameCenterX += speed;
				break;
			}
			break;
		default:
			break;
		}
	}
	
	private void processCurrentTile() {
		switch (qMap.getTileAt(gameCenterBlockX, gameCenterBlockY)) {
		case 0:
			//zilch
			break;
		case 1:
			//impossible
			break;
		case 2:
			qMap.setTileAt(gameCenterBlockX, gameCenterBlockY, 0);
			break;
		case 3:
			if (mana < 100) mana++;
			break;
		case 4:
			if (health < 100) health++;
			break;
		case 5:
			if (health > 0) health--;
			if (mana > 0) mana--;
			if (speed > 0.01) speed -= .001;
			break;
		case 6:
			if (speed < 1) speed += .001;
		default:
			//zilch
			break;
		}
	}
}