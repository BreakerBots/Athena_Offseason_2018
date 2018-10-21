package frc.team5104.subsystem.squeezy;

import frc.team5104.subsystem.BreakerSubsystem;

class SqueezyConstants extends BreakerSubsystem.Constants {
	public static final double _armsInSpeed = -0.6;	//(TalSpeed) [Choose/Tune]
	
	public static final double _wheelEjectSpeedLow = -0.3;	//(TalSpeed) [Choose]
	public static final double _wheelEjectSpeedMed = -0.6;	//(TalSpeed) [Choose]
	public static final double _wheelEjectSpeedHigh = -0.9;	//(TalSpeed) [Choose]
	public static final double _armsEjectSpeedLow = 0.6;	//(TalSpeed) [Choose]
	public static final double _armsEjectSpeedMed = 0.4;	//(TalSpeed) [Choose]
	public static final double _armsEjectSpeedHigh = 0.3;	//(TalSpeed) [Choose]
	public static final double _ejectTime = 500;			//(Milliseconds) [Choose]
	
	public static final double _armsOutSpeed      =  0.6;	//(TalSpeed) [Choose]
	public static final double _wheelIntakeSpeed  =  0.3;	//(TalSpeed) [Choose]
	
	public static final double _armsHoldSpeed     = -0.3;//(TalSpeed) [Choose]
	public static final double _wheelHoldSpeed    =  0.3;	//(TalSpeed) [Choose]
	public static final double _armsPhysicallyStoppedCurrent = 7; //(TalCurrent) [Tune]
}
