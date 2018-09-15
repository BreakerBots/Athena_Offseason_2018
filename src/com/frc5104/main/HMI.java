package com.frc5104.main;

import com.frc5104.utilities.ControllerHandler.Control;

/*Breakerbots Robotics Team 2018*/
/**
 * All Controls used in Athena's Code
 */
public class HMI {

	//Drive
	public static final Control kDriveX = Control.LX;		//Left  Stick
	public static final Control kDriveY = Control.LY;		//Right Stick
	public static final Control kDriveShift = Control.RT;	//Right Trigger
	
	//Elevator
	public static final Control kElevatorUpDown = Control.RY; //Right Stick

	//Climbing
	public static final Control kPtoHoldAndHookPressButton = Control.Y; //0.4 Sec Hold
	public static final Control kOpenHookHolder = Control.Y;
	
	//Squeezy Manual
	public static final Control kSqueezyUp = Control.N;		//Up    on D-Pad
	public static final Control kSqueezyDown = Control.S;	//Down  on D-Pad
	public static final Control kSqueezyOpen = Control.W;	//Left  on D-Pad
	public static final Control kSqueezyClose = Control.E;	//Right on D-Pad
	
	//Squeezy Automatic
	public static final Control kSqueezyEject = Control.LB;	//Eject Mode
	public static final Control kSqueezyNeutral = Control.B;//Neutral Mode (Stop Wheels from Spinning)
	public static final Control kSqueezyIntake = Control.X;	//Intake Mode (Spin Wheels and Move In)
	public static final Control kSqueezyKnock = Control.LT; //?

}
