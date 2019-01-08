package framework;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.swing.JOptionPane;

import entities.items.Item;
import entities.mobs.Player;
import game.QuestMap;
import urf.BinaryIn;
import urf.BinaryOut;
import urf.QuickHuffman;

public class Loader {

	private static File saveDir = new File("savedgames");

	public static void saveGame() {
		if (!saveDir.exists()) {
			saveDir.mkdir();
		}

		String filename = JOptionPane.showInputDialog(UrfQuest.panel, "Save Level", null) + ".urf";
		if (filename.equals("null.urf")) {
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
			BinaryOut bos = new BinaryOut(oos);

			// write number of maps
			bos.writeInt(UrfQuest.game.getAllMaps().size());
			ArrayList<QuestMap> maps = UrfQuest.game.getAllMaps();

			// write current map number
			bos.writeInt(UrfQuest.game.getAllMaps().indexOf(UrfQuest.game.getCurrMap()));

			// for each map
			for (int i = 0; i < UrfQuest.game.getAllMaps().size(); i++) {
				// get the ith map
				QuestMap map = maps.get(i);

				// write ith map's dimensions
				bos.writeInt(map.getWidth());
				bos.writeInt(map.getHeight());

				// set up for compression
				String input = "";
				for (int x = 0; x < map.getWidth(); x++) {
					for (int y = 0; y < map.getHeight(); y++) {
						input += map.getTileAt(x, y);
					}
				}
				HashMap<Character, String> encodings = QuickHuffman.compressionMap(input);

				// write the encoding map
				bos.writeInt(encodings.size());
				for (Entry<Character, String> e : encodings.entrySet()) {
					bos.writeChar((char) e.getKey());
					bos.writeInt(e.getValue().length());
					for (int j = 0; j < e.getValue().length(); j++) {
						bos.writeChar(e.getValue().charAt(j));
					}
					//System.out.println(e.getKey() + ": " + e.getValue());
				}

				// write each column of tiles
				for (int x = 0; x < map.getWidth(); x++) {
					for (int y = 0; y < map.getHeight(); y++) {
						int thisTile = map.getTileAt(x, y);
						String thisString = encodings.get(Integer.toString(thisTile).charAt(0));
						for (int l = 0; l < thisString.length(); l++) {
							if (thisString.charAt(l) == '0') {
								bos.writeBoolean(false);
							} else {
								bos.writeBoolean(true);
							}
						}
					}
				}

				// write number of items on the map
				bos.writeInt(map.getNumItems());

				// for each item (in this case, only keys exist), write coords
				for (i = 0; i < map.getNumItems(); i++) {
					Item curr = map.getItems().get(i);
					bos.writeDouble(curr.getPos()[0]);
					bos.writeDouble(curr.getPos()[1]);
				}
			}

			// get the player
			Player p = UrfQuest.game.getPlayer();

			// write the current location of the player
			bos.writeDouble(p.getPos()[0]);
			bos.writeDouble(p.getPos()[1]);

			// write the current stats of the player
			bos.writeDouble(p.getHealth());
			bos.writeDouble(p.getMana());
			bos.writeDouble(p.getVelocity());

			bos.flush();
			bos.close();
			System.out.println("Current game saved.");
		} catch (Exception e) {
			System.out.println("Gamesave failed.");
			e.printStackTrace();
			System.exit(1);
		}
	}

	public static void loadGame() {
		String filename = JOptionPane.showInputDialog(null, "Load a level...", null) + ".urf";
		if (filename.equals("null.urf")) {
			return;
		}

		// begin gameloading process
		try {
			System.out.print("Loading " + filename + "... ");
			File file = new File(saveDir, filename);
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
			BinaryIn bis = new BinaryIn(ois);

			// read number of maps
			int numMaps = bis.readInt();

			// read number of current map
			int currMap = bis.readInt();

			ArrayList<QuestMap> allMaps = new ArrayList<QuestMap>();

			// for each map
			for (int i = 0; i < numMaps; i++) {
				// read this map's width and height
				QuestMap map = new QuestMap(bis.readInt(), bis.readInt(), QuestMap.EMPTY_MAP);

				// read the encoding map
				HashMap<String, Character> encodings = new HashMap<String, Character>();
				int numEncodings = bis.readInt();
				for (int j = 0; j < numEncodings; j++) {
					Character c = bis.readChar();
					String s = "";
					int strLen = bis.readInt();
					for (int l = 0; l < strLen; l++) {
						s += bis.readChar();
					}
					encodings.put(s, c);
					//System.out.println("Entry: " + s + ": " + c);
				}
				
				// read each tile of this map
				for (int x = 0; x < map.getWidth(); x++) {
					for (int y = 0; y < map.getHeight(); y++) {
						String coded = "";
						while (!encodings.containsKey(coded)) {
							if (bis.readBoolean()) {
								coded += "1";
							} else {
								coded += "0";
							}
						}
						map.setTileAt(x, y, Integer.parseInt(String.valueOf(encodings.get(coded))));
					}
				}

				// read the number of items on this map
				int numItems = bis.readInt();

				// read each item (in this case, only keys)
				for (int j = 0; j < numItems; j++) {
					map.addItem(new Key(bis.readDouble(), bis.readDouble()));
				}

				// set the current map
				if (i == currMap) {
					UrfQuest.game.setCurrMap(map);
				}
				
				// regenerate the map's minimap
				map.generateMinimap();

				allMaps.add(map);
			}

			// set all maps
			UrfQuest.game.setAllMaps(allMaps);

			// read player data
			double xPos = bis.readDouble();
			double yPos = bis.readDouble();
			double health = bis.readDouble();
			double mana = bis.readDouble();
			double speed = bis.readDouble();

			// create new player
			Player p = new Player(xPos, yPos);
			p.setHealth(health);
			p.setMana((int)mana);
			p.setVelocity(speed);

			// set the player
			UrfQuest.game.setPlayer(p);

			System.out.println("success");
		} catch (Exception e) {
			System.out.println("failed");
			System.out.println("Savefile corrupted.");
			e.printStackTrace();
			System.exit(1);
		}
	}
}