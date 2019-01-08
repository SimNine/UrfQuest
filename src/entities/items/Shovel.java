package entities.items;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import entities.mobs.Mob;
import framework.UrfQuest;
import game.MapLink;
import game.QuestMap;

public class Shovel extends Item {
	public static BufferedImage shovelPic;

	public Shovel(double x, double y) {
		super(x, y);
		if (shovelPic == null) {
			try {
				shovelPic = ImageIO.read(UrfQuest.quest.getClass().getResourceAsStream(assetPath + "shovel_scaled_30px.png"));
			} catch (IOException e) {
				System.out.println("Image could not be read at: " + assetPath + "shovel_scaled_30px.png");
				e.printStackTrace();
			}
		}
		itemPic = shovelPic;
	}
	
	// manipulation methods
	public void use(Mob m) {
		if (m.tileTypeAtDistance(0) == 2) {
			int[] coords = m.tileCoordsAtDistance(0);
			if (Math.random() > .05) {
				UrfQuest.game.getCurrMap().setTileAt(coords[0], coords[1], 0);
			} else {
				UrfQuest.game.getCurrMap().setTileAt(coords[0], coords[1], 13);
				
				int caveSize = 50;
				
				// create new map
				QuestMap newCaveMap = new QuestMap(caveSize, caveSize, QuestMap.CAVE_MAP);
				newCaveMap.setTileAt(caveSize/2, caveSize/2, 13);
				
				//generate and add links
				MapLink currLink = new MapLink(UrfQuest.game.getCurrMap(), coords[0], coords[1]);
				MapLink newLink = new MapLink(newCaveMap, caveSize/2, caveSize/2);
				UrfQuest.game.getCurrMap().addLink(currLink, newLink);
				newCaveMap.addLink(newLink, currLink);
				
				//debug
				System.out.println("soruce: " + coords[0] + ", " + coords[1]);
				System.out.println("exit: " + caveSize/2 + ", " + caveSize/2);
			}
		}
	}

	public Shovel clone() {
		return new Shovel(this.getPos()[0], this.getPos()[1]);
	}
	
	// getters and setters
	public boolean isConsumable() {
		return false;
	}

	public int getMaxCooldown() {
		return 100;
	}

	public int maxStackSize() {
		return 1;
	}

	public int getMaxDurability() {
		return 100;
	}
}