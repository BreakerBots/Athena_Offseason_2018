package com.frc5104.autocommands;

import com.frc5104.main.subsystems.Drive;
import com.frc5104.utilities.console;
import com.frc5104.utilities.console.Type;

/*Breakerbots Robotics Team 2018*/
public class StopDrive implements BreakerCommand {

    public StopDrive() {
    	
    }

    public void initialize() {
    	console.log("Stopping Drive", Type.AUTO);
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
