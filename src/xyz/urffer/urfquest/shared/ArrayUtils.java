package xyz.urffer.urfquest.shared;

public class ArrayUtils {
	
	/*
	 * int array utilities
	 */
	
	public static int[] castToIntArr(double[] a) {
		int[] ret = new int[a.length];
		
		for (int i = 0; i < ret.length; i++) {
			ret[i] = (int)a[i];
		}
		
		return ret;
	}
	
	public static int[] add(int[] a, int[] b) {
		assert(a.length == b.length): "Arrays are different lengths";
		
		int[] ret = new int[a.length];
		
		for (int i = 0; i < ret.length; i++) {
			ret[i] = a[i] + b[i];
		}
		
		return ret;	
	}
	
	public static int[] subtract(int[] a, int[] b) {
		assert(a.length == b.length): "Arrays are different lengths";
		
		int[] ret = new int[a.length];
		
		for (int i = 0; i < ret.length; i++) {
			ret[i] = a[i] - b[i];
		}
		
		return ret;	
	}
	
	public static int[] multiply(int[] a, int[] b) {
		assert(a.length == b.length): "Arrays are different lengths";
		
		int[] ret = new int[a.length];
		
		for (int i = 0; i < ret.length; i++) {
			ret[i] = a[i] * b[i];
		}
		
		return ret;	
	}
	
	public static int[] multiply(int[] a, int mult) {
		int[] ret = new int[a.length];
		
		for (int i = 0; i < ret.length; i++) {
			ret[i] = a[i] * mult;
		}
		
		return ret;	
	}
	
	public static int[] divide(int[] a, int[] b) {
		assert(a.length == b.length): "Arrays are different lengths";
		
		int[] ret = new int[a.length];
		
		for (int i = 0; i < ret.length; i++) {
			ret[i] = a[i] / b[i];
		}
		
		return ret;	
	}
	
	public static int[] divide(int[] a, int div) {
		int[] ret = new int[a.length];
		
		for (int i = 0; i < ret.length; i++) {
			ret[i] = a[i] / div;
		}
		
		return ret;	
	}
	
	/*
	 * double array utilities
	 */
	
	public static double[] castToDoubleArr(int[] a) {
		double[] ret = new double[a.length];
		
		for (int i = 0; i < ret.length; i++) {
			ret[i] = (double)a[i];
		}
		
		return ret;
	}
	
	public static double[] add(double[] a, double[] b) {
		assert(a.length == b.length): "Arrays are different lengths";
		
		double[] ret = new double[a.length];
		
		for (int i = 0; i < ret.length; i++) {
			ret[i] = a[i] + b[i];
		}
		
		return ret;	
	}
	
	public static double[] subtract(double[] a, double[] b) {
		assert(a.length == b.length): "Arrays are different lengths";
		
		double[] ret = new double[a.length];
		
		for (int i = 0; i < ret.length; i++) {
			ret[i] = a[i] - b[i];
		}
		
		return ret;	
	}
	
	public static double[] multiply(double[] a, double[] b) {
		assert(a.length == b.length): "Arrays are different lengths";
		
		double[] ret = new double[a.length];
		
		for (int i = 0; i < ret.length; i++) {
			ret[i] = a[i] * b[i];
		}
		
		return ret;	
	}
	
	public static double[] multiply(double[] a, double mult) {
		double[] ret = new double[a.length];
		
		for (int i = 0; i < ret.length; i++) {
			ret[i] = a[i] * mult;
		}
		
		return ret;	
	}
	
	public static double[] divide(double[] a, double[] b) {
		assert(a.length == b.length): "Arrays are different lengths";
		
		double[] ret = new double[a.length];
		
		for (int i = 0; i < ret.length; i++) {
			ret[i] = a[i] / b[i];
		}
		
		return ret;	
	}
	
	public static double[] divide(double[] a, double div) {
		double[] ret = new double[a.length];
		
		for (int i = 0; i < ret.length; i++) {
			ret[i] = a[i] / div;
		}
		
		return ret;	
	}
	
	public static int[] floor(double[] a) {
		int[] ret = new int[a.length];
		
		for (int i = 0; i < ret.length; i++) {
			ret[i] = (int)Math.floor(a[i]);
		}
		
		return ret;	
	}
	
}
