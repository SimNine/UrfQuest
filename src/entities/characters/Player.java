package entities.characters;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import entities.Entity;
import framework.V;

public class Player extends Entity{
	private BufferedImage NW1, NW2, NW3, NW4, NW5, NW6, NW7, NW8,
						  N1, N2, N3, N4, N5, N6, N7, N8,
						  NE1, NE2, NE3, NE4, NE5, NE6, NE7, NE8,
						  E1, E2, E3, E4, E5, E6, E7, E8,
						  SE1, SE2, SE3, SE4, SE5, SE6, SE7, SE8,
						  S1, S2, S3, S4, S5, S6, S7, S8,
						  SW1, SW2, SW3, SW4, SW5, SW6, SW7, SW8,
						  W1, W2, W3, W4, W5, W6, W7, W8,
						  temp;
	
	private double health = 100.0;
	private double mana = 100.0;
	private double speed = 0.05;

	public Player(double x, double y) {
		super(x, y);
		type = "player";
		
		// Load east-west
		try {
			E1 = E5 = W1 = W5 = ImageIO.read(new File("src/entities/characters/player_sprites/E1.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Image could not be read at: " + "src/entities/characters/player_sprites/E1.png");
		}
		try {
			E2 = E4 = W2 = W4 = ImageIO.read(new File("src/entities/characters/player_sprites/E2.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Image could not be read at: " + "src/entities/characters/player_sprites/E2.png");
		}
		try {
			E3 = W3 = ImageIO.read(new File("src/entities/characters/player_sprites/E3.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Image could not be read at: " + "src/entities/characters/player_sprites/E3.png");
		}
		try {
			E6 = E8 = W6 = W8 = ImageIO.read(new File("src/entities/characters/player_sprites/E6.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Image could not be read at: " + "src/entities/characters/player_sprites/E6.png");
		}
		try {
			E7 = W7 = ImageIO.read(new File("src/entities/characters/player_sprites/E7.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Image could not be read at: " + "src/entities/characters/player_sprites/E7.png");
		}
		
		// Load NE-SW
		try {
			NE1 = NE5 = SW1 = SW5 = ImageIO.read(new File("src/entities/characters/player_sprites/NE1.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Image could not be read at: " + "src/entities/characters/player_sprites/NE1.png");
		}
		try {
			NE2 = NE4 = SW2 = SW4 = ImageIO.read(new File("src/entities/characters/player_sprites/NE2.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Image could not be read at: " + "src/entities/characters/player_sprites/NE2.png");
		}
		try {
			NE3 = SW3 = ImageIO.read(new File("src/entities/characters/player_sprites/NE3.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Image could not be read at: " + "src/entities/characters/player_sprites/NE3.png");
		}
		try {
			NE6 = NE8 = SW6 = SW8 = ImageIO.read(new File("src/entities/characters/player_sprites/NE6.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Image could not be read at: " + "src/entities/characters/player_sprites/NE6.png");
		}
		try {
			NE7 = SW7 = ImageIO.read(new File("src/entities/characters/player_sprites/NE7.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Image could not be read at: " + "src/entities/characters/player_sprites/NE7.png");
		}
		
		// Load NW-SE
		try {
			NW1 = NW5 = SE1 = SE5 = ImageIO.read(new File("src/entities/characters/player_sprites/NW1.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Image could not be read at: " + "src/entities/characters/player_sprites/NW1.png");
		}
		try {
			NW2 = NW4 = SE2 = SE4 = ImageIO.read(new File("src/entities/characters/player_sprites/NW2.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Image could not be read at: " + "src/entities/characters/player_sprites/NW2.png");
		}
		try {
			NW3 = SE3 = ImageIO.read(new File("src/entities/characters/player_sprites/NW3.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Image could not be read at: " + "src/entities/characters/player_sprites/NW3.png");
		}
		try {
			NW6 = NW8 = SE6 = SE8 = ImageIO.read(new File("src/entities/characters/player_sprites/NW6.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Image could not be read at: " + "src/entities/characters/player_sprites/NW6.png");
		}
		try {
			NW7 = SE7 = ImageIO.read(new File("src/entities/characters/player_sprites/NW7.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Image could not be read at: " + "src/entities/characters/player_sprites/NW7.png");
		}
		
		// Load north-south
		try {
			N1 = N5 = S1 = S5 = ImageIO.read(new File("src/entities/characters/player_sprites/N1.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Image could not be read at: " + "src/entities/characters/player_sprites/N1.png");
		}
		try {
			N2 = N4 = S2 = S4 = ImageIO.read(new File("src/entities/characters/player_sprites/N2.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Image could not be read at: " + "src/entities/characters/player_sprites/N2.png");
		}
		try {
			N3 = S3 = ImageIO.read(new File("src/entities/characters/player_sprites/N3.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Image could not be read at: " + "src/entities/characters/player_sprites/N3.png");
		}
		try {
			N6 = N8 = S6 = S8 = ImageIO.read(new File("src/entities/characters/player_sprites/N6.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Image could not be read at: " + "src/entities/characters/player_sprites/N6.png");
		}
		try {
			N7 = S7 = ImageIO.read(new File("src/entities/characters/player_sprites/N7.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Image could not be read at: " + "src/entities/characters/player_sprites/N7.png");
		}
	}

	@Override
	protected void drawEntity(Graphics g) {
		final int stepSize = 25;
		
		switch (orientation) {
		case "N":
			switch (moveStage) {
			case 0:
				temp = N1;
				break;
			case stepSize:
				temp = N2;
				break;
			case 2*stepSize:
				temp = N3;
				break;
			case 3*stepSize:
				temp = N4;
				break;
			case 4*stepSize:
				temp = N5;
				break;
			case 5*stepSize:
				temp = N6;
				break;
			case 6*stepSize:
				temp = N7;
				break;
			case 7*stepSize:
				temp = N8;
				break;
			case 8*stepSize:
				moveStage = -1;
				break;
			}
			break;
		case "NE":
			switch (moveStage) {
			case 0:
				temp = NE1;
				break;
			case stepSize:
				temp = NE2;
				break;
			case 2*stepSize:
				temp = NE3;
				break;
			case 3*stepSize:
				temp = NE4;
				break;
			case 4*stepSize:
				temp = NE5;
				break;
			case 5*stepSize:
				temp = NE6;
				break;
			case 6*stepSize:
				temp = NE7;
				break;
			case 7*stepSize:
				temp = NE8;
				break;
			case 8*stepSize:
				moveStage = -1;
				break;
			}
			break;
		case "E":
			switch (moveStage) {
			case 0:
				temp = E1;
				break;
			case stepSize:
				temp = E2;
				break;
			case 2*stepSize:
				temp = E3;
				break;
			case 3*stepSize:
				temp = E4;
				break;
			case 4*stepSize:
				temp = E5;
				break;
			case 5*stepSize:
				temp = E6;
				break;
			case 6*stepSize:
				temp = E7;
				break;
			case 7*stepSize:
				temp = E8;
				break;
			case 8*stepSize:
				moveStage = -1;
				break;
			}
			break;
		case "SE":
			switch (moveStage) {
			case 0:
				temp = SE1;
				break;
			case stepSize:
				temp = SE2;
				break;
			case 2*stepSize:
				temp = SE3;
				break;
			case 3*stepSize:
				temp = SE4;
				break;
			case 4*stepSize:
				temp = SE5;
				break;
			case 5*stepSize:
				temp = SE6;
				break;
			case 6*stepSize:
				temp = SE7;
				break;
			case 7*stepSize:
				temp = SE8;
				break;
			case 8*stepSize:
				moveStage = -1;
				break;
			}
			break;
		case "S":
			switch (moveStage) {
			case 0:
				temp = S1;
				break;
			case stepSize:
				temp = S2;
				break;
			case 2*stepSize:
				temp = S3;
				break;
			case 3*stepSize:
				temp = S4;
				break;
			case 4*stepSize:
				temp = S5;
				break;
			case 5*stepSize:
				temp = S6;
				break;
			case 6*stepSize:
				temp = S7;
				break;
			case 7*stepSize:
				temp = S8;
				break;
			case 8*stepSize:
				moveStage = -1;
				break;
			}
			break;
		case "SW":
			switch (moveStage) {
			case 0:
				temp = SW1;
				break;
			case stepSize:
				temp = SW2;
				break;
			case 2*stepSize:
				temp = SW3;
				break;
			case 3*stepSize:
				temp = SW4;
				break;
			case 4*stepSize:
				temp = SW5;
				break;
			case 5*stepSize:
				temp = SW6;
				break;
			case 6*stepSize:
				temp = SW7;
				break;
			case 7*stepSize:
				temp = SW8;
				break;
			case 8*stepSize:
				moveStage = -1;
				break;
			}
			break;
		case "W":
			switch (moveStage) {
			case 0:
				temp = W1;
				break;
			case stepSize:
				temp = W2;
				break;
			case 2*stepSize:
				temp = W3;
				break;
			case 3*stepSize:
				temp = W4;
				break;
			case 4*stepSize:
				temp = W5;
				break;
			case 5*stepSize:
				temp = W6;
				break;
			case 6*stepSize:
				temp = W7;
				break;
			case 7*stepSize:
				temp = W8;
				break;
			case 8*stepSize:
				moveStage = -1;
				break;
			}
			break;
		case "NW":
			switch (moveStage) {
			case 0:
				temp = NW1;
				break;
			case stepSize:
				temp = NW2;
				break;
			case 2*stepSize:
				temp = NW3;
				break;
			case 3*stepSize:
				temp = NW4;
				break;
			case 4*stepSize:
				temp = NW5;
				break;
			case 5*stepSize:
				temp = NW6;
				break;
			case 6*stepSize:
				temp = NW7;
				break;
			case 7*stepSize:
				temp = NW8;
				break;
			case 8*stepSize:
				moveStage = -1;
				break;
			}
			break;
		}
		
		g.drawImage(temp, V.dispCenterX - (int)(0.5*temp.getWidth()), V.dispCenterY - (int)(0.5*temp.getHeight()), null);
	}
	
	protected void drawDebug(Graphics g) {
		g.setColor(Color.BLACK);
		g.drawString("orientation: " + this.orientation, 
					 V.dispCenterX - (int)(0.5*temp.getWidth()), 
					 V.dispCenterY - (int)(0.5*temp.getHeight()));
		g.drawString("moveStage: " + this.moveStage, 
					 V.dispCenterX - (int)(0.5*temp.getWidth()), 
					 V.dispCenterY - (int)(0.5*temp.getHeight()) + 10);
	}

	@Override
	public void update() {
		processCurrentTile();
		
		String newOrientation = "";
		
		if (V.keys.contains(KeyEvent.VK_UP)) {
			attemptMove(1);
			newOrientation += "N";
		}
		if (V.keys.contains(KeyEvent.VK_DOWN)) {
			attemptMove(2);
			newOrientation += "S";
		}
		if (V.keys.contains(KeyEvent.VK_LEFT)) {
			attemptMove(3);
			newOrientation += "W";
		}
		if (V.keys.contains(KeyEvent.VK_RIGHT)) {
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
	
	// helper
	private void processCurrentTile() {
		switch (V.qMap.getTileAt((int)Xpos, (int)Ypos)) {
		case 0:
			//zilch
			break;
		case 1:
			//impossible
			break;
		case 2:
			//V.qMap.setTileAt((int)Xpos, (int)Ypos, 0);
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
		default:
			//zilch
			break;
		}
	}
	
	// helper
	private void attemptMove(int dir) { // 1 = up, 2 = down, 3 = left, 4 = right
		switch (dir) {
		case 1:
			switch (V.qMap.getTileAt((int)Xpos, (int)(Ypos - speed))) {
			case -1:
				Ypos = (int)(Ypos) + 0.0000001;
				break;
			case 0:
				Ypos -= speed;
				break;
			case 1:
				Ypos = (int)Math.floor(Ypos) + 0.0000001;
				break;
			case 2:
				Ypos -= speed;
				break;
			default:
				Ypos -= speed;
				break;
			}
			break;
		case 2:
			switch (V.qMap.getTileAt((int)Xpos, (int)(Ypos + speed))) {
			case -1:
				Ypos = (int)Math.ceil(Ypos) - 0.0000001;
				break;
			case 0:
				Ypos += speed;
				break;
			case 1:
				Ypos = (int)Math.ceil(Ypos) - 0.0000001;
				break;
			case 2:
				Ypos += speed;
				break;
			default:
				Ypos += speed;
				break;
			}
			break;
		case 3:
			switch (V.qMap.getTileAt((int)(Xpos - speed), (int)Ypos)) {
			case -1:
				Xpos = (int)Math.floor(Xpos) + 0.0000001;
				break;
			case 0:
				Xpos -= speed;
				break;
			case 1:
				Xpos = (int)Math.floor(Xpos) + 0.0000001;
				break;
			case 2:
				Xpos -= speed;
				break;
			default:
				Xpos -= speed;
				break;
			}
			break;
		case 4:
			switch (V.qMap.getTileAt((int)(Xpos + speed), (int)Ypos)) {
			case -1:
				Xpos = (int)Math.ceil(Xpos) - 0.0000001;
				break;
			case 0:
				Xpos += speed;
				break;
			case 1:
				Xpos = (int)Math.ceil(Xpos) - 0.0000001;
				break;
			case 2:
				Xpos += speed;
				break;
			default:
				Xpos += speed;
				break;
			}
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
