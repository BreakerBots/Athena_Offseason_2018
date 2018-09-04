package com.frc5104.autocommands;

import com.frc5104.utilities.console;
import com.frc5104.utilities.console.Type;

/*Breakerbots Robotics Team 2018*/
public class Delay extends BreakerCommand {
	long startTime;
	int delay;

    public Delay(int milliseconds) {
        delay = milliseconds;
    }

    public void init() {
    	startTime = System.currentTimeMillis();
    }

    public boolean update() {
    	console.log("Delaying", Type.AUTO);
    	
    	return (System.currentTimeMillis() >= startTime + delay);
    }

    public void end() {
    	
    }
}
