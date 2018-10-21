package frc.team5104.main.subsystems;

import frc.team5104.util.console;

/*Breakerbots Robotics Team 2018*/
/**
 * Manages the updating and handling of all BreakerSubsystems thrown into it
 * @author Liam Snow
 *
 */
public class BreakerSubsystemManager {
	private static BreakerSubsystem[] targets;
	/**
	 * NECESSARY: Tell the Subsystem Manager what Subsystems to manage
	 */
	@SafeVarargs
	public static void throwSubsystems(Class<? extends BreakerSubsystem>... subsystems) {
		try {
			//Convert Subsystem Classes into Instances
			targets = new BreakerSubsystem[subsystems.length];
			for (int i = 0; i < subsystems.length; i++) {
				targets[i] = subsystems[i].newInstance();
			}
		} catch (Exception e) {
			console.error("Failed to Cast Subsystem Classes into Instances", e);
			e.printStackTrace();
		}
	}
	
	/**
	 * CALLED when the robot code is ran
	 */
	public static void init() {
		for (BreakerSubsystem t : targets) {
			t.init();
		}
	}
	
	/**
	 * CALLED at the start of teleop
	 */
	public static void teleopInit() {
		for (BreakerSubsystem t : targets) {
			t.teleopInit();
		}
	}
	
	/**
	 * CALLED at ~50hz during teleop
	 */
	public static void teleopUpdate() {
		for (BreakerSubsystem t : targets) {
			t.teleopUpdate();
		}
	}
	
	/**
	 * CALLED at the start of autonomous
	 */
	public static void autoInit() {
		for (BreakerSubsystem t : targets) {
			t.autoInit();
		}
	}
	
	/**
	 * CALLED at ~50hz during autonomous
	 */
	public static void autoUpdate() {
		for (BreakerSubsystem t : targets) {
			t.autoUpdate();
		}
	}
	
	/**
	 * CALLED at ~50hz while the robot is powered
	 */
	public static void idleUpdate() {
		for (BreakerSubsystem t : targets) {
			t.idleUpdate();
		}
	}
	
	public static void disabled() {
		for (BreakerSubsystem t : targets) {
			t.robotDisabled();
		}
	}
}
