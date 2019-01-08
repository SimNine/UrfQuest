package guis;

import java.awt.Graphics;

import framework.UrfQuest;

public class ImageBox extends ImageArea {
	
	public ImageBox(String source, int scale, int xDisplacement, int yDisplacement) {
		super(source, scale, xDisplacement, yDisplacement);
	}

	public void click() {}

	public void draw(Graphics g) {
		g.drawImage(this.image,
					UrfQuest.panel.dispCenterX + xDisplacement,
					UrfQuest.panel.dispCenterY + yDisplacement - this.image.getHeight(),
					null);
		
		if (UrfQuest.debug) this.drawBoundingBox(g);
	}

}