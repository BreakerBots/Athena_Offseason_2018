package frc.team5104.util;

/*Breakerbots Robotics Team 2018*/
/**
 * Some basic unit conversions
 */
public class Units {
	
	// Wheels
	/**
	 * Calculates wheel circumfrance (in feet) from the wheel diameter
	 */
	public static double diameterToCircumfrance(double diameter) {
		return diameter * Math.PI;
	}
	
	// Ticks and Wheel Revs
	public static double ticksToWheelRevolutions(double ticks, double tpr) {
		return ticks / tpr;
	}
	public static double wheelRevolutionsToTicks(double revs, double tpr) {
		return revs * tpr;
	}
	
	// Feet and Wheel Revs
	public static double feetToWheelRevolutions(double feet, double wheelDiameter) {
		return feet / diameterToCircumfrance(wheelDiameter);
	}
	public static double wheelRevolutionsToFeet(double revs, double wheelDiameter) {
		return revs * diameterToCircumfrance(wheelDiameter);
	}
	
	// Feet and Ticks
	public static double ticksToFeet(double ticks, double tpr, double wheelDiameter) {
		double r = ticksToWheelRevolutions(ticks, tpr);
			   r = wheelRevolutionsToFeet(r, wheelDiameter);
		return r;
	}
	public static double feetToTicks(double feet, double tpr, double wheelDiameter) {
		double r = feetToWheelRevolutions(feet, wheelDiameter);
			   r = wheelRevolutionsToTicks(r, tpr);
		return r;
	}
	
	// feet/second and talon velocity (ticks/100ms)
	public static double talonVelToFeetPerSecond(double talonVel, double tpr, double wheelDiameter) {
		return ticksToFeet(talonVel, tpr, wheelDiameter) * 10.0;
	}
	public static double feetPerSecondToTalonVel(double feetPerSecond, double tpr, double wheelDiameter) {
		return feetToTicks(feetPerSecond, tpr, wheelDiameter) / 10.0;
	}
	
	// Feet and Inches
	public static double feetToInches(double feet) {
		return feet * 12.0;
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
