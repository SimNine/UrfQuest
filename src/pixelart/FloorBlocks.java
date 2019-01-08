package pixelart;

import java.awt.Color;
import java.awt.Graphics;

public class FloorBlocks {
	
	public static void drawGrassBlock(Graphics g, int x, int y, int s) {
		int W = s;
		int H = s;
		
		g.setColor(new Color(127, 196, 102));
		g.fillRect(x, y, W*10, H*10);
		
		g.setColor(new Color(124, 163, 73));
		g.fillRect(x + W*2, y + H*0, W*1, H*2);
		g.fillRect(x + W*7, y + H*1, W*3, H*1);
		g.fillRect(x + W*0, y + H*2, W*1, H*2);
		g.fillRect(x + W*4, y + H*2, W*1, H*2);
		g.fillRect(x + W*7, y + H*2, W*1, H*1);
		g.fillRect(x + W*1, y + H*3, W*1, H*1);
		g.fillRect(x + W*6, y + H*3, W*1, H*1);
		g.fillRect(x + W*2, y + H*4, W*1, H*1);
		g.fillRect(x + W*9, y + H*4, W*1, H*1);
		g.fillRect(x + W*0, y + H*5, W*1, H*1);
		g.fillRect(x + W*5, y + H*5, W*2, H*1);
		g.fillRect(x + W*6, y + H*6, W*1, H*1);
		g.fillRect(x + W*4, y + H*7, W*2, H*1);
		g.fillRect(x + W*2, y + H*8, W*3, H*1);
		g.fillRect(x + W*7, y + H*8, W*1, H*1);
		g.fillRect(x + W*4, y + H*9, W*1, H*1);
		
		g.setColor(new Color(83, 189, 99));
		g.fillRect(x + W*3, y + H*0, W*1, H*1);
		g.fillRect(x + W*6, y + H*0, W*1, H*1);
		g.fillRect(x + W*8, y + H*0, W*1, H*1);
		g.fillRect(x + W*0, y + H*1, W*2, H*1);
		g.fillRect(x + W*4, y + H*1, W*1, H*1);
		g.fillRect(x + W*1, y + H*2, W*1, H*1);
		g.fillRect(x + W*5, y + H*2, W*1, H*1);
		g.fillRect(x + W*9, y + H*2, W*1, H*1);
		g.fillRect(x + W*1, y + H*4, W*1, H*2);
		g.fillRect(x + W*3, y + H*4, W*4, H*1);
		g.fillRect(x + W*8, y + H*4, W*1, H*2);
		g.fillRect(x + W*4, y + H*6, W*2, H*1);
		g.fillRect(x + W*7, y + H*6, W*1, H*1);
		g.fillRect(x + W*9, y + H*6, W*1, H*1);
		g.fillRect(x + W*1, y + H*7, W*3, H*1);
		g.fillRect(x + W*0, y + H*8, W*1, H*1);
		g.fillRect(x + W*5, y + H*8, W*2, H*1);
		g.fillRect(x + W*1, y + H*9, W*2, H*1);
		g.fillRect(x + W*6, y + H*9, W*2, H*1);
		
		//g.setColor(Color.BLACK);
		//g.drawRect(x, y, W*10, H*10);
	}
	
	public static void drawStoneBlock(Graphics g, int x, int y, int s) {
		int W = s;
		int H = s;
		
		g.setColor(Color.GRAY);
		g.fillRect(x, y, W*10, H*10);
		
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(x + W*2, y + H*0, W*1, H*2);
		g.fillRect(x + W*7, y + H*1, W*3, H*1);
		g.fillRect(x + W*0, y + H*2, W*1, H*2);
		g.fillRect(x + W*4, y + H*2, W*1, H*2);
		g.fillRect(x + W*7, y + H*2, W*1, H*1);
		g.fillRect(x + W*1, y + H*3, W*1, H*1);
		g.fillRect(x + W*6, y + H*3, W*1, H*1);
		g.fillRect(x + W*2, y + H*4, W*1, H*1);
		g.fillRect(x + W*9, y + H*4, W*1, H*1);
		g.fillRect(x + W*0, y + H*5, W*1, H*1);
		g.fillRect(x + W*5, y + H*5, W*2, H*1);
		g.fillRect(x + W*6, y + H*6, W*1, H*1);
		g.fillRect(x + W*4, y + H*7, W*2, H*1);
		g.fillRect(x + W*2, y + H*8, W*3, H*1);
		g.fillRect(x + W*7, y + H*8, W*1, H*1);
		g.fillRect(x + W*4, y + H*9, W*1, H*1);
		
		g.setColor(Color.DARK_GRAY);
		g.fillRect(x + W*3, y + H*0, W*1, H*1);
		g.fillRect(x + W*6, y + H*0, W*1, H*1);
		g.fillRect(x + W*8, y + H*0, W*1, H*1);
		g.fillRect(x + W*0, y + H*1, W*2, H*1);
		g.fillRect(x + W*4, y + H*1, W*1, H*1);
		g.fillRect(x + W*1, y + H*2, W*1, H*1);
		g.fillRect(x + W*5, y + H*2, W*1, H*1);
		g.fillRect(x + W*9, y + H*2, W*1, H*1);
		g.fillRect(x + W*1, y + H*4, W*1, H*2);
		g.fillRect(x + W*3, y + H*4, W*4, H*1);
		g.fillRect(x + W*8, y + H*4, W*1, H*2);
		g.fillRect(x + W*4, y + H*6, W*2, H*1);
		g.fillRect(x + W*7, y + H*6, W*1, H*1);
		g.fillRect(x + W*9, y + H*6, W*1, H*1);
		g.fillRect(x + W*1, y + H*7, W*3, H*1);
		g.fillRect(x + W*0, y + H*8, W*1, H*1);
		g.fillRect(x + W*5, y + H*8, W*2, H*1);
		g.fillRect(x + W*1, y + H*9, W*2, H*1);
		g.fillRect(x + W*6, y + H*9, W*2, H*1);
		
		//g.setColor(Color.BLACK);
		//g.drawRect(x, y, W*10, H*10);
	}
		
	public static void drawDirtBlock(Graphics g, int x, int y, int s) {
		int W = s;
		int H = s;
		
		g.setColor(new Color(186, 136, 43));
		g.fillRect(x, y, W*10, H*10);
		
		g.setColor(new Color(196, 143, 56));
		g.fillRect(x + W*2, y + H*0, W*1, H*2);
		g.fillRect(x + W*7, y + H*1, W*3, H*1);
		g.fillRect(x + W*0, y + H*2, W*1, H*2);
		g.fillRect(x + W*4, y + H*2, W*1, H*2);
		g.fillRect(x + W*7, y + H*2, W*1, H*1);
		g.fillRect(x + W*1, y + H*3, W*1, H*1);
		g.fillRect(x + W*6, y + H*3, W*1, H*1);
		g.fillRect(x + W*2, y + H*4, W*1, H*1);
		g.fillRect(x + W*9, y + H*4, W*1, H*1);
		g.fillRect(x + W*0, y + H*5, W*1, H*1);
		g.fillRect(x + W*5, y + H*5, W*2, H*1);
		g.fillRect(x + W*6, y + H*6, W*1, H*1);
		g.fillRect(x + W*4, y + H*7, W*2, H*1);
		g.fillRect(x + W*2, y + H*8, W*3, H*1);
		g.fillRect(x + W*7, y + H*8, W*1, H*1);
		g.fillRect(x + W*4, y + H*9, W*1, H*1);
		
		g.setColor(new Color(166, 113, 56));
		g.fillRect(x + W*3, y + H*0, W*1, H*1);
		g.fillRect(x + W*6, y + H*0, W*1, H*1);
		g.fillRect(x + W*8, y + H*0, W*1, H*1);
		g.fillRect(x + W*0, y + H*1, W*2, H*1);
		g.fillRect(x + W*4, y + H*1, W*1, H*1);
		g.fillRect(x + W*1, y + H*2, W*1, H*1);
		g.fillRect(x + W*5, y + H*2, W*1, H*1);
		g.fillRect(x + W*9, y + H*2, W*1, H*1);
		g.fillRect(x + W*1, y + H*4, W*1, H*2);
		g.fillRect(x + W*3, y + H*4, W*4, H*1);
		g.fillRect(x + W*8, y + H*4, W*1, H*2);
		g.fillRect(x + W*4, y + H*6, W*2, H*1);
		g.fillRect(x + W*7, y + H*6, W*1, H*1);
		g.fillRect(x + W*9, y + H*6, W*1, H*1);
		g.fillRect(x + W*1, y + H*7, W*3, H*1);
		g.fillRect(x + W*0, y + H*8, W*1, H*1);
		g.fillRect(x + W*5, y + H*8, W*2, H*1);
		g.fillRect(x + W*1, y + H*9, W*2, H*1);
		g.fillRect(x + W*6, y + H*9, W*2, H*1);
		
		//g.setColor(Color.BLACK);
		//g.drawRect(x, y, W*10, H*10);
	}
}