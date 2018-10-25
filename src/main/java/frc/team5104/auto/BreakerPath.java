package frc.team5104.auto;

/*Breakerbots Robotics Team 2018*/
/**
 * A Collection of BreakerCommands (The Entire Path)
 * Ran through the BreakerCommandScheduler
 */
public abstract class BreakerPath {
	
	/**
	 * The Actions for the path
	 */
	public BreakerPathAction[] cs = new BreakerPathAction[10];
	
	/**
	 * The number of Actions in the path
	 */
	public int cl = 0;
	
	/**
	 * Add an action to the Path
	 */
	public void add(BreakerPathAction action) {
		cs[cl] = action;
		cl++;
	}
}
