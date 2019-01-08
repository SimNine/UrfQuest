package framework;

import javax.swing.JOptionPane;

import entities.items.Cheese;
import entities.items.Gem;
import entities.items.Key;
import entities.items.Pistol;
import entities.items.SMG;
import entities.mobs.Chicken;
import entities.mobs.Cyclops;
import entities.mobs.Player;

public class CommandProcessor {

	public static void process(String input) {
		// process the input into args
		if (input.isEmpty()) {
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
					+ "\"home\"", 
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
					UrfQuest.game.player.addItem(new Pistol(0, 0));
				} else if (args[1].equals("smg")) {
					UrfQuest.game.player.addItem(new SMG(0, 0));
				} else if (args[1].equals("cheese")) {
					UrfQuest.game.player.addItem(new Cheese(0, 0));
				} else if (args[1].equals("gem")) {
					UrfQuest.game.player.addItem(new Gem(0, 0));
				} else if (args[1].equals("key")) {
					UrfQuest.game.player.addItem(new Key(0, 0));
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
					UrfQuest.game.getCurrMap().mobs.add(new Chicken(p.getPos()[0], p.getPos()[1]));
				} else if (args[1].equals("cyclops")) {
					UrfQuest.game.getCurrMap().mobs.add(new Cyclops(p.getPos()[0], p.getPos()[1]));
				}
			}
		} else if (args[0].equals("set")) {
			if (args[1].equals("health")) {
				UrfQuest.game.player.setHealth(Double.parseDouble(args[2]));
			} else if (args[1].equals("mana")) {
				UrfQuest.game.player.setMana(Double.parseDouble(args[2]));
			} else if (args[1].equals("speed")) {
				UrfQuest.game.player.setVelocity(Double.parseDouble(args[2])/100.0);
			}
		} else if (args[0].equals("home")) {
			UrfQuest.game.player.setPos(250, 250);
		} else if (args[0].equals("tp")) {
			UrfQuest.game.player.setPos(Double.parseDouble(args[1]), Double.parseDouble(args[2]));
		} else if (args[0].equals("tprel")) {
			UrfQuest.game.player.move(Double.parseDouble(args[1]), Double.parseDouble(args[2]));
		} else {
			JOptionPane.showMessageDialog(null, "invalid command", "error", JOptionPane.INFORMATION_MESSAGE);
		}
	}
}