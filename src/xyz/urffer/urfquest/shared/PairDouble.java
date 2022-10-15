package xyz.urffer.urfquest.shared;

public class PairDouble {
	
	public double x;
	public double y;
	
	public PairDouble(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public PairDouble add(PairDouble p) {
		return new PairDouble(this.x + p.x, this.y + p.y);
	}
	
	public PairDouble subtract(PairDouble p) {
		return new PairDouble(this.x - p.x, this.y - p.y);
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
	
	public PairInt toInt() {
		return new PairInt((int)this.x, (int)this.y);
	}
	
	public PairDouble clone() {
		return new PairDouble(this.x, this.y);
	}

}
