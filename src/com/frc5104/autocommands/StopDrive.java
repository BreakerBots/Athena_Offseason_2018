package com.frc5104.autocommands;

import com.frc5104.main.subsystems.Drive;

import edu.wpi.first.wpilibj.command.Command;

/*Breakerbots Robotics Team 2018*/
public class StopDrive extends Command {

    public StopDrive() {
    	
    }

    protected void initialize() {
    	System.out.println("AUTO: Stopping Drive");
    }

    protected void execute() {
    	Drive.getInstance().set(0, 0);
    }

    protected boolean isFinished() {
    	return false;
    }

    protected void end() {

    }

    protected void interrupted() {

    }
}
