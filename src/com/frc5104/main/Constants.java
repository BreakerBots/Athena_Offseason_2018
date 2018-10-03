package com.frc5104.main;

import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Trajectory.FitMethod;

/*Breakerbots Robotics Team 2018*/
/**
 * Constants used in Athena's Code
 * Units: ()
 *    - Feet
 *    - Degrees
 *    - Encoder Tick
 *    - Talon Current (TalCurrent)
 *    - Talon Percent Speed (TalSpeed)
 *    - Seconds
 *    - Milliseconds
 *    - herts (hz)
 *    - Other/None
 *    
 * Marks: []
 *    - Tune
 *    - Choose
 *    - Measure
 *    - Other/None
 */
public class Constants {
	
	//Robot Varibles (In Feet)
	public static final double _wheelDiameter = 0.5; 				  //(Feet) [Measure] The diameter of the wheels
	public static final double _ticksPerRevolution = 2500; //(Encoder Tick) [Measure] Encoder Ticks Per Wheel Revolution
	public static final double _wheelBaseWidth = 2.179;			  //(Feet) [Measure] The Distance from the Left and Right Wheels
	
	//Drive
	public static final class Drive {
		public static final double _rightAccount = 1; //(TalSpeed) [Measure] Multiple the right motor by (For Driving Straight)
		public static final double _leftAccount  = 0.94; //(TalSpeed) [Measure] Multiple the left  motor by (For Driving Straight)
		
		public static final double _gyroAngle = 65;   //(Degrees) [Measure] Yaw Angle of Gyro (Athena is 65)
		
		public static final double _rampSeconds			= 0.0; //(Seconds) [Tune/Choose]
		public static final int _currentLimitPeak		= 80;  //(Current) [Tune/Choose]
		public static final int _currentLimitPeakTime	= 10;  //(Milliseconds) [Tune/Choose]
		public static final int _currentLimitSustained	= 36;  //(Current) [Tune/Choose]
		
		public static final int _highPidId = 0;
		public static final double highDrivePidF = 1.00;
		public static final double highDrivePidP = 0.00;
		public static final double highDrivePidI = 0.00;
		public static final double highDrivePidD = 0.00;
		
		public static final int _lowPidId = 1;
		public static final double lowDrivePidF = 1.00;
		public static final double lowDrivePidP = 0.00;
		public static final double lowDrivePidI = 0.00;
		public static final double lowDrivePidD = 0.00;
	}
	
	// -- AutonomousWP (! Deprication Notice)
	public static final class AutonomousWP {
		public static final double _PIDA[] = { 1.0, 0.0, 0.0, 0 };//(None) [Tune] Speed
		public static final double _maxVelocity = 8.0; 			  //(Feet) [Tune] in ft/s
		public static final double _maxAcceleration = 4.0; 		  //(Feet) [Tune] in ft/s/s
		public static final double _maxJerk = 50; 				  //(Feet) [Tune] in ft/s/s/s
		public static final double _angleMult = 0.8;			  //(None) [None] Multiply the gyro angle value by this, keep from 0.6 - 0.8
		public static final double _xAngleMult = 4.0;			  //(None) [Tune] Multiple the x cordinate of this in each trajectory
	}
	
	// -- Autonomous
	public static final class Autonomous {
		//Trajectory Generation
		public static final double _maxVelocity = 5.0; 			  //(Feet) [Tune] in ft/s
		public static final double _maxAcceleration = 4.0; 		  //(Feet) [Tune] in ft/s/s
		public static final double _maxJerk = 100; 				  //(Feet) [Tune] in ft/s/s/s
		public static final FitMethod _fitMethod = Trajectory.FitMethod.HERMITE_CUBIC; //(Other) [Choose] What curve to Gen trajectory in (use Hermite Cubic)
		public static final int 	  _samples	 = Trajectory.Config.SAMPLES_HIGH; //(Other) [Choose] Affects generation speed and quality of Trajectory Generation
		public static final double 	  _dt 		 = Constants.Loops._robotHz; //(hz) delta time of Trajectory (time between each point)
		
		//Trajectory Folowing
		public static final double _tfB    = 0.5; //(None) [Tune/Choose] (Range: Great Than Zero) Increases/Decreases Correction
		public static final double _tfZeta = 0.4; //(None) [Tune/Choose] (Range: Zero to One) Increases/Decreases Dampening
	}
	
	// -- Squeezy
	public static final class Squeezy {
		public static final double _armsInSpeed = -0.6;	//(TalSpeed) [Choose/Tune]
		
		public static final double _wheelEjectSpeedLow = -0.3;	//(TalSpeed) [Choose]
		public static final double _wheelEjectSpeedMed = -0.6;	//(TalSpeed) [Choose]
		public static final double _wheelEjectSpeedHigh = -0.9;	//(TalSpeed) [Choose]
		public static final double _armsEjectSpeedLow = 0.6;	//(TalSpeed) [Choose]
		public static final double _armsEjectSpeedMed = 0.4;	//(TalSpeed) [Choose]
		public static final double _armsEjectSpeedHigh = 0.3;	//(TalSpeed) [Choose]
		public static final double _ejectTime = 500;			//(Milliseconds) [Choose]
		
		public static final double _armsOutSpeed      =  0.6;	//(TalSpeed) [Choose]
		public static final double _wheelIntakeSpeed  =  0.4;	//(TalSpeed) [Choose]
		
		public static final double _armsHoldSpeed     = -0.25;//(TalSpeed) [Choose]
		public static final double _wheelHoldSpeed    =  0.3;	//(TalSpeed) [Choose]
		public static final double _armsPhysicallyStoppedCurrent = 7; //(TalCurrent) [Tune]
	}
	
	// -- Elevator
	public static final class Elevator {
		public static final double _downScalar = 0.4;   //(TalSpeed) [Choose]
		public static final double _upScalar   = 0.6; 	//(TalSpeed) [Choose]
		
		public static final boolean _hardLimitSwitchesEnabled = true;  //Should use hard limit switches
		public static final boolean _softLimitSwitchesEnabled = false; //Should use soft limit switches (encoder values)
		
		public static final int _softStopBottom = 0;	//(Encoder Tick) [Choose/Tune] Look into Elevator.java
		public static final int _softStopTop = -16150;  //(Encoder Tick) [Choose/Tune] Look into Elevator.java
	}
	
	// -- Logging
	public static final class Logging {
		public static final boolean _SaveNonMatchLogs = false;
		public static final boolean _SaveMatchLogs = true;
	}
	
	// -- Looper
	public static final class Loops {
		public static final double _odometryHz = 100; //(hz) [Choose]
		public static final double _robotHz    = 50;  //(hz) [Choose]
	}
}
