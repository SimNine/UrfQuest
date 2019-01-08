package pixelart;

import java.awt.Color;
import java.awt.Graphics;

public class Sprites {

	public static void drawCharacterPlaceholder(Graphics g, int x, int y, int s, String dir) {
		switch (dir) {
		case "NW":
			g.setColor(Color.BLACK);
			g.fillRect(x + s*0, y + s*0, s*6, s*6);
			g.fillRect(x + s*6, y + s*0, s*1, s*2);
			g.fillRect(x + s*0, y + s*6, s*2, s*1);
			g.fillRect(x + s*6, y + s*3, s*1, s*7);
			g.fillRect(x + s*5, y + s*4, s*3, s*5);
			g.fillRect(x + s*4, y + s*5, s*5, s*3);
			g.fillRect(x + s*3, y + s*6, s*7, s*1);
			g.setColor(Color.YELLOW);
			g.fillRect(x + s*1, y + s*1, s*3, s*3);
			g.fillRect(x + s*4, y + s*1, s*1, s*1);
			g.fillRect(x + s*1, y + s*4, s*1, s*1);
			g.fillRect(x + s*3, y + s*3, s*2, s*2);
			g.fillRect(x + s*4, y + s*4, s*2, s*2);
			g.fillRect(x + s*5, y + s*5, s*2, s*2);
			g.fillRect(x + s*6, y + s*7, s*1, s*1);
			g.fillRect(x + s*7, y + s*6, s*1, s*1);
			break;
		case "W":
			g.setColor(Color.BLACK);
			g.fillRect(x + s*0, y + s*3, s*10, s*4);
			g.fillRect(x + s*1, y + s*2, s*4, s*1);
			g.fillRect(x + s*2, y + s*1, s*3, s*1);
			g.fillRect(x + s*3, y + s*0, s*2, s*10);
			g.fillRect(x + s*1, y + s*7, s*2, s*1);
			g.fillRect(x + s*2, y + s*8, s*3, s*1);
			g.setColor(Color.YELLOW);
			g.fillRect(x + s*3, y + s*2, s*1, s*1);
			g.fillRect(x + s*2, y + s*3, s*2, s*1);
			g.fillRect(x + s*1, y + s*4, s*8, s*2);
			g.fillRect(x + s*2, y + s*6, s*2, s*1);
			g.fillRect(x + s*3, y + s*7, s*1, s*1);
			break;
		case "SW":
			g.setColor(Color.BLACK);
			g.fillRect(x + s*6, y + s*0, s*1, s*7);
			g.fillRect(x + s*5, y + s*1, s*3, s*5);
			g.fillRect(x + s*4, y + s*2, s*5, s*3);
			g.fillRect(x + s*3, y + s*3, s*7, s*1);
			g.fillRect(x + s*0, y + s*4, s*6, s*6);
			g.fillRect(x + s*0, y + s*3, s*2, s*1);
			g.fillRect(x + s*6, y + s*8, s*1, s*2);
			g.setColor(Color.YELLOW);
			g.fillRect(x + s*7, y + s*3, s*1, s*1);
			g.fillRect(x + s*6, y + s*2, s*1, s*1);
			g.fillRect(x + s*5, y + s*3, s*2, s*2);
			g.fillRect(x + s*4, y + s*4, s*2, s*2);
			g.fillRect(x + s*3, y + s*5, s*2, s*2);
			g.fillRect(x + s*1, y + s*6, s*3, s*3);
			g.fillRect(x + s*4, y + s*8, s*1, s*1);
			g.fillRect(x + s*1, y + s*5, s*1, s*1);
			break;
		case "N":
			g.setColor(Color.BLACK);
			g.fillRect(x + s*3, y + s*0, s*4, s*10);
			g.fillRect(x + s*2, y + s*1, s*6, s*4);
			g.fillRect(x + s*1, y + s*2, s*8, s*3);
			g.fillRect(x + s*0, y + s*3, s*10, s*2);
			g.setColor(Color.YELLOW);
			g.fillRect(x + s*4, y + s*1, s*2, s*8);
			g.fillRect(x + s*3, y + s*2, s*4, s*2);
			g.fillRect(x + s*2, y + s*3, s*6, s*1);
			break;
		case "S":
			g.setColor(Color.BLACK);
			g.fillRect(x + s*3, y + s*0, s*4, s*10);
			g.fillRect(x + s*0, y + s*5, s*10, s*2);
			g.fillRect(x + s*1, y + s*7, s*8, s*1);
			g.fillRect(x + s*2, y + s*8, s*6, s*1);
			g.setColor(Color.YELLOW);
			g.fillRect(x + s*4, y + s*1, s*2, s*8);
			g.fillRect(x + s*3, y + s*7, s*4, s*1);
			g.fillRect(x + s*2, y + s*6, s*6, s*1);
			break;
		case "NE":
			g.setColor(Color.BLACK);
			g.fillRect(x + s*4, y + s*0, s*6, s*6);
			g.fillRect(x + s*3, y + s*0, s*1, s*2);
			g.fillRect(x + s*8, y + s*6, s*2, s*1);
			g.fillRect(x + s*3, y + s*3, s*1, s*7);
			g.fillRect(x + s*2, y + s*4, s*3, s*5);
			g.fillRect(x + s*1, y + s*5, s*5, s*3);
			g.fillRect(x + s*0, y + s*6, s*7, s*1);
			g.setColor(Color.YELLOW);
			g.fillRect(x + s*6, y + s*1, s*3, s*3);
			g.fillRect(x + s*5, y + s*1, s*1, s*1);
			g.fillRect(x + s*8, y + s*4, s*1, s*1);
			g.fillRect(x + s*5, y + s*3, s*2, s*2);
			g.fillRect(x + s*4, y + s*4, s*2, s*2);
			g.fillRect(x + s*3, y + s*5, s*2, s*2);
			g.fillRect(x + s*2, y + s*6, s*1, s*1);
			g.fillRect(x + s*3, y + s*7, s*1, s*1);
			break;
		case "E":
			g.setColor(Color.BLACK);
			g.fillRect(x + s*0, y + s*3, s*10, s*4);
			g.fillRect(x + s*5, y + s*0, s*2, s*10);
			g.fillRect(x + s*5, y + s*1, s*3, s*8);
			g.fillRect(x + s*5, y + s*2, s*4, s*6);
			g.setColor(Color.YELLOW);
			g.fillRect(x + s*1, y + s*4, s*8, s*2);
			g.fillRect(x + s*6, y + s*2, s*1, s*6);
			g.fillRect(x + s*6, y + s*3, s*2, s*4);
			break;
		case "SE":
			g.setColor(Color.BLACK);
			g.fillRect(x + s*3, y + s*0, s*1, s*7);
			g.fillRect(x + s*2, y + s*1, s*3, s*5);
			g.fillRect(x + s*1, y + s*2, s*5, s*3);
			g.fillRect(x + s*0, y + s*3, s*7, s*1);
			g.fillRect(x + s*4, y + s*4, s*6, s*6);
			g.fillRect(x + s*3, y + s*8, s*1, s*2);
			g.fillRect(x + s*8, y + s*3, s*2, s*1);
			g.setColor(Color.YELLOW);
			g.fillRect(x + s*3, y + s*2, s*1, s*1);
			g.fillRect(x + s*2, y + s*3, s*1, s*1);
			g.fillRect(x + s*3, y + s*3, s*2, s*2);
			g.fillRect(x + s*4, y + s*4, s*2, s*2);
			g.fillRect(x + s*5, y + s*5, s*2, s*2);
			g.fillRect(x + s*6, y + s*6, s*3, s*3);
			g.fillRect(x + s*5, y + s*8, s*1, s*1);
			g.fillRect(x + s*8, y + s*5, s*1, s*1);
			break;
		default:
			g.setColor(Color.BLACK);
			g.fillRect(x + s*2, y + s*2, s*6, s*6);
			g.setColor(Color.YELLOW);
			g.fillRect(x + s*3, y + s*3, s*4, s*4);
			break;
		}
		g.drawString(dir + "", x, y);
	}	
}
