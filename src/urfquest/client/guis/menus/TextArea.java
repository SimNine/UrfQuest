package urfquest.client.guis.menus;

import urfquest.client.guis.GUIContainer;
import urfquest.client.guis.GUIObject;

public abstract class TextArea extends GUIObject {
	
	protected String text;
	protected int fontSize;
	
	public TextArea(String text, int fontSize, int xDisp, int yDisp, int anchor, GUIContainer parent) {
		super(anchor, xDisp, yDisp, (int)(((9.0/15.0)*fontSize)*text.length()), (int)(fontSize*(4.0/5.0)), parent);
		this.text = text;
		this.fontSize = fontSize;
	}

}