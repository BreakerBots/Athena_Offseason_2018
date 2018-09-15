package com.frc5104.autocommands;

import com.frc5104.main.subsystems.Squeezy;
import com.frc5104.main.subsystems.Squeezy.SqueezyState;
import com.frc5104.utilities.console;
import com.frc5104.utilities.console.c;

/*Breakerbots Robotics Team 2018*/
public class EjectSqueezy extends BreakerCommand {

	Squeezy squeezy;
	double ejectEffort;
	double previousEffort;
	
    public EjectSqueezy(double squeezyEffort) {
    	squeezy = Squeezy.getInstance();
    	ejectEffort = squeezyEffort;
    }

    public void init() {
    	console.log(c.AUTO, "Ejecting");
    	squeezy.forceState(Squeezy.SqueezyState.EJECT);
    	
    	previousEffort = Squeezy.kEjectEffort;
    	Squeezy.kEjectEffort = ejectEffort;
    }

    public boolean update() {
    	return  squeezy.isInState(SqueezyState.EMPTY) ||
    			squeezy.isInState(SqueezyState.UNJAM) ||
    			squeezy.isInState(SqueezyState.INTAKE);
    }

    public void end() {
    	squeezy.forceState(SqueezyState.EMPTY);
    	Squeezy.kEjectEffort = previousEffort;
    }
}
