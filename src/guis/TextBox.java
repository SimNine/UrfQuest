package guis;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import framework.UrfQuest;

public class TextBox extends TextArea {

	public TextBox(String text, int fontSize, int xDisplacement, int yDisplacement) {
		super(text, fontSize, xDisplacement, yDisplacement);
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.WHITE);
		g.setFont(new Font(Font.MONOSPACED, Font.BOLD, fontSize));
		g.drawString(this.text, UrfQuest.panel.dispCenterX + xDisplacement, UrfQuest.panel.dispCenterY + yDisplacement);
		
		if (UrfQuest.debug) this.drawBoundingBox(g);
	}

	@Override
	public void click() {}
	
}