package frc.team5104.autocommands;

import frc.team5104.util.console;
import frc.team5104.util.console.c;

/*Breakerbots Robotics Team 2018*/
public class Delay extends BreakerAction {
	long startTime;
	int delay;

    public Delay(int milliseconds) {
        delay = milliseconds;
    }

    public void init() {
    	console.log(c.AUTO, "Delaying + " + delay + "ms");
    	startTime = System.currentTimeMillis();
    }

    public boolean update() {
    	return (System.currentTimeMillis() >= startTime + delay);
    }

    public void end() {
    	
    }
}
