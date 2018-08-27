package com.frc5104.autocommands;

import com.frc5104.main.subsystems.Drive;

import edu.wpi.first.wpilibj.command.Command;

/*Breakerbots Robotics Team 2018*/
public class StopDrive implements BreakerCommand {

    public StopDrive() {
    	
    }

    public void initialize() {
    	System.out.println("AUTO: Stopping Drive");
    }

    public void execute() {
    	Drive.getInstance().set(0, 0);
    }

    public boolean isFinished() {
    	return false;
    }
    
    public void end() {
    	
    }
}
