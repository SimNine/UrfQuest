package display;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JPanel;
import javax.swing.Timer;

import game.QuestGame;
import game.QuestMap;

@SuppressWarnings("serial")
public class QuestPanel extends JPanel {
	
	private int scale = 3; // the scale is 1/10th the width of a block, i.e., the width of one "pixel" of a block
	private int dispCenterX; // the center of this JPanel relative to the top-left corner, in pixels
	private int dispCenterY;
	private int dispBlockWidth; // the number of blocks needed to fill the screen
	private int dispBlockHeight;
	private double gameCenterX; // the position of the in-game cursor (the player)
	private double gameCenterY;
	private int gameCenterBlockX; // the block that the in-game cursor is currently on
	private int gameCenterBlockY;
	private Set<Integer> keys = new HashSet<Integer>(0);
	private QuestMap questMap;
	private Timer time;
	
	public QuestPanel(QuestGame questGame, int initwidth, int initheight) {
		super();
		questMap = new QuestMap(500, 500);
		setFocusable(true);
		requestFocusInWindow();
		gameCenterX = questMap.getWidth()/2.0;
		gameCenterY = questMap.getHeight()/2.0;
		gameCenterBlockX = (int)gameCenterX;
		gameCenterBlockY = (int)gameCenterY;
		setSize(initwidth, initheight);
	    time = new Timer(5, new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            tick();
	        }
	    });
		addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
				keys.add(e.getKeyCode());
				repaint();
			}
			public void keyReleased(KeyEvent e) {
				keys.remove(e.getKeyCode());
				repaint();
			}
			public void keyTyped(KeyEvent e) {}
		});
		time.start();
	}
	
	private void tick() {
		if (keys.contains(KeyEvent.VK_UP)) {
			gameCenterY -= .05;
		}
		if (keys.contains(KeyEvent.VK_DOWN)) {
			gameCenterY += .05;
		}
		if (keys.contains(KeyEvent.VK_LEFT)) {
			gameCenterX -= .05;
		}
		if (keys.contains(KeyEvent.VK_RIGHT)) {
			gameCenterX += .05;
		}
		gameCenterBlockX = (int)(gameCenterX);
		gameCenterBlockY = (int)(gameCenterY);
		repaint();
	}
	
	public void paintComponent(Graphics g) {
		for (int x = -(int)Math.floor(dispBlockWidth/2.0); x < Math.ceil(dispBlockWidth/2.0); x++) {
			for (int y = -(int)Math.floor(dispBlockHeight/2.0); y < Math.ceil(dispBlockHeight/2.0); y++) {
				switch (questMap.getBlockAt(gameCenterBlockX + x, gameCenterBlockY + y)) {
				case -1:
					g.setColor(Color.BLACK);
					g.fillRect(dispCenterX - (int)((gameCenterX % 1)*scale*10) + x*scale*10, dispCenterY - (int)((gameCenterY % 1)*scale*10) + y*scale*10, scale*10, scale*10);
					break;
				case 0:
					pixelart.FloorBlocks.drawDirtBlock(g, dispCenterX - (int)((gameCenterX % 1)*scale*10) + x*scale*10, dispCenterY - (int)((gameCenterY % 1)*scale*10) + y*scale*10, scale);
					break;
				case 1:
					pixelart.FloorBlocks.drawStoneBlock(g, dispCenterX - (int)((gameCenterX % 1)*scale*10) + x*scale*10, dispCenterY - (int)((gameCenterY % 1)*scale*10) + y*scale*10, scale);
					break;
				case 2:
					pixelart.FloorBlocks.drawGrassBlock(g, dispCenterX - (int)((gameCenterX % 1)*scale*10) + x*scale*10, dispCenterY - (int)((gameCenterY % 1)*scale*10) + y*scale*10, scale);
					break;
				}
			}
		}
		
		g.setColor(Color.WHITE);
		g.drawLine(dispCenterX - 10, dispCenterY, dispCenterX + 10, dispCenterY);
		g.drawLine(dispCenterX, dispCenterY - 10, dispCenterX, dispCenterY + 10);
		g.drawString(keys.toString(), 10, 10);
		g.drawString("GameCenter: " + gameCenterX + ", " + gameCenterY, 10, 20);
		g.drawString("GameCenterBlock: " + gameCenterBlockX + ", " + gameCenterBlockY, 10, 30);
		g.drawString("DisplayCenter: " + dispCenterX + ", " + dispCenterY, 10, 40);
		g.drawString("DisplayDimensions: " + dispBlockWidth + ", " + dispBlockHeight, 10, 50);
	}
	
	public void setSize(int w, int h) {
		super.setSize(w, h);
		dispCenterX = w/2;
		dispCenterY = h/2;
		dispBlockWidth = (int)Math.ceil(this.getWidth()/(scale*10))+2;
		dispBlockHeight = (int)Math.ceil(this.getHeight()/(scale*10))+2;
		System.out.println("DisplayCenter: " + dispCenterX + ", " + dispCenterY);
		System.out.println("DisplayBlockWidth: " + dispBlockWidth + ", " + dispBlockHeight);
		repaint();
	}
}