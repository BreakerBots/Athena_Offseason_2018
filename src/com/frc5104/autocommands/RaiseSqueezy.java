package com.frc5104.autocommands;

import com.frc5104.utilities.console;
import com.frc5104.utilities.console.Type;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

/*Breakerbots Robotics Team 2018*/
public class RaiseSqueezy extends BreakerCommand {

	DoubleSolenoid squeezy;
	long start;
	
    public RaiseSqueezy(DoubleSolenoid squeezySol) {
    	squeezy = squeezySol;
    }

    public void init() {
    	console.log("Raising Squeezy", Type.AUTO);
    	squeezy.set(Value.kReverse);
    	start = System.currentTimeMillis();
    }

    public boolean update() {
    	return System.currentTimeMillis()-start>1000;
    }

    public void end() {
    	
    }
}
