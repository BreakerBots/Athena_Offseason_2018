package com.frc5104.autocommands;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;

/*Breakerbots Robotics Team 2018*/
public class DropSqueezy implements BreakerCommand {

	DoubleSolenoid squeezy;
	long start;
	
    public DropSqueezy(DoubleSolenoid squeezySol) {
    	squeezy = squeezySol;
    }

    public void initialize() {
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
