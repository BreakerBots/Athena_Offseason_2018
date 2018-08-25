package com.frc5104.autocommands;

import edu.wpi.first.wpilibj.command.Command;

/*Breakerbots Robotics Team 2018*/
public class Delay extends Command {
	long startTime;
	int delay;

    public Delay(int milliseconds) {
        delay = milliseconds;
    }

    protected void initialize() {
    	startTime = System.currentTimeMillis();
    }

    protected void execute() {
    	System.out.println("AUTO: Delay");
    }

    protected boolean isFinished() {
    	return (System.currentTimeMillis() >= startTime + delay);
    }

    protected void end() {
    
    }

    protected void interrupted() {
    
    }
}
