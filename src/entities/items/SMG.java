package entities.items;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import framework.UrfQuest;

public class SMG extends Item {
	public static BufferedImage smgPic;

	public SMG(double x, double y) {
		super(x, y);
		type = "smg";
		isStackable = false;
		if (smgPic == null) {
			try {
				smgPic = ImageIO.read(UrfQuest.quest.getClass().getResourceAsStream(assetPath + "smg_scaled_30px.png"));
			} catch (IOException e) {
				System.out.println("Image could not be read at: " + assetPath + "smg_scaled_30px.png");
				e.printStackTrace();
			}
		}
		itemPic = smgPic;
	}

	@Override
	public void update() {
		// nothing here
	}
}