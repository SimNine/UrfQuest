package xyz.urffer.urfquest.client.guis.menus;

import xyz.urffer.urfquest.client.Client;
import xyz.urffer.urfquest.client.guis.GUIAnchor;
import xyz.urffer.urfquest.client.guis.GUIContainer;
import xyz.urffer.urfquest.client.guis.GUIObject;

public abstract class TextArea extends GUIObject {
	
	protected String text;
	protected int fontSize;
	
	public TextArea(Client c, String text, int fontSize, int xDisp, int yDisp, GUIAnchor anchor, GUIContainer parent) {
		super(c, anchor, xDisp, yDisp, (int)(((9.0/15.0)*fontSize)*text.length()), (int)(fontSize*(4.0/5.0)), parent);
		this.text = text;
		this.fontSize = fontSize;
	}

}