package com.frc5104.autocommands;

import com.frc5104.main.subsystems.Drive;
import com.frc5104.utilities.console;
import com.frc5104.utilities.console.Type;

/*Breakerbots Robotics Team 2018*/
public class StopDrive extends BreakerCommand {

    public StopDrive() {
    	
    }

    public void init() {
    	console.log("Stopping Drive", Type.AUTO);
    }

    public boolean update() {
    	Drive.getInstance().set(0, 0);
    	
    	return true;
    }

    public void end() {
    	
    }
}
