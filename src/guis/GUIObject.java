package guis;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import framework.UrfQuest;

public abstract class GUIObject {
	protected int xDisplacement, yDisplacement;
	public static final int TOP_LEFT = 2010;
	public static final int TOP_RIGHT = 2015;
	public static final int BOTTOM_LEFT = 2020;
	public static final int BOTTOM_RIGHT = 2025;
	public static final int CENTER = 2030;
	protected int anchor;
	protected Rectangle bounds;
	
	protected GUIObject(int anchorPoint, int xRel, int yRel, int width, int height) {
		anchor = anchorPoint;
		xDisplacement = xRel;
		yDisplacement = yRel;
		bounds = new Rectangle();
		setBounds(xDisplacement, yDisplacement, width, height);
	}
	
	public boolean isMouseOver() {
		return bounds.contains(UrfQuest.mousePos[0], UrfQuest.mousePos[1]);
	};
	
	public int xAnchor() {
		switch (anchor) {
		case TOP_LEFT:
			return 0;
		case TOP_RIGHT:
			return UrfQuest.panel.getWidth();
		case BOTTOM_LEFT:
			return 0;
		case BOTTOM_RIGHT:
			return UrfQuest.panel.getWidth();
		case CENTER:
			return UrfQuest.panel.dispCenterX;
		default:
			throw new IllegalStateException();
		}
	}
	
	public int yAnchor() {
		switch (anchor) {
		case TOP_LEFT:
			return 0;
		case TOP_RIGHT:
			return 0;
		case BOTTOM_LEFT:
			return UrfQuest.panel.getHeight();
		case BOTTOM_RIGHT:
			return UrfQuest.panel.getHeight();
		case CENTER:
			return UrfQuest.panel.dispCenterY;
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
			g.drawLine(bounds.x, UrfQuest.mousePos[1], bounds.x + bounds.width, UrfQuest.mousePos[1]);
			g.drawLine(UrfQuest.mousePos[0], bounds.y, UrfQuest.mousePos[0], bounds.y + bounds.height);
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
