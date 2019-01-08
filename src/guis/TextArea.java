package guis;

public abstract class TextArea extends GUIObject {
	
	protected String text;
	protected int fontSize;
	
	public TextArea(String text, int fontSize, int xDisplacement, int yDisplacement) {
		this.text = text;
		this.fontSize = fontSize;
		this.xDisplacement = xDisplacement;
		this.yDisplacement = yDisplacement;
		this.width = (int)(((9.0/15.0)*fontSize)*this.text.length());
		this.height = (int)(this.fontSize*(4.0/5.0));
	}

}