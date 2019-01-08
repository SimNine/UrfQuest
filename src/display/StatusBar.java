package display;

import java.awt.Color;
import java.awt.Graphics;

public class StatusBar {
	private Color color;
	private int max;
	private int s;
	private int val;
	private int pixelLength;

	public StatusBar(int val, int max, int scale, Color color) {
		this.val = val;
		this.max = max;
		this.s = scale;
		this.color = color;
		updatePixLength();
	}
	
	public void drawStatusBar(Graphics g, int x, int y) {
		g.setColor(Color.BLACK);
		g.fillRect(x, y, s*100, s);
		g.fillRect(x, y + s*5, s*100, s);
		g.fillRect(x, y, s, s*5);
		g.fillRect(s*109, y, s, s*5);
		g.setColor(color);
		g.fillRect(x + s, y + s, pixelLength, s*4);
	}
	
	private void updatePixLength() {
		pixelLength = (int)(s*98*((double)val/(double)max));
	}
	
	public void setVal(int x) {
		if (x > max) val = max;
		else if (x < 0) val = 0;
		else val = x;
		updatePixLength();
	}
	
	public int getVal() {
		return val;
	}
}