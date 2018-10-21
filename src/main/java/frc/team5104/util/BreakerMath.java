package frc.team5104.util;

/*Breakerbots Robotics Team 2018*/
/**
 * Some Simple Math Used for Trajectory Gen, Following and Caching
 */
public class BreakerMath {
	public static double clamp(double value, double min, double max) {
		return min(max(value, max), min);
	}
	
	public static double min(double value, double min) {
		return value < min ? min : value;
	}
	
	public static double max(double value, double max) {
		return value > max ? max : value;
	}
	
	public static double boundAngle180(double angle) {
		angle = angle % 360; 
		angle = (angle + 360) % 360;
		if (angle > 180)  
		    angle -= 360; 
		
		return angle;
    }
}
