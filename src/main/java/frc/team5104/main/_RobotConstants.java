package frc.team5104.main;

import frc.team5104.util.Units;

public class _RobotConstants {
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
}