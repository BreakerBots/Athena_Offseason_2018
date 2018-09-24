package com.frc5104.math;

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
	
	public static double bound180(double a) {
		boolean b = a < 0;
		a = ((Math.abs(a) + 180) % 360) - 180;
		if (b && a != -180)
			a = -a;
        return a;
    }
}
