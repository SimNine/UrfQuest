package framework;

import javax.swing.JOptionPane;

import server.entities.items.Item;
import server.entities.mobs.Chicken;
import server.entities.mobs.Cyclops;
import server.entities.mobs.Player;
import server.entities.mobs.Rogue;
import server.game.QuestMap;

public class CommandProcessor {

	public static void process(String input) {
		// process the input into args
		if (input == null || input.isEmpty()) {
			JOptionPane.showMessageDialog(null, "no parameters entered", "error", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		String args[] = input.split(" ");
		if (args.length == 0) {
			JOptionPane.showMessageDialog(null, "no parameters entered", "error", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		for (int i = 0; i < args.length; i++) {
			args[i] = args[i].replaceAll("\\s","").toLowerCase();
		}
		
		Player p = UrfQuest.game.getPlayer();
		QuestMap m = p.getMap();
		
		// process the given arguments
		if (args[0].equals("help")) {
			JOptionPane.showMessageDialog(null, 
					"Commands: \n"
					+ "\"give (item name) [amount]\"\n"
					+ "\"spawn (mob name) [amount]\"\n"
					+ "\"set (health/mana/speed) (0-100)\"\n"
					+ "\"setblock (x) (y) (blockID)\"\n"
					+ "\"tp (xpos) (ypos)\"\n"
					+ "\"tprel (xoffset) (yoffset)\"\n"
					+ "\"home\"\n"
					+ "\"toolkit\"", 
					"command help", JOptionPane.INFORMATION_MESSAGE);
		} else if (args[0].equals("give")) {
			if (args.length < 2) {
				JOptionPane.showMessageDialog(null, 
						"incorrect usage\nuse \"give (item name) [amount]\"", "error", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			int count;
			if (args.length > 2) {
				count = Integer.parseInt(args[2]);
			} else {
				count = 1;
			}
			for (int i = 0; i < count; i++) {
				if (args[1].equals("pistol")) {
					p.addItem(new Item(0, 0, 13, m));
				} else if (args[1].equals("smg")) {
					p.addItem(new Item(0, 0, 16, m));
				} else if (args[1].equals("cheese")) {
					p.addItem(new Item(0, 0, 5, m));
				} else if (args[1].equals("gem")) {
					p.addItem(new Item(0, 0, 7, m));
				} else if (args[1].equals("key")) {
					p.addItem(new Item(0, 0, 11, m));
				} else if (args[1].equals("hatchet")) {
					p.addItem(new Item(0, 0, 18, m));
				} else if (args[1].equals("chickenleg")) {
					p.addItem(new Item(0, 0, 4, m));
				} else if (args[1].equals("log")) {
					p.addItem(new Item(0, 0, 8, m));
				} else if (args[1].equals("shovel")) {
					p.addItem(new Item(0, 0, 19, m));
				} else if (args[1].equals("pickaxe")) {
					p.addItem(new Item(0, 0, 17, m));
				} else if (args[1].equals("shotgun")) {
					p.addItem(new Item(0, 0, 15, m));
				} else if (args[1].equals("grenade")) {
					p.addItem(new Item(0, 0, 12, m));
				} else if (args[1].equals("lawrune")) {
					p.addItem(new Item(0, 0, 3, m));
				} else if (args[1].equals("cosmicrune")) {
					p.addItem(new Item(0, 0, 2, m));
				} else if (args[1].equals("astralrune")) {
					p.addItem(new Item(0, 0, 1, m));
				} else if (args[1].equals("rpg")) {
					p.addItem(new Item(0, 0, 14, m));
				} else if (args[1].equals("mic")) {
					p.addItem(new Item(0, 0, 10, m));
				} else if (args[1].equals("head")) {
					for (int l = 0; l < 100; l++) {
						p.addItem(new Item(0, 0, 5, m));
					}
				}
			}
		} else if (args[0].equals("spawn")) {
			int count;
			if (args.length > 2) {
				count = Integer.parseInt(args[2]);
			} else {
				count = 1;
			}
			for (int i = 0; i < count; i++) {
				if (args[1].equals("chicken")) {
					m.addMob(new Chicken(p.getPos()[0], p.getPos()[1], m));
				} else if (args[1].equals("cyclops")) {
					m.addMob(new Cyclops(p.getPos()[0], p.getPos()[1], m));
				} else if (args[1].equals("rogue")) {
					m.addMob(new Rogue(p.getPos()[0], p.getPos()[1], m));
				}
			}
		} else if (args[0].equals("set")) {
			if (args[1].equals("health")) {
				p.setHealth(Double.parseDouble(args[2]));
			} else if (args[1].equals("mana")) {
				p.setMana(Double.parseDouble(args[2]));
			} else if (args[1].equals("speed")) {
				p.setVelocity(Double.parseDouble(args[2])/100.0);
			}
		} else if (args[0].equals("home")) {
			p.setPos(250, 250);
		} else if (args[0].equals("tp")) {
			p.setPos(Double.parseDouble(args[1]), Double.parseDouble(args[2]));
		} else if (args[0].equals("tprel")) {
			p.move(Double.parseDouble(args[1]), Double.parseDouble(args[2]));
		} else if (args[0].equals("toolkit")) {
			p.addItem(new Item(0, 0, 17, m));
			p.addItem(new Item(0, 0, 18, m));
			p.addItem(new Item(0, 0, 19, m));
		} else if (args[0].equals("tick")) {
			for (int t = 0; t < Integer.parseInt(args[1]); t++) {
				UrfQuest.game.tick();
			}
		} else if (args[0].equals("setblock")) {
			int x = Integer.parseInt(args[1]);
			int y = Integer.parseInt(args[2]);
			int t = Integer.parseInt(args[3]);
			UrfQuest.game.getCurrMap().setTileAt(x, y, t);
		} else {
			JOptionPane.showMessageDialog(null, "invalid command", "error", JOptionPane.INFORMATION_MESSAGE);
		}
	}
}