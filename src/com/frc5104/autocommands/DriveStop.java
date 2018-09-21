package com.frc5104.autocommands;

import com.frc5104.main.subsystems.Drive;
import com.frc5104.utilities.console;
import com.frc5104.utilities.console.c;

/*Breakerbots Robotics Team 2018*/
public class DriveStop extends BreakerCommand {

    public void init() {
    	console.log(c.DRIVE, "Stopping Drive");
    }

    public boolean update() {
    	Drive.getInstance().set(0, 0);
    	
    	return true;
    }

    public void end() {
    	
    }
}
