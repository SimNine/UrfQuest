package guis;

import java.awt.Color;
import java.awt.Graphics;
import java.util.HashSet;
import java.util.Set;

import framework.UrfQuest;

public class Overlay implements Comparable<Overlay> {

	private String name;
	private Color bkg;
	protected Set<GUIObject> guiObjects;
	
	public Overlay(String name) {
		this(name, null);
	}
	
	public Overlay(String name, Color bkg) {
		this(name, bkg, new HashSet<GUIObject>());
	}

	public Overlay(String name, Color bkg, Set<GUIObject> objects) {
		this.name = name;
		this.bkg = bkg;
		this.guiObjects = objects;
	}
	
	public boolean click() { // returns true if an object in this overlay was clicked
		boolean ret = false;
		for (GUIObject o : guiObjects) {
			if (o instanceof Clickable && o.isMouseOver()) {
				((Clickable) o).click();
				ret = true;
				if (UrfQuest.debug) {
					System.out.println("GUIObject " + o.getClass().getSimpleName() + " clicked");
				}
			}
		}
		return ret;
	}
	
	public void draw(Graphics g) {
		if (bkg != null) {
			g.setColor(bkg);
			g.fillRect(0, 0, UrfQuest.panel.getWidth(), UrfQuest.panel.getHeight());
		}
		for (GUIObject o : guiObjects) {
			o.draw(g);
		}
	}
	
	public void resetObjectBounds() {
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
	
	public int compareTo(Overlay o) {
		return name.compareTo(o.name);
	}
	
}