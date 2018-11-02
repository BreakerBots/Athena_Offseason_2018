package frc.team5104.auto;

/*Breakerbots Robotics Team 2018*/
/**
 * A Single BreakerCommand (ind. peices of a path)
 */
public abstract class BreakerPathAction {
	/**
	 * Called when command is started to be run
	 */
	public abstract void init();
	
	/**
	 * Called periodically when the command is being run
	 * @return If the command is finished
	 */
	public abstract boolean update();
	
	/**
	 * Called when the command is finished being run
	 */
	public abstract void end();
}
