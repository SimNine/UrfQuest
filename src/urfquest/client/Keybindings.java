package urfquest.client;

import java.awt.event.KeyEvent;

public class Keybindings {

	public int CONSOLE = KeyEvent.VK_F2;
	public int FULLSCREEN = KeyEvent.VK_F12;
	public int CYCLE_MINIMAP = KeyEvent.VK_X;
	public int TOGGLEMAPVIEW = KeyEvent.VK_M;
	public int DROPITEM = KeyEvent.VK_Q;
	public int BUILDMODE = KeyEvent.VK_B;
	public int MAPLINK = KeyEvent.VK_ENTER;
	public int CRAFTING = KeyEvent.VK_C;
	public int CYCLE_DEBUG = KeyEvent.VK_F3;
	//public int CHAT = KeyEvent.VK_T;
	
	public String toString() {
		String ret = "CONSOLE: " + KeyEvent.getKeyText(CONSOLE) + "\n";
		ret += "FULLSCREEN: " + KeyEvent.getKeyText(FULLSCREEN) + "\n";
		ret += "CYCLE_MINIMAP: " + KeyEvent.getKeyText(CYCLE_MINIMAP) + "\n";
		ret += "TOGGLEMAPVIEW: " + KeyEvent.getKeyText(TOGGLEMAPVIEW) + "\n";
		ret += "DROPITEM: " + KeyEvent.getKeyText(DROPITEM) + "\n";
		ret += "BUILDMODE: " + KeyEvent.getKeyText(BUILDMODE) + "\n";
		ret += "MAPLINK: " + KeyEvent.getKeyText(MAPLINK) + "\n";
		ret += "CRAFTING: " + KeyEvent.getKeyText(CRAFTING) + "\n";
		ret += "CYCLE_DEBUG: " + KeyEvent.getKeyText(CRAFTING) + "\n";
		//ret += "CHAT: " + KeyEvent.getKeyText(CHAT) + "\n";
		return ret;
	}
}