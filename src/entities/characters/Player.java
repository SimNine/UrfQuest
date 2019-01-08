package entities.characters;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.geom.Rectangle2D;
import java.io.IOException;

import javax.imageio.ImageIO;

import entities.Entity;
import framework.UrfQuest;
import game.QuestMap;
import tiles.Tiles;

public class Player extends Entity{
	private static BufferedImage NW1, NW2, NW3, NW4, NW5, NW6, NW7, NW8,
						  N1, N2, N3, N4, N5, N6, N7, N8,
						  NE1, NE2, NE3, NE4, NE5, NE6, NE7, NE8,
						  E1, E2, E3, E4, E5, E6, E7, E8,
						  SE1, SE2, SE3, SE4, SE5, SE6, SE7, SE8,
						  S1, S2, S3, S4, S5, S6, S7, S8,
						  SW1, SW2, SW3, SW4, SW5, SW6, SW7, SW8,
						  W1, W2, W3, W4, W5, W6, W7, W8,
						  temp;
	
	private final static String assetPath = "/assets/player/";
	
	private double health = 100.0;
	private double mana = 100.0;
	private double speed = 0.05;

	public Player(double x, double y) {
		super(x, y);
		type = "player";
		
		bounds = new Rectangle2D.Double(x, y, 30, 30);
	}
	
	public static void initPlayer() {
		// Load east-west
		try {
			E1 = E5 = W1 = W5 = ImageIO.read(UrfQuest.quest.getClass().getResourceAsStream(assetPath + "E1.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Image could not be read at: " + assetPath + "E1.png");
		}
		try {
			E2 = E4 = W2 = W4 = ImageIO.read(UrfQuest.quest.getClass().getResourceAsStream(assetPath + "E2.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Image could not be read at: " + assetPath + "E2.png");
		}
		try {
			E3 = W3 = ImageIO.read(UrfQuest.quest.getClass().getResourceAsStream(assetPath + "E3.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Image could not be read at: " + assetPath + "E3.png");
		}
		try {
			E6 = E8 = W6 = W8 = ImageIO.read(UrfQuest.quest.getClass().getResourceAsStream(assetPath + "E6.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Image could not be read at: " + assetPath + "E6.png");
		}
		try {
			E7 = W7 = ImageIO.read(UrfQuest.quest.getClass().getResourceAsStream(assetPath + "E7.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Image could not be read at: " + assetPath + "E7.png");
		}
		
		// Load NE-SW
		try {
			NE1 = NE5 = SW1 = SW5 = ImageIO.read(UrfQuest.quest.getClass().getResourceAsStream(assetPath + "NE1.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Image could not be read at: " + assetPath + "NE1.png");
		}
		try {
			NE2 = NE4 = SW2 = SW4 = ImageIO.read(UrfQuest.quest.getClass().getResourceAsStream(assetPath + "NE2.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Image could not be read at: " + assetPath + "NE2.png");
		}
		try {
			NE3 = SW3 = ImageIO.read(UrfQuest.quest.getClass().getResourceAsStream(assetPath + "NE3.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Image could not be read at: " + assetPath + "NE3.png");
		}
		try {
			NE6 = NE8 = SW6 = SW8 = ImageIO.read(UrfQuest.quest.getClass().getResourceAsStream(assetPath + "NE6.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Image could not be read at: " + assetPath + "NE6.png");
		}
		try {
			NE7 = SW7 = ImageIO.read(UrfQuest.quest.getClass().getResourceAsStream(assetPath + "NE7.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Image could not be read at: " + assetPath + "NE7.png");
		}
		
		// Load NW-SE
		try {
			NW1 = NW5 = SE1 = SE5 = ImageIO.read(UrfQuest.quest.getClass().getResourceAsStream(assetPath + "NW1.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Image could not be read at: " + assetPath + "NW1.png");
		}
		try {
			NW2 = NW4 = SE2 = SE4 = ImageIO.read(UrfQuest.quest.getClass().getResourceAsStream(assetPath + "NW2.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Image could not be read at: " + assetPath + "NW2.png");
		}
		try {
			NW3 = SE3 = ImageIO.read(UrfQuest.quest.getClass().getResourceAsStream(assetPath + "NW3.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Image could not be read at: " + assetPath + "NW3.png");
		}
		try {
			NW6 = NW8 = SE6 = SE8 = ImageIO.read(UrfQuest.quest.getClass().getResourceAsStream(assetPath + "NW6.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Image could not be read at: " + assetPath + "NW6.png");
		}
		try {
			NW7 = SE7 = ImageIO.read(UrfQuest.quest.getClass().getResourceAsStream(assetPath + "NW7.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Image could not be read at: " + assetPath + "NW7.png");
		}
		
		// Load north-south
		try {
			N1 = N5 = S1 = S5 = ImageIO.read(UrfQuest.quest.getClass().getResourceAsStream(assetPath + "N1.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Image could not be read at: " + assetPath + "N1.png");
		}
		try {
			N2 = N4 = S2 = S4 = ImageIO.read(UrfQuest.quest.getClass().getResourceAsStream(assetPath + "N2.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Image could not be read at: " + assetPath + "N2.png");
		}
		try {
			N3 = S3 = ImageIO.read(UrfQuest.quest.getClass().getResourceAsStream(assetPath + "N3.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Image could not be read at: " + assetPath + "N3.png");
		}
		try {
			N6 = N8 = S6 = S8 = ImageIO.read(UrfQuest.quest.getClass().getResourceAsStream(assetPath + "N6.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Image could not be read at: " + assetPath + "N6.png");
		}
		try {
			N7 = S7 = ImageIO.read(UrfQuest.quest.getClass().getResourceAsStream(assetPath + "N7.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Image could not be read at: " + assetPath + "N7.png");
		}
	}

	@Override
	protected void drawEntity(Graphics g) {
		final int STEP_SIZE = 25;
		
		switch (orientation) {
		case "N":
			switch (moveStage) {
			case 0:
				temp = N1;
				break;
			case STEP_SIZE:
				temp = N2;
				break;
			case 2*STEP_SIZE:
				temp = N3;
				break;
			case 3*STEP_SIZE:
				temp = N4;
				break;
			case 4*STEP_SIZE:
				temp = N5;
				break;
			case 5*STEP_SIZE:
				temp = N6;
				break;
			case 6*STEP_SIZE:
				temp = N7;
				break;
			case 7*STEP_SIZE:
				temp = N8;
				break;
			case 8*STEP_SIZE:
				moveStage = -1;
				break;
			}
			break;
		case "NE":
			switch (moveStage) {
			case 0:
				temp = NE1;
				break;
			case STEP_SIZE:
				temp = NE2;
				break;
			case 2*STEP_SIZE:
				temp = NE3;
				break;
			case 3*STEP_SIZE:
				temp = NE4;
				break;
			case 4*STEP_SIZE:
				temp = NE5;
				break;
			case 5*STEP_SIZE:
				temp = NE6;
				break;
			case 6*STEP_SIZE:
				temp = NE7;
				break;
			case 7*STEP_SIZE:
				temp = NE8;
				break;
			case 8*STEP_SIZE:
				moveStage = -1;
				break;
			}
			break;
		case "E":
			switch (moveStage) {
			case 0:
				temp = E1;
				break;
			case STEP_SIZE:
				temp = E2;
				break;
			case 2*STEP_SIZE:
				temp = E3;
				break;
			case 3*STEP_SIZE:
				temp = E4;
				break;
			case 4*STEP_SIZE:
				temp = E5;
				break;
			case 5*STEP_SIZE:
				temp = E6;
				break;
			case 6*STEP_SIZE:
				temp = E7;
				break;
			case 7*STEP_SIZE:
				temp = E8;
				break;
			case 8*STEP_SIZE:
				moveStage = -1;
				break;
			}
			break;
		case "SE":
			switch (moveStage) {
			case 0:
				temp = SE1;
				break;
			case STEP_SIZE:
				temp = SE2;
				break;
			case 2*STEP_SIZE:
				temp = SE3;
				break;
			case 3*STEP_SIZE:
				temp = SE4;
				break;
			case 4*STEP_SIZE:
				temp = SE5;
				break;
			case 5*STEP_SIZE:
				temp = SE6;
				break;
			case 6*STEP_SIZE:
				temp = SE7;
				break;
			case 7*STEP_SIZE:
				temp = SE8;
				break;
			case 8*STEP_SIZE:
				moveStage = -1;
				break;
			}
			break;
		case "S":
			switch (moveStage) {
			case 0:
				temp = S1;
				break;
			case STEP_SIZE:
				temp = S2;
				break;
			case 2*STEP_SIZE:
				temp = S3;
				break;
			case 3*STEP_SIZE:
				temp = S4;
				break;
			case 4*STEP_SIZE:
				temp = S5;
				break;
			case 5*STEP_SIZE:
				temp = S6;
				break;
			case 6*STEP_SIZE:
				temp = S7;
				break;
			case 7*STEP_SIZE:
				temp = S8;
				break;
			case 8*STEP_SIZE:
				moveStage = -1;
				break;
			}
			break;
		case "SW":
			switch (moveStage) {
			case 0:
				temp = SW1;
				break;
			case STEP_SIZE:
				temp = SW2;
				break;
			case 2*STEP_SIZE:
				temp = SW3;
				break;
			case 3*STEP_SIZE:
				temp = SW4;
				break;
			case 4*STEP_SIZE:
				temp = SW5;
				break;
			case 5*STEP_SIZE:
				temp = SW6;
				break;
			case 6*STEP_SIZE:
				temp = SW7;
				break;
			case 7*STEP_SIZE:
				temp = SW8;
				break;
			case 8*STEP_SIZE:
				moveStage = -1;
				break;
			}
			break;
		case "W":
			switch (moveStage) {
			case 0:
				temp = W1;
				break;
			case STEP_SIZE:
				temp = W2;
				break;
			case 2*STEP_SIZE:
				temp = W3;
				break;
			case 3*STEP_SIZE:
				temp = W4;
				break;
			case 4*STEP_SIZE:
				temp = W5;
				break;
			case 5*STEP_SIZE:
				temp = W6;
				break;
			case 6*STEP_SIZE:
				temp = W7;
				break;
			case 7*STEP_SIZE:
				temp = W8;
				break;
			case 8*STEP_SIZE:
				moveStage = -1;
				break;
			}
			break;
		case "NW":
			switch (moveStage) {
			case 0:
				temp = NW1;
				break;
			case STEP_SIZE:
				temp = NW2;
				break;
			case 2*STEP_SIZE:
				temp = NW3;
				break;
			case 3*STEP_SIZE:
				temp = NW4;
				break;
			case 4*STEP_SIZE:
				temp = NW5;
				break;
			case 5*STEP_SIZE:
				temp = NW6;
				break;
			case 6*STEP_SIZE:
				temp = NW7;
				break;
			case 7*STEP_SIZE:
				temp = NW8;
				break;
			case 8*STEP_SIZE:
				moveStage = -1;
				break;
			}
			break;
		}
		
		g.drawImage(temp, 
					UrfQuest.panel.dispCenterX - (int)(0.5*temp.getWidth()),
					UrfQuest.panel.dispCenterY - (int)(0.5*temp.getHeight()), null);
	}
	
	protected void drawDebug(Graphics g) {
		g.setColor(Color.BLACK);
		g.drawString("orientation: " + this.orientation, 
					 UrfQuest.panel.dispCenterX - (int)(0.5*temp.getWidth()), 
					 UrfQuest.panel.dispCenterY - (int)(0.5*temp.getHeight()));
		g.drawString("moveStage: " + this.moveStage, 
					 UrfQuest.panel.dispCenterX - (int)(0.5*temp.getWidth()), 
					 UrfQuest.panel.dispCenterY - (int)(0.5*temp.getHeight()) + 10);
	}

	@Override
	public void update() {
		processCurrentTile();
		
		String newOrientation = "";
		
		if (UrfQuest.keys.contains(KeyEvent.VK_UP)) {
			attemptMove(1);
			newOrientation += "N";
		}
		if (UrfQuest.keys.contains(KeyEvent.VK_DOWN)) {
			attemptMove(2);
			newOrientation += "S";
		}
		if (UrfQuest.keys.contains(KeyEvent.VK_LEFT)) {
			attemptMove(3);
			newOrientation += "W";
		}
		if (UrfQuest.keys.contains(KeyEvent.VK_RIGHT)) {
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
		
		bounds.setRect(this.Xpos, this.Ypos, 30, 30);
	}
	
	// helper
	private void processCurrentTile() {
		switch (UrfQuest.game.getCurrMap().getTileAt((int)Xpos, (int)Ypos)) {
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
			if (speed > 0.01) incrementSpeed(-.001);
			break;
		case 6:
			if (speed < 1) incrementSpeed(.001);
			break;
		case 7:
			//impossible
		default:
			//nothing
			break;
		}
	}
	
	// helper
	private void attemptMove(int dir) { // 1 = up, 2 = down, 3 = left, 4 = right
		QuestMap currMap = UrfQuest.game.getCurrMap();
		switch (dir) {
		case 1:
			if (Tiles.isWalkable(currMap.getTileAt((int)Xpos, (int)(Ypos - speed)))) Ypos -= speed;
			else Ypos = (int)Math.floor(Ypos) + 0.0000001;
			break;
		case 2:
			if (Tiles.isWalkable(currMap.getTileAt((int)Xpos, (int)(Ypos + speed)))) Ypos += speed;
			else Ypos = (int)Math.ceil(Ypos) - 0.0000001;
			break;
		case 3:
			if (Tiles.isWalkable(currMap.getTileAt((int)(Xpos - speed), (int)Ypos))) Xpos -= speed;
			else Xpos = (int)Math.floor(Xpos) + 0.0000001;
			break;
		case 4:
			if (Tiles.isWalkable(currMap.getTileAt((int)(Xpos + speed), (int)Ypos))) Xpos += speed;
			else Xpos = (int)Math.ceil(Xpos) - 0.0000001;
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
