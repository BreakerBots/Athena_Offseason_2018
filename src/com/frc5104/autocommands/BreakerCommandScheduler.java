package com.frc5104.autocommands;

import com.frc5104.main.subsystems.Climber;

public class BreakerCommandScheduler {
	static BreakerCommandScheduler m_instance = null;
	public static BreakerCommandScheduler getInstance() {
		double curTime = (double)(System.currentTimeMillis());
		if (m_instance == null) {
			m_instance = new BreakerCommandScheduler();
		}
		return m_instance;
	}
	
	public BreakerCommandGroup r = null;
	public int i = 0;
	public boolean s = false;
	
	public void set(BreakerCommandGroup commandGroup) {
		double curTime = (double)(System.currentTimeMillis());
		r = commandGroup;
		i = 0;
		r.init();
		System.out.println("Finished set: " + ((double)(System.currentTimeMillis()) - curTime));
	}
	
	public void update() {
		if (i < r.cl) {
			if (!s) {
				r.cs[i].initialize();
				s = true;
			}
			r.cs[i].execute();
			if (r.cs[i].isFinished()) {
				i++;
				s = false;
			}
		}
	}
}
