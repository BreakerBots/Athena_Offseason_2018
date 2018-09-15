package com.frc5104.utilities;

/*Breakerbots Robotics Team 2018*/
/**
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

	static final Deadband default_01 = new Deadband(0.1);
	
	public static Deadband getDefault() {
		return default_01;
	}
	
	private boolean inverted = false;
	
	private double radius;
	private double m, b;
	
	public Deadband (double radius) {
		this.radius = radius;
		
		//m = (y2-y1)/(x2-x1)
		//b = -m*radius
		m = (1-0) / (1-radius);
		b = 0 - m*radius;
		
	}
	
	public double get(double x) {
		double output = 0;
		
		if (inverted) x *= -1;
		
		if (x > radius)
			output = m*x+b;
		else if (x < -radius)
			output = m*x-b;
			
		return output;
	}
	
	public void setInverted (boolean invert) {
		inverted = invert;
	}
	
}
