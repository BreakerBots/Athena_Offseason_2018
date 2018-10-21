package frc.team5104.main;

import frc.team5104.util.Units;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Trajectory.FitMethod;

public class RobotConstants {
	public static final double _robotLength = Units.inchesToFeet(32.0 + 4.0);
	public static final double _robotWidth = Units.inchesToFeet(28.0 + 4.0);

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
	
	// -- Autonomous
	public static final class Autonomous {
		//Trajectory Generation
		public static final double _maxVelocity = 5.1; 			  //(Feet) [Tune] in ft/s
		public static final double _maxAcceleration = 5.5; 		  //(Feet) [Tune] in ft/s/s
		public static final double _maxJerk = 60; 				  //(Feet) [Tune] in ft/s/s/s
		public static final FitMethod _fitMethod = Trajectory.FitMethod.HERMITE_CUBIC; //(Other) [Choose] What curve to Gen trajectory in (use Hermite Cubic)
		public static final int 	  _samples	 = Trajectory.Config.SAMPLES_HIGH; //(Other) [Choose] Affects generation speed and quality of Trajectory Generation
		public static final double 	  _dt 		 = Loops._robotHz; //(hz) delta time of Trajectory (time between each point)
		
		//Trajectory Folowing
		public static final double _tfB    = 0.5; //(None) [Tune/Choose] (Range: Great Than Zero) Increases/Decreases Correction
		public static final double _tfZeta = 0.4; //(None) [Tune/Choose] (Range: Zero to One) Increases/Decreases Dampening
	}
}