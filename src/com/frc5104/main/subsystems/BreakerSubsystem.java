package com.frc5104.main.subsystems;

/*Breakerbots Robotics Team 2018*/
/**
 * A class that can be thrown into the BreakerSubsystemManager
 */
public abstract class BreakerSubsystem {
	/**
	 * CALLED when the robot code is ran
	 */
	protected abstract void init();
	
	/**
	 * CALLED at the start of teleop
	 */
	protected abstract void teleopInit();
	
	/**
	 * CALLED at ~50hz during teleop
	 */
	protected abstract void teleopUpdate();
	
	/**
	 * CALLED at the start of autonomous
	 */
	protected abstract void autoInit();
	
	/**
	 * CALLED at ~50hz during autonomous
	 */
	protected abstract void autoUpdate();
	
	/**
	 * CALLED at ~50hz while the robot is powered
	 */
	protected abstract void idleUpdate();
	
	/**
	 * CALLED when the robot code is ran TO init the subsystems network table
	 */
	protected abstract void initNetworkPosting();
	
	/**
	 * CALLED at ~50hz while the robot is powered TO update the subsystems network table
	 */
	protected abstract void postToNetwork();
	
	/**
	 * CALLED to debug (print) the subsystem
	 */
	public static void debug() {};
	
	/**
	 * CALLED when the robot is disabled
	 */
	protected abstract void robotDisabled();
}
