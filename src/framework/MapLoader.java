package framework;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import game.QuestMap;

public class MapLoader {

	public static void saveLevel(QuestMap map, String filename) {
        File levelDir = new File("levels");
        if (!levelDir.exists())
            levelDir.mkdir();
        
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
            oos.writeInt(board.getBoardWidth()); // width byte
            oos.writeInt(board.getBoardHeight()); // height byte
            oos.writeInt(board.getScale()); // scale byte
            oos.writeInt(board.getWaterRemaining()); // waterRemaining byte
            oos.writeInt(board.getWaterNeeded()); // waterNeeded byte
            oos.writeInt(board.getWaterExisting()); // waterExisting byte
            for (int w = 0; w < board.getBoardWidth(); w++) {
                for (int h = 0; h < board.getBoardHeight(); h++) {
                    oos.writeByte(board.getBlockAt(w, h)); // block's type
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
    }
    
    @SuppressWarnings("resource")
    public static void loadLevel(GamePanel board, String filename) {
        try {
            System.out.print("Loading " + filename + "... ");
            File file = new File("levels", filename);
            FileInputStream fos = new FileInputStream(file);
            ObjectInputStream oos = new ObjectInputStream(fos);
            int width = oos.readInt();
            int height = oos.readInt();
            board.setBoardGrid(new int[width][height]);
            int scale = oos.readInt();
            board.setScale(scale);
            int waterRemaining = oos.readInt();
            board.setWaterRemaining(waterRemaining);
            int waterNeeded = oos.readInt();
            board.setWaterNeeded(waterNeeded);
            int waterExisting = oos.readInt();
            board.setWaterExisting(waterExisting);
            while (oos.available() != 0) {
                for (int x = 0; x < width; x++) {
                    for (int y = 0; y < height; y++) {
                        board.setBlockAt(x, y, oos.readByte());
                        if (board.getBlockAt(x, y) == 2)
                            board.setWaterExisting(board.getWaterExisting() + 1);
                    }
                }
            }
            System.out.println("success");
        } catch (Exception e) {
            System.out.println("failed");
            System.out.println("Savefile corrupted.");
            e.printStackTrace();
            System.exit(1);
        }
    }
}