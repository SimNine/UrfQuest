package entities.mobs;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import entities.items.Bone;
import entities.mobs.ai.routines.IdleRoutine;
import entities.mobs.ai.routines.AttackRoutine;
import framework.QuestPanel;
import framework.UrfQuest;

public class Cyclops extends Mob {
	private static BufferedImage pic;
	private int thinkingDelay;
	private final int intelligence;

	public Cyclops(double x, double y) {
		super(x, y);
		animStage = (int)(Math.random()*200.0);
		if (pic == null) {
			initCyclops();
		}
		bounds = new Rectangle2D.Double(x, y, 
										pic.getWidth()/(double)QuestPanel.TILE_WIDTH,
										pic.getHeight()/(double)QuestPanel.TILE_WIDTH);
		velocity = 0.01;
		health = 50.0;
		maxHealth = 50.0;
		intelligence = 50;
		
		routine = new IdleRoutine(this);
	}
	
	public static void initCyclops() {
		try {
			pic = ImageIO.read(UrfQuest.quest.getClass().getResourceAsStream(assetPath + "cyclops_unscaled.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Image could not be read at: " + assetPath + "cyclops_unscaled.png");
		}
	}

	protected void drawEntity(Graphics g) {
		g.drawImage(pic, 
					(int) UrfQuest.panel.gameToWindowX(bounds.getX()), 
					(int) UrfQuest.panel.gameToWindowY(bounds.getY()), 
					null);
		drawHealthBar(g);
	}

	public void update() {
		if (healthbarVisibility > 0) {
			healthbarVisibility--;
		}
		
		// if the chicken can think again
		thinkingDelay--;
		if (thinkingDelay <= 0) {
			think();
			thinkingDelay = intelligence;
		}
	
		routine.update();
		attemptMove(routine.suggestedDirection(), routine.suggestedVelocity());
	}
	
	private void think() {
		// if the cyclops is within 20 blocks of the player, and it isn't attacking already, attack
		if (Math.abs(getPos()[0] - UrfQuest.game.getPlayer().getPos()[0]) < 20 &&
			Math.abs(getPos()[1] - UrfQuest.game.getPlayer().getPos()[1]) < 20 &&
			this.hasClearPathTo(UrfQuest.game.getPlayer())) {
			if (!(routine instanceof AttackRoutine)) {
				routine = new AttackRoutine(this, UrfQuest.game.getPlayer());
			}
		} else {
			if (!(routine instanceof IdleRoutine)){
				routine = new IdleRoutine(this);
			}
		}
	}
	
	public void onDeath() {
		UrfQuest.game.getCurrMap().addItem(new Bone(bounds.getCenterX(), bounds.getCenterY()));
	}
}