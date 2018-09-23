package com.frc5104.main;

import com.frc5104.autocommands.BreakerCommandScheduler;
import com.frc5104.autopaths.AutoSelector;
import com.frc5104.main.subsystems.*;

/*Breakerbots Robotics Team 2018*/
public class Robot extends BreakerRobot {
	public void _robotInit() {
		BreakerSubsystemManager.throwSubsystems(new BreakerSubsystem[] {
			Drive.getInstance(), 
			Squeezy.getInstance(), 
			Elevator.getInstance()
		});
		
		//CameraServer.getInstance().startAutomaticCapture();
	}
	
	public void _autonomousInit() {
		BreakerCommandScheduler.getInstance().set(
			/*AutoSelector.getAuto()*/
			AutoSelector.Paths.LR.getPath()
		);
	}
	
	public void _teleopInit() { }
	
	public void _testInit() { }
	public void _testPeriodic() { }
}