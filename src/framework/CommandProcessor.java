package framework;

import javax.swing.JOptionPane;

import entities.items.AstralRune;
import entities.items.Cheese;
import entities.items.ChickenLeg;
import entities.items.CosmicRune;
import entities.items.Gem;
import entities.items.GrenadeItem;
import entities.items.Hatchet;
import entities.items.Key;
import entities.items.LawRune;
import entities.items.Log;
import entities.items.Mic;
import entities.items.Pickaxe;
import entities.items.Pistol;
import entities.items.RPG;
import entities.items.SMG;
import entities.items.Shotgun;
import entities.items.Shovel;
import entities.mobs.Chicken;
import entities.mobs.Cyclops;
import entities.mobs.Player;
import entities.mobs.Rogue;
import game.QuestMap;

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
					p.addItem(new Pistol(0, 0, m));
				} else if (args[1].equals("smg")) {
					p.addItem(new SMG(0, 0, m));
				} else if (args[1].equals("cheese")) {
					p.addItem(new Cheese(0, 0, m));
				} else if (args[1].equals("gem")) {
					p.addItem(new Gem(0, 0, m));
				} else if (args[1].equals("key")) {
					p.addItem(new Key(0, 0, m));
				} else if (args[1].equals("hatchet")) {
					p.addItem(new Hatchet(0, 0, m));
				} else if (args[1].equals("chickenleg")) {
					p.addItem(new ChickenLeg(0, 0, m));
				} else if (args[1].equals("log")) {
					p.addItem(new Log(0, 0, m));
				} else if (args[1].equals("shovel")) {
					p.addItem(new Shovel(0, 0, m));
				} else if (args[1].equals("pickaxe")) {
					p.addItem(new Pickaxe(0, 0, m));
				} else if (args[1].equals("shotgun")) {
					p.addItem(new Shotgun(0, 0, m));
				} else if (args[1].equals("grenade")) {
					p.addItem(new GrenadeItem(0, 0, m));
				} else if (args[1].equals("lawrune")) {
					p.addItem(new LawRune(0, 0, m));
				} else if (args[1].equals("cosmicrune")) {
					p.addItem(new CosmicRune(0, 0, m));
				} else if (args[1].equals("astralrune")) {
					p.addItem(new AstralRune(0, 0, m));
				} else if (args[1].equals("rpg")) {
					p.addItem(new RPG(0, 0, m));
				} else if (args[1].equals("mic")) {
					p.addItem(new Mic(0, 0, m));
				} else if (args[1].equals("head")) {
					for (int l = 0; l < 100; l++) {
						p.addItem(new Cheese(0, 0, m));
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
			p.addItem(new Pickaxe(0, 0, m));
			p.addItem(new Hatchet(0, 0, m));
			p.addItem(new Shovel(0, 0, m));
		} else {
			JOptionPane.showMessageDialog(null, "invalid command", "error", JOptionPane.INFORMATION_MESSAGE);
		}
	}
}