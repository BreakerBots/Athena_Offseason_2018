package frc.team5104.subsystem;

import frc.team5104.main.BreakerRobotController.RobotMode;

/*Breakerbots Robotics Team 2018*/
/**
 * Manages the updating and handling of all BreakerSubsystems thrown into it
 */
public class BreakerSubsystemManager {
	private static BreakerSubsystem.Manager[] targets;
	
	/**
	 * NECESSARY: Tell the Subsystem Manager what Subsystems to manage
	 */
	public static void throwSubsystems(BreakerSubsystem.Manager... subsystems) {
		targets = subsystems;
	}
	
	/**
	 * CALL when the robot becomes enabled
	 * @param teleop
	 */
	public static void enabled(RobotMode mode) {
		for (BreakerSubsystem.Manager t : targets) {
			t.enabled(mode);
		}
	}
	
	/**
	 * CALL periodically when the robot is enabled
	 */
	public static void update() {
		for (BreakerSubsystem.Manager t : targets) {
			t.update();
		}
	}
	
	/**
	 * CALL when the robot becomes disabled
	 */
	public static void disabled() {
		for (BreakerSubsystem.Manager t : targets) {
			t.disabled();
		}
	}
}
