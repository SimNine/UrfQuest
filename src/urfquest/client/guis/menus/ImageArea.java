package urfquest.client.guis.menus;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import urfquest.client.Client;
import urfquest.client.guis.GUIAnchor;
import urfquest.client.guis.GUIContainer;
import urfquest.client.guis.GUIObject;

public abstract class ImageArea extends GUIObject {
	protected BufferedImage image;
	
	public ImageArea(Client c, String source, int xDisp, int yDisp, GUIAnchor anchor, GUIContainer parent) {
		super(c, anchor, xDisp, yDisp, 0, 0, parent);
		try {
			this.image = ImageIO.read(new File(source));
		} catch (IOException e) {
			e.printStackTrace();
			this.client.getLogger().error("Image could not be read at: " + source);
		}
		
		this.setBounds(this.xDisplacement, this.yDisplacement, image.getWidth(), image.getHeight());
	}
}