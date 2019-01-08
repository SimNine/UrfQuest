package guis;

import java.awt.Color;
import java.awt.Graphics;

public class QuestGUI {
	
	public static void drawStatusBar(Graphics g, Color col, double val,
			int max, int scale, int x, int y) {
		if (val > max) val = max;
		else if (val < 0) val = 0;
		int pixelLength = (int)(scale*98*(val/(double)max));
		
		g.setColor(Color.BLACK);
		g.fillRect(x, y, scale*100, scale);
		g.fillRect(x, y + scale*5, scale*100, scale);
		g.fillRect(x, y, scale, scale*5);
		g.fillRect(scale*109, y, scale, scale*5);
		g.setColor(col);
		g.fillRect(x + scale, y + scale, pixelLength, scale*4);
	}
}