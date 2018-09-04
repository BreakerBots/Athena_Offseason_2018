package com.frc5104.main.subsystems;

/*Breakerbots Robotics Team 2018*/
public class BreakerSubsystemManager {
	private static BreakerSubsystem[] targets;
	public static void throwSubsystems(BreakerSubsystem[] subsystems) {
		targets = subsystems;
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
}
