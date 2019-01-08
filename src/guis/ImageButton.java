package guis;

import java.awt.Graphics;

import framework.UrfQuest;

public class ImageButton extends ImageArea {

	public ImageButton(String location, int scale, int xDisplacement, int yDisplacement) {
		super(location, scale, xDisplacement, yDisplacement);
	}

	@Override
	public void click() {}

	@Override
	public void draw(Graphics g) {
		g.drawImage(this.image,
					UrfQuest.panel.dispCenterX + xDisplacement,
					UrfQuest.panel.dispCenterY + yDisplacement - this.image.getHeight(), 
					null);
		
		if (UrfQuest.debug) this.drawBoundingBox(g);
	}

}