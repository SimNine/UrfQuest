package xyz.urffer.urfquest.shared;

import java.io.Serializable;

public class PairDouble implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4400630006283145474L;
	public double x;
	public double y;
	
	public PairDouble(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public PairDouble add(PairDouble p) {
		return new PairDouble(this.x + p.x, this.y + p.y);
	}
	
	public PairDouble add(double d) {
		return new PairDouble(this.x + d, this.y + d);
	}
	
	public PairDouble subtract(PairDouble p) {
		return new PairDouble(this.x - p.x, this.y - p.y);
	}
	
	public PairDouble subtract(PairInt p) {
		return new PairDouble(this.x - p.x, this.y - p.y);
	}
	
	public PairDouble subtract(double n) {
		return new PairDouble(this.x - n, this.y - n);
	}
	
	public PairDouble multiply(PairDouble p) {
		return new PairDouble(this.x * p.x, this.y * p.y);
	}
	
	public PairDouble multiply(double n) {
		return new PairDouble(this.x * n, this.y * n);
	}
	
	public PairDouble divide(PairDouble p) {
		return new PairDouble(this.x / p.x, this.y / p.y);
	}
	
	public PairDouble divide(double n) {
		return new PairDouble(this.x / n, this.y / n);
	}
	
	public PairInt floor() {
		return new PairInt((int)Math.floor(this.x), (int)Math.floor(this.y));
	}
	
	public PairDouble mod(int n) {
		return new PairDouble(this.x % n, this.y % n);
	}
	
	public static PairDouble subtract(double n, PairDouble p) {
		return new PairDouble(n - p.x, n - p.y);
	}
	
	public PairInt toInt() {
		return new PairInt((int)this.x, (int)this.y);
	}
	
	
	
	public PairDouble clone() {
		return new PairDouble(this.x, this.y);
	}
	
	public String toString() {
		return "(" + x + "," + y + ")";
	}
	
	public boolean equals(PairDouble p) {
		return (p.x == this.x) && (p.y == this.y);
	}

}
