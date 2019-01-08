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

	public Shovel(double x, double y, QuestMap m) {
		super(x, y, m);
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
	public boolean use(Mob m) {
		if (cooldown > 0) {
			return false;
		} // else
		if (m.tileTypeAtDistance(0) == 2) {
			int[] coords = m.tileCoordsAtDistance(0);
			if (Math.random() > .05) {
				m.getMap().setTileAt(coords[0], coords[1], 0);
			} else {
				m.getMap().setTileAt(coords[0], coords[1], 13);
				
				int caveSize = 400;
				
				// create new map
				QuestMap newCaveMap = new QuestMap(caveSize, caveSize, QuestMap.CAVE_MAP);
				int xHome = newCaveMap.getHomeCoords()[0];
				int yHome = newCaveMap.getHomeCoords()[1];
				newCaveMap.setTileAt(xHome, yHome, 13);
				
				//generate and add links
				MapLink currLink = new MapLink(m.getMap(), coords[0], coords[1]);
				MapLink newLink = new MapLink(newCaveMap, xHome, yHome);
				m.getMap().addLink(currLink, newLink);
				newCaveMap.addLink(newLink, currLink);
				
				//debug
				System.out.println("soruce: " + coords[0] + ", " + coords[1]);
				System.out.println("exit: " + xHome + ", " + yHome);
			}
			
			cooldown = getMaxCooldown();
			return true;
		} else {
			return false;
		}
	}

	public Shovel clone() {
		return new Shovel(this.getPos()[0], this.getPos()[1], map);
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