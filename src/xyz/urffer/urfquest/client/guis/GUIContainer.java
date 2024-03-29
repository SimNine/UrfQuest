package xyz.urffer.urfquest.client.guis;

import java.awt.Color;
import java.awt.Graphics;
import java.util.HashSet;
import java.util.Set;

import xyz.urffer.urfquest.LogLevel;
import xyz.urffer.urfquest.Logger;
import xyz.urffer.urfquest.client.Client;

public class GUIContainer extends GUIObject implements Clickable {

	protected Set<GUIObject> guiObjects = new HashSet<GUIObject>();
	private Color bkg;
	private Color borderColor;
	private int borderThickness;
	private String name;
	
	public GUIContainer(Client c, GUIAnchor anchorPoint, int xRel, int yRel, int width, 
						int height, String name, 
						GUIObject parent, Color bkg, Color borderColor, int borderThickness) {
		super(c, anchorPoint, xRel, yRel, width, height, parent);
		this.name = name;
		this.bkg = bkg;
		this.borderColor = borderColor;
		this.borderThickness = borderThickness;
	}
	
	public void draw(Graphics g) {
		if (bkg != null) {
			g.setColor(bkg);
			g.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
		}
		if (borderColor != null) {
			g.setColor(borderColor);
			for (int i = 0; i < borderThickness; i++) {
				g.drawRect(bounds.x + i, bounds.y + i, bounds.width - 2*i, bounds.height - 2*i);
			}
		}
		for (GUIObject o : guiObjects) {
			o.draw(g);
		}
		if (this.client.getLogger().getLogLevel().compareTo(LogLevel.DEBUG) >= 0) {
			this.drawDebug(g);
		}
	}
	
	public boolean click() { // returns true if an object in this container was clicked
		boolean ret = false;
		for (GUIObject o : guiObjects) {
			if (o instanceof Clickable && o.isMouseOver()) {
				((Clickable) o).click();
				ret = true;
				this.client.getLogger().debug("GUIObject " + o.getClass().getSimpleName() + " clicked");
			}
		}
		return ret;
	}

	public void resetBounds() {
		super.resetBounds();
		
		if (parent == null) {
			this.setBounds(0, 0, this.client.getPanel().getWidth(), this.client.getPanel().getHeight());
		}
		
		for (GUIObject o : guiObjects) {
			o.resetBounds();
		}
	}
	
	public String getName() {
		return name;
	}
	
	public Color getBkg() {
		return bkg;
	}
	
	public void setBkg(Color bkg) {
		this.bkg = bkg;
	}
	
	public Color getBorderCol() {
		return this.borderColor;
	}
	
	public void setBorderCol(Color c) {
		this.borderColor = c;
	}
	
	public void addObject(GUIObject o) {
		guiObjects.add(o);
	}
	
	public void addAllObjects(Set<GUIObject> o) {
		guiObjects.addAll(o);
	}
	
	public void removeAllObjects() {
		guiObjects.clear();
	}
}
