package entities;

import java.awt.Color;
import java.awt.Graphics;

public abstract class Shape extends Entity {
	protected Color color;
	protected double diameter;

	public Shape(double x, double y, Color c, double diameter) {
		super(x, y);
		color = c;
		this.diameter = diameter;
	}

	@Override
	protected abstract void drawEntity(Graphics g);

	@Override
	public abstract void update();
	
	public Color getColor() {
		return color;
	}
	
	public double getDiameter() {
		return diameter;
	}

}
