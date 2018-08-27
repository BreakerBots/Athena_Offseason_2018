package com.frc5104.autocommands;

import edu.wpi.first.wpilibj.command.Command;

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
    	System.out.println("AUTO: Delay");
    }

    public boolean isFinished() {
    	return (System.currentTimeMillis() >= startTime + delay);
    }
    
    public void end() {
    	
    }
}
