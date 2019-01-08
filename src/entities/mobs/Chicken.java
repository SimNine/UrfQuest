package entities.mobs;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

import entities.items.ChickenLeg;
import entities.mobs.ai.routines.FleeRoutine;
import entities.mobs.ai.routines.IdleRoutine;
import framework.QuestPanel;
import framework.UrfQuest;

public class Chicken extends Mob {
	private static BufferedImage pic;
	private int thinkingDelay;
	private final int intelligence;
	
	public Chicken(double x, double y) {
		super(x, y);
		bounds = new Rectangle2D.Double(x, y, 1, 1);
		animStage = (int)(Math.random()*200.0);
		velocity = 0.02;
		health = 10.0;
		maxHealth = 10.0;
		
		if (pic == null) {
			initChicken();
		}
		
		routine = new IdleRoutine(this);
		intelligence = 50;
		thinkingDelay = intelligence;
	}
	
	public static void initChicken() {
		try {
			pic = ImageIO.read(UrfQuest.quest.getClass().getResourceAsStream(assetPath + "chicken_scaled_30px.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Image could not be read at: " + assetPath + "chicken_scaled_30px.png");
		}
	}

	protected void drawEntity(Graphics g) {
		int tileWidth = QuestPanel.TILE_WIDTH;
		g.drawImage(pic, 
					(int)(UrfQuest.panel.dispCenterX - (UrfQuest.game.getPlayer().getPos()[0] - bounds.getX())*tileWidth), 
					(int)(UrfQuest.panel.dispCenterY - (UrfQuest.game.getPlayer().getPos()[1] - bounds.getY())*tileWidth), 
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
		
		// execute the current action
		routine.update();
		attemptMove(routine.suggestedDirection(), routine.suggestedVelocity());
	}
	
	private void think() {
		// if the chicken is within 10 blocks of the player, and it isn't fleeing already, flee
		if (Math.abs(getPos()[0] - UrfQuest.game.getPlayer().getPos()[0]) < 5 &&
			Math.abs(getPos()[1] - UrfQuest.game.getPlayer().getPos()[1]) < 5) {
			if (!(routine instanceof FleeRoutine)) {
				routine = new FleeRoutine(this, UrfQuest.game.getPlayer());
			}
		} else {
			if (!(routine instanceof IdleRoutine)){
				routine = new IdleRoutine(this);
			}
		}
	}
	
	public void onDeath() {
		if (Math.random() > 0.5) {
			UrfQuest.game.getCurrMap().addItem(new ChickenLeg(bounds.getCenterX(), bounds.getCenterY()));
		}
	}
}