package com.frc5104.utilities;

import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;

/*Breakerbots Robotics Team 2018*/
/**
 * <h1>Controller</h1>
 * A much simpler to use version of WPI's Joystick (controller) class
 * There is also a bunch of added functional for rumble and click events
 * Every single control in the Control Enum is treated as a Button and Axis
 * Features:
 * 	Rumble (Soft, Hard)
 *  Rumble for Duration (Soft, Hard)
 *	Get Held Time (time button has been held for)
 *	Get Axis Value
 *	Get Button Down
 *	Button Pressed/Released Event (Events are true for one tick (and then false) when triggered)
 */
public class controller {
	private static Joystick controller = new Joystick(0);

	//Normal Buttons (Control, Slots, and Type all line up in indexes)
	public static enum Control { 
		A, B, X, Y, LB, RB , MENU, LIST, LJ, RJ, /*Button*/
		N, NE, E , SE , S  , SW , W  , NW ,		 /*Dpad*/
		LX, LY, LT, RT, RX, RY					 /*Axis*/
	}
	private static final int[] Slots = {
		1, 2, 3, 4, 5 , 6 , 7    , 8   , 9 , 10, /*Button*/ 
		0, 45, 90, 135, 180, 225, 270, 315,		 /*Dpad*/
		0 , 1 , 2 , 3 , 4 , 5					 /*Axis*/
	}; 
	private static int[]     Type = {
		1, 1, 1, 1, 1 , 1 , 1    , 1   , 1 , 1 , /*Button*/
		3, 3 , 3 , 3  , 3  , 3  , 3  , 3  ,		 /*Dpad*/
		2 , 2 , 2 , 2 , 2 , 2					 /*Axis*/
	};
	//Deadzones are for converting axises to buttons
	private static double[]  Deadzones = { 0.6,0.6,0.6,0.6,0.6,0.6,0.6,0.6,0.6,0.6,0.6,0.6,0.6,0.6,0.6,0.6,0.6,0.6,0.6,0.6,0.6,0.6,0.6,0.6 };
	private static boolean[] Val = new boolean[Slots.length];
	private static boolean[] LastVal = new boolean[Slots.length];
	private static boolean[] Pressed = new boolean[Slots.length];
	private static boolean[] Released = new boolean[Slots.length];
	private static    long[] Time = new long[Slots.length];
		
	//Rumble
	private static long hardTarget; private static boolean hardTimer = false;
	private static long softTarget; private static boolean softTimer = false;
	
	public static void update() {
		//A single binary number representing the on/off state of each button
		int buttons = DriverStation.getInstance().getStickButtons(0);
		
		//Normal Buttons
		for (int i = 0; i < Slots.length; i++) {
			Pressed[i] = false;
			Released[i] = false;
			
			Val[i] = Type[i] == 1 ? ((buttons & 1 << (Slots[i]-1)) != 0) : (Type[i] == 2 ? (controller.getRawAxis(Slots[i]) <= Deadzones[i] ? false : true) : (controller.getPOV() == Slots[i]));
			
			if (Val[i] != LastVal[i]) {
				LastVal[i] = Val[i];
				if (Val[i] == true) { Pressed[i] = true; Time[i] = System.currentTimeMillis(); }
				else { Released[i] = true; }
			}
		}
		
		//Rumble
		if (hardTimer) { if (hardTarget <= System.currentTimeMillis()) { controller.setRumble(RumbleType.kLeftRumble, 0); hardTimer = false; } }
		if (softTimer) { if (softTarget <= System.currentTimeMillis()) { controller.setRumble(RumbleType.kRightRumble, 0); softTimer = false; } }
	}
	
	//Control Functions
	/**Returns the percent of the axis, Just The Default Axis*/
	public static double getAxis(Control control) { return controller.getRawAxis(Slots[control.ordinal()]); }
	/**Returns true if button is down, Just The Default Button State*/
	public static boolean getHeld(Control control) { return Val[control.ordinal()]; }
	/**Returns how long the button has been held down for, if not held down returns 0*/
	public static double getHeldTime(Control control) { return Val[control.ordinal()] ? ((double)(System.currentTimeMillis() - Time[control.ordinal()]))/1000 : 0; }
	/**Returns true for one tick if button goes from up to down*/
	public static boolean getPressed(Control control) { return Pressed[control.ordinal()]; }
	/**Returns true for one tick if button goes from down to up*/
	public static boolean getReleased(Control control) { return Released[control.ordinal()]; }
	/** Sets the deadzone[ the desired point in which the axis is considered pressed ] for the desired axis. */
	public static void setDeadzone(Control control, double deadzonePercent) { Deadzones[control.ordinal()] = deadzonePercent; }
	/**Returns the time the click lasted for, for one tick when button goes from down to up*/
	public static double getClickTime(Control control) { return Released[control.ordinal()] ? ((double)(System.currentTimeMillis() - Time[control.ordinal()]))/1000 : 0; }
	/**Returns true for one tick if the button has been held for the specified time*/
	public static boolean getHeldEvent(Control control, double time) { return Math.abs(getHeldTime(control) - time) <= 0.01; }

	//Rumble
	/** Rumbles the controller hard[ hard rumble is a deep rumble and soft rumble is lighter rumble ] at (strength) until set again */
	public static void rumbleHard(double strength) { controller.setRumble(RumbleType.kLeftRumble, strength); hardTimer = false; }
	/** Rumbles the controller soft[ hard rumble is a deep rumble and soft rumble is lighter rumble ] at (strength) until set again */
	public static void rumbleSoft(double strength) { controller.setRumble(RumbleType.kRightRumble, strength); softTimer = false; }
	/** Rumbles the controller hard[ hard rumble is a deep rumble and soft rumble is lighter rumble ] at (strength) for (seconds) */
	public static void rumbleHardFor(double strength, double seconds) { controller.setRumble(RumbleType.kLeftRumble, strength);  hardTarget = (System.currentTimeMillis() + ((long) (seconds*1000))); hardTimer = true; }
	/** Rumbles the controller soft[ hard rumble is a deep rumble and soft rumble is lighter rumble ] at (strength) for (seconds) */
	public static void rumbleSoftFor(double strength, double seconds) { controller.setRumble(RumbleType.kRightRumble, strength); softTarget = (System.currentTimeMillis() + ((long) (seconds*1000))); softTimer = true; }
}
