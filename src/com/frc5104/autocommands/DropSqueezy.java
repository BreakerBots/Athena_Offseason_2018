package com.frc5104.autocommands;

import com.frc5104.utilities.console;
import com.frc5104.utilities.console.Type;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

/*Breakerbots Robotics Team 2018*/
public class DropSqueezy implements BreakerCommand {

	DoubleSolenoid squeezy;
	long start;
	
    public DropSqueezy(DoubleSolenoid squeezySol) {
    	squeezy = squeezySol;
    }

    public void initialize() {
    	console.log("Dropping Squeezy", Type.AUTO);
    	squeezy.set(Value.kForward);
    	start = System.currentTimeMillis();
    }

    public void execute() {
    
    }

    public boolean isFinished() {
    	return System.currentTimeMillis()-start>1000;
    }

    public void end() {
   
    }
}
