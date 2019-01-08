package framework;

import java.awt.event.KeyEvent;

public class Keybindings {

	public int CONSOLE = KeyEvent.VK_F2;
	public int FULLSCREEN = KeyEvent.VK_F12;
	public int CYCLEMINIMAP = KeyEvent.VK_X;
	public int TOGGLEMAPVIEW = KeyEvent.VK_M;
	public int DROPITEM = KeyEvent.VK_Q;
	public int BUILDMODE = KeyEvent.VK_B;
	
	public String toString() {
		String ret = "CONSOLE: " + KeyEvent.getKeyText(CONSOLE) + "\n";
		ret += "FULLSCREEN: " + KeyEvent.getKeyText(FULLSCREEN) + "\n";
		ret += "CYCLEMINIMAP: " + KeyEvent.getKeyText(CYCLEMINIMAP) + "\n";
		ret += "TOGGLEMAPVIEW: " + KeyEvent.getKeyText(TOGGLEMAPVIEW) + "\n";
		ret += "DROPITEM: " + KeyEvent.getKeyText(DROPITEM) + "\n";
		ret += "BUILDMODE: " + KeyEvent.getKeyText(BUILDMODE) + "\n";
		return ret;
	}
}