package xyz.urffer.urfquest.client.entities.mobs;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import xyz.urffer.urfquest.client.Client;
import xyz.urffer.urfquest.client.QuestPanel;
import xyz.urffer.urfquest.client.entities.items.ItemStack;
import xyz.urffer.urfquest.client.state.Inventory;
import xyz.urffer.urfquest.shared.Constants;
import xyz.urffer.urfquest.shared.ImageUtils;

public class NPCHuman extends Mob {

	private final static String assetPath = QuestPanel.assetPath + "/entities/npc_human/";

	// img[dir][frame]
	// dir = right/left
	// frame = idle/step1/step2/step3
	// walk: 1 -> 2 -> 3 -> 2 -> 1 -> 2 etc...
	private static BufferedImage[][] img = new BufferedImage[2][4];
	
	static {
		BufferedImage idle = ImageUtils.loadImage(assetPath + "new_0.png");
		BufferedImage walk1 = ImageUtils.loadImage(assetPath + "new_1.png");
		BufferedImage walk2 = ImageUtils.loadImage(assetPath + "new_2.png");
		
		img[0][0] = idle;
		img[0][1] = walk1;
		img[0][2] = walk2;
		img[0][3] = walk1;
		img[1][0] = ImageUtils.flipImage(idle, true, false);
		img[1][1] = ImageUtils.flipImage(walk1, true, false);
		img[1][2] = ImageUtils.flipImage(walk2, true, false);
		img[1][3] = ImageUtils.flipImage(walk1, true, false);
	}
	
	protected String name;
	protected int statCounter = 200;
	protected Inventory inventory;
	protected ItemStack heldItem;
	
	protected double pickupRange = 3.0;

	public NPCHuman(Client c, int id, String name) {
		super(c, id);
		this.bounds = new Rectangle2D.Double(0, 0, 1, 1);
		
		health = 1000;
		maxHealth = 1000;
		mana = 1000;
		maxMana = 1000;
		fullness = 1000;
		maxFullness = 1000;
		
		inventory = new Inventory(this, Constants.DEFAULT_PLAYER_INVENTORY_SIZE);
		
		this.name = name;
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
