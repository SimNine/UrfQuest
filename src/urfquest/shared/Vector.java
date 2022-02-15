package urfquest.shared;

import java.io.Serializable;

public class Vector implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6725819972455602153L;
	
	public static final double EAST = 0.0;
	public static final double SOUTHEAST = Math.PI/4.0;
	public static final double SOUTH = Math.PI/2.0;
	public static final double SOUTHWEST = Math.PI*3.0/4.0;
	public static final double WEST = Math.PI;
	public static final double NORTHWEST = Math.PI + SOUTHEAST;
	public static final double NORTH = Math.PI + SOUTH;
	public static final double NORTHEAST = Math.PI + SOUTHWEST;

	public double dirRadians;
	public double magnitude;
	
	public Vector(double dirRadians, double magnitude) {
		this.dirRadians = dirRadians;
		this.magnitude = magnitude;
	}
	
}
