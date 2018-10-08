package com.frc5104.utilities;

/*Breakerbots Robotics Team 2018*/
/**
 * <h1>Deadband</h1>
 * A deadband is a mathmatical process to stop input from the center of the joystick.
 * The xbox controller can get stuck slightly off of 0 causing the robot to move slowly on it's own
 * Most deadbands like WPI's and the TalonSRX's just clip off numbers < ~0.05.
 * This sometimes doesn't account for the range in which the joystick can get stuck and may prevent moving the robot a speeds like 0.05.
 * THIS CLASS uses a deadband system that basically, instead of clipping, pushes the values away.
 * A graph of "x" input, "y" output would be like so:
 *  \
 *   \
 *    -----
 *         \
 *          \
 */
public class Deadband {
	/**
	 * Calculates a deadband upon "x" with the "radius"
	 * @param x The number to calculate on
	 * @param radius the Radius of the deadband
	 * @return The calculated value upon "x"
	 */
	public static double get(double x, double radius) {
		//m = (y2-y1)/(x2-x1)
		//b = -m*radius
		double m = (1-0) / (1-radius);
		double b = 0 - m*radius;
		
		double output = 0;
		
		if (x > radius)
			output = m*x+b;
		else if (x < -radius)
			output = m*x-b;
			
		return output;
	}
	
	/**
	 * This is not an inverted deadband, but instead has calculates a fix for a clipping deadband
	 */
	public static double getReverse(double x, double radius) {
		return get(x, -radius) - radius;
	}
}
