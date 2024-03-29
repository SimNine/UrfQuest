package xyz.urffer.urfquest.client.guis;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import xyz.urffer.urfquest.client.Client;

public abstract class GUIObject {
	
	protected Client client;
	
	protected int xDisplacement, yDisplacement;
	protected GUIAnchor anchor;
	protected Rectangle bounds;
	protected GUIObject parent;
	
	protected GUIObject(Client c, GUIAnchor anchorPoint, int xRel, int yRel, int width, int height, GUIObject parent) {
		this.client = c;
		anchor = anchorPoint;
		xDisplacement = xRel;
		yDisplacement = yRel;
		bounds = new Rectangle();
		this.parent = parent;
		setBounds(xDisplacement, yDisplacement, width, height);
	}
	
	public boolean isMouseOver() {
		return bounds.contains(client.getPanel().mousePos.x, client.getPanel().mousePos.y);
	};
	
	public int xAnchor() {
		if (parent == null) {
			return 0;
		}
		
		switch (anchor) {
		case TOP_LEFT:
			return parent.getBounds().x;
		case TOP_RIGHT:
			return parent.getBounds().x + parent.getBounds().width;
		case BOTTOM_LEFT:
			return parent.getBounds().x;
		case BOTTOM_RIGHT:
			return parent.getBounds().x + parent.getBounds().width;
		case CENTER:
			return parent.getBounds().x + parent.getBounds().width/2;
		default:
			throw new IllegalStateException();
		}
	}
	
	public int yAnchor() {
		if (parent == null) {
			return 0;
		}
		
		switch (anchor) {
		case TOP_LEFT:
			return parent.getBounds().y;
		case TOP_RIGHT:
			return parent.getBounds().y;
		case BOTTOM_LEFT:
			return parent.getBounds().y + parent.getBounds().height;
		case BOTTOM_RIGHT:
			return parent.getBounds().y + parent.getBounds().height;
		case CENTER:
			return parent.getBounds().y + parent.getBounds().height/2;
		default:
			throw new IllegalStateException();
		}
	}
	
	public abstract void draw(Graphics g);
	
	protected void drawDebug(Graphics g) {
		drawBoundingBox(g);
		drawMousePositioning(g);
	}

	private void drawBoundingBox(Graphics g) {
		if (this.isMouseOver()) {
			g.setColor(Color.GREEN);
		} else {
			g.setColor(Color.BLUE);
		}
		
		g.drawRect(bounds.x, bounds.y, bounds.width, bounds.height);
	}
	
	private void drawMousePositioning(Graphics g) {
		g.setColor(Color.RED);
		if (this.isMouseOver()) {
			g.drawLine(bounds.x, client.getPanel().mousePos.y, bounds.x + bounds.width, client.getPanel().mousePos.y);
			g.drawLine(client.getPanel().mousePos.x, bounds.y, client.getPanel().mousePos.x, bounds.y + bounds.height);
		}
	}
	
	public Rectangle getBounds() {
		return bounds;
	}
	
	protected void setBounds(int xRel, int yRel, int width, int height) {
		bounds.setRect(xAnchor() + xRel, yAnchor() + yRel, width, height);
	}
	
	public void resetBounds() {
		bounds.setRect(xAnchor() + xDisplacement, yAnchor() + yDisplacement, bounds.width, bounds.height);
	}
}
