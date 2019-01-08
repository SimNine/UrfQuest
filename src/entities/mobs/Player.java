package entities.mobs;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.util.Collection;

import javax.imageio.ImageIO;

import entities.items.Item;
import framework.UrfQuest;
import game.Inventory;
import game.InventoryEntry;

public class Player extends Mob {

	// img[0] is east, img[1] is SE, img[2] is S, etc
	private static BufferedImage[][] img = new BufferedImage[8][8];
	private final static String assetPath = "/assets/player/";
	
	private final double maxMana = 100.0;
	private final double maxFullness = 100.0;
	private double mana = 100.0;
	private double fullness = 100.0;
	private int fullnessCounter = 200;
	
	private Inventory inventory = new Inventory();

	public Player(double x, double y) {
		super(x, y);
		bounds = new Rectangle2D.Double(x, y, 1, 1);
		if (img[0][0] == null) {
			initPlayer();
		}
		velocity = 0.05;
		health = 100.0;
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

	// drawing methods
	protected void drawEntity(Graphics g) {
		final int STEP_SIZE = 15;
		
		if (animStage/STEP_SIZE == 8) {
			animStage = -1;
		}
		
		g.drawImage(img[direction/45][animStage/STEP_SIZE], 
					UrfQuest.panel.dispCenterX,
					UrfQuest.panel.dispCenterY,
					null);
	}
	
	public void drawDebug(Graphics g) {
		g.setColor(Color.BLACK);
		g.drawString("direction: " + this.direction, 
					 UrfQuest.panel.dispCenterX, 
					 UrfQuest.panel.dispCenterY);
		g.drawString("moveStage: " + this.animStage, 
					 UrfQuest.panel.dispCenterX, 
					 UrfQuest.panel.dispCenterY + 10);
	}

	public void update() {
		if (UrfQuest.panel.isGUIOpen()) {
			return;
		}
		
		// update hunger and health mechanics
		if (fullnessCounter > 0) {
			fullnessCounter--;
		} else {
			if (fullness > 0) {
				fullness -= 0.2;
			} else {
				health -= 0.2;
			}
			fullnessCounter = 200;
		}
		
		// use the selected item if space is down
		if (UrfQuest.keys.contains(KeyEvent.VK_SPACE)) {
			useSelectedItem();
		}
		
		// update each entry (cooldown) in the inventory
		for (InventoryEntry e : inventory.getInventoryEntries()) {
			e.update();
		}
		
		// process the tile the player is currently on
		processCurrentTile();
		
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
	}
	
	// helpers
	private void processCurrentTile() {
		switch (UrfQuest.game.getCurrMap().getTileAt((int)(bounds.getCenterX()),
													 (int)(bounds.getCenterY()))) {
		case 0:
			//nothing
			break;
		case 1:
			//impossible
			break;
		case 2:
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
			if (velocity > 0.01) incrementVelocity(-0.001);
			break;
		case 6:
			if (velocity < 1) incrementVelocity(0.001);
			break;
		case 7:
			//impossible
			break;
		case 8:
			//impossible
			break;
		case 9:
			//nothing
			break;
		default:
			//nothing
			break;
		}
	}

	// getters, setters, and incrementers
	public void incrementMana(double amt) {
		setMana(mana + amt);
	}
	
	public void setMana(double m) {
		if (m > maxMana) {
			mana = maxMana;
		} else {
			mana = m;
		}
	}
	
	public double getMana() {
		return mana;
	}
	
	public double getMaxMana() {
		return maxMana;
	}
	
	public void incrementHunger(double amt) {
		setFullness(fullness + amt);
	}
	
	public void setFullness(double f) {
		if (f > maxFullness) {
			fullness = maxFullness;
		} else {
			fullness = f;
		}
	}
	
	public double getFullness() {
		return fullness;
	}
	
	public double getMaxFullness() {
		return maxFullness;
	}
	
	// inventory methods
	public Collection<InventoryEntry> getInventory() {
		return inventory.getInventoryEntries();
	}
	
	public boolean addItem(Item i) {
		return inventory.addItem(i);
	}
	
	public Item getSelectedItem() {
		return inventory.getSelectedItem();
	}
	
	public Item dropOneOfSelectedItem() {
		return inventory.removeOneOfSelectedItem();
	}
	
	public void setSelectedEntry(int i) {
		inventory.setSelectedEntry(i);
	}
	
	public void useSelectedItem() {
		inventory.useSelectedItem();
	}
	
	// This is used in case the player's sprites somehow can't be loaded
	@SuppressWarnings("unused")
	private static void drawCharacterPlaceholder(Graphics g, int x, int y, int s, String dir) {
		switch (dir) {
		case "NW":
			g.setColor(Color.BLACK);
			g.fillRect(x + s*0, y + s*0, s*6, s*6);
			g.fillRect(x + s*6, y + s*0, s*1, s*2);
			g.fillRect(x + s*0, y + s*6, s*2, s*1);
			g.fillRect(x + s*6, y + s*3, s*1, s*7);
			g.fillRect(x + s*5, y + s*4, s*3, s*5);
			g.fillRect(x + s*4, y + s*5, s*5, s*3);
			g.fillRect(x + s*3, y + s*6, s*7, s*1);
			g.setColor(Color.YELLOW);
			g.fillRect(x + s*1, y + s*1, s*3, s*3);
			g.fillRect(x + s*4, y + s*1, s*1, s*1);
			g.fillRect(x + s*1, y + s*4, s*1, s*1);
			g.fillRect(x + s*3, y + s*3, s*2, s*2);
			g.fillRect(x + s*4, y + s*4, s*2, s*2);
			g.fillRect(x + s*5, y + s*5, s*2, s*2);
			g.fillRect(x + s*6, y + s*7, s*1, s*1);
			g.fillRect(x + s*7, y + s*6, s*1, s*1);
			break;
		case "W":
			g.setColor(Color.BLACK);
			g.fillRect(x + s*0, y + s*3, s*10, s*4);
			g.fillRect(x + s*1, y + s*2, s*4, s*1);
			g.fillRect(x + s*2, y + s*1, s*3, s*1);
			g.fillRect(x + s*3, y + s*0, s*2, s*10);
			g.fillRect(x + s*1, y + s*7, s*2, s*1);
			g.fillRect(x + s*2, y + s*8, s*3, s*1);
			g.setColor(Color.YELLOW);
			g.fillRect(x + s*3, y + s*2, s*1, s*1);
			g.fillRect(x + s*2, y + s*3, s*2, s*1);
			g.fillRect(x + s*1, y + s*4, s*8, s*2);
			g.fillRect(x + s*2, y + s*6, s*2, s*1);
			g.fillRect(x + s*3, y + s*7, s*1, s*1);
			break;
		case "SW":
			g.setColor(Color.BLACK);
			g.fillRect(x + s*6, y + s*0, s*1, s*7);
			g.fillRect(x + s*5, y + s*1, s*3, s*5);
			g.fillRect(x + s*4, y + s*2, s*5, s*3);
			g.fillRect(x + s*3, y + s*3, s*7, s*1);
			g.fillRect(x + s*0, y + s*4, s*6, s*6);
			g.fillRect(x + s*0, y + s*3, s*2, s*1);
			g.fillRect(x + s*6, y + s*8, s*1, s*2);
			g.setColor(Color.YELLOW);
			g.fillRect(x + s*7, y + s*3, s*1, s*1);
			g.fillRect(x + s*6, y + s*2, s*1, s*1);
			g.fillRect(x + s*5, y + s*3, s*2, s*2);
			g.fillRect(x + s*4, y + s*4, s*2, s*2);
			g.fillRect(x + s*3, y + s*5, s*2, s*2);
			g.fillRect(x + s*1, y + s*6, s*3, s*3);
			g.fillRect(x + s*4, y + s*8, s*1, s*1);
			g.fillRect(x + s*1, y + s*5, s*1, s*1);
			break;
		case "N":
			g.setColor(Color.BLACK);
			g.fillRect(x + s*3, y + s*0, s*4, s*10);
			g.fillRect(x + s*2, y + s*1, s*6, s*4);
			g.fillRect(x + s*1, y + s*2, s*8, s*3);
			g.fillRect(x + s*0, y + s*3, s*10, s*2);
			g.setColor(Color.YELLOW);
			g.fillRect(x + s*4, y + s*1, s*2, s*8);
			g.fillRect(x + s*3, y + s*2, s*4, s*2);
			g.fillRect(x + s*2, y + s*3, s*6, s*1);
			break;
		case "S":
			g.setColor(Color.BLACK);
			g.fillRect(x + s*3, y + s*0, s*4, s*10);
			g.fillRect(x + s*0, y + s*5, s*10, s*2);
			g.fillRect(x + s*1, y + s*7, s*8, s*1);
			g.fillRect(x + s*2, y + s*8, s*6, s*1);
			g.setColor(Color.YELLOW);
			g.fillRect(x + s*4, y + s*1, s*2, s*8);
			g.fillRect(x + s*3, y + s*7, s*4, s*1);
			g.fillRect(x + s*2, y + s*6, s*6, s*1);
			break;
		case "NE":
			g.setColor(Color.BLACK);
			g.fillRect(x + s*4, y + s*0, s*6, s*6);
			g.fillRect(x + s*3, y + s*0, s*1, s*2);
			g.fillRect(x + s*8, y + s*6, s*2, s*1);
			g.fillRect(x + s*3, y + s*3, s*1, s*7);
			g.fillRect(x + s*2, y + s*4, s*3, s*5);
			g.fillRect(x + s*1, y + s*5, s*5, s*3);
			g.fillRect(x + s*0, y + s*6, s*7, s*1);
			g.setColor(Color.YELLOW);
			g.fillRect(x + s*6, y + s*1, s*3, s*3);
			g.fillRect(x + s*5, y + s*1, s*1, s*1);
			g.fillRect(x + s*8, y + s*4, s*1, s*1);
			g.fillRect(x + s*5, y + s*3, s*2, s*2);
			g.fillRect(x + s*4, y + s*4, s*2, s*2);
			g.fillRect(x + s*3, y + s*5, s*2, s*2);
			g.fillRect(x + s*2, y + s*6, s*1, s*1);
			g.fillRect(x + s*3, y + s*7, s*1, s*1);
			break;
		case "E":
			g.setColor(Color.BLACK);
			g.fillRect(x + s*0, y + s*3, s*10, s*4);
			g.fillRect(x + s*5, y + s*0, s*2, s*10);
			g.fillRect(x + s*5, y + s*1, s*3, s*8);
			g.fillRect(x + s*5, y + s*2, s*4, s*6);
			g.setColor(Color.YELLOW);
			g.fillRect(x + s*1, y + s*4, s*8, s*2);
			g.fillRect(x + s*6, y + s*2, s*1, s*6);
			g.fillRect(x + s*6, y + s*3, s*2, s*4);
			break;
		case "SE":
			g.setColor(Color.BLACK);
			g.fillRect(x + s*3, y + s*0, s*1, s*7);
			g.fillRect(x + s*2, y + s*1, s*3, s*5);
			g.fillRect(x + s*1, y + s*2, s*5, s*3);
			g.fillRect(x + s*0, y + s*3, s*7, s*1);
			g.fillRect(x + s*4, y + s*4, s*6, s*6);
			g.fillRect(x + s*3, y + s*8, s*1, s*2);
			g.fillRect(x + s*8, y + s*3, s*2, s*1);
			g.setColor(Color.YELLOW);
			g.fillRect(x + s*3, y + s*2, s*1, s*1);
			g.fillRect(x + s*2, y + s*3, s*1, s*1);
			g.fillRect(x + s*3, y + s*3, s*2, s*2);
			g.fillRect(x + s*4, y + s*4, s*2, s*2);
			g.fillRect(x + s*5, y + s*5, s*2, s*2);
			g.fillRect(x + s*6, y + s*6, s*3, s*3);
			g.fillRect(x + s*5, y + s*8, s*1, s*1);
			g.fillRect(x + s*8, y + s*5, s*1, s*1);
			break;
		default:
			g.setColor(Color.BLACK);
			g.fillRect(x + s*2, y + s*2, s*6, s*6);
			g.setColor(Color.YELLOW);
			g.fillRect(x + s*3, y + s*3, s*4, s*4);
			break;
		}
	}
}
