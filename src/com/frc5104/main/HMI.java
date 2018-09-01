package com.frc5104.main;

import com.frc5104.utilities.ControllerHandler.Control;

/*Breakerbots Robotics Team 2018*/
public class HMI {

	//Recording
	public static final Control kStartRecording = Control.MENU;
	public static final Control kStopRecording = Control.MENU;
	public static final Control kPlayback = Control.LIST;
	
	//Drive
	public static final Control kDriveX = Control.LX;
	public static final Control kDriveY = Control.LY;
	public static final Control kDriveShift = Control.RT;
	
	//Elevator
	public static final Control kPtoHoldAndHookPressButton = Control.Y; //hold for 0.4 sec
	public static final Control kElevatorUpDown = Control.RY;
	public static final Control kOpenHookHolder = /*Control.A -- Changed To Y*/ Control.Y;
	
	//Squeezy
	public static final Control kSqueezyUp = Control.N;
	public static final Control kSqueezyDown = Control.S;
	
	public static final Control kSqueezyOpen = Control.W;
	public static final Control kSqueezyClose = Control.E;
	
	public static final Control kSqueezyEject = Control.LB;
	public static final Control kSqueezyNeutral = Control.B;
	public static final Control kSqueezyIntake = Control.X;
	public static final Control kSqueezyKnock = Control.LT;
	
	public static final Control kElevatorDown = Control.A;
	public static final Control kElevatorUp = Control.RB;
}
