package xyz.urffer.urfquest.shared;

import java.awt.Graphics2D;
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
		}
		return null;
	}
	
	public static BufferedImage flipImage(final BufferedImage image, boolean horizontal, boolean vertical) {
        int x = 0;
        int y = 0;
        int w = image.getWidth();
        int h = image.getHeight();

        final BufferedImage out = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        final Graphics2D g2d = out.createGraphics();

        if (horizontal) {
            x = w;
            w *= -1;
        }

        if (vertical) {
            y = h;
            h *= -1;
        }

        g2d.drawImage(image, x, y, w, h, null);
        g2d.dispose();

        return out;
    }
}
