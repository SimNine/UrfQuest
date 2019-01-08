package guis.menus;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import guis.GUIObject;

public abstract class ImageArea extends GUIObject {
	
	protected BufferedImage image;
	
	public ImageArea(String source, int xDisp, int yDisp, int anchor) {
		super(anchor, xDisp, yDisp, 0, 0);
		try {
			this.image = ImageIO.read(new File(source));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Image could not be read at: " + source);
		}
		
		this.xDisplacement = xDisp;
		this.yDisplacement = yDisp;
		
		this.setBounds(xDisp, yDisp, image.getWidth(), image.getHeight());
	}
}