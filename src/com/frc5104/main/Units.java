package com.frc5104.main;

/*Breakerbots Robotics Team 2018*/
/**
 * Some basic unit conversions
 */
public class Units {
	
	// Wheels
	/**
	 * Calculates wheel circumfrance (in feet) from the wheel diameter
	 */
	public static double wheelCircum() {
		return Constants._wheelDiameter * Math.PI;
	}
	
	// Ticks and Wheel Revs
	public static double ticksToWheelRevolutions(double ticks) {
		return ticks / Constants._ticksPerRevolution;
	}
	public static double wheelRevolutionsToTicks(double revs) {
		return revs * Constants._ticksPerRevolution;
	}
	
	// Feet and Wheel Revs
	public static double feetToWheelRevolutions(double feet) {
		return feet / wheelCircum();
	}
	public static double wheelRevolutionsToFeet(double revs) {
		return revs * wheelCircum();
	}
	
	// Feet and Ticks
	public static double ticksToFeet(double ticks) {
		double r = ticksToWheelRevolutions(ticks);
			   r = wheelRevolutionsToFeet(r);
		return r;
	}
	public static double feetToTicks(double feet) {
		double r = feetToWheelRevolutions(feet);
			   r = wheelRevolutionsToTicks(r);
		return r;
	}
	
	// feet/second and talon velocity (ticks/100ms)
	public static double talonVelToFeetPerSecond(double talonVel) {
		return ticksToFeet(talonVel) * 10;
	}
	public static double feetPerSecondToTalonVel(double feetPerSecond) {
		return feetToTicks(feetPerSecond) / 10;
	}
	
	// Feet and Inches
	public static double feetToInches(double feet) {
		return feet  * 12.0;
	}
	public static double inchesToFeet(double inches) {
		return inches / 12.0;
	}
	
	
	// Meters and Feet
	public static double metersToFeet(double meters) {
		return meters * 3.2808;
	}
	public static double feetToMeters(double feet) {
		return feet / 3.2808;
	}
	
	
	// Radians and Degrees
	public static double degreesToRadians(double deg) {
		return Math.toRadians(deg);
	}
	public static double radiansToDegress(double radians) {
		return Math.toDegrees(radians);
	}
}
