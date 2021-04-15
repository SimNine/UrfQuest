package server.entities.mobs;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.imageio.ImageIO;

import server.entities.items.Item;
import server.game.Inventory;
import server.game.QuestMap;
import framework.UrfQuest;

public class Player extends Mob {

	// img[0] is east, img[1] is SE, img[2] is S, etc
	private static BufferedImage[][] img = new BufferedImage[8][8];
	private final static String assetPath = "/assets/player/";
	
	private String name;
	private int statCounter = 200;
	private Inventory inventory;
	private Item heldItem;
	
	private double pickupRange = 3.0;

	public Player(double x, double y, QuestMap currMap, String name) {
		super(x, y, currMap);
		bounds = new Rectangle2D.Double(x, y, 1, 1);
		if (img[0][0] == null) {
			initPlayer();
		}
		velocity = 0.05;
		
		health = 100.0;
		maxHealth = 100.0;
		mana = 100.0;
		maxMana = 100.0;
		fullness = 100.0;
		maxFullness = 100.0;
		
		inventory = new Inventory(this, 10);
		inventory.addItem(new Item(0, 0, 19, currMap));
		inventory.addItem(new Item(0, 0, 17, currMap));
		inventory.addItem(new Item(0, 0, 18, currMap));
		
		this.name = name;
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

	/*
	 * per-tick updater
	 */
	
	public void update() {
		if (UrfQuest.panel.isGUIOpen()) {
			return;
		}
		
		if (healthbarVisibility > 0) {
			healthbarVisibility--;
		}
		
		// update hunger and health mechanics
		if (statCounter > 0) {
			statCounter--;
		} else {
			if (fullness > maxFullness/2) {
				if (health < maxHealth) {
					health += 0.2;
				}
			}
			
			if (fullness > 0) {
				fullness -= 0.2;
			} else {
				health -= 0.2;
			}
			
			if (mana < maxMana) {
				mana += 0.1;
			}
			
			statCounter = 200;
		}
		
		// update each entry (cooldown) in the inventory
		for (Item i : inventory.getItems()) {
			if (i != null) {
				i.update();
			}
		}
		
		// process the tile the player is currently on
		processCurrentTile();
		
		// if this player isn't the player currently being controlled, stop
		if (this != UrfQuest.game.getPlayer()) {
			return;
		}
		
		// use the selected item if space is down
		if (UrfQuest.keys.contains(KeyEvent.VK_SPACE)) {
			useSelectedItem();
		}
		
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
		switch (map.getTileTypeAt((int)(bounds.getCenterX()),
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
	
	/*
	 * Getters, setters, incrementers
	 */
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setMap(QuestMap m) {
		map.removePlayer(this);
		m.addPlayer(this);
		map = m;
	}
	
	/*
	 * Inventory management
	 */
	
	public Inventory getInventory() {
		return inventory;
	}
	
	public ArrayList<Item> getInventoryItems() {
		return inventory.getItems();
	}
	
	public boolean addItem(Item i) {
		return inventory.addItem(i);
	}
	
	public Item getSelectedItem() {
		return inventory.getSelectedItem();
	}
	
	public void dropOneOfSelectedItem() {
		Item i = inventory.removeOneOfSelectedItem();
		
		if (i == null) {
			return;
		}
		
		double[] playerPos = getPos();
		i.setPos(playerPos[0], playerPos[1]);
		i.resetDropTimeout();
		map.addItem(i);
	}
	
	public void setSelectedEntry(int i) {
		inventory.setSelectedEntry(i);
	}
	
	public void useSelectedItem() {
		inventory.useSelectedItem();
	}
	
	public void tryCrafting(Collection<Item> input, Collection<Item> output) {
		inventory.tryCrafting(input, output);
	}
	
	public void setHeldItem(Item i) {
		heldItem = i;
	}
	
	public Item getHeldItem() {
		return heldItem;
	}
	
	/*
	 * Misc
	 */
	
	public void useTileUnderneath() {
		map.useActiveTile((int)getCenter()[0], (int)getCenter()[1], this);
	}
	
	public double getPickupRange() {
		return pickupRange;
	}
	
	public void setPickupRange(double d) {
		pickupRange = d;
	}
}
