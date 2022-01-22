package urfquest.client.guis.game;

import java.awt.Color;
import java.awt.Graphics;
import java.util.HashSet;

import urf.Pair;
import urfquest.Main;
import urfquest.client.Client;
import urfquest.client.QuestPanel;
import urfquest.client.guis.GUIAnchor;
import urfquest.client.guis.GUIContainer;

public class GameWeatherOverlay extends GUIContainer {
	
//	private int weatherTick = 0;
//	private boolean wTickUp = true;
	
	private HashSet<Pair<Integer, Integer>> set = new HashSet<Pair<Integer, Integer>>();
	
	public GameWeatherOverlay(Client c) {
		super(c, 
			  GUIAnchor.TOP_LEFT, 
			  0, 
			  0, 
			  0, 
			  0, 
			  "board", 
			  null, null, null, 0);
	}

	public void draw(Graphics g) {
		QuestPanel p = Main.panel;
		g.setColor(new Color(164, 255, 244, 50));
		g.fillRect(0, 0, p.getWidth(), p.getHeight());

		if (Math.random() > 0.5) {
			int xPos = 0;
			int yPos = 0;
			if (Math.random() > 0.5) {
				yPos = (int)(Math.random()*p.getHeight());
			} else {
				xPos = (int)(Math.random()*p.getWidth());
			}
			set.add(new Pair<Integer, Integer>(xPos, yPos));
		}
		
		HashSet<Pair<Integer, Integer>> newSet = new HashSet<Pair<Integer, Integer>>();
		for (Pair<Integer, Integer> pt : set) {
			if (pt.a >= p.getWidth() || pt.b >= p.getHeight()) {
				continue;
			}
			int temp = (int)(Math.random()*3.0);
			newSet.add(new Pair<Integer, Integer>(pt.a + temp, pt.b + 1));
		}
		set = newSet;
		
		g.setColor(Color.WHITE);
		for (Pair<Integer, Integer> pt : set) {
			g.drawRect(pt.a, pt.b, 2, 2);
		}
	}
	
//	private void weatherTick() {
//		if (wTickUp) {
//			if (weatherTick < 255) {
//				weatherTick++;
//			} else {
//				wTickUp = false;
//			}
//		} else {
//			if (weatherTick > 0) {
//				weatherTick--;
//			} else {
//				wTickUp = true;
//			}
//		}
//	}
	
	public boolean click() {
		return false;
	}
}