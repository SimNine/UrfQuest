package guis.menus;

public abstract class KeybindingButton extends TextButton {
	private boolean waiting = false;

	public KeybindingButton(String text, int fontSize, int xDisplacement, int yDisplacement, int anchor) {
		super(text, fontSize, xDisplacement, yDisplacement, anchor);
	}
	
	public void click() {
		if (!waiting) {
			text = "press a key";
			waiting = true;
		}
	}
	
	public void setWaiting(boolean w) {
		waiting = w;
	}
	
	public abstract void setKey(int s);
}