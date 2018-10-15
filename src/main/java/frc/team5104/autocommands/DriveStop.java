package frc.team5104.autocommands;

import frc.team5104.main.subsystems.Drive;
import frc.team5104.util.console;
import frc.team5104.util.console.c;

/*Breakerbots Robotics Team 2018*/
public class DriveStop extends BreakerAction {

    public void init() {
    	console.log(c.DRIVE, "Stopping Drive");
    }

    public boolean update() {
    	Drive.stop();
    	
    	return true;
    }

    public void end() {
    	
    }
}
