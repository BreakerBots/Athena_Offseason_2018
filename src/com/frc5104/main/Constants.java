package com.frc5104.main;

public class Constants {
	//Robot Varibles (In Imperial)
	public static double _wheelDiameter = 6; 				  //Inches
	public static double _ticksPerRevolution = 4698.25181344; //Encoder Ticks Per Wheel Rev
	public static double _wheelBaseWidth = 26.15;			  //The Distance from the Left and Right Wheels in Inches
	
	//Motion Profiling
	public static double _PIDA[] = { 5.0, 0.0, 0.0, 0 }; // Tuning Variables for the Robot
	public static double _maxVelocity = 2.2; 			 // The Max Velocity of Your Robot
	public static double _maxAcceleration = 3.0; 		 // The Max Acc of Your Robot
	public static double _maxJerk = 100; 				 // The Max Jerk of Your Robot
	public static double _angleMult = 0.8;				 //Keep from 0.6 - 0.8 MULTIPLYING
	public static double _gyroAngle = 65; 				 //If your gyro is a yaw (Athena 65 deg)
	
	//Squeezy
	public static class Squeezy {
		//Encoder
		public static final int HasCubePosition = -68000;
		
		//Opening/Closing Arms
		public static final double HoldEffort = -0.25;
		public static final double ShootSqueezeEffort = -0.05;
		public static final double CloseEffort = -0.30;
		public static final double OpenEffort  = 0.25;
		
		//Spinning Wheels
		public static final double RightSpinMultiplier = 1.1;
		public static final double IntakeEffort = -/*0.4*//*3-12-18 0.2*/0.2;
		public static final double PinchEffort = -0.2;
		public static double EjectEffort = 0.6;
	}
	
	//Elevator
	public static class Elevator {
		//Teleop Movement Speed
		public static final double DownScalar = 0.5;
		
		//Encoder
		public static final int SOFT_STOP_BOTTOM = 0;
		public static final int SOFT_STOP_TOP = -16150;
	}
}