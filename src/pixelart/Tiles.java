package pixelart;

import java.awt.Color;
import java.awt.Graphics;

public class Tiles {
	
	public static void drawGrassBlock(Graphics g, int x, int y, int s) {
		g.setColor(new Color(127, 196, 102));
		g.fillRect(x, y, s*10, s*10);
		
		g.setColor(new Color(124, 163, 73));
		g.fillRect(x + s*2, y + s*0, s*1, s*2);
		g.fillRect(x + s*7, y + s*1, s*3, s*1);
		g.fillRect(x + s*0, y + s*2, s*1, s*2);
		g.fillRect(x + s*4, y + s*2, s*1, s*2);
		g.fillRect(x + s*7, y + s*2, s*1, s*1);
		g.fillRect(x + s*1, y + s*3, s*1, s*1);
		g.fillRect(x + s*6, y + s*3, s*1, s*1);
		g.fillRect(x + s*2, y + s*4, s*1, s*1);
		g.fillRect(x + s*9, y + s*4, s*1, s*1);
		g.fillRect(x + s*0, y + s*5, s*1, s*1);
		g.fillRect(x + s*5, y + s*5, s*2, s*1);
		g.fillRect(x + s*6, y + s*6, s*1, s*1);
		g.fillRect(x + s*4, y + s*7, s*2, s*1);
		g.fillRect(x + s*2, y + s*8, s*3, s*1);
		g.fillRect(x + s*7, y + s*8, s*1, s*1);
		g.fillRect(x + s*4, y + s*9, s*1, s*1);
		
		g.setColor(new Color(83, 189, 99));
		g.fillRect(x + s*3, y + s*0, s*1, s*1);
		g.fillRect(x + s*6, y + s*0, s*1, s*1);
		g.fillRect(x + s*8, y + s*0, s*1, s*1);
		g.fillRect(x + s*0, y + s*1, s*2, s*1);
		g.fillRect(x + s*4, y + s*1, s*1, s*1);
		g.fillRect(x + s*1, y + s*2, s*1, s*1);
		g.fillRect(x + s*5, y + s*2, s*1, s*1);
		g.fillRect(x + s*9, y + s*2, s*1, s*1);
		g.fillRect(x + s*1, y + s*4, s*1, s*2);
		g.fillRect(x + s*3, y + s*4, s*4, s*1);
		g.fillRect(x + s*8, y + s*4, s*1, s*2);
		g.fillRect(x + s*4, y + s*6, s*2, s*1);
		g.fillRect(x + s*7, y + s*6, s*1, s*1);
		g.fillRect(x + s*9, y + s*6, s*1, s*1);
		g.fillRect(x + s*1, y + s*7, s*3, s*1);
		g.fillRect(x + s*0, y + s*8, s*1, s*1);
		g.fillRect(x + s*5, y + s*8, s*2, s*1);
		g.fillRect(x + s*1, y + s*9, s*2, s*1);
		g.fillRect(x + s*6, y + s*9, s*2, s*1);
	}
	
	public static void drawStoneBlock(Graphics g, int x, int y, int s) {
		g.setColor(Color.GRAY);
		g.fillRect(x, y, s*10, s*10);
		
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(x + s*2, y + s*0, s*1, s*2);
		g.fillRect(x + s*7, y + s*1, s*3, s*1);
		g.fillRect(x + s*0, y + s*2, s*1, s*2);
		g.fillRect(x + s*4, y + s*2, s*1, s*2);
		g.fillRect(x + s*7, y + s*2, s*1, s*1);
		g.fillRect(x + s*1, y + s*3, s*1, s*1);
		g.fillRect(x + s*6, y + s*3, s*1, s*1);
		g.fillRect(x + s*2, y + s*4, s*1, s*1);
		g.fillRect(x + s*9, y + s*4, s*1, s*1);
		g.fillRect(x + s*0, y + s*5, s*1, s*1);
		g.fillRect(x + s*5, y + s*5, s*2, s*1);
		g.fillRect(x + s*6, y + s*6, s*1, s*1);
		g.fillRect(x + s*4, y + s*7, s*2, s*1);
		g.fillRect(x + s*2, y + s*8, s*3, s*1);
		g.fillRect(x + s*7, y + s*8, s*1, s*1);
		g.fillRect(x + s*4, y + s*9, s*1, s*1);
		
		g.setColor(Color.DARK_GRAY);
		g.fillRect(x + s*3, y + s*0, s*1, s*1);
		g.fillRect(x + s*6, y + s*0, s*1, s*1);
		g.fillRect(x + s*8, y + s*0, s*1, s*1);
		g.fillRect(x + s*0, y + s*1, s*2, s*1);
		g.fillRect(x + s*4, y + s*1, s*1, s*1);
		g.fillRect(x + s*1, y + s*2, s*1, s*1);
		g.fillRect(x + s*5, y + s*2, s*1, s*1);
		g.fillRect(x + s*9, y + s*2, s*1, s*1);
		g.fillRect(x + s*1, y + s*4, s*1, s*2);
		g.fillRect(x + s*3, y + s*4, s*4, s*1);
		g.fillRect(x + s*8, y + s*4, s*1, s*2);
		g.fillRect(x + s*4, y + s*6, s*2, s*1);
		g.fillRect(x + s*7, y + s*6, s*1, s*1);
		g.fillRect(x + s*9, y + s*6, s*1, s*1);
		g.fillRect(x + s*1, y + s*7, s*3, s*1);
		g.fillRect(x + s*0, y + s*8, s*1, s*1);
		g.fillRect(x + s*5, y + s*8, s*2, s*1);
		g.fillRect(x + s*1, y + s*9, s*2, s*1);
		g.fillRect(x + s*6, y + s*9, s*2, s*1);
	}
		
	public static void drawDirtBlock(Graphics g, int x, int y, int s) {
		g.setColor(new Color(186, 136, 43));
		g.fillRect(x, y, s*10, s*10);
		
		g.setColor(new Color(196, 143, 56));
		g.fillRect(x + s*2, y + s*0, s*1, s*2);
		g.fillRect(x + s*7, y + s*1, s*3, s*1);
		g.fillRect(x + s*0, y + s*2, s*1, s*2);
		g.fillRect(x + s*4, y + s*2, s*1, s*2);
		g.fillRect(x + s*7, y + s*2, s*1, s*1);
		g.fillRect(x + s*1, y + s*3, s*1, s*1);
		g.fillRect(x + s*6, y + s*3, s*1, s*1);
		g.fillRect(x + s*2, y + s*4, s*1, s*1);
		g.fillRect(x + s*9, y + s*4, s*1, s*1);
		g.fillRect(x + s*0, y + s*5, s*1, s*1);
		g.fillRect(x + s*5, y + s*5, s*2, s*1);
		g.fillRect(x + s*6, y + s*6, s*1, s*1);
		g.fillRect(x + s*4, y + s*7, s*2, s*1);
		g.fillRect(x + s*2, y + s*8, s*3, s*1);
		g.fillRect(x + s*7, y + s*8, s*1, s*1);
		g.fillRect(x + s*4, y + s*9, s*1, s*1);
		
		g.setColor(new Color(166, 113, 56));
		g.fillRect(x + s*3, y + s*0, s*1, s*1);
		g.fillRect(x + s*6, y + s*0, s*1, s*1);
		g.fillRect(x + s*8, y + s*0, s*1, s*1);
		g.fillRect(x + s*0, y + s*1, s*2, s*1);
		g.fillRect(x + s*4, y + s*1, s*1, s*1);
		g.fillRect(x + s*1, y + s*2, s*1, s*1);
		g.fillRect(x + s*5, y + s*2, s*1, s*1);
		g.fillRect(x + s*9, y + s*2, s*1, s*1);
		g.fillRect(x + s*1, y + s*4, s*1, s*2);
		g.fillRect(x + s*3, y + s*4, s*4, s*1);
		g.fillRect(x + s*8, y + s*4, s*1, s*2);
		g.fillRect(x + s*4, y + s*6, s*2, s*1);
		g.fillRect(x + s*7, y + s*6, s*1, s*1);
		g.fillRect(x + s*9, y + s*6, s*1, s*1);
		g.fillRect(x + s*1, y + s*7, s*3, s*1);
		g.fillRect(x + s*0, y + s*8, s*1, s*1);
		g.fillRect(x + s*5, y + s*8, s*2, s*1);
		g.fillRect(x + s*1, y + s*9, s*2, s*1);
		g.fillRect(x + s*6, y + s*9, s*2, s*1);
	}
	
	public static void drawHealthPad(Graphics g, int x, int y, int s) {
		g.setColor(Color.RED);
		g.fillRect(x, y, s*10, s*10);
		g.setColor(Color.RED.darker());
		g.fillRect(x + s, y + s, s*8, s*8);
		g.setColor(Color.RED.darker().darker());
		g.fillRect(x + s*2, y + s*2, s*6, s*6);
	}
	
	public static void drawManaPad(Graphics g, int x, int y, int s) {
		g.setColor(Color.BLUE);
		g.fillRect(x, y, s*10, s*10);
		g.setColor(Color.BLUE.darker());
		g.fillRect(x + s, y + s, s*8, s*8);
		g.setColor(Color.BLUE.darker().darker());
		g.fillRect(x + s*2, y + s*2, s*6, s*6);
	}
	
	public static void drawHurtPad(Graphics g, int x, int y, int s) {
		g.setColor(Color.BLACK);
		g.fillRect(x, y, s*10, s*10);
		g.setColor(Color.DARK_GRAY);
		g.fillRect(x + s, y + s, s*8, s*8);
		g.setColor(Color.GRAY);
		g.fillRect(x + s*2, y + s*2, s*6, s*6);
	}
	
	public static void drawSpeedPad(Graphics g, int x, int y, int s) {
		g.setColor(Color.GREEN);
		g.fillRect(x, y, s*10, s*10);
		g.setColor(Color.GREEN.darker());
		g.fillRect(x + s, y + s, s*8, s*8);
		g.setColor(Color.GREEN.darker().darker());
		g.fillRect(x + s*2, y + s*2, s*6, s*6);
	}
}