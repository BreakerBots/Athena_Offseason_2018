package com.frc5104.main;

import com.frc5104.autocommands.BreakerCommandScheduler;
import com.frc5104.autopaths.AutoSelector;
import com.frc5104.main.subsystems.*;

/*Breakerbots Robotics Team 2018*/
public class Robot extends BreakerRobot {
	public void _robotInit() {
		//Subsystems
		BreakerSubsystemManager.throwSubsystems(new BreakerSubsystem[] {
			Drive.getInstance(), 
			Squeezy.getInstance(), 
			Elevator.getInstance()
		});
	}
	
	public void _autonomousInit() {
		//Autonomous Picker
		BreakerCommandScheduler.getInstance().set(
			/*AutoSelector.getAuto()*/
			AutoSelector.Paths.Baseline.getPath()
		);
	}
	
	public void _testInit() {}
	public void _testPeriodic() {}
}