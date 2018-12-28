package frc.team5104.util;

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
	private static Joystick controller = new Joystick(2);

	//Normal Buttons (Control, Slots, and Type all line up in indexes)
	public static enum Control { 
		/*Button*/	A(1, 1),	B(2, 1),	X(3, 1),	Y(4, 1),	LB(5, 1),	RB(6, 1),	MENU(7, 1),	LIST(8, 1),	LJ(9, 1),	RJ(10, 1),
		/*Dpad*/	N(0, 3),	NE(45, 3),	E(90, 3),	SE(135, 3),	S(180, 3),	SW(225, 3), W(270, 3),	NW(315, 3),
		/*Axis*/	LX(0, 2),	LY(1, 2),	LT(2, 2),	RT(3, 2),	RX(4, 2),	RY(5, 2);					 
		
		protected int slot, type;
		protected double deadzone;
		protected boolean val, lastVal, pressed, released;
		protected long time;
		Control(int slot, int type) {
			this(slot, type, 0.6);
		}
		Control(int slot, int type, double deadzone) {
			this.slot = slot;
			this.type = type;
			deadzone = 0.6;
		}
		
		//Control Functions
		/**Returns the percent of the axis, Just The Default Axis*/
		public double getAxis() { return controller.getRawAxis(slot); }
		/**Returns true if button is down, Just The Default Button State*/
		public boolean getHeld() { return val; }
		/**Returns how long the button has been held down for, if not held down returns 0*/
		public double getHeldTime() { return val ? ((double)(System.currentTimeMillis() - time))/1000 : 0; }
		/**Returns true for one tick if button goes from up to down*/
		public boolean getPressed() { return pressed; }
		/**Returns true for one tick if button goes from down to up*/
		public boolean getReleased() { return released; }
		/**Returns the time the click lasted for, for one tick when button goes from down to up*/
		public double getClickTime() { return released ? ((double)(System.currentTimeMillis() - time))/1000 : 0; }
		/**Returns true for one tick if the button has been held for the specified time*/
		public boolean getHeldEvent(double time) { return Math.abs(getHeldTime() - time) <= 0.01; }
	}
		
	//Rumble
	private static long hardTarget; private static boolean hardTimer = false;
	private static long softTarget; private static boolean softTimer = false;
	
	public static void update() {
		//Normal Buttons
		for (Control c : Control.values()) {
			c.pressed = false;
			c.released = false;
			
			switch (c.type) {
				//Button	
				case 1:
					c.val = controller.getRawButton(c.slot);
					break;
				//Axis
				case 2:
					c.val = controller.getRawAxis(c.slot) <= c.deadzone ? false : true;
					break;
				//D-pad
				case 3:
					c.val = controller.getPOV() == c.slot;
					break;
			}
			
			if (c.val != c.lastVal) {
				c.lastVal = c.val;
				if (c.val == true) { 
					c.pressed = true; 
					c.time = System.currentTimeMillis(); 
				}
				else 
					c.released = true;
			}
		}
		
		//Rumble
		if (hardTimer) { if (hardTarget <= System.currentTimeMillis()) { controller.setRumble(RumbleType.kLeftRumble, 0); hardTimer = false; } }
		if (softTimer) { if (softTarget <= System.currentTimeMillis()) { controller.setRumble(RumbleType.kRightRumble, 0); softTimer = false; } }
	}
	
	//Control Functions
	/**Returns the percent of the axis, Just The Default Axis*/
	public static double getAxis(Control control) { return control.getAxis(); }
	/**Returns true if button is down, Just The Default Button State*/
	public static boolean getHeld(Control control) { return control.getHeld(); }
	/**Returns how long the button has been held down for, if not held down returns 0*/
	public static double getHeldTime(Control control) { return control.getHeldTime(); }
	/**Returns true for one tick if button goes from up to down*/
	public static boolean getPressed(Control control) { return control.getPressed(); }
	/**Returns true for one tick if button goes from down to up*/
	public static boolean getReleased(Control control) { return control.getReleased(); }
	/**Returns the time the click lasted for, for one tick when button goes from down to up*/
	public static double getClickTime(Control control) { return control.getClickTime(); }
	/**Returns true for one tick if the button has been held for the specified time*/
	public static boolean getHeldEvent(Control control, double time) { return control.getHeldEvent(time); }
	
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
