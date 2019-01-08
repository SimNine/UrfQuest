package entities.items;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import entities.mobs.Mob;
import framework.UrfQuest;

public class AstralRune extends Item {
	public static BufferedImage astralRunePic;

	public AstralRune(double x, double y) {
		super(x, y);
		if (astralRunePic == null) {
			try {
				astralRunePic = ImageIO.read(UrfQuest.quest.getClass().getResourceAsStream(assetPath + "astralRune_scaled_30px.png"));
			} catch (IOException e) {
				System.out.println("Image could not be read at: " + assetPath + "astralRune_scaled_30px.png");
				e.printStackTrace();
			}
		}
		itemPic = astralRunePic;
	}
	
	// manipulation methods
	public boolean use(Mob m) {
		if (UrfQuest.game.getPlayer().getMana() < 50.0) {
			return false;
		} // else
		UrfQuest.game.getPlayer().incrementMana(-50.0);
		UrfQuest.game.initiateAstral();
		return true;
	}
	
	public AstralRune clone() {
		return new AstralRune(this.getPos()[0], this.getPos()[1]);
	}
	
	// getters and setters
	public boolean isConsumable() {
		return true;
	}

	public int getMaxCooldown() {
		return 1000;
	}

	public int maxStackSize() {
		return 10;
	}

	public int getMaxDurability() {
		return -1;
	}

}