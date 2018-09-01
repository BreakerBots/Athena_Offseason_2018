package com.frc5104.autocommands;

import com.frc5104.utilities.console;
import com.frc5104.utilities.console.Type;

/*Breakerbots Robotics Team 2018*/
public class Delay implements BreakerCommand {
	long startTime;
	int delay;

    public Delay(int milliseconds) {
        delay = milliseconds;
    }

    public void initialize() {
    	startTime = System.currentTimeMillis();
    }

    public void execute() {
    	console.log("Delaying", Type.AUTO);
    }

    public boolean isFinished() {
    	return (System.currentTimeMillis() >= startTime + delay);
    }
    
    public void end() {
    	
    }
}
