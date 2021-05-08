package client.entities.mobs;

import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import server.entities.mobs.Font;
import server.entities.mobs.Graphics;

public class Player {

	private final static String assetPath = "/assets/player/";

	// img[0] is east, img[1] is SE, img[2] is S, etc
	private static BufferedImage[][] img = new BufferedImage[8][8];
	
	public Player() {
		if (img[0][0] == null) {
			initPlayer();
		}
	}
	
	private void initPlayer() {
		// Load E-W
		try {
			img[0][0] = img[0][4] = img[4][0] = img[4][4] = ImageIO.read(UrfQuest.quest.getClass().getResourceAsStream(assetPath + "E1.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Image could not be read at: " + assetPath + "E1.png");
		}
		try {
			img[0][1] = img[0][3] = img[4][1] = img[4][3] = ImageIO.read(UrfQuest.quest.getClass().getResourceAsStream(assetPath + "E2.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Image could not be read at: " + assetPath + "E2.png");
		}
		try {
			img[0][2] = img[4][2] = ImageIO.read(UrfQuest.quest.getClass().getResourceAsStream(assetPath + "E3.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Image could not be read at: " + assetPath + "E3.png");
		}
		try {
			img[0][5] = img[0][7] = img[4][5] = img[4][7] = ImageIO.read(UrfQuest.quest.getClass().getResourceAsStream(assetPath + "E6.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Image could not be read at: " + assetPath + "E6.png");
		}
		try {
			img[0][6] = img[4][6] = ImageIO.read(UrfQuest.quest.getClass().getResourceAsStream(assetPath + "E7.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Image could not be read at: " + assetPath + "E7.png");
		}
		
		// Load NE-SW
		try {
			img[7][0] = img[7][4] = img[3][0] = img[3][4] = ImageIO.read(UrfQuest.quest.getClass().getResourceAsStream(assetPath + "NE1.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Image could not be read at: " + assetPath + "NE1.png");
		}
		try {
			img[7][1] = img[7][3] = img[3][1] = img[3][3] = ImageIO.read(UrfQuest.quest.getClass().getResourceAsStream(assetPath + "NE2.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Image could not be read at: " + assetPath + "NE2.png");
		}
		try {
			img[7][2] = img[3][2] = ImageIO.read(UrfQuest.quest.getClass().getResourceAsStream(assetPath + "NE3.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Image could not be read at: " + assetPath + "NE3.png");
		}
		try {
			img[7][5] = img[7][7] = img[3][5] = img[3][7] = ImageIO.read(UrfQuest.quest.getClass().getResourceAsStream(assetPath + "NE6.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Image could not be read at: " + assetPath + "NE6.png");
		}
		try {
			img[7][6] = img[3][6] = ImageIO.read(UrfQuest.quest.getClass().getResourceAsStream(assetPath + "NE7.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Image could not be read at: " + assetPath + "NE7.png");
		}
		
		// Load NW-SE
		try {
			img[5][0] = img[5][4] = img[1][0] = img[1][4] = ImageIO.read(UrfQuest.quest.getClass().getResourceAsStream(assetPath + "NW1.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Image could not be read at: " + assetPath + "NW1.png");
		}
		try {
			img[5][1] = img[5][3] = img[1][1] = img[1][3] = ImageIO.read(UrfQuest.quest.getClass().getResourceAsStream(assetPath + "NW2.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Image could not be read at: " + assetPath + "NW2.png");
		}
		try {
			img[5][2] = img[1][2] = ImageIO.read(UrfQuest.quest.getClass().getResourceAsStream(assetPath + "NW3.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Image could not be read at: " + assetPath + "NW3.png");
		}
		try {
			img[5][5] = img[5][7] = img[1][5] = img[1][7] = ImageIO.read(UrfQuest.quest.getClass().getResourceAsStream(assetPath + "NW6.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Image could not be read at: " + assetPath + "NW6.png");
		}
		try {
			img[5][6] = img[1][6] = ImageIO.read(UrfQuest.quest.getClass().getResourceAsStream(assetPath + "NW7.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Image could not be read at: " + assetPath + "NW7.png");
		}
		
		// Load north-south
		try {
			img[6][0] = img[6][4] = img[2][0] = img[2][4] = ImageIO.read(UrfQuest.quest.getClass().getResourceAsStream(assetPath + "N1.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Image could not be read at: " + assetPath + "N1.png");
		}
		try {
			img[6][1] = img[6][3] = img[2][1] = img[2][3] = ImageIO.read(UrfQuest.quest.getClass().getResourceAsStream(assetPath + "N2.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Image could not be read at: " + assetPath + "N2.png");
		}
		try {
			img[6][2] = img[2][2] = ImageIO.read(UrfQuest.quest.getClass().getResourceAsStream(assetPath + "N3.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Image could not be read at: " + assetPath + "N3.png");
		}
		try {
			img[6][5] = img[6][7] = img[2][5] = img[2][7] = ImageIO.read(UrfQuest.quest.getClass().getResourceAsStream(assetPath + "N6.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Image could not be read at: " + assetPath + "N6.png");
		}
		try {
			img[6][6] = img[2][6] = ImageIO.read(UrfQuest.quest.getClass().getResourceAsStream(assetPath + "N7.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Image could not be read at: " + assetPath + "N7.png");
		}
	}
	
	public void update() {
		// get the direction indicated by the current keys pressed
		int newDir;
		if (UrfQuest.keys.contains(KeyEvent.VK_W)) {
			if (UrfQuest.keys.contains(KeyEvent.VK_D)) {
				newDir = 315;
			} else if (UrfQuest.keys.contains(KeyEvent.VK_A)) {
				newDir = 225;
			} else {
				newDir = 270;
			}
		} else if (UrfQuest.keys.contains(KeyEvent.VK_S)) {
			if (UrfQuest.keys.contains(KeyEvent.VK_D)) {
				newDir = 45;
			} else if (UrfQuest.keys.contains(KeyEvent.VK_A)) {
				newDir = 135;
			} else {
				newDir = 90;
			}
		} else if (UrfQuest.keys.contains(KeyEvent.VK_D)) {
			newDir = 0;
		} else if (UrfQuest.keys.contains(KeyEvent.VK_A)) {
			newDir = 180;
		} else { // returns if no keys are pressed
			this.animStage = 0;
			return;
		}
	
		// try to move in the current direction with the current velocity
		attemptMove(newDir, velocity);
		if (newDir == direction) {
			this.animStage++;
		} else { // if (newOrientation != orientation)
			this.animStage = 0;
			direction = newDir;
		}
		
		// use the selected item if space is down
		if (UrfQuest.keys.contains(KeyEvent.VK_SPACE)) {
			useSelectedItem();
		}
	}
	
	/*
	 * Drawing methods
	 */
	
	protected void drawEntity(Graphics g) {
		final int STEP_SIZE = 15;
		
		if (animStage/STEP_SIZE == 8) {
			animStage = -1;
		}
		
		g.drawImage(img[direction/45][animStage/STEP_SIZE], 
					UrfQuest.panel.gameToWindowX(bounds.getX()), 
					UrfQuest.panel.gameToWindowY(bounds.getY()), 
					null);
		
		g.setColor(Color.BLACK);
		g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 15));
		g.drawString(name, 
					 UrfQuest.panel.gameToWindowX(bounds.getX()) - 5*(name.length()/2), 
					 UrfQuest.panel.gameToWindowY(bounds.getY()));
		
		drawHealthBar(g);
	}
	
	public void drawDebug(Graphics g) {
		g.setColor(Color.BLACK);
		g.drawString("direction: " + this.direction, 
					 UrfQuest.panel.gameToWindowX(bounds.getX()), 
					 UrfQuest.panel.gameToWindowY(bounds.getY()));
		g.drawString("moveStage: " + this.animStage, 
					 UrfQuest.panel.gameToWindowX(bounds.getX()), 
					 UrfQuest.panel.gameToWindowY(bounds.getY()) + 10);
	}

	
}
