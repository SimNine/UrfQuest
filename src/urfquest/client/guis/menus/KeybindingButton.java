package urfquest.client.guis.menus;

import urfquest.client.guis.GUIContainer;

public abstract class KeybindingButton extends TextButton {
	private boolean waiting = false;

	public KeybindingButton(String text, int fontSize, int xDisplacement, int yDisplacement, int anchor, GUIContainer parent) {
		super(text, fontSize, xDisplacement, yDisplacement, anchor, parent);
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