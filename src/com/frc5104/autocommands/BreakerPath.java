package com.frc5104.autocommands;

/*Breakerbots Robotics Team 2018*/
/**
 * A Collection of BreakerCommands (The Entire Path)
 * Ran through the BreakerCommandScheduler
 */
public abstract class BreakerPath {
	
	/**
	 * The Actions for the path
	 */
	public BreakerAction[] cs = new BreakerAction[10];
	
	/**
	 * The number of Actions in the path
	 */
	public int cl = 0;
	
	/**
	 * Add an action to the Path
	 */
	public void add(BreakerAction action) {
		cs[cl] = action;
		cl++;
	}
}
