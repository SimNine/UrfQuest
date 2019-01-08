package entities.characters;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.util.Collection;

import javax.imageio.ImageIO;

import entities.Entity;
import entities.items.Item;
import framework.UrfQuest;
import game.Inventory;
import game.InventoryEntry;
import game.QuestMap;
import tiles.Tiles;

public class Player extends Entity {

	private static BufferedImage[] NW = new BufferedImage[8];
	private static BufferedImage[] N = new BufferedImage[8];
	private static BufferedImage[] NE = new BufferedImage[8];
	private static BufferedImage[] E = new BufferedImage[8];
	private static BufferedImage[] SE = new BufferedImage[8];
	private static BufferedImage[] S = new BufferedImage[8];
	private static BufferedImage[] SW = new BufferedImage[8];
	private static BufferedImage[] W = new BufferedImage[8];
	private static BufferedImage temp;
	
	private final static String assetPath = "/assets/player/";
	
	private double health = 100.0;
	private double mana = 100.0;
	private double speed = 0.05;
	
	private Inventory inventory = new Inventory();

	public Player(double x, double y) {
		super(x, y);
		type = "player";
		bounds = new Rectangle2D.Double(x, y, 1, 1);
	}
	
	public static void initPlayer() {
		// Load east-west
		try {
			E[0] = E[4] = W[0] = W[4] = ImageIO.read(UrfQuest.quest.getClass().getResourceAsStream(assetPath + "E1.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Image could not be read at: " + assetPath + "E1.png");
		}
		try {
			E[1] = E[3] = W[1] = W[3] = ImageIO.read(UrfQuest.quest.getClass().getResourceAsStream(assetPath + "E2.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Image could not be read at: " + assetPath + "E2.png");
		}
		try {
			E[2] = W[2] = ImageIO.read(UrfQuest.quest.getClass().getResourceAsStream(assetPath + "E3.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Image could not be read at: " + assetPath + "E3.png");
		}
		try {
			E[5] = E[7] = W[5] = W[7] = ImageIO.read(UrfQuest.quest.getClass().getResourceAsStream(assetPath + "E6.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Image could not be read at: " + assetPath + "E6.png");
		}
		try {
			E[6] = W[6] = ImageIO.read(UrfQuest.quest.getClass().getResourceAsStream(assetPath + "E7.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Image could not be read at: " + assetPath + "E7.png");
		}
		
		// Load NE-SW
		try {
			NE[0] = NE[4] = SW[0] = SW[4] = ImageIO.read(UrfQuest.quest.getClass().getResourceAsStream(assetPath + "NE1.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Image could not be read at: " + assetPath + "NE1.png");
		}
		try {
			NE[1] = NE[3] = SW[1] = SW[3] = ImageIO.read(UrfQuest.quest.getClass().getResourceAsStream(assetPath + "NE2.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Image could not be read at: " + assetPath + "NE2.png");
		}
		try {
			NE[2] = SW[2] = ImageIO.read(UrfQuest.quest.getClass().getResourceAsStream(assetPath + "NE3.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Image could not be read at: " + assetPath + "NE3.png");
		}
		try {
			NE[5] = NE[7] = SW[5] = SW[7] = ImageIO.read(UrfQuest.quest.getClass().getResourceAsStream(assetPath + "NE6.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Image could not be read at: " + assetPath + "NE6.png");
		}
		try {
			NE[6] = SW[6] = ImageIO.read(UrfQuest.quest.getClass().getResourceAsStream(assetPath + "NE7.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Image could not be read at: " + assetPath + "NE7.png");
		}
		
		// Load NW-SE
		try {
			NW[0] = NW[4] = SE[0] = SE[4] = ImageIO.read(UrfQuest.quest.getClass().getResourceAsStream(assetPath + "NW1.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Image could not be read at: " + assetPath + "NW1.png");
		}
		try {
			NW[1] = NW[3] = SE[1] = SE[3] = ImageIO.read(UrfQuest.quest.getClass().getResourceAsStream(assetPath + "NW2.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Image could not be read at: " + assetPath + "NW2.png");
		}
		try {
			NW[2] = SE[2] = ImageIO.read(UrfQuest.quest.getClass().getResourceAsStream(assetPath + "NW3.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Image could not be read at: " + assetPath + "NW3.png");
		}
		try {
			NW[5] = NW[7] = SE[5] = SE[7] = ImageIO.read(UrfQuest.quest.getClass().getResourceAsStream(assetPath + "NW6.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Image could not be read at: " + assetPath + "NW6.png");
		}
		try {
			NW[6] = SE[6] = ImageIO.read(UrfQuest.quest.getClass().getResourceAsStream(assetPath + "NW7.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Image could not be read at: " + assetPath + "NW7.png");
		}
		
		// Load north-south
		try {
			N[0] = N[4] = S[0] = S[4] = ImageIO.read(UrfQuest.quest.getClass().getResourceAsStream(assetPath + "N1.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Image could not be read at: " + assetPath + "N1.png");
		}
		try {
			N[1] = N[3] = S[1] = S[3] = ImageIO.read(UrfQuest.quest.getClass().getResourceAsStream(assetPath + "N2.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Image could not be read at: " + assetPath + "N2.png");
		}
		try {
			N[2] = S[2] = ImageIO.read(UrfQuest.quest.getClass().getResourceAsStream(assetPath + "N3.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Image could not be read at: " + assetPath + "N3.png");
		}
		try {
			N[5] = N[7] = S[5] = S[7] = ImageIO.read(UrfQuest.quest.getClass().getResourceAsStream(assetPath + "N6.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Image could not be read at: " + assetPath + "N6.png");
		}
		try {
			N[6] = S[6] = ImageIO.read(UrfQuest.quest.getClass().getResourceAsStream(assetPath + "N7.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Image could not be read at: " + assetPath + "N7.png");
		}
	}

	@Override
	protected void drawEntity(Graphics g) {
		final int STEP_SIZE = 15;
		
		if (moveStage/STEP_SIZE == 8) {
			moveStage = -1;
		}
	
		switch (orientation) {
		case "N":
			temp = N[moveStage/STEP_SIZE];
			break;
		case "NE":
			temp = NE[moveStage/STEP_SIZE];
			break;
		case "E":
			temp = E[moveStage/STEP_SIZE];
			break;
		case "SE":
			temp = SE[moveStage/STEP_SIZE];
			break;
		case "S":
			temp = S[moveStage/STEP_SIZE];
			break;
		case "SW":
			temp = SW[moveStage/STEP_SIZE];
			break;
		case "W":
			temp = W[moveStage/STEP_SIZE];
			break;
		case "NW":
			temp = NW[moveStage/STEP_SIZE];
			break;
		}
		
		g.drawImage(temp, 
					UrfQuest.panel.dispCenterX,
					UrfQuest.panel.dispCenterY,
					null);
	}
	
	protected void drawDebug(Graphics g) {
		g.setColor(Color.BLACK);
		g.drawString("orientation: " + this.orientation, 
					 UrfQuest.panel.dispCenterX, 
					 UrfQuest.panel.dispCenterY);
		g.drawString("moveStage: " + this.moveStage, 
					 UrfQuest.panel.dispCenterX, 
					 UrfQuest.panel.dispCenterY + 10);
	}

	@Override
	public void update() {
		processCurrentTile();
		
		String newOrientation = "";
		
		if (UrfQuest.keys.contains(KeyEvent.VK_W)) {
			attemptMove(1);
			newOrientation += "N";
		}
		if (UrfQuest.keys.contains(KeyEvent.VK_S)) {
			attemptMove(2);
			newOrientation += "S";
		}
		if (UrfQuest.keys.contains(KeyEvent.VK_A)) {
			attemptMove(3);
			newOrientation += "W";
		}
		if (UrfQuest.keys.contains(KeyEvent.VK_D)) {
			attemptMove(4);
			newOrientation += "E";
		}
		if (newOrientation.isEmpty()) {
			newOrientation = "NONE";
		}
		
		if (newOrientation.equals(orientation)) {
			this.moveStage++;
		} else { // if (newOrientation != orientation)
			this.moveStage = 0;
			if (newOrientation != "NONE") {
				orientation = newOrientation;
			}
		}
	}
	
	// helpers
	private void processCurrentTile() {
		switch (UrfQuest.game.getCurrMap().getTileAt((int)(bounds.getX()+0.5),
													 (int)(bounds.getY()+0.5))) {
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
			if (speed > 0.01) incrementSpeed(-0.001);
			break;
		case 6:
			if (speed < 1) incrementSpeed(0.001);
			break;
		case 7:
			//impossible
		case 8:
			//impossible
		default:
			//nothing
			break;
		}
	}
	
	private void attemptMove(int dir) { // 1 = up, 2 = down, 3 = left, 4 = right
		QuestMap currMap = UrfQuest.game.getCurrMap();
		double newX = bounds.getX()+0.5;
		double newY = bounds.getY()+0.5;
		
		switch (dir) {
		case 1:
			if (Tiles.isWalkable(currMap.getTileAt((int)newX, (int)(newY - speed)))) newY -= speed;
			else newY = (int)Math.floor(newY) + 0.0000001;
			break;
		case 2:
			if (Tiles.isWalkable(currMap.getTileAt((int)newX, (int)(newY + speed)))) newY += speed;
			else newY = (int)Math.ceil(newY) - 0.0000001;
			break;
		case 3:
			if (Tiles.isWalkable(currMap.getTileAt((int)(newX - speed), (int)newY))) newX -= speed;
			else newX = (int)Math.floor(newX) + 0.0000001;
			break;
		case 4:
			if (Tiles.isWalkable(currMap.getTileAt((int)(newX + speed), (int)newY))) newX += speed;
			else newX = (int)Math.ceil(newX) - 0.0000001;
			break;
		default:
			break;
		}
		
		bounds.setRect(newX-0.5, newY-0.5, bounds.getWidth(), bounds.getHeight());
	}
	
	// incrementers
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
	
	public Collection<InventoryEntry> getInventory() {
		return inventory.getInventoryEntries();
	}
	
	public boolean addItem(Item i) {
		return inventory.addItem(i);
	}
	
	public Item dropOneOfSelectedItem() {
		return inventory.removeOneOfSelectedItem();
	}
	
	public void setSelectedEntry(int i) {
		inventory.setSelectedEntry(i);
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
