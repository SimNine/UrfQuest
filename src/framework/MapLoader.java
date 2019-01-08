package framework;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JOptionPane;

import entities.Ball;
import entities.Entity;
import entities.Square;

public class MapLoader {

	public static void saveLevel() {
		V.time.stop();
        File levelDir = new File("levels");
        if (!levelDir.exists())
            levelDir.mkdir();
        
        String filename = JOptionPane.showInputDialog(V.qPanel,
                "Save Level", null) + ".urf";
        
        File level = new File(levelDir, filename);
        
        if (level.exists()) {
            System.out.println("Deleting previous save...");
            level.delete();
            System.out.println("Previous save deleted.");
        }
        try {
            System.out.println("Saving current game.");
            level.createNewFile();
            FileOutputStream fos = new FileOutputStream(level);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            
            oos.writeInt(V.scale);
            oos.writeDouble(V.playerPositionX);
            oos.writeDouble(V.playerPositionY);
            //oos.writeInt(V.playerOrientation);
            oos.writeDouble(V.health);
            oos.writeDouble(V.mana);
            oos.writeDouble(V.speed);
            
            for (Entity e : V.entities) {
            	oos.writeBoolean(true);
            	oos.writeInt(e.getType());
            	oos.writeObject(e.getColor());
            	oos.writeDouble(e.getDiameter());
            	oos.writeDouble(e.getPhysics()[0]);
            	oos.writeDouble(e.getPhysics()[1]);
            	oos.writeDouble(e.getPhysics()[2]);
            	oos.writeDouble(e.getPhysics()[3]);
            	oos.writeDouble(e.getPhysics()[4]);
            	oos.writeDouble(e.getPhysics()[5]);
            }
            oos.writeBoolean(false);
            
            oos.writeInt(V.qMap.getWidth());
            oos.writeInt(V.qMap.getHeight());
            for (int x = 0; x < V.qMap.getWidth(); x++) {
            	for (int y = 0; y < V.qMap.getHeight(); y++) {
            		oos.writeInt(V.qMap.getTileAt(x, y));
            	}
            }
            
            oos.flush();
            oos.close();
            System.out.println("Current game saved.");
        } catch (Exception e) {
            System.out.println("Gamesave failed.");
            e.printStackTrace();
            System.exit(1);
        }
        V.time.start();
    }
    
    @SuppressWarnings("resource")
    public static void loadLevel() {
    	V.time.stop();
        String filename = JOptionPane.showInputDialog(null,
                "Load a level...", null) + ".urf";
        try {
            System.out.print("Loading " + filename + "... ");
            File file = new File("levels", filename);
            FileInputStream fos = new FileInputStream(file);
            ObjectInputStream oos = new ObjectInputStream(fos);
            
            V.scale = oos.readInt();
            V.playerPositionX = oos.readDouble();
            V.playerPositionY = oos.readDouble();
            //V.playerOrientation = oos.readInt();
            V.health = oos.readDouble();
            V.mana = oos.readDouble();
            V.speed = oos.readDouble();
            
            V.entities.clear();
            while (oos.readBoolean() == true) {
            	int type = oos.readInt();
            	Color tempCol = (Color)oos.readObject();
            	double diameter = oos.readDouble();
            	double posX = oos.readDouble();
            	double posY = oos.readDouble();
            	double velX = oos.readDouble();
            	double velY = oos.readDouble();
            	double accX = oos.readDouble();
            	double accY = oos.readDouble();
            	
            	switch (type) {
            	case 0:
                	Entity temp = new Ball(posX, posY, tempCol, diameter);
                	temp.setPhysics(posX, posY, velX, velY, accX, accY);
                	V.entities.add(temp);
                	break;
            	case 1:
            		Entity temp1 = new Square(posX, posY, tempCol, diameter);
                	temp1.setPhysics(posX, posY, velX, velY, accX, accY);
                	V.entities.add(temp1);
                	break;
                default:
                	continue;
            	}
            }
            
            int mapWidth = oos.readInt();
            int mapHeight = oos.readInt();
            V.qMap.setNewMap(mapWidth, mapHeight);
            for (int x = 0; x < mapWidth; x++) {
            	for (int y = 0; y < mapHeight; y++) {
            		V.qMap.setTileAt(x, y, oos.readInt());
            	}
            }
            
            System.out.println("success");
        } catch (Exception e) {
            System.out.println("failed");
            System.out.println("Savefile corrupted.");
            e.printStackTrace();
            System.exit(1);
        }
        V.time.start();
    }
}