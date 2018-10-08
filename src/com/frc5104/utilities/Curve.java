package com.frc5104.utilities;
/**
 * <h1>Curve</h1>
 * A simple static class for processesing curves. <br>
 * <a href="https://www.desmos.com/calculator/fojynjbgnz">Basic Curve Desmos Link</a> <br>
 * <a href="https://www.desmos.com/calculator/da8zwxpgzo">Bezier Curve Desmos Link</a>
 */
public class Curve {
	
	/**
	 * <h1>Bezier Curve</h1>
	 * Takes in a percent[0-1] and outputs a processed percent[0-1] (from the corresponding point along the bezier curve)
	 * <a href="https://www.desmos.com/calculator/da8zwxpgzo">Desmos Link</a>
	 * @param t The input percent[0-1]
	 * @param x1 The x of Point 1 on the bezier curve
	 * @param y1 The y of Point 1 on the bezier curve
	 * @param x2 The x of Point 2 on the bezier curve 
	 * @param y2 The y of Point 2 on the bezier curve
	 * @return The processed[0-1] percent (from the corresponding point along the bezier curve)
	 */
	public static double getBezierCurve(double t, double x1, double y1, double x2, double y2) {
		BezierCurve bezierCurve = new BezierCurve(x1, y1, x2, y2);
		return bezierCurve.getPoint(t);
	}
	/**
	 * <h1>Bezier Curve</h1>
	 * Takes in a percent[0-1] and outputs a processed percent[0-1] (from the corresponding point along the bezier curve)
	 * <a href="https://www.desmos.com/calculator/da8zwxpgzo">Desmos Link</a>
	 * @param t The input percent[0-1]
	 * @param bezierCurve The curve to process
	 * @return The processed[0-1] percent (from the corresponding point along the bezier curve)
	 */
	public static double getBezierCurve(double t, BezierCurve bezierCurve) {
		return bezierCurve.getPoint(t);
	}
	
	public static class BezierCurve {
		double x1, y1, x2, y2;
		
		public BezierCurve(double x1, double y1, double x2, double y2) {
			this.x1 = x1;
			this.y1 = y1;
			this.x2 = x2;
			this.y2 = y2;
		}
		
		private double p(double t, double a, double b, double c) {
			return 
			(
				(1-t) *
				( (1-t) * a + (t * b) ) 
			)
			+ 
			(
				t *
				( (1-t) * b + (t * c) )
			);
		}
		
		/**
		 * Gets point along the curve at (t) percent[0-1]
		 * @param t Percent point along curve [0-1]
		 */
		public double getPoint(double t) {
			double x0 = 0.0;
			double y0 = 0.0;
			double x3 = 1.0;
			double y3 = 1.0;
			
			t = (1-t);
			t = 1 - ( ((1-t) * p(t, x0, x1, x2)) + (t * p(t, x1, x2, x3)) );
			t =	( ((1-t) * p(t, y0, y1, y2)) + (t * p(t, y1, y2, y3)) );
			
			return t;
		}
	}
	
	/**
	 * <h1>Basic Curves</h1>
	 * Takes in a percent[0-1] and outputs a processed percent[0-1] (from the corresponding point along the curve)
	 * <a href="https://www.desmos.com/calculator/fojynjbgnz">Desmos Link</a>
	 * <br>
	 * @param x The input percent[0-1]
	 * @param overArch If the curve should be over or under the linear line
	 * @param c The percent[0-1] curvature among the curve.
	 * @returns The processed[0-1] percent (from the corresponding point along the curve)
	 */
	public static double getBasicCurve(double x, boolean overArch, double c) {
		c = Math.pow((c * ((1-0.4)/10) + 0.4), 7);
		if (overArch)
			return Math.pow(x, c);
		else
			return Math.pow(x, 1/c);
	}
}
