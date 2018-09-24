package com.frc5104.autocommands;

import com.frc5104.main.subsystems.Squeezy;
import com.frc5104.main.subsystems.Squeezy.SqueezyEjectSpeed;
import com.frc5104.utilities.console;
import com.frc5104.utilities.console.c;

/*Breakerbots Robotics Team 2018*/
public class SqueezyEject extends BreakerAction {

	SqueezyEjectSpeed speed;
	
	public SqueezyEject() {
		this.speed = SqueezyEjectSpeed.High;
	}
	
	public SqueezyEject(SqueezyEjectSpeed speed) {
		this.speed = speed;
	}
	
    public void init() {
    	console.log(c.SQUEEZY, "Ejecting");
    	Squeezy.actions.eject(this.speed);
    }

    public boolean update() {
    	return Squeezy.getState() == Squeezy.SqueezyState.idle;
    }

    public void end() {
    	
    }
}
