package xyz.urffer.urfquest.client.guis.menus;

import xyz.urffer.urfquest.client.Client;
import xyz.urffer.urfquest.client.guis.GUIAnchor;
import xyz.urffer.urfquest.client.guis.GUIContainer;

public abstract class KeybindingButton extends TextButton {
	private boolean waiting = false;

	public KeybindingButton(Client c, String text, int fontSize, int xDisplacement, int yDisplacement, GUIAnchor anchor, GUIContainer parent) {
		super(c, text, fontSize, xDisplacement, yDisplacement, anchor, parent);
	}
	
	public boolean click() {
		if (!waiting) {
			text = "press a key";
			waiting = true;
			return true;
		} else {
			return false;
		}
	}
	
	public void setWaiting(boolean w) {
		waiting = w;
	}
	
	public abstract void setKey(int s);
}