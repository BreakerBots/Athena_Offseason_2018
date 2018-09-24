package com.frc5104.autocommands;

/*Breakerbots Robotics Team 2018*/
/**
 * Handles the Execution of BreakerCommands inside the assigned BreakerCommandGroup (Entire Path)
 */
public class BreakerPathScheduler {
	static BreakerPathScheduler m_instance = null;
	public static BreakerPathScheduler getInstance() { if (m_instance == null) { m_instance = new BreakerPathScheduler(); } return m_instance; }
	
	public BreakerPath r = null;
	public int cl = 0;
	public int i = 0;
	public boolean s = false;
	
	/**
	 * Set the target command group
	 */
	public void set(BreakerPath path) {
		//Save the new Command Group
		r = null;
		r = path;
		cl = path.cl;
		
		//Reset Command Group Filter Index
		i = 0;
		
		//Say that the first command hasn't been Initiated
		s = false;
	}
	
	/**
	 * The update function call in Autonomous Periodic
	 */
	public void update() {
		//if the command index is less than the commandGroup length
		if (i < r.cl) {
			//If command has not been initialized
			if (!s) {
				//Call the init function
				r.cs[i].init();
				
				//Dont call it next time
				s = true;
			}
			
			//Call the update function (then if finished)
			if (r.cs[i].update()) {
				//Call the end init function
				r.cs[i].end();
				
				//Go to the next command
				i++;
				
				//Say the command hasn't been initialized
				s = false;
			}
		}
	}
}
