package framework;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import entities.characters.Player;
import entities.items.Item;
import entities.items.Key;
import game.QuestMap;

public class Loader {
	
	private static File saveDir = new File("savedgames");

	public static void saveGame() {
		UrfQuest.time.stop();
		
        if (!saveDir.exists()) saveDir.mkdir();
        
        String filename = JOptionPane.showInputDialog(UrfQuest.panel, "Save Level", null) + ".urf";
        if (filename.equals("null.urf")) {
        	UrfQuest.time.start();
        	return;
        }
        
        File save = new File(saveDir, filename);
        
        if (save.exists()) {
            System.out.println("Deleting previous save...");
            save.delete();
            System.out.println("Previous save deleted.");
        }
        
        // begin gamesaving process
        try {
            System.out.println("Saving current game.");
            save.createNewFile();
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(save));
            
            // write number of maps
            oos.writeInt(UrfQuest.game.getAllMaps().size());
            ArrayList<QuestMap> maps = UrfQuest.game.getAllMaps();
            
            // write current map number
            oos.writeInt(UrfQuest.game.getAllMaps().indexOf(UrfQuest.game.getCurrMap()));
            
            // for each map
            for (int i = 0; i < UrfQuest.game.getAllMaps().size(); i++) {
            	// get the ith map
            	QuestMap map = maps.get(i);
            	
            	// write ith map's dimensions
            	oos.writeInt(map.getWidth());
            	oos.writeInt(map.getHeight());
            	
            	// write each column of tiles
            	for (int x = 0; x < map.getWidth(); x++) {
            		for (int y = 0; y < map.getHeight(); y++) {
            			oos.writeInt(map.getTileAt(x, y));
            		}
            	}
            	
            	// write number of items on the map
            	oos.writeInt(map.items.size());
            	
            	// for each item (in this case, only keys exist), write coords
            	for (i = 0; i < map.items.size(); i++) {
            		Item curr = map.items.get(i);
            		oos.writeDouble(curr.getPosition()[0]);
            		oos.writeDouble(curr.getPosition()[1]);
            	}
            }
            
            // write the current keyCount
            oos.writeInt(UrfQuest.game.getKeyCount());
            
            // get the player
            Player p = UrfQuest.game.getPlayer();
            
            // write the current location of the player
            oos.writeDouble(p.getPosition()[0]);
            oos.writeDouble(p.getPosition()[1]);
            
            // write the current stats of the player
            oos.writeDouble(p.getHealth());
            oos.writeDouble(p.getMana());
            oos.writeDouble(p.getSpeed());
            
            oos.flush();
            oos.close();
            System.out.println("Current game saved.");
        } catch (Exception e) {
            System.out.println("Gamesave failed.");
            e.printStackTrace();
            System.exit(1);
        }
        
        UrfQuest.time.start();
    }
    
    public static void loadGame() {
    	UrfQuest.time.stop();
        String filename = JOptionPane.showInputDialog(null, "Load a level...", null) + ".urf";
        if (filename.equals("null.urf")) {
        	UrfQuest.time.start();
        	return;
        }
        
        // begin gameloading process
        try {
            System.out.print("Loading " + filename + "... ");
            File file = new File(saveDir, filename);
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
            
            // read number of maps
            int numMaps = ois.readInt();
            
            // read number of current map
            int currMap = ois.readInt();
            
            ArrayList<QuestMap> allMaps = new ArrayList<QuestMap>();
            
            // for each map
            for (int i = 0; i < numMaps; i++) {
            	// read this map's width and height
            	QuestMap map = new QuestMap(ois.readInt(), ois.readInt());
            	
            	// read each tile of this map
            	for (int x = 0; x < map.getWidth(); x++) {
            		for (int y = 0; y < map.getHeight(); y++) {
            			map.setTileAt(x, y, ois.readInt());
            		}
            	}
            	
            	// read the number of items on this map
            	int numItems = ois.readInt();
            	
            	// read each item (in this case, only keys)
            	for (int j = 0; j < numItems; j++) {
            		map.items.add(new Key(ois.readDouble(), ois.readDouble()));
            	}
            	
            	// set the current map
            	if (i == currMap) {
            		UrfQuest.game.setCurrMap(map);
            	}
            	
            	allMaps.add(map);
            }
            
            // set all maps
            UrfQuest.game.setAllMaps(allMaps);
            
            // read current keyCount
            UrfQuest.game.setKeyCount(ois.readInt());
            
            // read player data
            double xPos = ois.readDouble();
            double yPos = ois.readDouble();
            double health = ois.readDouble();
            double mana = ois.readDouble();
            double speed = ois.readDouble();
            
            // create new player
            Player p = new Player(xPos, yPos);
            p.setHealth(health);
            p.setMana(mana);
            p.setSpeed(speed);
            
            // set the player
            UrfQuest.game.setPlayer(p);
            
            // finish up
            ois.close();
            
            System.out.println("success");
        } catch (Exception e) {
            System.out.println("failed");
            System.out.println("Savefile corrupted.");
            e.printStackTrace();
            System.exit(1);
        }
        UrfQuest.time.start();
    }
}