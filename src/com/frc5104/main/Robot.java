package com.frc5104.main;

import com.frc5104.autocommands.BreakerPathScheduler;
import com.frc5104.autopaths.AutoSelector;
import com.frc5104.main.subsystems.*;
import edu.wpi.first.wpilibj.CameraServer;

/*Breakerbots Robotics Team 2018*/
public class Robot extends BreakerRobot {
	public void _robotInit() {
		BreakerSubsystemManager.throwSubsystems(
			Squeezy.class,
			Elevator.class,
			Drive.class
		);
		
		CameraServer.getInstance().startAutomaticCapture();
	}
	
	public void _robotPeriodic() {
//		console.log("L:" + Drive.encoders.getLeft(), "R: " + Drive.encoders.getRight());
	}
	
	public void _autonomousInit() {
		BreakerPathScheduler.getInstance().set(
			//AutoSelector.getAuto()
			AutoSelector.Paths.LR.getPath()
		);
	}
	
	public void _teleopInit() { 
		
	}
	
	public void _testInit() {
		
	}
	
	public void _testPeriodic() { 
		
	}
}