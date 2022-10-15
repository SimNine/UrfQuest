package xyz.urffer.urfquest.client.entities.mobs;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import xyz.urffer.urfquest.Main;
import xyz.urffer.urfquest.client.Client;
import xyz.urffer.urfquest.client.QuestPanel;
import xyz.urffer.urfquest.client.entities.items.Item;
import xyz.urffer.urfquest.client.map.Map;
import xyz.urffer.urfquest.client.state.Inventory;
import xyz.urffer.urfquest.shared.PairDouble;

public class NPCHuman extends Mob {

	private final static String assetPath = QuestPanel.assetPath + "/entities/npc_human/";

	// img[dir][frame]
	// dir = right/left
	// frame = idle/step1/step2/step3
	// walk: 1 -> 2 -> 3 -> 2 -> 1 -> 2 etc...
	private static BufferedImage[][] img = new BufferedImage[2][4];
	
	public static void initGraphics() {
		try {
			BufferedImage idle = ImageIO.read(Main.self.getClass().getResourceAsStream(assetPath + "new_0.png"));
			BufferedImage walk1 = ImageIO.read(Main.self.getClass().getResourceAsStream(assetPath + "new_1.png"));
			BufferedImage walk2 = ImageIO.read(Main.self.getClass().getResourceAsStream(assetPath + "new_2.png"));
			
			img[0][0] = idle;
			img[0][1] = walk1;
			img[0][2] = walk2;
			img[0][3] = walk1;
			img[1][0] = flipImage(idle, true, false);
			img[1][1] = flipImage(walk1, true, false);
			img[1][2] = flipImage(walk2, true, false);
			img[1][3] = flipImage(walk1, true, false);
		} catch (IOException e) {
			e.printStackTrace();
			Main.mainLogger.error("Image could not be read at: " + "new_0.png");
		}
	}  
	
	protected String name;
	protected int statCounter = 200;
	protected Inventory inventory;
	protected Item heldItem;
	
	protected double pickupRange = 3.0;

	public NPCHuman(Client c, int id, Map currMap, PairDouble pos, String name) {
		super(c, id, currMap, pos);
		this.bounds = new Rectangle2D.Double(pos.x, pos.y, 1, 1);
		
		health = 100.0;
		maxHealth = 100.0;
		mana = 100.0;
		maxMana = 100.0;
		fullness = 100.0;
		maxFullness = 100.0;
		
		inventory = new Inventory(this, 10);
		
		this.name = name;
	}
	
	public static BufferedImage flipImage(final BufferedImage image, boolean horizontal, boolean vertical) {
        int x = 0;
        int y = 0;
        int w = image.getWidth();
        int h = image.getHeight();

        final BufferedImage out = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        final Graphics2D g2d = out.createGraphics();

        if (horizontal) {
            x = w;
            w *= -1;
        }

        if (vertical) {
            y = h;
            h *= -1;
        }

        g2d.drawImage(image, x, y, w, h, null);
        g2d.dispose();

        return out;
    }

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}
	
	/*
	 * Drawing methods
	 */
	
	protected void drawEntity(Graphics g) {
		final int WALK_CYCLE_NUM_PHASES = 4;
		final int WALK_CYCLE_STEP_LENGTH_MS = 220;
		final int WALK_CYCLE_TOTAL_LENGTH = WALK_CYCLE_NUM_PHASES * WALK_CYCLE_STEP_LENGTH_MS;
		
		int dirIndex;
		if (this.movementVector.dirRadians < (Math.PI / 2) || this.movementVector.dirRadians > (Math.PI * 3.0 / 2.0)) {
			dirIndex = 0;
		} else {
			dirIndex = 1;
		}
		
		int stepIndex;
		if (this.movementVector.magnitude == 0) {
			stepIndex = 0;
		} else {
			stepIndex = (int)(System.currentTimeMillis() % WALK_CYCLE_TOTAL_LENGTH) / WALK_CYCLE_STEP_LENGTH_MS;
		}
		
		g.drawImage(img[dirIndex][stepIndex], 
					client.getPanel().gameToWindowX(bounds.getX()), 
					client.getPanel().gameToWindowY(bounds.getY()), 
					null);
		
		g.setColor(Color.BLACK);
		g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 15));
		g.drawString(name, 
				client.getPanel().gameToWindowX(bounds.getX()) - 5*(name.length()/2), 
				client.getPanel().gameToWindowY(bounds.getY()));
		
		drawHealthBar(g);
	}
	
	public void drawDebug(Graphics g) {
		g.setColor(Color.BLACK);
		g.drawString("direction: " + this.movementVector.dirRadians, 
				client.getPanel().gameToWindowX(bounds.getX()), 
				client.getPanel().gameToWindowY(bounds.getY()));
	}

}
