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
		
		if (keys.contains(KeyEvent.VK_UP)) {
			attemptMove(1);
		}
		if (keys.contains(KeyEvent.VK_DOWN)) {
			attemptMove(2);
		}
		if (keys.contains(KeyEvent.VK_LEFT)) {
			attemptMove(3);
		}
		if (keys.contains(KeyEvent.VK_RIGHT)) {
			attemptMove(4);
		}
		
		gameCenterBlockX = (int)(gameCenterX);
		gameCenterBlockY = (int)(gameCenterY);
		
		qPanel.setRenderVars(gameCenterX, gameCenterY, gameCenterBlockX, gameCenterBlockY);
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
}