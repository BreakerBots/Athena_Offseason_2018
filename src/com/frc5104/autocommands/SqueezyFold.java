package com.frc5104.autocommands;

import com.frc5104.main.subsystems.Squeezy;
import com.frc5104.utilities.console;
import com.frc5104.utilities.console.c;

/*Breakerbots Robotics Team 2018*/
public class SqueezyFold extends BreakerAction {

	private boolean up;
	
	public SqueezyFold(boolean up) {
		this.up = up;
	}
	
    public void init() {
    	console.log(c.SQUEEZY, up ? "Folding Up" : "Folding Down");
    	Squeezy.actions.foldSet(up);
    }

    public boolean update() {
    	return true;
    }

    public void end() {
    	
    }
}
