package frc.team5104.auto;

/*Breakerbots Robotics Team 2018*/
/**
 * A Single BreakerCommand (ind. peices of a path)
 */
abstract public class BreakerPathAction {
	/**
	 * Called when command is started to be run
	 */
	abstract public void init();
	
	/**
	 * Called periodically when the command is being run
	 * @return If the command is finished
	 */
	abstract public boolean update();
	
	/**
	 * Called when the command is finished being run
	 */
	abstract public void end();
}
