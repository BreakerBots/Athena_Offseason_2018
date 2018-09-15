package com.frc5104.autocommands;

/*Breakerbots Robotics Team 2018*/
/**
 * Handles the Execution of BreakerCommands inside the assigned BreakerCommandGroup (Entire Path)
 */
public class BreakerCommandScheduler {
	static BreakerCommandScheduler m_instance = null;
	public static BreakerCommandScheduler getInstance() {
		if (m_instance == null) {
			m_instance = new BreakerCommandScheduler();
		}
		return m_instance;
	}
	
	public BreakerCommandGroup r = null;
	public int i = 0;
	public boolean s = false;
	
	public void set(BreakerCommandGroup commandGroup) {
		//Save the new Command Group
		r = commandGroup;
		
		//Reset Command Group Filter Index
		i = 0;
		
		//Make the Command Init Commands and Add To Array
		r.init();
		
		//Say that the first command hasn't been Initiated
		s = false;
	}
	
	public void update() {
		if (i < r.cl) {
			if (!s) {
				r.cs[i].init();
				s = true;
			}
			if (r.cs[i].update()) {
				r.cs[i].end();
				i++;
				s = false;
			}
		}
	}
}
