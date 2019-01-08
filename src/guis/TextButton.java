package guis;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import framework.UrfQuest;

public class TextButton extends TextArea{
	
	public boolean clickable = true;

	public TextButton(String text, int fontSize, int xDisplacement, int yDisplacement) {
		super(text, fontSize, xDisplacement, yDisplacement);
	}
	
	public void click(){}
	
	public void draw(Graphics g) {
		g.setFont(new Font(Font.MONOSPACED, Font.BOLD, fontSize));
		
		if (this.isMouseOver()) g.setColor(Color.LIGHT_GRAY);
		else g.setColor(Color.WHITE);
		
		g.drawString(this.text, UrfQuest.panel.dispCenterX + xDisplacement, UrfQuest.panel.dispCenterY + yDisplacement);
		
		if (UrfQuest.debug) this.drawBoundingBox(g);
	}
	
}