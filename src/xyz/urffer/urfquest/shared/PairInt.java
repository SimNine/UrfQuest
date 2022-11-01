package xyz.urffer.urfquest.shared;

import java.io.Serializable;

public class PairInt implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8954555054152988508L;
	public int x;
	public int y;
	
	public PairInt(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public PairInt add(PairInt p) {
		return new PairInt(this.x + p.x, this.y + p.y);
	}
	
	public PairInt add(int n) {
		return new PairInt(this.x + n, this.y + n);
	}
	
	public PairInt subtract(PairInt p) {
		return new PairInt(this.x - p.x, this.y - p.y);
	}
	
	public PairInt subtract(int n) {
		return new PairInt(this.x - n, this.y - n);
	}
	
	public PairInt multiply(PairInt p) {
		return new PairInt(this.x * p.x, this.y * p.y);
	}
	
	public PairInt multiply(int n) {
		return new PairInt(this.x * n, this.y * n);
	}
	
	public PairInt divide(PairInt p) {
		return new PairInt(this.x / p.x, this.y / p.y);
	}
	
	public PairInt divide(int n) {
		return new PairInt(this.x / n, this.y / n);
	}
	
	public PairDouble dividePrecise(int n) {
		return new PairDouble(this.x / (double)n, this.y / (double)n);
	}
	
	public PairInt mod(int n) {
		return new PairInt(this.x % n, this.y % n);
	}
	
	public PairInt floorMod(int n) {
		return new PairInt(Math.floorMod(this.x, n), Math.floorMod(this.y, n));
	}
	
	public PairDouble toDouble() {
		return new PairDouble(this.x, this.y);
	}
	
	
	
	public static PairInt subtract(int n, PairInt p) {
		return new PairInt(n - p.x, n - p.y);
	}
	
	
	
	public PairInt clone() {
		return new PairInt(this.x, this.y);
	}
	
	public String toString() {
		return "(" + x + "," + y + ")";
	}
	
	public boolean equals(PairInt p) {
		return (p.x == this.x) && (p.y == this.y);
	}

}
