package com.frc5104.main;

/**
 * All Constants used in Athena's Code 
 */
/*Breakerbots Robotics Team 2018*/
public class Constants {
	//Robot Varibles (In Feet)
	public static double _wheelDiameter = 6 / 12; 			  //
	public static double _ticksPerRevolution = 4698.25181344; //Encoder Ticks Per Wheel Rev
	public static double _wheelBaseWidth = 26.15 / 12;		  //The Distance from the Left and Right Wheels in Inches
	
	//Motion Profiling
	public static double _PIDA[] = { 5.0, 0.0, 0.0, 0 };// Tuning Variables for the Robot
	public static double _maxVelocity = 5.015; 			 // The Max Velocity of Your Robot (ft/s)
	public static double _maxAcceleration = 8.003; 		 // The Max Acc of Your Robot (ft/s/s)
	public static double _maxJerk = 100; 				 // The Max Jerk of Your Robot (ft/s/s/s but arbitrary value)
	public static double _angleMult = 0.8;				 //Keep from 0.6 - 0.8 MULTIPLYING
	public static double _gyroAngle = 65; 				 //If your gyro is at a yaw (Athena 65 deg)
	
	//Squeezy
	public static class Squeezy {
		public static double _armsInSpeed       = -0.6;
		
		public static double _wheelEjectSpeed   = -0.8;
		public static double _armsOutEjectSpeed = -0.1;
		public static double _ejectTime = 500;
		
		public static double _armsOutSpeed      = 0.6;
		public static double _wheelIntakeSpeed  = 0.4;
		
		public static double _armsHoldSpeed     = -0.15;
		public static double _wheelHoldSpeed    = 0.1;
		public static double _armsPhysicallyStoppedCurrent = 10;
	}
	
	//Elevator
	public static class Elevator {
		//Teleop Movement Speed
		public static final double _downScalar = 0.5;
		
		//Encoder
		public static final int _softStopBottom = 0;
		public static final int _softStopTop = -16150;
	}
	
	//Logging
	public static class Logging {
		public static boolean _SaveNonMatchLogs = false;
		public static boolean _SaveMatchLogs = true;
	}
}
