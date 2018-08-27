package com.frc5104.autocommands;

import com.frc5104.main.subsystems.Squeezy;
import com.frc5104.main.subsystems.Squeezy.SqueezyState;

/*Breakerbots Robotics Team 2018*/
public class EjectSqueezy implements BreakerCommand {

	Squeezy squeezy;
	double ejectEffort;
	double previousEffort;
	
    public EjectSqueezy(double squeezyEffort) {
    	squeezy = Squeezy.getInstance();
    	ejectEffort = squeezyEffort;
    }

    public void initialize() {
    	squeezy.forceState(Squeezy.SqueezyState.EJECT);
    	
    	previousEffort = Squeezy.kEjectEffort;
    	Squeezy.kEjectEffort = ejectEffort;
    }

    public void execute() {
    	squeezy.update();
    	squeezy.updateState();
    }

    public boolean isFinished() {
        return squeezy.isInState(SqueezyState.EMPTY) ||
        		squeezy.isInState(SqueezyState.UNJAM) ||
        		squeezy.isInState(SqueezyState.INTAKE);
    }

    public void end() {
    	squeezy.forceState(SqueezyState.EMPTY);
    	Squeezy.kEjectEffort = previousEffort;
    }
}
