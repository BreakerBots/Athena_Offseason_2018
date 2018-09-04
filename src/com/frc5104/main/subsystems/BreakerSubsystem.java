package com.frc5104.main.subsystems;

/*Breakerbots Robotics Team 2018*/
public interface BreakerSubsystem {
	
	
	/**
	 * CALLED when the robot code is ran
	 */
	public void init();
	
	/**
	 * CALLED at the start of teleop
	 */
	public void teleopInit();
	
	/**
	 * CALLED at ~50hz during teleop
	 */
	public void teleopUpdate();
	
	/**
	 * CALLED at the start of autonomous
	 */
	public void autoInit();
	
	/**
	 * CALLED at ~50hz during autonomous
	 */
	public void autoUpdate();
	
	/**
	 * CALLED at ~50hz while the robot is powered
	 */
	public void idleUpdate();
	
	/**
	 * CALLED when the robot code is ran TO init the subsystems network table
	 */
	public void initNetworkPosting();
	
	/**
	 * CALLED at ~50hz while the robot is powered TO update the subsystems network table
	 */
	public void postToNetwork();
}
