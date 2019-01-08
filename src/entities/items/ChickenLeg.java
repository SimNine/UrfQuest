package entities.items;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import entities.mobs.Mob;
import entities.mobs.Player;
import framework.UrfQuest;

public class ChickenLeg extends Item {
	public static BufferedImage chickenLegPic;

	public ChickenLeg(double x, double y) {
		super(x, y);
		if (chickenLegPic == null) {
			try {
				chickenLegPic = ImageIO.read(UrfQuest.quest.getClass().getResourceAsStream(assetPath + "chickenLeg_scaled_30px.png"));
			} catch (IOException e) {
				System.out.println("Image could not be read at: " + assetPath + "chickenLeg_scaled_30px.png");
				e.printStackTrace();
			}
		}
		itemPic = chickenLegPic;
	}
	
	public void update() {
		// nothing
	}
	
	public void use(Mob m) {
		if (m instanceof Player) {
			((Player) m).incrementHunger(5.0);
		}
	}

	public boolean isConsumable() {
		return true;
	}

	public int getCooldown() {
		return 50;
	}

	public boolean isStackable() {
		return true;
	}
}