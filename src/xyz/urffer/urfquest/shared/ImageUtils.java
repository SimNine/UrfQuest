package xyz.urffer.urfquest.shared;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import xyz.urffer.urfquest.Main;

public class ImageUtils {
	public static String errMsg = "Could not find image: ";
	
	public static BufferedImage loadImage(String s) {
		try {
			return ImageIO.read(Main.self.getClass().getResourceAsStream(s));
		} catch (IOException | IllegalArgumentException e) {
			Main.mainLogger.error("Tile image \"" + s + "\" was unable to be loaded");
			//e.printStackTrace();
		}
		return null;
	}
}
