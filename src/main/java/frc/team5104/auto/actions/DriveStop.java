package frc.team5104.auto.actions;

import frc.team5104.auto.BreakerPathAction;
import frc.team5104.subsystem.drive.DriveActions;
import frc.team5104.util.console;
import frc.team5104.util.console.c;

/*Breakerbots Robotics Team 2018*/
public class DriveStop extends BreakerPathAction {

    public void init() {
    	console.log(c.DRIVE, "Stopping Drive");
    }

    public boolean update() {
    	DriveActions.stop();
    	
    	return true;
    }

    public void end() {
    	
    }
}
