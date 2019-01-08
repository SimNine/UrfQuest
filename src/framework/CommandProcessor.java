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
import entities.items.Pickaxe;
import entities.items.Pistol;
import entities.items.SMG;
import entities.items.Shotgun;
import entities.items.Shovel;
import entities.mobs.Chicken;
import entities.mobs.Cyclops;
import entities.mobs.Player;
import entities.mobs.Rogue;

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
					UrfQuest.game.getPlayer().addItem(new Pistol(0, 0));
				} else if (args[1].equals("smg")) {
					UrfQuest.game.getPlayer().addItem(new SMG(0, 0));
				} else if (args[1].equals("cheese")) {
					UrfQuest.game.getPlayer().addItem(new Cheese(0, 0));
				} else if (args[1].equals("gem")) {
					UrfQuest.game.getPlayer().addItem(new Gem(0, 0));
				} else if (args[1].equals("key")) {
					UrfQuest.game.getPlayer().addItem(new Key(0, 0));
				} else if (args[1].equals("hatchet")) {
					UrfQuest.game.getPlayer().addItem(new Hatchet(0, 0));
				} else if (args[1].equals("chickenleg")) {
					UrfQuest.game.getPlayer().addItem(new ChickenLeg(0, 0));
				} else if (args[1].equals("log")) {
					UrfQuest.game.getPlayer().addItem(new Log(0, 0));
				} else if (args[1].equals("shovel")) {
					UrfQuest.game.getPlayer().addItem(new Shovel(0, 0));
				} else if (args[1].equals("pickaxe")) {
					UrfQuest.game.getPlayer().addItem(new Pickaxe(0, 0));
				} else if (args[1].equals("shotgun")) {
					UrfQuest.game.getPlayer().addItem(new Shotgun(0, 0));
				} else if (args[1].equals("grenade")) {
					UrfQuest.game.getPlayer().addItem(new GrenadeItem(0, 0));
				} else if (args[1].equals("lawrune")) {
					UrfQuest.game.getPlayer().addItem(new LawRune(0, 0));
				} else if (args[1].equals("cosmicrune")) {
					UrfQuest.game.getPlayer().addItem(new CosmicRune(0, 0));
				} else if (args[1].equals("astralrune")) {
					UrfQuest.game.getPlayer().addItem(new AstralRune(0, 0));
				}
			}
		} else if (args[0].equals("spawn")) {
			int count;
			if (args.length > 2) {
				count = Integer.parseInt(args[2]);
			} else {
				count = 1;
			}
			Player p = UrfQuest.game.getPlayer();
			for (int i = 0; i < count; i++) {
				if (args[1].equals("chicken")) {
					UrfQuest.game.getCurrMap().addMob(new Chicken(p.getPos()[0], p.getPos()[1]));
				} else if (args[1].equals("cyclops")) {
					UrfQuest.game.getCurrMap().addMob(new Cyclops(p.getPos()[0], p.getPos()[1]));
				} else if (args[1].equals("rogue")) {
					UrfQuest.game.getCurrMap().addMob(new Rogue(p.getPos()[0], p.getPos()[1]));
				}
			}
		} else if (args[0].equals("set")) {
			if (args[1].equals("health")) {
				UrfQuest.game.getPlayer().setHealth(Double.parseDouble(args[2]));
			} else if (args[1].equals("mana")) {
				UrfQuest.game.getPlayer().setMana(Double.parseDouble(args[2]));
			} else if (args[1].equals("speed")) {
				UrfQuest.game.getPlayer().setVelocity(Double.parseDouble(args[2])/100.0);
			}
		} else if (args[0].equals("home")) {
			UrfQuest.game.getPlayer().setPos(250, 250);
		} else if (args[0].equals("tp")) {
			UrfQuest.game.getPlayer().setPos(Double.parseDouble(args[1]), Double.parseDouble(args[2]));
		} else if (args[0].equals("tprel")) {
			UrfQuest.game.getPlayer().move(Double.parseDouble(args[1]), Double.parseDouble(args[2]));
		} else if (args[0].equals("toolkit")) {
			UrfQuest.game.getPlayer().addItem(new Pickaxe(0, 0));
			UrfQuest.game.getPlayer().addItem(new Hatchet(0, 0));
			UrfQuest.game.getPlayer().addItem(new Shovel(0, 0));
		} else {
			JOptionPane.showMessageDialog(null, "invalid command", "error", JOptionPane.INFORMATION_MESSAGE);
		}
	}
}