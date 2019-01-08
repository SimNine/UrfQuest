package guis;

import java.awt.Color;
import java.awt.Graphics;
import java.util.HashSet;
import java.util.Set;

import framework.UrfQuest;

public class Overlay {

	private String name;
	private Color bkg;
	protected Set<GUIObject> objects;
	
	public Overlay(String name, Color bkg) {
		this.name = name;
		this.bkg = bkg;
		this.objects = new HashSet<GUIObject>();
	}

	public Overlay(String name, Color bkg, Set<GUIObject> objects) {
		this.name = name;
		this.bkg = bkg;
		this.objects = objects;
	}
	
	public void click() {
		for (GUIObject o : objects) {
			if (o.isMouseOver()) o.click();
		}
	}
	
	public void draw(Graphics g) {
		g.setColor(bkg);
		g.fillRect(0, 0, UrfQuest.panel.getWidth(), UrfQuest.panel.getHeight());
		for (GUIObject o : objects) {
			o.draw(g);
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
	
}