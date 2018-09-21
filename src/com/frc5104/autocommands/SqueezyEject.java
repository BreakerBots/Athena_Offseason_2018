package com.frc5104.autocommands;

import com.frc5104.main.subsystems.Squeezy;
import com.frc5104.main.subsystems.Squeezy.SqueezyState;
import com.frc5104.utilities.console;
import com.frc5104.utilities.console.c;

/*Breakerbots Robotics Team 2018*/
public class SqueezyEject extends BreakerCommand {

    public void init() {
    	console.log(c.SQUEEZY, "Ejecting");
    	Squeezy.actions.eject();
    }

    public boolean update() {
    	return Squeezy.getState() == Squeezy.SqueezyState.idle;
    }

    public void end() {
    	
    }
}
