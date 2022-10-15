package xyz.urffer.urfquest.shared;

public class PairInt {
	
	public int x;
	public int y;
	
	public PairInt(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public PairInt add(PairInt p) {
		return new PairInt(this.x + p.x, this.y + p.y);
	}
	
	public PairInt subtract(PairInt p) {
		return new PairInt(this.x - p.x, this.y - p.y);
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
	
	public PairDouble toDouble() {
		return new PairDouble(this.x, this.y);
	}
	
	public PairInt clone() {
		return new PairInt(this.x, this.y);
	}

}
