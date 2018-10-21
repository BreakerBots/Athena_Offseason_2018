package frc.team5104.subsystem.elevator;

import frc.team5104.subsystem.BreakerSubsystem;

class ElevatorConstants extends BreakerSubsystem.Constants {
	public static final double _downScalar = 0.6;   //(TalSpeed) [Choose]
	public static final double _upScalar = 1.0; 	//(TalSpeed) [Choose]
	
	public static final boolean _hardLimitSwitchesEnabled = true;  //Should use hard limit switches
	public static final boolean _softLimitSwitchesEnabled = false; //Should use soft limit switches (encoder values)
	
	public static final int _softStopBottom = 0;	//(Encoder Tick) [Choose/Tune]
	public static final int _softStopTop = -16150;  //(Encoder Tick) [Choose/Tune]
}
