package guis;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public abstract class ImageArea extends GUIObject {
	
	protected BufferedImage image;
	int scale;
	
	public ImageArea(String location, int scale, int xDisplacement, int yDisplacement) {
		try {
			this.image = ImageIO.read(new File(location));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Image could not be read at: " + location);
		}
		
		this.scale = scale;
		this.xDisplacement = xDisplacement;
		this.yDisplacement = yDisplacement;
		this.width = image.getWidth();
		this.height = image.getHeight();
	}
	
}